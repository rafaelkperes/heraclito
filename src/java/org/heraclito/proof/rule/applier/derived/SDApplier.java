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
public class SDApplier extends Applier {

    public SDApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        // PvQ, ~P |- Q
        
        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);
        
        Expression disjExp = null, negExp = null;
        
        if(Operator.DISJUNCTION.equals(firstInnerExpression.getMainOperator())) {
            disjExp = firstInnerExpression;
            negExp = secondInnerExpression;
        } else if(Operator.DISJUNCTION.equals(firstInnerExpression.getMainOperator())) {
            disjExp = secondInnerExpression;
            negExp = firstInnerExpression;
        } else {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        if(!Operator.NEGATION.equals(negExp.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        Expression retExp = null;
        
        if(negExp.isContradictionOf(disjExp.getLeftExpression())) {
            retExp = disjExp.getRightExpression();
        } else if(negExp.isContradictionOf(disjExp.getRightExpression())) {
            retExp = disjExp.getLeftExpression();
        } else {
            throw new ProofException("exception.invalid.expression");
        }
        
        return retExp;
    }
    
}
