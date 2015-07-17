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
ANYTHING : (.)+ ;
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;

// ***************** parser rules:
root
    : hyplist=hypothesysList '|-' result=ANYTHING
    ;

hypothesysList
    : expvalue=ANYTHING                              #lastHypothesys
    | expvalue=ANYTHING ',' hyplist=hypothesysList      #hypothesys
    ;