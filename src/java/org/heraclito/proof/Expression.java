package org.heraclito.proof;

import java.io.Serializable;
import java.util.Objects;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.heraclito.parser.line.LineLexer;
import org.heraclito.parser.line.LineParser;
import org.heraclito.parser.line.visitor.LeftExpressionVisitor;
import org.heraclito.parser.line.visitor.MainOperatorVisitor;
import org.heraclito.parser.line.visitor.RightExpressionVisitor;
import org.heraclito.parser.line.visitor.StringPatternVisitor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class Expression implements Serializable {

    private String expression;
    private transient ParserRuleContext parserRoot;

    public Expression(String expression) throws ProofException {
        setStringValue(expression);
    }

    public static String formatExpression(String stringValue) throws ProofException {
        ANTLRInputStream input = new ANTLRInputStream(stringValue);
        TokenStream tokens = new CommonTokenStream(new LineLexer(input));

        LineParser parser = new LineParser(tokens);

        try {
            ParserRuleContext tree = parser.root(); // parse
            StringPatternVisitor stringVisitor = new StringPatternVisitor();
            return stringVisitor.visit(tree);
        } catch (IllegalStateException e) {
            throw new ProofException("exception.invalid.expression.input");
        }
    }

    private void setStringValue(String stringValue) throws ProofException {
        ANTLRInputStream input = new ANTLRInputStream(stringValue);
        TokenStream tokens = new CommonTokenStream(new LineLexer(input));

        LineParser parser = new LineParser(tokens);

        try {
            ParserRuleContext tree = parser.root(); // parse
            this.parserRoot = tree;
            StringPatternVisitor stringVisitor = new StringPatternVisitor();
            this.expression = stringVisitor.visit(tree);
        } catch (IllegalStateException e) {
            throw new ProofException("exception.invalid.expression.input");
        }
    }

    /**
     * Returns the main operator of the line or null if there's no operator.
     *
     * @return main operator of the line or null if there's no operator.
     */
    public Operator getMainOperator() {
        if(this.parserRoot == null) {            
            try {
                setStringValue(this.expression);
            } catch (ProofException ex) {
                return null;
            }
        }
        
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
        if(this.parserRoot == null) {            
            try {
                setStringValue(this.expression);
            } catch (ProofException ex) {
                return null;
            }
        }
        
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
        if(this.parserRoot == null) {            
            try {
                setStringValue(this.expression);
            } catch (ProofException ex) {
                return null;
            }
        }
        
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
     * Returns the string for this expression. The string is the original entry
     * modified to match the expression pattern.
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

    public boolean isContradictionOf(Expression exp) {
        if (Operator.NEGATION.equals(this.getMainOperator())) {
            if (this.getRightExpression().equals(exp)) {
                return true;
            }
        }
        if (Operator.NEGATION.equals(exp.getMainOperator())) {
            if (exp.getRightExpression().equals(this)) {
                return true;
            }
        }
        return false;
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
