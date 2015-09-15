/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.heraclito.proof.Expression;
import org.heraclito.proof.ProofException;

/**
 *
 * @author gia
 */
public abstract class Applier {

    private final Rule.ID rule;
    private Rule.ID ruleResult;
    private List<Expression> innerExpressions;
    private Expression outterExpression;

    public Applier(Rule.ID rule) {
        this.rule = rule;
        this.outterExpression = null;
        this.innerExpressions = new ArrayList<>();
    }

    protected void checkParameters() throws ProofException {
        if (rule.getQtyInnerExpressions() > 0
                && innerExpressions.size() < rule.getQtyInnerExpressions()) {
            throw new ProofException("exception.missing.inner.expression");
        }
        if (rule.needsOutterExpression() && outterExpression == null) {
            throw new ProofException("exception.missing.outter.expression");
        }
    }

    public void addInnerExpression(Expression exp) {
        if (exp != null) {
            this.innerExpressions.add(exp);
        }
    }

    public void addInnerExpression(Collection<Expression> exp) {
        if (exp != null) {
            this.innerExpressions.addAll(exp);
        }
    }

    public void setOutterExpression(Expression exp) {
        this.outterExpression = exp;
    }

    protected Expression getInnerExpression(Integer index) throws ProofException {
        try {
            return this.innerExpressions.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ProofException("exception.invalid.line.index");
        }
    }

    protected Expression getOutterExpression() {
        return this.outterExpression;
    }

    public void setRuleResult(Rule.ID rule) {
        this.ruleResult = rule;
    }

    public Rule.ID getRuleResult() {
        return this.ruleResult;
    }

    public abstract Expression apply() throws ProofException;

    public void start() {
        this.innerExpressions = new ArrayList<>();
        this.outterExpression = null;
        this.ruleResult = this.rule;
    }
}
