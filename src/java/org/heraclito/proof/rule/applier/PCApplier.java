/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule.applier;

import org.heraclito.proof.Expression;
import org.heraclito.proof.Operator;
import org.heraclito.proof.ProofException;
import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;

/**
 *
 * @author gia
 */
public class PCApplier  extends Applier {

    public PCApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression innerExpression = this.getInnerExpression(0);
        Expression outterExpression = this.getOutterExpression();
        
        if(!Operator.IMPLICATION.equals(outterExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        if(!outterExpression.getRightExpression().equals(innerExpression)) {
            throw new ProofException("exception.invalid.inner.expression");
        }
        
        return outterExpression;
    }
    
}
