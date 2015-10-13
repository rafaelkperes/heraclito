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
public class MTApplier extends Applier {

    public MTApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        // P->Q, ~Q |- ~P

        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);

        Expression impExp = null;
        Expression negExp = null;
        if (Operator.IMPLICATION.equals(firstInnerExpression.getMainOperator())) {
            impExp = firstInnerExpression;
            negExp = secondInnerExpression;
        } else if (Operator.IMPLICATION.equals(secondInnerExpression.getMainOperator())) {
            impExp = secondInnerExpression;
            negExp = firstInnerExpression;
        } else {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        if(!Operator.NEGATION.equals(negExp.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }

        Expression subNeg = negExp.getRightExpression();
        
        if(!subNeg.equals(impExp.getRightExpression())) {
            throw new ProofException("exception.invalid.expression");
        }
        
        Expression retExp = new Expression(Operator.NEGATION 
                + impExp.getLeftExpression().toString());
        return retExp;
    }

}
