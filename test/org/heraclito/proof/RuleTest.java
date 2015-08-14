/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import org.heraclito.proof.rule.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gia
 */
public class RuleTest {

    public RuleTest() {
    }

    /**
     * Test of apply method, of class Rule.
     */
    @Test
    public void testApply_CJ_returnNewExpression() throws Exception {
        Expression expA = new Expression("A");
        Expression expB = new Expression("B");
        Expression expResult = new Expression("A^B");
        Expression result = Rule.CJ.apply(expA, expB);
        assertEquals(expResult, result);
    }

    @Test
    public void testApply_CJNullParameter_throwProofException() throws Exception {
        Expression expA = new Expression("A");
        Expression expB = null;
        Expression expResult = new Expression("A^B");
        try {
            Expression result = Rule.CJ.apply(expA, expB);
            fail("Should throw ProofException.");
        } catch (ProofException e) {
        }
    }
    
    @Test
    public void testApply_DN_returnNewExpression() throws Exception {
        Expression expA = new Expression("~~A");
        Expression expResult = new Expression("A");
        Expression result = Rule.DN.apply(expA);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testApply_DNNullParameter_throwProofException() throws Exception {
        Expression expA = null;
        try {
            Expression result = Rule.DN.apply(expA);
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            System.out.println("Exception message should be about invalid parameters:");
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void testApply_DNParameterWithoutNeg_throwProofException() throws Exception {
        Expression expA = new Expression("AvBvC");
        try {
            Expression result = Rule.DN.apply(expA);
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            System.out.println("Exception message should be about wrong operator:");
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void testApply_DNParameterSingleNeg_throwProofException() throws Exception {
        Expression expA = new Expression("~C");
        try {
            Expression result = Rule.DN.apply(expA);
            fail("Should throw ProofException.");
        } catch (ProofException e) {
            System.out.println("Exception message should be about wrong operator:");
            System.out.println(e.getMessage());
        }
    }

}
