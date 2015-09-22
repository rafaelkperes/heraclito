/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.util.ArrayList;
import org.heraclito.proof.rule.Rule;
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
    
    @Test
    public void applyRule_applyCJandCheckResult_addsNewRespectiveLine() throws ProofException {
        System.out.println("Expected header, all hypothesis and CJ (A, B |- A^B):");
        
        Proof proof = new Proof("A, B |- AvB");
        proof.addHypothesis("A");
        proof.addAllHypothesis();
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        innerExp.add(0);
        innerExp.add(1);
        
        proof.applyRule(Rule.ID.CJ, innerExp, null);
        
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void applyRule_applyCJonDoneProof_failsToApply() throws ProofException {
        // throws exception
        
        Proof proof = new Proof("A, B |- A^B");
        proof.addHypothesis("A");
        proof.addAllHypothesis();
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        innerExp.add(0);
        innerExp.add(1);
        
        proof.applyRule(Rule.ID.CJ, innerExp, null);
        
        try {
            proof.applyRule(Rule.ID.CJ, innerExp, null);
            System.out.println(proof);
            fail("Should throw exception for concluded proof.");
        } catch (ProofException pe) {
            assertEquals(pe.getMessage(), "exception.concluded.proof");
        }
        
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void applyRule_applyCJwithoutAllHypothesis_failsToApply() throws ProofException {
        // throws exception
        
        Proof proof = new Proof("A, B |- A^B");
        proof.addHypothesis("A");
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        innerExp.add(0);
        innerExp.add(0);
        
        try {
            proof.applyRule(Rule.ID.CJ, innerExp, null);
            fail("Should throw exception for not concluded hypothesis.");
        } catch (ProofException pe) {
            assertEquals(pe.getMessage(), "exception.not.concluded.hypothesis");
        }
        
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void applyRule_applyCH_createsHPCLine() throws ProofException {
        System.out.println("Expected header, all hypothesis and HRAA (A) (A, B |- A^B):");
        
        Proof proof = new Proof("A, B |- A^B");
        
        proof.addAllHypothesis();
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        Expression outterExp = new Expression("~A");
        
        try {
            proof.applyRule(Rule.ID.CH, innerExp, outterExp);
        } catch (ProofException pe) {
            fail(pe.getMessage());
        }
        
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void applyRule_applyCH_doesNotRAAAutomatically() throws ProofException {
        System.out.println("Expected header, all hypothesis, HRAA (A, B |- A^B):");
        
        Proof proof = new Proof("A, B |- A^B");
        
        proof.addAllHypothesis();
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        Expression outterExp = new Expression("~A");
        
        try {
            proof.applyRule(Rule.ID.CH, innerExp, outterExp);
        } catch (ProofException pe) {
            fail(pe.getMessage());
        }
        
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void applyRule_applyCH_doesRAAAutomatically() throws ProofException {
        System.out.println("Expected header, all hypothesis, HRAA, CJ and RAA (A, B |- A^B):");
        
        Proof proof = new Proof("A, B |- A^B");
        
        proof.addAllHypothesis();
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        Expression outterExp = new Expression("~~A");
        
        try {
            proof.applyRule(Rule.ID.CH, innerExp, outterExp);
        } catch (ProofException pe) {
            fail(pe.getMessage());
        }
        
        innerExp = new ArrayList<>();
        innerExp.add(0);
        innerExp.add(2);
        
        try {
            proof.applyRule(Rule.ID.CJ, innerExp, outterExp);
        } catch (ProofException pe) {
            fail(pe.getMessage());
        }
        
        
        
        System.out.println(proof);
        System.out.println("");
    }
    
    @Test
    public void applyRule_applyCH_doesPCAutomatically() throws ProofException {
        System.out.println("Expected header, all hypothesis, HPC, CL and PC (A, B |- A^B):");
        
        Proof proof = new Proof("A, B |- A^B");
        
        proof.addAllHypothesis();
        
        ArrayList<Integer> innerExp = new ArrayList<>();
        Expression outterExp = new Expression("A->B");
        
        try {
            proof.applyRule(Rule.ID.CH, innerExp, outterExp);
        } catch (ProofException pe) {
            fail(pe.getMessage());
        }
        
        innerExp = new ArrayList<>();
        innerExp.add(1);
        
        try {
            proof.applyRule(Rule.ID.CL, innerExp, outterExp);
        } catch (ProofException pe) {
            fail(pe.getMessage());
        }
        
        System.out.println(proof);
        System.out.println("");
    }

}
