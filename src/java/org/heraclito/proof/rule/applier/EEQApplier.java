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
public class EEQApplier extends Applier {

    public EEQApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression firstInnerExpression = getInnerExpression(0);
        Expression outterExpression = getOutterExpression();

        if (!Operator.BICONDITIONAL.equals(firstInnerExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        if (!Operator.IMPLICATION.equals(outterExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.outter.expression");
        }
        
        if(firstInnerExpression.getLeftExpression().equals(outterExpression.getLeftExpression())
                && firstInnerExpression.getRightExpression().equals(outterExpression.getRightExpression())) {
            return outterExpression;
        } else if (firstInnerExpression.getLeftExpression().equals(outterExpression.getRightExpression())
                && firstInnerExpression.getRightExpression().equals(outterExpression.getLeftExpression())) {
            return outterExpression;
        } else {
            throw new ProofException("exception.invalid.outter.expression");
        }

        //return outterExpression;
    }
    
}
