/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof.rule;

import java.util.EnumMap;
import java.util.Map;
import org.heraclito.proof.Expression;
import org.heraclito.proof.Operator;
import org.heraclito.proof.ProofException;
import org.heraclito.proof.rule.applier.*;
import org.heraclito.proof.rule.applier.derived.CLApplier;

/**
 *
 * @author gia
 */
public class Rule {
    public enum ID {
        HIP("HIP", "rule.id.hip", "rule.name.hip", 0, true),
        
        CH("CH", "rule.id.ch", "rule.name.ch", 0, true),
            HRAA("HPC", "rule.id.hraa", "rule.name.hraa", 0, true),
            HPC("HRAA", "rule.id.hpc", "rule.name.hpc", 0, true),
            RAA("RAA", "rule.id.raa", "rule.name.raa", 1, true),
            PC("PC", "rule.id.pc", "rule.name.pc", 1, true),
        AD("AD", "rule.id.ad", "rule.name.ad", 1, true),
        CJ("CJ", "rule.id.cj", "rule.name.cj", 2, false),
        DN("DN", "rule.id.dn", "rule.name.dn", 1, false),
        EDJ("EDJ", "rule.id.edj", "rule.name.edj", 3, false),
        IEQ("IEQ", "rule.id.ieq", "rule.name.ieq", 2, false),
        EEQ("EEQ", "rule.id.eeq", "rule.name.eeq", 1, true),
        MP("MP", "rule.id.mp", "rule.name.mp", 2, false),
        SP("SP", "rule.id.sp", "rule.name.sp", 1, true),
        
        CL("CL", "rule.id.cl", "rule.name.cl", 1, false),
        ;

        private final String code;
        private final String id;
        private final String name;
        private final Integer qtyInnerExpressions; // qty. of expressions as parameter needed for aplying the rule
        private final Boolean needsOutterExpression;

        ID(String code, String id, String name, Integer qtyInnerExpressions, Boolean needsOutterExpression) {
            this.code = code;
            this.id = id;
            this.name = name;
            this.qtyInnerExpressions = qtyInnerExpressions;
            this.needsOutterExpression = needsOutterExpression;
        }

        public String getCode() {
            return this.code;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public Integer getQtyInnerExpressions() {
            return this.qtyInnerExpressions;
        }
        
        public boolean needsOutterExpression() {
            return this.needsOutterExpression;
        }

        public Expression apply(Expression expA, Expression expB) throws ProofException {
            if (expA == null || expB == null) {
                throw new ProofException("exception.invalid.parameters");
            }

            return new Expression(expA.toString() + Operator.CONJUNCTION + expB.toString());
        }        
    }

    private static Rule instance = null;
    
    private Map<Rule.ID, Applier> ruleAppliers;
    
    
    public Rule() {
        this.ruleAppliers = new EnumMap<>(Rule.ID.class);
        
        this.ruleAppliers.put(ID.DN, new DNApplier(ID.DN));
        this.ruleAppliers.put(ID.CJ, new CJApplier(ID.CJ));
        this.ruleAppliers.put(ID.AD, new ADApplier(ID.AD));
        this.ruleAppliers.put(ID.EDJ, new EDJApplier(ID.EDJ));
        this.ruleAppliers.put(ID.IEQ, new IEQApplier(ID.IEQ));
        this.ruleAppliers.put(ID.EEQ, new EEQApplier(ID.EEQ));
        this.ruleAppliers.put(ID.MP, new MPApplier(ID.MP));
        this.ruleAppliers.put(ID.SP, new SPApplier(ID.SP));
        this.ruleAppliers.put(ID.CH, new CHApplier(ID.CH));
        this.ruleAppliers.put(ID.PC, new PCApplier(ID.PC));
        this.ruleAppliers.put(ID.RAA, new RAAApplier(ID.RAA));
        
        this.ruleAppliers.put(ID.CL, new CLApplier(ID.CL));
    }
    
    public static Rule getInstance() {
        if(instance == null) {
            instance = new Rule();
        }
        
        return instance;
    }
    
    public Applier getApplier(Rule.ID rule) {
        return this.ruleAppliers.get(rule);
    }
}
