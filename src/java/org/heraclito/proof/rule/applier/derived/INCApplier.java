/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule.applier.derived;

import org.heraclito.proof.Expression;
import org.heraclito.proof.Operator;
import org.heraclito.proof.ProofException;
import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;

/**
 *
 * @author gia
 */
public class INCApplier extends Applier {

    public INCApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        // P, ~P |- Q
        
        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);        
        Expression outterExpression = getOutterExpression();
        
        if(!Operator.NEGATION.equals(firstInnerExpression.getMainOperator())) {            
            if(!Operator.NEGATION.equals(secondInnerExpression.getMainOperator())) {
                throw new ProofException("exception.invalid.main.operator");
            }
            
            if(!firstInnerExpression.getRightExpression().equals(secondInnerExpression)
                   && !secondInnerExpression.getRightExpression().equals(firstInnerExpression)) {
                throw new ProofException("exception.invalid.expression");
            }
        }
        
        return outterExpression;
    }
    
}
