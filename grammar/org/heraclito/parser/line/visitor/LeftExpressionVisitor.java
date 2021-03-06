/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.line.visitor;

import org.heraclito.parser.line.LineParser;
import org.heraclito.parser.line.LineBaseVisitor;
import org.antlr.v4.runtime.tree.RuleNode;

/**
 *
 * @author Rafael
 */
public class LeftExpressionVisitor extends LineBaseVisitor<String> {
    
    @Override
    protected boolean shouldVisitNextChild(RuleNode node, String currentResult) {
        return currentResult == null;
    }
    
    @Override
    public String visitNegation(LineParser.NegationContext ctx) {
        return null;
    }

    @Override
    public String visitConjunction(LineParser.ConjunctionContext ctx) {
        return ctx.leftexp.getText();
    }

    @Override
    public String visitDisjunction(LineParser.DisjunctionContext ctx) {
        return ctx.leftexp.getText();
    }

    @Override
    public String visitImplication(LineParser.ImplicationContext ctx) {
        return ctx.leftexp.getText();
    }

    @Override
    public String visitBiconditional(LineParser.BiconditionalContext ctx) {
        return ctx.leftexp.getText();
    }
    
}
