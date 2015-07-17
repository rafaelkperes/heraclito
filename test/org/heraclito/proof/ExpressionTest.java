/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.util.ArrayList;
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

    private String[] validInputs = {
        "a",
        "A",
        "AvB",
        "A->B",
        "a->b",
        "avb",
        "(avb)",
        "(avb)vc",
    };
    
    private String[] expectedExpressions = {
        "A",
        "A",
        "AvB",
        "A->B",
        "A->B",
        "AvB",
        "AvB",
        "(AvB)vC",
    };
    
    private Operator[] expectedOperators = {
        null,
        null,
        Operator.DISJUNCTION,
        Operator.IMPLICATION,
        Operator.IMPLICATION,
        Operator.DISJUNCTION,
        Operator.DISJUNCTION,
        Operator.DISJUNCTION,
    };

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

    public void toString_StringEqualsInput_StringAsVariousValidInputs() throws ProofException {
        ArrayList<String> resultExpressions = new ArrayList<>();
        for(String validInput : validInputs) {
            Expression expression = new Expression(validInput);
            resultExpressions.add(expression.toString());
        }
        assertArrayEquals(expectedExpressions, resultExpressions.toArray());
    }
    
    @Test
    public void getMainOperator_MainOperatorFromExpression_OperatorFromVariousInputs() throws ProofException {
        ArrayList<Operator> resultOperators = new ArrayList<>();
        for(String validInput : validInputs) {
            Expression expression = new Expression(validInput);
            resultOperators.add(expression.getMainOperator());
        }
        assertArrayEquals(expectedOperators, resultOperators.toArray());
    }
}