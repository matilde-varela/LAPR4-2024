grammar Plugin;

start: (group)* EOF;

group: (question NEWLINE?)+;

question: type '"' PHRASE ('.' | '?') '"' choices answer;

type: TYPES '-' NUM ' ';
choices: choice*;

choice: NUM ') '  PHRASE ;

// Answers for the questions
answer: START_ANSWER answer_option ;

answer_option: true_false_a | text |  date_a | time_a | whole_n_a |  scale_a | decimal_a  ;

text:  PHRASE ;
whole_n_a:   NUM+;
true_false_a:  BOOLEAN ;
scale_a:   NUM+ '-'  NUM+;
decimal_a:  NUM+ '.' NUM+;
date_a:  NUM NUM '/' NUM NUM '/' NUM NUM NUM NUM;
time_a:  NUM NUM ':' NUM NUM;

BOOLEAN: 'True' | 'False' | 'true' | 'false';
START_ANSWER: 'A:. ';
PHRASE: (WORD ' ')* WORD;
WORD : ([a-zA-Z]|[-]|[/])+;
NEWLINE: '\r'? '\n'->skip;
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
