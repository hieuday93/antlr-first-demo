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
                    |   propertyPath 'inContains' stringList                    # propertyConditionInContains
                    |   propertyPath 'hasSomeOf' anyTypeValues                  # propertyConditionHasSomeOf
                    |   propertyPath 'hasNoneOf' anyTypeValues                  # propertyConditionHasNoneOf
                    |   propertyPath 'isDay' anyDateValue                       # propertyConditionIsDay
                    |   propertyPath 'isNotDay' anyDateValue                    # propertyConditionIsNotDay
                    ;

propertyPath: FIELD ('.' FIELD)*;

anyTypeValue    :   STRING | DATE | integer | DECIMAL;
nonPureTextValue:   DATE | integer | DECIMAL;
anyDateValue    :   DATE;
anyTypeValues    :   '[' (stringList | DATELIST | integerList | DECIMALLIST) ']';
anyTypeRange    :   dateRange | integerRange | decimalRange;

dateRange   :  DATE AND DATE;
integerRange    :   integer AND integer;
decimalRange    :   DECIMAL AND DECIMAL;


AND :   'AND';
OR  :   'OR';

integer :   DIGIT+;
integerList :   integer (',' integer)*;

DECIMAL :   (DIGIT+)'.'(DIGIT+);
DECIMALLIST :   DECIMAL (',' DECIMAL)*;

FIELD   : (CHAR(CHAR|DIGIT)+);
STRING  : DOUBLEQUOTE [a-zA-Z0-9 ]+ DOUBLEQUOTE;
stringList  :   STRING (',' STRING)*;

DATE    :   DOUBLEQUOTE DATECONTENT (' ' TIME)? DOUBLEQUOTE;
DATECONTENT:    DIGIT4CHAR '-' DIGIT2CHAR '-' DIGIT2CHAR;
DATELIST:   DATE (',' DATE)*;
TIME    :   DIGIT2CHAR COLON DIGIT2CHAR COLON DIGIT2CHAR;

DOUBLEQUOTE:    '"';
COLON: ':';
DIGIT   : [0-9];
DIGIT2CHAR: DIGIT DIGIT;
DIGIT4CHAR: DIGIT DIGIT DIGIT DIGIT;
CHAR    : [a-zA-Z];
WS  : [ \t\r\n-] -> skip;
