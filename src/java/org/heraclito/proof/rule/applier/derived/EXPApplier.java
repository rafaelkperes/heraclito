/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule.applier.derived;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.heraclito.proof.Expression;
import org.heraclito.proof.Operator;
import org.heraclito.proof.ProofException;
import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;

/**
 *
 * @author gia
 */
public class EXPApplier extends Applier {

    public EXPApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        // (P^Q)->R |- P->(Q->R)
        
        Expression firstInnerExpression = getInnerExpression(0);
        
        if(!Operator.IMPLICATION.equals(firstInnerExpression.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        Expression subExp = firstInnerExpression.getLeftExpression();
        
        if(!Operator.CONJUNCTION.equals(subExp.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }
        
        Expression expP = subExp.getLeftExpression();
        Expression expQ = subExp.getRightExpression();
        Expression expR = firstInnerExpression.getRightExpression();
        
        Expression qThenR = new Expression(expQ.toString() +
                Operator.IMPLICATION.toString() + expR.toString());
        
        Expression retExp = new Expression(expP.toString() +
                Operator.IMPLICATION.toString() + qThenR.toString());
        
        return retExp;
    }
    
}
