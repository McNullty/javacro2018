lexer grammar FilterLexer;

EQ
    : 'eq'
    | 'EQ'
    | 'Eq'
    ;

NOT_EQ
    : 'ne'
    | 'NE'
    | 'Ne'
    ;
   
GT
    : 'gt'
    | 'GT'
    | 'Gt'
    ;

LT
    : 'lt'
    | 'LT'
    | 'Lt'
    ;   

AND
    : 'and'
    | 'AND'
    | 'And'
    ;

OR
    : 'or'
    | 'OR'
    | 'Or'
    ;
   
LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;   

QUOTE 
    : '\''
    ;

WS
    : [ \r\n\t] + -> skip
    ;

ID  
    : ('a'..'z'|'A'..'Z'|'.')+
    ;
    
STRING
    : QUOTE (.)+? QUOTE
    ;
    
NUMBER
    : [0-9]+('.'[0-9]*)?
    ;


