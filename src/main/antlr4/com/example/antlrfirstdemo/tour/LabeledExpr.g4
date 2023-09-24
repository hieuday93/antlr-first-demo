grammar LabeledExpr;

prog:   stat+;

stat:   expr NEWLINE            # printExpr
    |   ID '=' expr NEWLINE     # assign
    |   NEWLINE                 # blank
    |   CE                      # clear
    ;

expr:   expr op=(MUL|DIV) expr  # MulDiv
    |   expr op=(ADD|SUB) expr  # AddSub
    |   INT                     # int
    |   ID                      # id
    |   '(' expr ')'            # parens
    ;

MUL :   '*';
DIV :   '/';
ADD :   '+';
SUB :   '-';

CE  : 'CE';

INT :   [0-9]+;
ID  :   [a-zA-Z]+;
NEWLINE:    '\r'? '\n';
WS  :   [ \t] -> skip;