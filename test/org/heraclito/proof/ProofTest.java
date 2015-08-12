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
public class ProofTest {

    private static String[] validInputs = {
        "A |- AvB",
        "AvB, BvC |- AvB",
        "(A) |- ((A)vB)",
        "AvBvC |- AvB",};

    private static String[] expectedHeaders = {
        "A |- AvB",
        "AvB, BvC |- AvB",
        "A |- AvB",
        "(AvB)vC |- AvB",};

    public ProofTest() {
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
    public void validInput_ConstructorReceivesValidInputs_ReturnsHeaderAsStringOfInputExpressions() throws ProofException {
        ArrayList<String> resultExpressions = new ArrayList<>();
        for (String validInput : validInputs) {
            Proof proof = new Proof(validInput);
            resultExpressions.add(proof.getHeader());
        }
        assertArrayEquals(expectedHeaders, resultExpressions.toArray());
    }
    
    @Test
    public void toString_ProofOnlyHeader_PrintsProofsHeader() throws ProofException {
        System.out.println("Expected only header (A, B |- AvB):");
        Proof proof = new Proof("A, B |- AvB");
        System.out.println(proof);
    }

    @Test
    public void toString_ProofWithHypothesis_PrintsProofsHeaderAndHypothesis() throws ProofException {
        System.out.println("Expected header and hypothesis (A, B |- AvB):");
        Proof proof = new Proof("A, B |- AvB");
        proof.addAllHypothesis();
        System.out.println(proof);
    }
}
