/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.header.visitor;

import java.util.ArrayList;
import java.util.List;
import org.heraclito.parser.header.HeaderBaseVisitor;
import org.heraclito.parser.header.HeaderParser;
/**
 *
 * @author Rafael
 */
public class HypothesysVisitor extends HeaderBaseVisitor<List<String>> {

    @Override
    public List<String> visitRoot(HeaderParser.RootContext ctx) {
        return visit(ctx.hyplist);
    }

    @Override
    public List<String> visitHypothesys(HeaderParser.HypothesysContext ctx) {
        List expressions = visit(ctx.hyplist);
        expressions.add(ctx.expvalue.getText());
        return expressions;
    }

    @Override
    public List<String> visitLastHypothesys(HeaderParser.LastHypothesysContext ctx) {
        List<String> expressions = new ArrayList<>();
        expressions.add(ctx.expvalue.getText());
        return expressions;
    }

}
