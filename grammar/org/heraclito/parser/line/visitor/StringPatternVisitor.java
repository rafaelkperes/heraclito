/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.line.visitor;

import org.heraclito.parser.line.LineBaseVisitor;
import org.heraclito.parser.line.LineParser;
import org.antlr.v4.runtime.tree.RuleNode;
import org.heraclito.proof.Operator;

/**
 * Changes input to the Line string value standard
 *
 * @author Rafael
 */
public class StringPatternVisitor extends LineBaseVisitor<String> {

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, String currentResult) {
        return currentResult == null;
    }

    @Override
    public String visitVar(LineParser.VarContext ctx) {
        return ctx.getText().toUpperCase();
    }

    @Override
    public String visitNegation(LineParser.NegationContext ctx) {
        return Operator.NEGATION.getExpression() + visit(ctx.rightexp);
    }

    @Override
    public String visitConjunction(LineParser.ConjunctionContext ctx) {
        // w/o parenthesis
        if (LineParser.RULE_root == ctx.parent.getRuleIndex()) {
            return visit(ctx.leftexp)
                    + Operator.CONJUNCTION.getExpression()
                    + visit(ctx.rightexp);
        }
        // w/ parenthesis
        return "(" + visit(ctx.leftexp)
                + Operator.CONJUNCTION.getExpression()
                + visit(ctx.rightexp) + ")";
    }

    @Override
    public String visitDisjunction(LineParser.DisjunctionContext ctx) {
        // w/o parenthesis
        if (LineParser.RULE_root == ctx.parent.getRuleIndex()) {
            return visit(ctx.leftexp)
                    + Operator.DISJUNCTION.getExpression()
                    + visit(ctx.rightexp);
        }
        // w/ parenthesis
        return "(" + visit(ctx.leftexp)
                + Operator.DISJUNCTION.getExpression()
                + visit(ctx.rightexp) + ")";
    }

    @Override
    public String visitImplication(LineParser.ImplicationContext ctx) {
        // w/o parenthesis
        if (LineParser.RULE_root == ctx.parent.getRuleIndex()) {
            return visit(ctx.leftexp)
                    + Operator.IMPLICATION.getExpression()
                    + visit(ctx.rightexp);
        }
        // w/ parenthesis
        return "(" + visit(ctx.leftexp)
                + Operator.IMPLICATION.getExpression()
                + visit(ctx.rightexp) + ")";
    }

    @Override
    public String visitBiconditional(LineParser.BiconditionalContext ctx) {
        // w/o parenthesis
        if (LineParser.RULE_root == ctx.parent.getRuleIndex()) {
            return visit(ctx.leftexp)
                    + Operator.BICONDITIONAL.getExpression()
                    + visit(ctx.rightexp);
        }
        // w/ parenthesis
        return "(" + visit(ctx.leftexp)
                + Operator.BICONDITIONAL.getExpression()
                + visit(ctx.rightexp) + ")";
    }

}
