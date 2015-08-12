/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

/**
 *
 * @author gia
 */
public enum Rule {

    CH("rule_id_ch", "rule_name_ch"),
    ;
    
    private final String id;
    private final String name;

    Rule(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}
