/*
* File: extension1.yy
* Purpose: This file is meant to show the different ways to make comments in flex
* Author: Samuel Munoz
* Date: 02/26/2020
*/
%{
	// only inside these brackets to single line comments work
%}
/* However, outside of the brackets, this is the only way to make comments
   This is a multiple line comment */

%%

.	printf("%c ", *yytext);

%%
int main(int argc, char *argv[]) {
	yylex();

	return 0;
}
