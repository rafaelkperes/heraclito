/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.parser.header.visitor;
import org.heraclito.parser.header.HeaderBaseVisitor;
import org.heraclito.parser.header.HeaderParser;
/**
 *
 * @author Rafael
 */
public class ResultVisitor extends HeaderBaseVisitor<String> {

    @Override
    public String visitRoot(HeaderParser.RootContext ctx) {
        return ctx.result.getText();
    }
    
}
