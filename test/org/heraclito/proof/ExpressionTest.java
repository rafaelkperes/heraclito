/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rafael
 */
public class ExpressionTest {

    private static String[] validInputs = {
        "a",
        "A",
        "AvB",
        "A->B",
        "a->b",
        "avb",
        "(avb)",
        "(avb)vc",
        "avbvc",
        "a->b->c",};

    private static String[] expectedExpressions = {
        "A",
        "A",
        "AvB",
        "A->B",
        "A->B",
        "AvB",
        "AvB",
        "(AvB)vC",
        "(AvB)vC",
        "A->(B->C)"};

    private static Operator[] expectedOperators = {
        null,
        null,
        Operator.DISJUNCTION,
        Operator.IMPLICATION,
        Operator.IMPLICATION,
        Operator.DISJUNCTION,
        Operator.DISJUNCTION,
        Operator.DISJUNCTION,
        Operator.DISJUNCTION,
        Operator.IMPLICATION,};

    private static Object[] expectedLeftExpressions;
    private static Object[] expectedRightExpressions;

    public ExpressionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws ProofException {
        ArrayList<Expression> expectedLeftExpressionsBuilder = new ArrayList<>();
        expectedLeftExpressionsBuilder.add(null);
        expectedLeftExpressionsBuilder.add(null);
        expectedLeftExpressionsBuilder.add(new Expression("A"));
        expectedLeftExpressionsBuilder.add(new Expression("A"));
        expectedLeftExpressionsBuilder.add(new Expression("A"));
        expectedLeftExpressionsBuilder.add(new Expression("A"));
        expectedLeftExpressionsBuilder.add(new Expression("A"));
        expectedLeftExpressionsBuilder.add(new Expression("(AvB)"));
        expectedLeftExpressionsBuilder.add(new Expression("(AvB)"));
        expectedLeftExpressionsBuilder.add(new Expression("A"));
        expectedLeftExpressions = expectedLeftExpressionsBuilder.toArray();

        ArrayList<Expression> expectedRightExpressionsBuilder = new ArrayList<>();
        expectedRightExpressionsBuilder.add(null);
        expectedRightExpressionsBuilder.add(null);
        expectedRightExpressionsBuilder.add(new Expression("B"));
        expectedRightExpressionsBuilder.add(new Expression("B"));
        expectedRightExpressionsBuilder.add(new Expression("B"));
        expectedRightExpressionsBuilder.add(new Expression("B"));
        expectedRightExpressionsBuilder.add(new Expression("B"));
        expectedRightExpressionsBuilder.add(new Expression("C"));
        expectedRightExpressionsBuilder.add(new Expression("C"));
        expectedRightExpressionsBuilder.add(new Expression("(B->C)"));
        expectedRightExpressions = expectedRightExpressionsBuilder.toArray();
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
        for (String validInput : validInputs) {
            Expression expression = new Expression(validInput);
            resultExpressions.add(expression.toString());
        }
        assertArrayEquals(expectedExpressions, resultExpressions.toArray());
    }

    @Test
    public void getMainOperator_MainOperatorFromExpression_OperatorFromVariousInputs() throws ProofException {
        ArrayList<Operator> resultOperators = new ArrayList<>();
        for (String validInput : validInputs) {
            Expression expression = new Expression(validInput);
            resultOperators.add(expression.getMainOperator());
        }
        assertArrayEquals(expectedOperators, resultOperators.toArray());
    }

    @Test
    public void getLeftExpression_LeftExpressionFromExpression_LeftExpressionFromVariousInputs() throws ProofException {
        ArrayList<Expression> resultLeftExpressions = new ArrayList<>();
        for (String validInput : validInputs) {
            Expression expression = new Expression(validInput);
            resultLeftExpressions.add(expression.getLeftExpression());
        }
        assertArrayEquals(expectedLeftExpressions, resultLeftExpressions.toArray());
    }

    @Test
    public void getLeftExpression_RightExpressionFromExpression_LeftExpressionFromVariousInputs() throws ProofException {
        ArrayList<Expression> resultRightExpressions = new ArrayList<>();
        for (String validInput : validInputs) {
            Expression expression = new Expression(validInput);
            resultRightExpressions.add(expression.getRightExpression());
        }
        assertArrayEquals(expectedRightExpressions, resultRightExpressions.toArray());
    }

    @Test
    public void serialize_KeepsStringAsitIs_() throws Exception {
        Expression exp = new Expression("A^B");

        try (FileOutputStream fileOut = new FileOutputStream("./expression.her");
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(exp);
            out.close();
            fileOut.close();
            System.out.println("Serialized expression.");
        }

        Expression ret = null;

        try (FileInputStream fileIn = new FileInputStream("./expression.her"); 
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            ret = (Expression) in.readObject();
            
            System.out.println("Deserialized expression:");
            System.out.println(ret);
            System.out.println("Right Expression: " + ret.getRightExpression());
        } catch (Exception e) {
            System.out.println("Couldn't read from serialized expression.");
        }

    }
}
