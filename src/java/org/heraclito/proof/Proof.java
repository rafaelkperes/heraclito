/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.heraclito.parser.header.HeaderLexer;
import org.heraclito.parser.header.HeaderParser;
import org.heraclito.parser.header.visitor.HypothesysVisitor;
import org.heraclito.parser.header.visitor.ResultVisitor;
import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;

/**
 *
 * @author Rafael
 */
public class Proof {

    private ParserRuleContext treeroot;
    private List<Expression> hypothesis;
    private Expression result;
    private String header;

    private List<Line> lines;

    public Proof(String header) throws ProofException {
        setHeader(header);
        this.lines = new ArrayList();
    }

    private void setHeader(String header) throws ProofException {
        parseHeader(header);
        setHypothesis();
        setResult();

        String anotherHeader = "";
        Iterator hypIt = this.hypothesis.iterator();
        anotherHeader += hypIt.next().toString();
        while (hypIt.hasNext()) {

            anotherHeader += ", " + hypIt.next().toString();
        }
        anotherHeader += " |- " + this.result.toString();
        this.header = anotherHeader;
    }

    private void parseHeader(String header) throws ProofException {
        ANTLRInputStream input = new ANTLRInputStream(header);
        TokenStream tokens = new CommonTokenStream(new HeaderLexer(input));

        HeaderParser parser = new HeaderParser(tokens);
        try {
            this.treeroot = parser.root(); // parse
        } catch (IllegalStateException e) {
            throw new ProofException("exception_invalid_header_input");
        }
    }

    private void setHypothesis() throws ProofException {
        HypothesysVisitor hypothesisVisitor = new HypothesysVisitor();

        List<Expression> hypothesisList = new ArrayList<>();
        for (String hyp : hypothesisVisitor.visit(this.treeroot)) {
            hypothesisList.add(0, new Expression(hyp));
        }
        this.hypothesis = hypothesisList;
    }

    private void setResult() throws ProofException {
        ResultVisitor resultVisitor = new ResultVisitor();
        this.result = new Expression(resultVisitor.visit(this.treeroot));
    }

    public String getHeader() {
        return this.header;
    }

    public void addAllHypothesis() {
        for (Expression exp : this.hypothesis) {
            boolean notYetInserted = true;
            for (Line l : this.lines) {
                if (exp.equals(l.getExpression()) && Rule.ID.CH.equals(l.getAppliedRule())) {
                    notYetInserted = false;
                }
            }
            if (notYetInserted) {
                this.lines.add(new Line(exp, Rule.ID.CH));
            }
        }
    }

    public void addHypothesis(String expression) throws ProofException {
        Expression param = new Expression(expression);
        for (Expression it : this.hypothesis) {
            if (param.equals(it)) {
                this.lines.add(new Line(param, Rule.ID.CH));
                return;
            }
        }
        throw new ProofException("exception_invalid_hypothesis_expression");
    }

    public Line getLine(Integer index) {
        return this.lines.get(index);
    }

    private List<Expression> getExpressions(List<Integer> linesIndex)
            throws ProofException {
        ArrayList expList = new ArrayList<>();

        for (Integer index : linesIndex) {
            Line itLine;

            try {
                itLine = this.lines.get(index);
                if (itLine.isLocked()) {
                    throw new ProofException("exception_invalid_line");
                }
                expList.add(itLine.getExpression());
            } catch (Exception e) {
                throw new ProofException("exception_invalid_line");
            }
        }

        return expList;
    }

    public void applyRule(Rule.ID ruleID, List<Integer> linesIndex,
            Expression outterExpression) throws ProofException {
        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(ruleID);
        applier.start();

        applier.addInnerExpression(this.getExpressions(linesIndex));
        applier.setOutterExpression(outterExpression);

        Expression result = applier.apply();

        Line newLine = new Line(result, ruleID, linesIndex);
        this.lines.add(newLine);
    }

    public Boolean isDone() {
        Line lastLine = this.lines.get(this.lines.size() - 1);
        if (this.result.equals(lastLine.getExpression())) {
            return lastLine.getHypothesisLevel() == 0;
        }

        return false;
    }
    

    @Override
    public String toString() {
        return this.printProof();
    }

    private String printProof() {
        StringBuilder proofString = new StringBuilder(this.getHeader());
        for (Line line : this.lines) {
            proofString.append("\n").append(line);
        }
        return proofString.toString();
    }

}
