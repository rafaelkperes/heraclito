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
public class ADApplier extends Applier {

    public ADApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression firstInnerExpression = getInnerExpression(0);
        Expression outterExpression = getOutterExpression();

        if (!Operator.DISJUNCTION.equals(outterExpression.getMainOperator())) {
            throw new ProofException("exception_invalid_main_operator");
        }

        if (!firstInnerExpression.equals(outterExpression.getLeftExpression())
                && !firstInnerExpression.equals(outterExpression.getRightExpression())) {
            throw new ProofException("exception_invalid_outter_expression");
        }

        return outterExpression;
    }

}
