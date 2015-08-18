/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof.rule;

import java.util.ArrayList;
import java.util.List;
import org.heraclito.proof.Expression;
import org.heraclito.proof.ProofException;

/**
 *
 * @author gia
 */
public abstract class Applier {

    private final Rule.ID rule;
    private List<Expression> innerExpressions;
    private Expression outterExpression;

    public Applier(Rule.ID rule) {
        this.rule = rule;
        this.outterExpression = null;
        this.innerExpressions = new ArrayList<>();
    }

    protected void checkParameters() throws ProofException {
        if (innerExpressions.size() < rule.getQtyInnerExpressions()) {
            throw new ProofException("exception_missing_inner_expression");
        }
        if (rule.needsOutterExpression() && outterExpression == null) {
            throw new ProofException("exception_missing_outter_expression");
        }
    }

    public void addInnerExpression(Expression exp) {
        if (exp != null) {
            this.innerExpressions.add(exp);
        }
    }

    public void setOutterExpression(Expression exp) {
        this.outterExpression = exp;
    }

    protected Expression getInnerExpression(Integer index) {
        return this.innerExpressions.get(index);
    }

    protected Expression getOutterExpression() {
        return this.outterExpression;
    }

    public abstract Expression apply() throws ProofException;

    public void start() {
        this.innerExpressions = new ArrayList<>();
        this.outterExpression = null;
    }
}
