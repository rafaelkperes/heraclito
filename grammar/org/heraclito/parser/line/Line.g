grammar Line;

options
{
  // antlr will generate java lexer and parser
  language = Java;
}

//as the generated lexer will reside in org.meri.antlr_step_by_step.parsers 
//package, we have to add package declaration on top of it
@lexer::header {
package org.heraclito.parser.line;
}

//as the generated parser will reside in org.meri.antlr_step_by_step.parsers 
//package, we have to add package declaration on top of it
@parser::header {
package org.heraclito.parser.line;
}

// ***************** lexer rules:
//the grammar must contain at least one lexer rule
VAR : (('A'..'U') | ('X'..'Z') | ('a'..'u') | ('x'..'z')) ;
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;

// ***************** parser rules:
//our grammar accepts only salutation followed by an end symbol
root
    : '(' root ')'
    | exp
    ;
exp
    : VAR                                                               #var
    | operator='~' rightexp=exp                                         #negation
    | leftexp=exp operator='^' rightexp=exp                #conjunction
    | leftexp=exp operator='v' rightexp=exp                #disjunction
    | <assoc=right>leftexp=exp operator='->' rightexp=exp                            #implication
    | <assoc=right>leftexp=exp operator='<->' rightexp=exp                           #biconditional
    | '(' expvalue=exp ')'                                              #toExp
    ;