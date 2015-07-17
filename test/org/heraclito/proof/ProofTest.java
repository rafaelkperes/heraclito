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
        "A, B |- A^B",
        "A, B |- (A^B)",};
    
    private static String[] expectedHeaders = {
        "A, B |- A^B",
        "A, B |- A^B",};
    
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
    public void validInput_ConstructorReceivesValidInputs_ReturnsHeaderAsStringOfInputExpressions() {
        ArrayList<String> resultExpressions = new ArrayList<>();
        for (String validInput : validInputs) {
            Proof proof = new Proof(validInput);
            resultExpressions.add(proof.getHeader());
        }
        assertArrayEquals(expectedHeaders, resultExpressions.toArray());
    }
    
}
