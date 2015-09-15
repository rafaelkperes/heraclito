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
public class CHApplier extends Applier {

    public CHApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        Expression outterExpression = getOutterExpression();
        if (Operator.NEGATION.equals(outterExpression.getMainOperator())) {
            this.setRuleResult(Rule.ID.HRAA);
            return outterExpression.getRightExpression();
        } else if (Operator.IMPLICATION.equals(outterExpression.getMainOperator())) {
            this.setRuleResult(Rule.ID.HPC);
            return outterExpression.getLeftExpression();
        } else {
            throw new ProofException("exception.invalid.main.operator");
        }
    }

}
