/*
 * File: task2.yy
 * Purpose: Write a regex file that information about the text file
 * Author: Samuel Munoz
 * Date: 02-21-2020
 */

%{
	int lineCount = 1;
	int vowelCount = 0;
	int charCount = 0;
%}

%%

[a e i o u]|[A E I O U]	{
	charCount++;
	vowelCount++;
}

['\n']	lineCount++;

[a-z]|[A-Z]	charCount++;	

.

%%
int main(int argc, char *argv[]) {
	
	yyin = fopen(argv[1], "r");

	yylex();

	fclose(yyin);

	printf("Number of lines: %d\nNumber of characters: %d\nNumber of vowels: %d\n", lineCount, charCount, vowelCount);

	return 0;
}
