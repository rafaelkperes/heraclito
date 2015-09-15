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
public class EEQApplierTest {

    public EEQApplierTest() {
    }

    /**
     * Test of apply method, of class EEQApplier.
     */
    @Test
    public void testApply_rightParams_returnsNewExpression() throws ProofException {
        Expression expected = new Expression("B->A");

        Expression firstInnerLine = new Expression("A<->B");
        Expression outterExpression = new Expression("B->A");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EEQ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.setOutterExpression(outterExpression);

        try {
            Expression result = applier.apply();
            assertEquals(expected, result);
        } catch (ProofException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testApply_moreRightParams_returnsNewExpression() throws ProofException {
        Expression expected = new Expression("A->B");

        Expression firstInnerLine = new Expression("A<->B");
        Expression outterExpression = new Expression("A->B");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EEQ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.setOutterExpression(outterExpression);

        try {
            Expression result = applier.apply();
            assertEquals(expected, result);
        } catch (ProofException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testApply_wrongMainOperator_returnsNewExpression() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("A->B");
        Expression outterExpression = new Expression("A->B");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EEQ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.setOutterExpression(outterExpression);

        try {
            Expression result = applier.apply();
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception.invalid.main.operator");
        }
    }

    @Test
    public void testApply_wrongOutterExpression_returnsNewExpression() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("A<->B");
        Expression outterExpression = new Expression("A->A");

        Rule rule = Rule.getInstance();
        Applier applier = rule.getApplier(Rule.ID.EEQ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.setOutterExpression(outterExpression);

        try {
            Expression result = applier.apply();
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception.invalid.outter.expression");
        }
    }
}
