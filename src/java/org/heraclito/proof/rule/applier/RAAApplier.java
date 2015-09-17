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
public class RAAApplier extends Applier {

    public RAAApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        
        Expression innerExpression = this.getInnerExpression(0);
        Expression outterExpression = this.getOutterExpression();
        
        if(!Operator.CONJUNCTION.equals(innerExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        Expression exp1 = innerExpression.getLeftExpression();
        Expression exp2 = innerExpression.getRightExpression();
        
        if(Operator.NEGATION.equals(exp1.getMainOperator())) {
            if(exp1.getRightExpression().equals(exp2)) {
                return outterExpression;
            }
        }
        
        if(Operator.NEGATION.equals(exp2.getMainOperator())) {
            if(exp2.getRightExpression().equals(exp1)) {
                return outterExpression;
            }
        }
        
        throw new ProofException("exception.invalid.inner.expression");
    }
    
}
