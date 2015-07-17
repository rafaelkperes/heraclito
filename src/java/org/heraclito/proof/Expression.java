package org.heraclito.proof;

import antlrparser.LineLexer;
import antlrparser.LineParser;
import antlrparser.visitor.StringPatternVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
class Expression {

    private String expression;
    private ParserRuleContext parserRoot;
    
    Expression(String expression) throws ProofException {
        setStringValue(expression);
    }
    
    private void setStringValue(String stringValue) throws ProofException {
        ANTLRInputStream input = new ANTLRInputStream(stringValue);
        TokenStream tokens = new CommonTokenStream(new LineLexer(input));

        LineParser parser = new LineParser(tokens);

        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                    Object offendingSymbol, int line, int charPositionInLine,
                    String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line "
                        + line + " due to " + msg, e);
            }
        });

        try {
            ParserRuleContext tree = parser.root(); // parse
            this.parserRoot = tree;
            StringPatternVisitor stringVisitor = new StringPatternVisitor();
            this.expression = stringVisitor.visit(tree);
        } catch (IllegalStateException e) {
            throw new ProofException("exception_invalid_line_input");
        }
    }
    
    @Override
    public String toString() {
        return this.expression;
    }
    
}
