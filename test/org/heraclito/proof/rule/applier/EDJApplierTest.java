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
public class EDJApplierTest {

    public EDJApplierTest() {
    }

    /**
     * Test of apply method, of class EDJApplier.
     */
    @Test
    public void testApply_rightParams_returnsNewExpression() throws Exception {
        Expression expected = new Expression("C");

        Expression firstInnerLine = new Expression("AvB");
        Expression secondInnerLine = new Expression("A->C");
        Expression thirdInnerLine = new Expression("B->C");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EDJ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);
        applier.addInnerExpression(thirdInnerLine);
        Expression result = applier.apply();

        assertEquals(expected, result);
    }

    @Test
    public void testApply_evenMoreRightParams_returnsNewExpression() throws Exception {
        Expression expected = new Expression("C");

        Expression firstInnerLine = new Expression("BvA");
        Expression secondInnerLine = new Expression("B->C");
        Expression thirdInnerLine = new Expression("A->C");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EDJ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);
        applier.addInnerExpression(thirdInnerLine);
        Expression result = applier.apply();

        assertEquals(expected, result);
    }

    @Test
    public void testApply_wrongMainOperator_returnsNewExpression() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("B^A");
        Expression secondInnerLine = new Expression("B->C");
        Expression thirdInnerLine = new Expression("A->C");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EDJ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);
        applier.addInnerExpression(thirdInnerLine);

        try {
            Expression result = applier.apply();
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception_invalid_main_operator");
        }
    }
    
    @Test
    public void testApply_wrongSubexpression_returnsNewExpression() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("BvC");
        Expression secondInnerLine = new Expression("B->C");
        Expression thirdInnerLine = new Expression("A->C");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EDJ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);
        applier.addInnerExpression(thirdInnerLine);

        try {
            Expression result = applier.apply();
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception_invalid_expression");
        }
    }

}
