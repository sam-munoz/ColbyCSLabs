/*
 * File: task1.cpp
 * Purpose: To show all the rules for naming identifier, variable declarations, and identifier scope
 * Author: Samuel Munoz
 * Date: 03-01-2020
 */

/*
Rules for Naming Identifiers:
1. C++ is case-sensitive so that Uppercase Letters and Lower Case letters are different
2. The name of identifier cannot begin with a digit. However, Underscore can be used as first character while declaring the identifier.
3. Only alphabetic characters, digits and underscore (_) are permitted in C++ language for declaring identifier.
4. Other special characters are not allowed for naming a variable / identifier
5. Keywords cannot be used as Identifier.
*/

#include <stdio.h>

int global_var = 12;

void function() {
	int i = 4;
	printf("in function: %d\n", i);
	printf("in function (global_var): %d\n", global_var);
} 

int main(int argc, char *argv[]) {

	int i = 1;
	printf("in main: %d\n", i);
	printf("in main (global_var): %d\n", global_var);
	for(int i=2;i < 3;i++) {
		printf("in for loop 1: %d\n", i);
		for(int i=3;i < 4;i++) {
			printf("in for loop 2: %d\n", i);
			function();
		}
	}

	

	return 0;
}

/*
Sources
http://www.c4learn.com/cplusplus/cpp-identifiers-tokens/
https://www.w3schools.in/cplusplus-tutorial/variables/
https://docs.microsoft.com/en-us/cpp/cpp/scope-visual-cpp?view=vs-2019
*/