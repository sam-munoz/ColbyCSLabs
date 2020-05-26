/*
 * File: task3.yy
 * Purpose: To strip down a html file of all its tags
 * Author: Samuel Munoz
 * Date: 02-21-2020
 */

%%

\<[^>]+\>
[\t]
.	printf("%c", *yytext);

%%
int main(int argc, char *argv[]) {
	yyin = fopen(argv[1], "r");

	yylex();

	fclose(yyin);

	printf("\n");

	return 0;
}
