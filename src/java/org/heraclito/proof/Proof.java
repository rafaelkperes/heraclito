/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.heraclito.generic.Pair;
import org.heraclito.generic.Triplet;
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
public class Proof implements Serializable {

    private transient ParserRuleContext treeroot;
    private List<Pair<Expression, Boolean>> hypothesisPairs;
    private Expression result;
    private String header;
    private final Stack<Triplet<Expression, Rule.ID, Integer>> expectedResult; /* from HRAA/HPC  - result, result rule, beginning line*/

    private final List<Line> lines;

    public Proof(String header) throws ProofException {
        setHeader(header);
        this.lines = new ArrayList();
        this.expectedResult = new Stack<>();
    }

    private void setHeader(String header) throws ProofException {
        parseHeader(header);
        setHypothesis();
        setResult();

        String anotherHeader = "";
        for (Iterator it = this.hypothesisPairs.iterator(); it.hasNext();) {
            Pair<Expression, Boolean> p = (Pair<Expression, Boolean>) it.next();
            anotherHeader += p.getKey().toString();
            if (it.hasNext()) {
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
            throw new ProofException("exception.invalid.header.input");
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
            if (!p.getValue()) {
                this.lines.add(new Line(p.getKey(), Rule.ID.HIP));
                p.setValue(true);
            }
        }
    }

    public void addHypothesis(String expression) throws ProofException {
        Expression param = new Expression(expression);

        for (Pair<Expression, Boolean> p : this.hypothesisPairs) {
            if (param.equals(p.getKey())) {
                this.lines.add(new Line(p.getKey(), Rule.ID.HIP));
                p.setValue(true);
                return;
            }
        }

        throw new ProofException("exception.invalid.hypothesis.expression");
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
                    throw new ProofException("exception.invalid.line");
                }
                expList.add(itLine.getExpression());
            } catch (Exception e) {
                throw new ProofException("exception.invalid.line");
            }
        }

        return expList;
    }

    public void applyRule(Rule.ID ruleID, List<Integer> linesIndex,
            Expression outterExpression) throws ProofException {
        /* checking proof state */
        if (this.isDone()) {
            throw new ProofException("exception.concluded.proof");
        }

        if (!this.hasAllHypothesis()) {
            throw new ProofException("exception.not.concluded.hypothesis");
        }

        /* applying rule */
        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(ruleID);
        applier.start();

        applier.addInnerExpression(this.getExpressions(linesIndex));
        applier.setOutterExpression(outterExpression);

        Expression result = applier.apply();

        Line newLine = new Line(result, applier.getRuleResult(), linesIndex);

        /* setting hypothesis level */
        int hypothesisLevel = 0;

        try {
            Line lastLine = this.getLastLine();
            hypothesisLevel = lastLine.getHypothesisLevel();
        } catch (Exception e) {
        }

        if (Rule.ID.CH.equals(ruleID)) {
            newLine.setHypothesisLevel(hypothesisLevel + 1);
            if (Rule.ID.HPC.equals(applier.getRuleResult())) {
                this.expectedResult.push(new Triplet<>(outterExpression, Rule.ID.PC, this.lines.size()));
            } else if (Rule.ID.HRAA.equals(applier.getRuleResult())) {
                this.expectedResult.push(new Triplet<>(outterExpression, Rule.ID.RAA, this.lines.size()));
            } else {
                throw new ProofException("exception.invalid.main.operator");
            }
        } else {
            newLine.setHypothesisLevel(hypothesisLevel);
        }

        /* adding new line */
        this.lines.add(newLine);

        this.checkHypothesisResult();
    }

    private void checkHypothesisResult() {
        if (!this.expectedResult.isEmpty()) {
            Rule.ID ruleID = this.expectedResult.peek().getValue();
            
            Expression expected = this.expectedResult.peek().getKey();
            Expression actual = this.getLastLine().getExpression();
            
            ArrayList<Integer> lineIndexList = new ArrayList();
            lineIndexList.add(this.expectedResult.peek().getComplement()); // first hypothesis line
            lineIndexList.add(this.lines.size() - 1); // last line
            
            try {
                Rule rule = Rule.getInstance();
                Applier applier = rule.getApplier(ruleID);

                applier.start();
                applier.addInnerExpression(actual);
                applier.setOutterExpression(expected);
                Expression ret = applier.apply();
                Line line = new Line(expected, ruleID, lineIndexList);
                line.setHypothesisLevel(this.getLastLine().getHypothesisLevel() - 1);
                this.lines.add(line);
                return;
            } catch (ProofException pe) {
            }
        }
    }

    private Line getLastLine() {
        if (this.lines.isEmpty()) {
            return null;
        }
        return this.lines.get(this.lines.size() - 1);
    }

    public Boolean canApplyRule() {
        return !this.isDone() && this.hasAllHypothesis();
    }

    private Boolean hasAllHypothesis() {
        for (Pair<Expression, Boolean> p : this.hypothesisPairs) {
            if (!p.getValue()) {
                return false;
            }
        }

        return true;
    }

    public Boolean isDone() {
        try {
            Line lastLine = this.getLastLine();
            if (lastLine.getExpression().equals(this.result)) {
                return lastLine.getHypothesisLevel() == 0;
            }
        } catch (IndexOutOfBoundsException e) {
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
