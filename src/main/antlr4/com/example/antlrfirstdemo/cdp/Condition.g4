grammar Condition;

condition   :   propertyCondition                       # singleCondition
            |   condition op=(AND|OR) condition      # relationCondition
            |   '(' condition ')'                       # groupCondition
            ;

propertyCondition   :   propertyPath 'equals' anyTypeValue                      # propertyConditionEquals
                    |   propertyPath 'notEquals' anyTypeValue                   # propertyConditionNotEquals
                    |   propertyPath 'greaterThan' nonPureTextValue             # propertyConditionGreaterThan
                    |   propertyPath 'greaterThanOrEqualTo' nonPureTextValue    # propertyConditionGreaterThanOrEqualTo
                    |   propertyPath 'lessThan' nonPureTextValue                # propertyConditionLessThan
                    |   propertyPath 'lessThanOrEqualTo' nonPureTextValue       # propertyConditionLessThanOrEqualTo
                    |   propertyPath 'between' anyTypeRange                     # propertyConditionBetween
                    |   propertyPath 'exists'                                   # propertyConditionExists
                    |   propertyPath 'missing'                                  # propertyConditionMissing
                    |   propertyPath 'contains' STRING                          # propertyConditionContains
                    |   propertyPath 'notContains' STRING                       # propertyConditionNotContains
                    |   propertyPath 'startsWith' STRING                        # propertyConditionStartsWith
                    |   propertyPath 'endsWith' STRING                          # propertyConditionEndsWith
                    |   propertyPath 'matchesRegex' STRING                      # propertyConditionMatchesRegex
                    |   propertyPath 'in' anyTypeValues                         # propertyConditionIn
                    |   propertyPath 'notIn' anyTypeValues                      # propertyConditionNotIn
                    |   propertyPath 'all' anyTypeValues                        # propertyConditionAll
                    |   propertyPath 'inContains' STRINGLIST                    # propertyConditionInContains
                    |   propertyPath 'hasSomeOf' anyTypeValues                  # propertyConditionHasSomeOf
                    |   propertyPath 'hasNoneOf' anyTypeValues                  # propertyConditionHasNoneOf
                    |   propertyPath 'isDay' anyDateValue                       # propertyConditionIsDay
                    |   propertyPath 'isNotDay' anyDateValue                    # propertyConditionIsNotDay
                    ;

propertyPath: FIELD ('.' FIELD)*;

anyTypeValue    :   STRING | DATE | DATETIME | INT | DECIMAL;
nonPureTextValue:   DATE | DATETIME | INT | DECIMAL;
anyDateValue    :   DATE | DATETIME;
anyTypeValues    :   '[' STRINGLIST | DATELIST | DATETIMELIST | INTLIST | DECIMALLIST ']';
anyTypeRange    :   DATERANGE | DATETIMERANGE | INTRANGE | DECIMALRANGE;
multipleValues: '[' STRING (',' STRING)* ']';

DATERANGE   :  DATE AND DATE;
DATETIMERANGE   : DATETIME AND DATETIME;
INTRANGE    :   INT AND INT;
DECIMALRANGE    :   DECIMAL AND DECIMAL;


//PATH    :   FIELD ('.' FIELD)*;
AND :   'AND';
OR  :   'OR';

INT :   DIGIT+;
INTLIST :   INT (',' INT)*;

DECIMAL :   (DIGIT+)'.'(DIGIT+);
DECIMALLIST :   DECIMAL (',' DECIMAL)*;

FIELD   : (CHAR(CHAR|DIGIT)+);
STRING  : '"' [a-zA-Z0-9 ]+ '"';
STRINGLIST  :   STRING (',' STRING)*;

YEAR    :   DIGIT DIGIT DIGIT DIGIT;
MONTH   :   ('0'[1-9]) | ('1'[0-2]);
DAY :   ('0'[1-9]) | ([1-2]DIGIT) | ('3'[0-1]);
DATE    :   '"' YEAR '-' MONTH '-' DAY '"';
DATELIST:   DATE (',' DATE)*;

HOUR    :   ('0'[1-9]) | ('1'[0-9]) | ('2'[0-3]);
MINUTE  :   ('0'[1-9]) | ([1-5]DIGIT);
SECOND  :   ('0'[1-9]) | ([1-5]DIGIT);
TIME    :   HOUR ':' MINUTE ':' SECOND;

DATETIME    : '"' YEAR '-' MONTH '-' DAY ' ' TIME '"';
DATETIMELIST    :   DATETIME (',' DATETIME)*;

DIGIT   : [0-9];
CHAR    : [a-zA-Z];
WS  : [ \t\r\n-] -> skip;
