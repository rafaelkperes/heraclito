/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenStream;
import org.heraclito.parser.header.HeaderLexer;
import org.heraclito.parser.header.HeaderParser;

/**
 *
 * @author Rafael
 */
public class Proof {
    
    String header;

    public Proof(String header) throws ProofException {
        setHeader(header);
    }
    
    private void setHeader(String header) throws ProofException {
        ANTLRInputStream input = new ANTLRInputStream(header);
        TokenStream tokens = new CommonTokenStream(new HeaderLexer(input));

        HeaderParser parser = new HeaderParser(tokens);

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
            //HeaderPatternVisitor headerPatternVisitor = new HeaderPatternVisitor();
            //this.header = headerPatternVisitor.visit(tree);
        } catch (IllegalStateException e) {
            throw new ProofException("exception_invalid_header_input");
        }
    }
    
    public String getHeader() {
        return this.header;
    }
    
}
