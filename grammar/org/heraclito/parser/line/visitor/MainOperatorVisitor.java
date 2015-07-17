/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.line.visitor;

import org.heraclito.parser.line.LineParser;
import org.heraclito.parser.line.LineBaseVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.heraclito.proof.Operator;

/**
 *
 * @author Rafael
 */
public class MainOperatorVisitor extends LineBaseVisitor<Operator> {

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, Operator currentResult) {
        return currentResult == null;
    }

    @Override
    public Operator visitNegation(LineParser.NegationContext ctx) {
        return Operator.NEGATION;
    }

    @Override
    public Operator visitConjunction(LineParser.ConjunctionContext ctx) {
        return Operator.CONJUNCTION;
    }

    @Override
    public Operator visitDisjunction(LineParser.DisjunctionContext ctx) {
        return Operator.DISJUNCTION;
    }

    @Override
    public Operator visitImplication(LineParser.ImplicationContext ctx) {
        return Operator.IMPLICATION;
    }

    @Override
    public Operator visitBiconditional(LineParser.BiconditionalContext ctx) {
        return Operator.BICONDITIONAL;
    }

}
