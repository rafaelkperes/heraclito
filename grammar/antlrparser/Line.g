grammar Line;

options
{
  // antlr will generate java lexer and parser
  language = Java;
}

//as the generated lexer will reside in org.meri.antlr_step_by_step.parsers 
//package, we have to add package declaration on top of it
@lexer::header {
package antlrparser;
}

//as the generated parser will reside in org.meri.antlr_step_by_step.parsers 
//package, we have to add package declaration on top of it
@parser::header {
package antlrparser;
}

// ***************** lexer rules:
//the grammar must contain at least one lexer rule
VAR : (('A'..'U') | ('X'..'Z')) ;
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
    | <assoc=right>leftexp=exp operator='^' rightexp=exp                #conjunction
    | <assoc=right>leftexp=exp operator='v' rightexp=exp                #disjunction
    | leftexp=exp operator='->' rightexp=exp                            #implication
    | leftexp=exp operator='<->' rightexp=exp                           #biconditional
    | '(' expvalue=exp ')'                                              #toExp
    ;