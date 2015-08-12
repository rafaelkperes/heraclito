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
        System.out.println("");
    }

    @Test
    public void toString_ProofWithAllHypothesis_PrintsProofsHeaderAndAllHypothesis() throws ProofException {
        System.out.println("Expected header and all hypothesis (A, B |- AvB):");
        Proof proof = new Proof("A, B |- AvB");
        proof.addAllHypothesis();
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void toString_ProofWithOneHypothesis_PrintsProofsHeaderAndOneHypothesis() throws ProofException {
        System.out.println("Expected header and \"A\" hypothesis (A, B |- AvB):");
        Proof proof = new Proof("A, B |- AvB");
        proof.addHypothesis("A");
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void addAllHypothesys_AddOneHypothesysThenAddAll_PrintsProofsHeaderAndAllHypothesis() throws ProofException {
        System.out.println("Expected header and all hypothesis (A, B |- AvB):");
        Proof proof = new Proof("A, B |- AvB");
        proof.addHypothesis("A");
        proof.addAllHypothesis();
        System.out.println(proof);
        System.out.println("");
    }
}
