/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof.rule;

import org.heraclito.proof.rule.applier.DNApplier;
import java.util.EnumMap;
import java.util.Map;
import org.heraclito.proof.Expression;
import org.heraclito.proof.Operator;
import org.heraclito.proof.ProofException;

/**
 *
 * @author gia
 */
public class Rule {
    public enum ID {
        CH("CH", "rule_id_ch", "rule_name_ch", 0, false),
        CJ("CJ", "rule_id_cj", "rule_name_cj", 2, false),
        DN("DN", "rule_id_dn", "rule_name_dn", 1, false),;

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
        
        public void start() {
            
        }
        
        public Expression apply() throws ProofException {
            return null;
            
        }

        public Expression apply(Expression expA, Expression expB) throws ProofException {
            if (expA == null || expB == null) {
                throw new ProofException("exception_invalid_parameters");
            }

            return new Expression(expA.toString() + Operator.CONJUNCTION + expB.toString());
        }

        public Expression apply(Expression expA) throws ProofException {
            if (expA == null) {
                throw new ProofException("exception_invalid_parameters");
            }

            if (!Operator.NEGATION.equals(expA.getMainOperator())) {
                throw new ProofException("exception_invalid_main_operator");
            }

            Expression singleNeg = new Expression(expA.getRightExpression().toString());
            if (!Operator.NEGATION.equals(singleNeg.getMainOperator())) {
                throw new ProofException("exception_invalid_main_operator");
            }

            return new Expression(singleNeg.getRightExpression().toString());
        }

        
    }

    private static Rule instance = null;
    private Map<Rule.ID, Applier> ruleAppliers;
    
    public Rule() {
        this.ruleAppliers = new EnumMap<>(Rule.ID.class);
        this.ruleAppliers.put(ID.DN, new DNApplier(ID.DN));
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
