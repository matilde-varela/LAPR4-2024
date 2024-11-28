grammar SymbolTable;

start : (line NEWLINE?)+;

line : type question choices? evals;

evals : answer_eval*;

answer_eval: '/' (PHRASE ';'?)* ':' numbers
           | '/' answer_numbers ':' numbers
           | '/' condition
           | '/' date ':' numbers
           | '/' BOOLEAN ':' numbers
           | '/' scale ':' numbers
           | '/' decimal ':' numbers
           | '/' time ':' numbers
           ;

date:  NUM NUM '-' NUM NUM '-' NUM NUM NUM NUM;

scale:   NUM+ '-'  NUM+;
decimal:  NUM+ '.' NUM+;
time:  NUM NUM ':' NUM NUM;

condition : SIGNALS+;

answer_numbers : numbers;

choices : choice+;

choice : '/' (PHRASE ';'?)*;

type : TYPES'-' numbers;
question: '/' PHRASE ('.' | '?');
numbers : NUM+;


BOOLEAN: 'True' | 'False' | 'true' | 'false';
START_ANSWER: 'A:. ';
PHRASE: (WORD ' ')* WORD;
WORD : ([a-zA-Z]|[-,])+;
NEWLINE: '\r'? '\n'->skip;
SIGNALS: ([<]|[=]|[>])+;
NUM: [0-9];
TYPES: '[SC]'
    | '[MC]'
    | '[WN]'
    | '[TF]'
    | '[NS]'
    | '[DN]'
    | '[SA]'
    | '[D]'
    | '[T]'
    ;
