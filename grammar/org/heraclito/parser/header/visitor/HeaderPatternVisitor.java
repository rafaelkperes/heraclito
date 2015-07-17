/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.header.visitor;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.heraclito.parser.header.HeaderBaseVisitor;
import org.heraclito.parser.header.HeaderParser;
import org.heraclito.proof.Expression;
import org.heraclito.proof.ProofException;

/**
 *
 * @author Rafael
 */
public class HeaderPatternVisitor extends HeaderBaseVisitor<String> {

    @Override
    public String visitRoot(HeaderParser.RootContext ctx) {
        String resultStr;
        try {
            resultStr = (new Expression(ctx.result.toString()).toString());
        } catch (ProofException ex) {
            throw new ParseCancellationException(ex.getMessage());
        }
        return visit(ctx.hyplist) + " |- " + resultStr;
    }

    @Override
    public String visitHypothesys(HeaderParser.HypothesysContext ctx) {
        String childrenStr = visit(ctx.hyplist);
        String str;
        try {
            str = new Expression(ctx.expvalue.toString()).toString();
        } catch (ProofException ex) {
            throw new ParseCancellationException(ex.getMessage());
        }
        return str + ", " + childrenStr;
    }

    @Override
    public String visitLastHypothesys(HeaderParser.LastHypothesysContext ctx) {
        try {
            return new Expression(ctx.expvalue.toString()).toString();
        } catch (ProofException ex) {
            throw new ParseCancellationException(ex.getMessage());
        }
    }

}
