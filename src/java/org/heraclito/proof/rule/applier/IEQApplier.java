/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule.applier;

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
public class IEQApplier extends Applier {

    public IEQApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);
        
        if(!Operator.IMPLICATION.equals(firstInnerExpression.getMainOperator())) {
            throw new ProofException("exception_invalid_main_operator");
        }        
        if(!Operator.IMPLICATION.equals(secondInnerExpression.getMainOperator())) {
            throw new ProofException("exception_invalid_main_operator");
        }
        
        if(!firstInnerExpression.getLeftExpression().equals(
            secondInnerExpression.getRightExpression())) {
            throw new ProofException("exception_invalid_expression");
        }        
        if(!secondInnerExpression.getLeftExpression().equals(
            firstInnerExpression.getRightExpression())) {
            throw new ProofException("exception_invalid_expression");
        }
        
        return new Expression(firstInnerExpression.getLeftExpression().toString()
            + Operator.BICONDITIONAL 
            + firstInnerExpression.getRightExpression().toString());
    }
    
}
