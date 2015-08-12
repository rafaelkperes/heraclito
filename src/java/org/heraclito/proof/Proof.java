/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.proof;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.heraclito.parser.header.HeaderLexer;
import org.heraclito.parser.header.HeaderParser;
import org.heraclito.parser.header.visitor.HypothesysVisitor;
import org.heraclito.parser.header.visitor.ResultVisitor;

/**
 *
 * @author Rafael
 */
public class Proof {

    private ParserRuleContext treeroot;
    private List<Expression> hypothesis;
    private Expression result;
    private String header;
    
    private List<Line> lines;

    public Proof(String header) throws ProofException {
        setHeader(header);
        this.lines = new ArrayList();
    }

    private void setHeader(String header) throws ProofException {        
        parseHeader(header);
        setHypothesis();
        setResult();
        
        String anotherHeader = "";
        Iterator hypIt = this.hypothesis.iterator();
        anotherHeader += hypIt.next().toString();
        while(hypIt.hasNext()) {
            
            anotherHeader += ", " + hypIt.next().toString();
        }
        anotherHeader += " |- " + this.result.toString();
        this.header = anotherHeader;
    }

    private void parseHeader(String header) throws ProofException {
        ANTLRInputStream input = new ANTLRInputStream(header);
        TokenStream tokens = new CommonTokenStream(new HeaderLexer(input));

        HeaderParser parser = new HeaderParser(tokens);
        try {
            this.treeroot = parser.root(); // parse
        } catch (IllegalStateException e) {
            throw new ProofException("exception_invalid_header_input");
        }
    }

    private void setHypothesis() throws ProofException {
        HypothesysVisitor hypothesisVisitor = new HypothesysVisitor();

        List<Expression> hypothesisList = new ArrayList<>();
        for (String hyp : hypothesisVisitor.visit(this.treeroot)) {
            hypothesisList.add(0, new Expression(hyp));
        }
        this.hypothesis = hypothesisList;
    }

    private void setResult() throws ProofException {
        ResultVisitor resultVisitor = new ResultVisitor();
        this.result = new Expression(resultVisitor.visit(this.treeroot));
    }

    public String getHeader() {
        return this.header;
    }
    
    public void addAllHypothesis() {
        for(Expression exp : this.hypothesis) {
            this.lines.add(new Line(exp, Rule.CH));
        }
    }
    
    @Override
    public String toString() {
        return this.printProof();
    }

    private String printProof() {
        StringBuilder proofString = new StringBuilder(this.getHeader());
        for(Line line : this.lines) {
            proofString.append("\n").append(line);
        }
        return proofString.toString();
    }
    
}
