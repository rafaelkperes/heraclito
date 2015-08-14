/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.heraclito.proof.rule;

import org.heraclito.proof.Expression;

/**
 *
 * @author gia
 */
public interface Aplicable {
    void addParameter(Expression exp);
    Expression apply();
}
