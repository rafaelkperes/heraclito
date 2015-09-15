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
public class MPApplier extends Applier {

    public MPApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);

        if(!Operator.IMPLICATION.equals(firstInnerExpression.getMainOperator())
                && !Operator.IMPLICATION.equals(secondInnerExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        if (Operator.IMPLICATION.equals(firstInnerExpression.getMainOperator())
                && secondInnerExpression.equals(firstInnerExpression.getLeftExpression())) {
            return firstInnerExpression.getRightExpression();
        } else if (Operator.IMPLICATION.equals(secondInnerExpression.getMainOperator())
                && firstInnerExpression.equals(secondInnerExpression.getLeftExpression()))  {
            return secondInnerExpression.getRightExpression();
        } else {
            throw new ProofException("exception.invalid.expression");
        }

        //return null;
    }

}
