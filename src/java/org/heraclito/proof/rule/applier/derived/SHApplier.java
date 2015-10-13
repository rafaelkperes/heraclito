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
public class SHApplier extends Applier {

    public SHApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        // P->Q, P->R |- Q->R

        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);

        if (!Operator.IMPLICATION.equals(firstInnerExpression.getMainOperator())
                && !Operator.IMPLICATION.equals(secondInnerExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }

        if (!firstInnerExpression.getLeftExpression().equals(
                secondInnerExpression.getLeftExpression())) {
            throw new ProofException("exception.invalid.expression");
        }

        Expression retExp = new Expression(firstInnerExpression.getRightExpression().toString()
                + Operator.IMPLICATION + secondInnerExpression.getRightExpression().toString());

        return retExp;
    }

}
