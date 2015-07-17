/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.lineparser.visitor;

import antlrparser.LineParser;
import antlrparser.LineBaseVisitor;
import org.antlr.v4.runtime.tree.RuleNode;

/**
 *
 * @author Rafael
 */
public class RightExpressionVisitor extends LineBaseVisitor<String> {

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, String currentResult) {
        return currentResult == null;
    }
    
    @Override
    public String visitNegation(LineParser.NegationContext ctx) {
        return ctx.rightexp.getText();
    }

    @Override
    public String visitConjunction(LineParser.ConjunctionContext ctx) {
        return ctx.rightexp.getText();
    }

    @Override
    public String visitDisjunction(LineParser.DisjunctionContext ctx) {
        return ctx.rightexp.getText();
    }

    @Override
    public String visitImplication(LineParser.ImplicationContext ctx) {
        return ctx.rightexp.getText();
    }

    @Override
    public String visitBiconditional(LineParser.BiconditionalContext ctx) {
        return ctx.rightexp.getText();
    }
    
}
