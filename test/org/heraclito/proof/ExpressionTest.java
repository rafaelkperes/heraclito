/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rafael
 */
public class ExpressionTest {
    
    public ExpressionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void toString_StringEqualsInput_SameStringAsInput() throws ProofException {
        Expression expression = new Expression("A->B");
        assertEquals("A->B", expression.toString());
    }
    
    @Test
    public void toString_StringEqualsInput_StringAsInputToUpper() throws ProofException {
        Expression expression = new Expression("a->b");
        assertEquals("A->B", expression.toString());
    }
    
    @Test
    public void toString_StringEqualsInput_StringAsInputTrimmedParenthesis() throws ProofException {
        Expression expression = new Expression("(A->B)");
        assertEquals("A->B", expression.toString());
    }
    
    @Test
    public void toString_StringEqualsInput_StringAsInputUnchangedOperatorV() throws ProofException {
        Expression expression = new Expression("avb");
        assertEquals("AvB", expression.toString());
    }
    
    
}
