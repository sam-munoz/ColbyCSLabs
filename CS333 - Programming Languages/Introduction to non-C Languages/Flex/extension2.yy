/*
* File: extension2.yy
* Purpose: to create a more complex encoder than the encoder made earlier
* Author: Samuel Munoz
* Date: 02/26/2020
*/

%%

[a|e]	printf("%c", (char) ((int) *yytext)+4);
[i|o]	printf("%c", (char) ((int) *yytext)+6);
[u]	printf("%c", (char) ((int) *yytext)-20);
.	printf("%c", (char) (((int) *(yytext)+100-19)%26)+97);

%%
int main(int argc, char *argv[]) {
	yylex();

	return 0;
}
