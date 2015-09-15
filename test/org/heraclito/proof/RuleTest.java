/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import org.heraclito.proof.rule.Applier;
import org.heraclito.proof.rule.Rule;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gia
 */
public class RuleTest {

    Rule rule;

    public RuleTest() {
    }

    @Before
    public void setUp() {
        this.rule = Rule.getInstance();
    }

    /**
     * Test of apply method, of class Rule.
     * @throws java.lang.Exception
     */
    @Test
    public void testApply_AD_returnNewExpression() throws Exception {
        Expression expected = new Expression("AvB");

        Expression firstInnerLine = new Expression("A");
        Expression outterLine = new Expression("AvB");

        Applier applier = rule.getApplier(Rule.ID.AD);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.setOutterExpression(outterLine);
        Expression result = applier.apply();

        assertEquals(expected, result);
    }
    
    @Test
    public void testApply_CJ_returnNewExpression() throws Exception {
        Expression expected = new Expression("A^B");

        Expression firstInnerLine = new Expression("A");
        Expression secondInnerLine = new Expression("B");

        Applier applier = rule.getApplier(Rule.ID.CJ);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        applier.addInnerExpression(secondInnerLine);
        Expression result = applier.apply();

        assertEquals(expected, result);
    }

    @Test
    public void testApply_CJNullParameter_throwProofException() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("A");
        Expression secondInnerLine = null;
        try {
            Applier applier = rule.getApplier(Rule.ID.CJ);
            applier.start();
            applier.addInnerExpression(firstInnerLine);
            applier.addInnerExpression(secondInnerLine);
            Expression result = applier.apply();
            
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception.missing.inner.expression");
        }
    }

    @Test
    public void testApply_DN_returnNewExpression() throws Exception {
        Expression expected = new Expression("A");

        Expression firstInnerLine = new Expression("~~A");

        Applier applier = rule.getApplier(Rule.ID.DN);
        applier.start();
        applier.addInnerExpression(firstInnerLine);
        Expression result = applier.apply();

        assertEquals(expected, result);
    }

    @Test
    public void testApply_DNNullParameter_throwProofException() throws Exception {
        // throws exception

        Expression firstInnerLine = null;
        try {
            Applier applier = rule.getApplier(Rule.ID.DN);
            applier.start();
            applier.addInnerExpression(firstInnerLine);
            Expression result = applier.apply();
            
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception.missing.inner.expression");
        }
    }

    @Test
    public void testApply_DNParameterWithoutNeg_throwProofException() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("AvBvC");
        try {
            Applier applier = rule.getApplier(Rule.ID.DN);
            applier.start();
            applier.addInnerExpression(firstInnerLine);
            Expression result = applier.apply();
            
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception.invalid.main.operator");
        }
    }

    @Test
    public void testApply_DNParameterSingleNeg_throwProofException() throws Exception {
        // throws exception

        Expression firstInnerLine = new Expression("~C");
        try {
            Applier applier = rule.getApplier(Rule.ID.DN);
            applier.start();
            applier.addInnerExpression(firstInnerLine);
            Expression result = applier.apply();
            
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            assertEquals(e.getMessage(), "exception.invalid.main.operator");
        }
    }

}
