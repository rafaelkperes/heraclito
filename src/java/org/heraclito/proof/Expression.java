package org.heraclito.proof;

import antlrparser.LineLexer;
import antlrparser.LineParser;
import antlrparser.visitor.LeftExpressionVisitor;
import antlrparser.visitor.MainOperatorVisitor;
import antlrparser.visitor.RightExpressionVisitor;
import antlrparser.visitor.StringPatternVisitor;
import java.util.Objects;
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
    
    /**
     * Returns the main operator of the line or null if there's no operator.
     * 
     * @return main operator of the line or null if there's no operator.
     */
    public Operator getMainOperator() {
        try {
            MainOperatorVisitor operatorVisitor = new MainOperatorVisitor();
            Operator ret = operatorVisitor.visit(this.parserRoot);
            return ret;
        } catch (IllegalStateException e) {
            return null;
        }
    }
    
     /**
     * Returns the expression to the left of the main operator, or null if
     * there's no expression to the left of the main operator or no main
     * operator.
     *
     * @return expression to the left of the main operator (may be null).
     */
    public Expression getLeftExpression() {
        try {
            LeftExpressionVisitor stringVisitor = new LeftExpressionVisitor();
            String ret = stringVisitor.visit(this.parserRoot);
            if (null == ret) {
                return null;
            }
            return new Expression(ret);
        } catch (ProofException | IllegalStateException e) {
            return null;
        }
    }

    /**
     * Returns the expression to the right of the main operator, or null if
     * there's no expression to the right of the main operator or no main
     * operator.
     *
     * @return expression to the right of the main operator (may be null).
     */
    public Expression getRightExpression() {
        try {
            RightExpressionVisitor stringVisitor = new RightExpressionVisitor();
            String ret = stringVisitor.visit(this.parserRoot);
            if (null == ret) {
                return null;
            }
            return new Expression(ret);
        } catch (ProofException | IllegalStateException e) {
            return null;
        }
    }
    
    
    /**
     * Returns the string for this expression.
     * The string is the original entry modified to match the expression 
     * pattern.
     * 
     * @return string representing the expression.
     */
    @Override
    public String toString() {
        return this.expression;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.expression);
        hash = 13 * hash + Objects.hashCode(this.parserRoot);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expression other = (Expression) obj;
        if (!Objects.equals(this.expression, other.expression)) {
            return false;
        }
        return true;
    }
    
    
}
