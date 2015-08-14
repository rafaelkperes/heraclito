/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof.rule;

import org.heraclito.proof.Expression;
import org.heraclito.proof.Operator;
import org.heraclito.proof.ProofException;

/**
 *
 * @author gia
 */
public enum Rule {

    CH("CH", "rule_id_ch", "rule_name_ch", 0),
    CJ("CJ", "rule_id_cj", "rule_name_cj", 2),
    DN("DN", "rule_id_dn", "rule_name_dn", 1),
    ;

    private final String code;
    private final String id;
    private final String name;
    private final Integer noOfExp; // qty. of expressions as parameter needed for aplying the rule
   

    Rule(String code, String id, String name, Integer noOfExp) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.noOfExp = noOfExp;
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
    
    public Integer getNoOfExp() {
        return this.noOfExp;
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
        
        if(!Operator.NEGATION.equals(expA.getMainOperator())) {
            throw new ProofException("exception_invalid_main_operator");
        }
        
        Expression singleNeg = new Expression(expA.getRightExpression().toString());
        if(!Operator.NEGATION.equals(singleNeg.getMainOperator())) {
            throw new ProofException("exception_invalid_main_operator");
        }
        
        return new Expression(singleNeg.getRightExpression().toString());
    }
}