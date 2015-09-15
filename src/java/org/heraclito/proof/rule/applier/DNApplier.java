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
public class DNApplier extends Applier {

    public DNApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression firstInnerExpression = getInnerExpression(0);

        if (!Operator.NEGATION.equals(firstInnerExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }

        Expression reducedExpressionSingleNeg = new Expression(firstInnerExpression.getRightExpression().toString());
        if (!Operator.NEGATION.equals(reducedExpressionSingleNeg.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }

        return new Expression(reducedExpressionSingleNeg.getRightExpression().toString());
    }
    
}
