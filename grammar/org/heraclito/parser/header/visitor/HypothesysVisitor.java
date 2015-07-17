/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.header.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.heraclito.parser.header.HeaderBaseVisitor;
import org.heraclito.parser.header.HeaderParser;
import org.heraclito.proof.Expression;
import org.heraclito.proof.ProofException;

/**
 *
 * @author Rafael
 */
public class HypothesysVisitor extends HeaderBaseVisitor<List<Expression>> {

    @Override
    public List<Expression> visitRoot(HeaderParser.RootContext ctx) {
        return visit(ctx.hyplist);
    }

    @Override
    public List<Expression> visitHypothesys(HeaderParser.HypothesysContext ctx) {
        List expressions = visit(ctx.hyplist);
        try {
            expressions.add(new Expression(ctx.expvalue.toString()));
        } catch (ProofException ex) {
            throw new ParseCancellationException(ex.getMessage());
        }
        return expressions;
    }

    @Override
    public List<Expression> visitLastHypothesys(HeaderParser.LastHypothesysContext ctx) {
        List<Expression> expressions =  new ArrayList<>();
        try {
            expressions.add(new Expression(ctx.expvalue.toString()));
        } catch (ProofException ex) {
            throw new ParseCancellationException(ex.getMessage());
        }
        return expressions;
    }

}
