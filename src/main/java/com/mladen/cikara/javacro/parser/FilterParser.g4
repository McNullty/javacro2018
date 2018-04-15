parser grammar FilterParser;

options { tokenVocab=FilterLexer; }

logical_operator
    : AND
    | OR
    ;

relational_operator
    : EQ
    | GT
    | NOT_EQ
    | LT
    ;

expression_list
    : e (logical_operator e)*
    ;

e 
    : expression
    | expression_with_paren
    ;

expression_with_paren
    : LPAREN expression RPAREN
    ;

expression
    : se (logical_operator se)*
    ;

se 
    : simple_expression
    | simple_expression_with_paren
    ;

simple_expression_with_paren
    : LPAREN simple_expression RPAREN
    ;

simple_expression
    : field relational_operator value
    ;

value
    : number
    | string
    ;

number
    : NUMBER
    ;

string
    : STRING
    ;

field
    : ID
    ;
