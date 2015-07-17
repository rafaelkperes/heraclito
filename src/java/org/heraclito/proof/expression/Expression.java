package org.heraclito.proof.expression;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
class Expression {

    private String expression;
    
    Expression(String expression) {
        this.expression = expression.toUpperCase();
    }
    
    @Override
    public String toString() {
        return this.expression;
    }
    
}
