/*
 * File: task4.yy
 * Purpose: Create a mimi-parser
 * Author: Samuel Munoz
 * Date: 02-21-2020
 */

%%

[' ']
['\n']
"if"|"else"|"for"|"int"|"float"		printf("Keyword-%s\n", (char *) yytext);
[a-z]+|[A-Z]+				printf("Identifier-%s\n", (char *) yytext);
['=']					printf("Assignment\n");
['{']					printf("Open-bracket\n");
['}']					printf("Close-bracket\n");
['(']					printf("Open-paren\n");
[')']					printf("Close-paren\n");
['==' '<' '>' '<=' '>=']		printf("Comparison-%s\n", (char * )yytext);
['+' '-' '*' '/']			printf("Operator-%c\n", *yytext);
[0-9]+					printf("Integer-%s\n", (char *) yytext);
[0-9]+\.[0-9]+				printf("Float-%s\n", yytext);
.

%% 
int main(int argc, char *argv[]) {
	yyin = fopen(argv[1], "r");

	yylex();

	fclose(yyin);

	return 0;
}
