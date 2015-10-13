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
public class DCApplier extends Applier {

    public DCApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();

        Expression firstInnerExpression = getInnerExpression(0);
        Expression secondInnerExpression = getInnerExpression(1);
        Expression thirdInnerExpression = getInnerExpression(2);

        Expression conjExp = null;
        Expression impExp1 = null;
        Expression impExp2 = null;

        // finds ->
        if (Operator.CONJUNCTION.equals(firstInnerExpression.getMainOperator())) {
            conjExp = firstInnerExpression;
            impExp1 = secondInnerExpression;
            impExp2 = thirdInnerExpression;
        } else if (Operator.CONJUNCTION.equals(firstInnerExpression.getMainOperator())) {
            conjExp = secondInnerExpression;
            impExp1 = firstInnerExpression;
            impExp2 = thirdInnerExpression;
        } else if (Operator.CONJUNCTION.equals(firstInnerExpression.getMainOperator())) {
            conjExp = thirdInnerExpression;
            impExp1 = firstInnerExpression;
            impExp2 = secondInnerExpression;
        } else {
            throw new ProofException("exception.invalid.main.operator");
        }

        if (!Operator.IMPLICATION.equals(impExp1.getMainOperator())
                || !Operator.IMPLICATION.equals(impExp2.getMainOperator())) {
            throw new ProofException("exception.invalid.main.operator");
        }

        Expression conjSub1 = conjExp.getLeftExpression();
        Expression conjSub2 = conjExp.getRightExpression();

        Expression retLeft = null, retRight = null;
        
        // finds P, P->R
        if (conjSub1.equals(impExp1.getLeftExpression())) {
            retLeft = impExp1.getRightExpression();
        } else if (conjSub1.equals(impExp2.getLeftExpression())) {
            retLeft = impExp1.getRightExpression();
        } else {
            throw new ProofException("exception.invalid.expression");
        }
        
        // finds Q, Q->S
        if (conjSub2.equals(impExp1.getLeftExpression())) {
            retRight = impExp2.getRightExpression();
        } else if (conjSub2.equals(impExp2.getLeftExpression())) {
            retRight = impExp1.getRightExpression();
        } else {
            throw new ProofException("exception.invalid.expression");
        }
        
        Expression retExp = new Expression(
                retLeft.toString() + Operator.DISJUNCTION + retRight.toString());
        return retExp;
    }

}
