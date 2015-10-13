/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.heraclito.proof.rule.Rule;

/**
 *
 * @author gia
 */
public class Line implements Serializable {

    private Expression expression;
    private Rule.ID appliedRule;
    private List<Integer> appliedRuleLinesIndex;
    private Boolean locked;
    private Integer hypothesisLevel;
    

    public Line(Expression expression) {
        this.expression = expression;
        this.appliedRule = null;
        this.appliedRuleLinesIndex = new ArrayList<>();
        this.locked = false;
        this.hypothesisLevel = 0;
    }

    public Line(String expression) throws ProofException {
        this(new Expression(expression));
    }

    public Line(Expression expression, Rule.ID appliedRule) {
        this(expression);
        this.appliedRule = appliedRule;
    }

    public Line(String expression, Rule.ID appliedRule) throws ProofException {
        this(expression);
        this.appliedRule = appliedRule;
    }

    public Line(Expression expression, Rule.ID appliedRule, List<Integer> appliedRuleLinesIndex) {
        this(expression, appliedRule);
        setAppliedRuleLines(appliedRuleLinesIndex);
    }

    public Line(String expression, Rule.ID appliedRule, List<Integer> appliedRuleLinesIndex)
            throws ProofException {
        this(expression, appliedRule);
        setAppliedRuleLines(appliedRuleLinesIndex);
    }

    private void setAppliedRuleLines(List<Integer> appliedRuleLinesIndex) {
        this.appliedRuleLinesIndex = appliedRuleLinesIndex;
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

    public Rule.ID getAppliedRule() {
        return appliedRule;
    }

    public Integer getHypothesisLevel() {
        return hypothesisLevel;
    }

    public void setHypothesisLevel(Integer hypothesisLevel) {
        this.hypothesisLevel = hypothesisLevel;
    }
    
    public void lock() {
        this.locked = true;
    }
    
    public Boolean isLocked() {
        return this.locked;
    }

    @Override
    public String toString() {
        String ret = "(" + this.hypothesisLevel + ")";
        ret += " "  + this.expression.toString();
        ret += '\t' + this.appliedRule.toString();
        ret += '\t' + this.getLinesIndexString();
        return ret;
    }

}
