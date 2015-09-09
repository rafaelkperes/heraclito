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
import org.heraclito.generic.Pair;
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
    private List<Pair<Expression, Boolean>> hypothesisPairs;
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
        for(Iterator it = this.hypothesisPairs.iterator(); it.hasNext();) {
            Pair<Expression, Boolean> p = (Pair<Expression, Boolean>) it.next();
            anotherHeader += p.getKey().toString();
            if(it.hasNext()) {
                anotherHeader += ", ";
            }
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
        
        List<Pair<Expression, Boolean>> pairList = new ArrayList<>();
        
        for (String hyp : hypothesisVisitor.visit(this.treeroot)) {
            pairList.add(0, new Pair<>(new Expression(hyp), false));
        }
        
        this.hypothesisPairs = pairList;                
    }

    private void setResult() throws ProofException {
        ResultVisitor resultVisitor = new ResultVisitor();
        this.result = new Expression(resultVisitor.visit(this.treeroot));
    }

    public String getHeader() {
        return this.header;
    }

    public void addAllHypothesis() {        
        for (Pair<Expression, Boolean> p : this.hypothesisPairs) {
            if(!p.getValue()) {
                this.lines.add(new Line(p.getKey(), Rule.ID.CH));
                p.setValue(true);
            }
        }
    }

    public void addHypothesis(String expression) throws ProofException {
        Expression param = new Expression(expression);
        
        for (Pair<Expression, Boolean> p : this.hypothesisPairs) {
            if(param.equals(p.getKey())) {
                this.lines.add(new Line(p.getKey(), Rule.ID.CH));
                p.setValue(true);
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
        if (this.isDone()) {
            throw new ProofException("exception.concluded.proof");
        }
        
        if(!this.hasAllHypothesis()) {
            throw new ProofException("exception.not.concluded.hypothesis");
        }

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(ruleID);
        applier.start();

        applier.addInnerExpression(this.getExpressions(linesIndex));
        applier.setOutterExpression(outterExpression);

        Expression result = applier.apply();

        Line newLine = new Line(result, ruleID, linesIndex);
        this.lines.add(newLine);
    }
    
    public Boolean canApplyRule() {
        return !this.isDone() && this.hasAllHypothesis();
    }
    
    private Boolean hasAllHypothesis() {
        for (Pair<Expression, Boolean> p : this.hypothesisPairs) {
            if(!p.getValue()) {
                return false;
            }
        }
        
        return true;
    }

    public Boolean isDone() {
        Line lastLine = this.lines.get(this.lines.size() - 1);
        if (lastLine.getExpression().equals(this.result)) {
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
