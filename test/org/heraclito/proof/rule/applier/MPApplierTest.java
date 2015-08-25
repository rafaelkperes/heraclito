/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule.applier;

import org.heraclito.proof.Expression;
import org.heraclito.proof.ProofException;
import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gia
 */
public class MPApplierTest {
    
    public MPApplierTest() {
    }

    /**
     * Test of apply method, of class MPApplier.
     */
    @Test
    public void testApply_rightParams_returnsNewExpression() throws Exception {
        Expression expected = new Expression("B");

        Expression firstInnerLine = new Expression("A->B");
        Expression secondInnerLine = new Expression("A");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.MP);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);
        Expression result = applier.apply();

        assertEquals(expected, result);
    }

    @Test
    public void testApply_wrongMainOperator_throwsProofException() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("AvB");
        Expression secondInnerLine = new Expression("A");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.MP);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);

        try {
            Expression result = applier.apply();
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception_invalid_main_operator");
        }
    }
    
    @Test
    public void testApply_wrongSubexpression_throwsProofException() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("A->B");
        Expression secondInnerLine = new Expression("B");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.MP);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);

        try {
            Expression result = applier.apply();
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception_invalid_expression");
        }
    }
    
}
