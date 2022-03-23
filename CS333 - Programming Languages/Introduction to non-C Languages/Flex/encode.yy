/*
 * File: encode.yy
 * Purpose: To shift characters in the alphabet by 13
 * Author: Samuel Munoz
 * Date: 02-21-2020
 */

%%

// this should be a comment
[A-Z]	printf("%c", (*yytext+13-13)%26 + 65);
[a-z]	printf("%c", (*yytext+13-19)%26 + 97);

%%
int main(int argc, char *argv[]) {
	yylex();

	return 0;
}
