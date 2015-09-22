/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule.applier.derived;

import org.heraclito.proof.Expression;
import org.heraclito.proof.ProofException;
import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;

/**
 *
 * @author gia
 */
public class CLApplier extends Applier {

    public CLApplier(Rule.ID rule) {
        super(rule);
    }

    @Override
    public Expression apply() throws ProofException {
        checkParameters();
        
        return getInnerExpression(0);
    }
    
}
