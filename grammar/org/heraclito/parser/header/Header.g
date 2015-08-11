grammar Header;

options
{
  // antlr will generate java lexer and parser
  language = Java;
}

//as the generated lexer will reside in org.meri.antlr_step_by_step.parsers 
//package, we have to add package declaration on top of it
@lexer::header {
package org.heraclito.parser.header;
}

//as the generated parser will reside in org.meri.antlr_step_by_step.parsers 
//package, we have to add package declaration on top of it
@parser::header {
package org.heraclito.parser.header;
}

// ***************** lexer rules:
//the grammar must contain at least one lexer rule
VAR : (('A'..'U') | ('X'..'Z') | ('a'..'u') | ('x'..'z')) ;
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;

// ***************** parser rules:
root
    : hyplist=hypothesysList '|-' result=exp
    ;

hypothesysList
    : expvalue=exp                              #lastHypothesys
    | expvalue=exp ',' hyplist=hypothesysList      #hypothesys
    ;

exp
    : VAR                                                               
    | '~' exp                                         
    | exp '^' exp                
    | exp 'v' exp                
    | <assoc=right>exp '->' exp                            
    | <assoc=right>exp '<->' exp                           
    | '(' exp ')'                                              
    ;