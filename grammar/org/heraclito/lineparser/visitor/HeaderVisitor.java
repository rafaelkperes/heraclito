/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.lineparser.visitor;

import antlrparser.LineBaseVisitor;
import antlrparser.LineParser;
import org.antlr.v4.runtime.tree.RuleNode;

/**
 *
 * @author Rafael
 */
public class HeaderVisitor extends LineBaseVisitor<Boolean> {

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, Boolean currentResult) {
        return false;
    }
    
    @Override
    public Boolean visitIsHeader(LineParser.IsHeaderContext ctx) {
        return true;
    }
}
