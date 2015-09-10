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

/**
 *
 * @author gia
 */
public class Rule {
    public enum ID {
        HIP("HIP", "rule_id_hip", "rule_name_hip", 0, false),
        
        CH("CH", "rule_id_ch", "rule_name_ch", 0, false),
        AD("AD", "rule_id_ad", "rule_name_ad", 1, true),
        CJ("CJ", "rule_id_cj", "rule_name_cj", 2, false),
        DN("DN", "rule_id_dn", "rule_name_dn", 1, false),
        EDJ("EDJ", "rule_id_edj", "rule_name_edj", 3, false),
        IEQ("IEQ", "rule_id_ieq", "rule_name_ieq", 2, false),
        EEQ("EEQ", "rule_id_eeq", "rule_name_eeq", 1, true),
        MP("MP", "rule_id_mp", "rule_name_mp", 2, false),
        SP("SP", "rule_id_sp", "rule_name_sp", 1, true),
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
                throw new ProofException("exception_invalid_parameters");
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
