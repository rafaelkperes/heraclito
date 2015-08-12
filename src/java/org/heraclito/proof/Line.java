/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author gia
 */
class Line {

    private Expression expression;
    private Rule appliedRule;
    private ArrayList<Integer> appliedRuleLinesIndex;

    public Line(Expression expression) {
        this.expression = expression;
        this.appliedRule = null;
        this.appliedRuleLinesIndex = new ArrayList<>();
    }

    public Line(String expression) throws ProofException {
        this(new Expression(expression));
    }

    public Line(Expression expression, Rule appliedRule) {
        this(expression);
        this.appliedRule = appliedRule;
    }

    public Line(String expression, Rule appliedRule) throws ProofException {
        this(expression);
        this.appliedRule = appliedRule;
    }

    public Line(Expression expression, Rule appliedRule, Integer[] appliedRuleLinesIndex) {
        this(expression, appliedRule);
        setAppliedRuleLines(appliedRuleLinesIndex);
    }

    public Line(String expression, Rule appliedRule, Integer[] appliedRuleLinesIndex)
            throws ProofException {
        this(expression, appliedRule);
        setAppliedRuleLines(appliedRuleLinesIndex);
    }

    private void setAppliedRuleLines(Integer[] appliedRuleLinesIndex) {
        this.appliedRuleLinesIndex = new ArrayList<>(Arrays.asList(appliedRuleLinesIndex));
    }

    private Boolean hasLinesIndex() {
        return this.appliedRuleLinesIndex != null
                && !this.appliedRuleLinesIndex.isEmpty();
    }
    
    public String getLinesIndexString() {
        if(!hasLinesIndex())
            return "";
        String ret = "(" + this.appliedRuleLinesIndex.get(0);
        if(this.appliedRuleLinesIndex.size() >= 2)
                ret += ", " + this.appliedRuleLinesIndex.get(1);
        return ret + ")";
    }
    
    public Expression getExpression() {
        return this.expression;
    }

    public Rule getAppliedRule() {
        return appliedRule;
    }

    @Override
    public String toString() {
        String ret = this.expression.toString();
        ret += '\t' + this.appliedRule.toString();
        ret += '\t' + this.getLinesIndexString();
        return ret;
    }

}
