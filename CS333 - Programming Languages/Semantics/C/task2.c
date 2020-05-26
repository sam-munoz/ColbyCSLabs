/*
 * File: task2.c
 * Purpose: To create variables that have a function data type and use the variable to run a factorial-based function
 * Author: Samuel Munoz
 * Date: 03-06-2020
 */

#include <stdio.h>
#include <stdlib.h>

int factorial(const int i) {
	if(i == 1)
	       return 1;
	else
		return i * factorial(i-1);	
}

int main(int argc, char **argv) {
	if(argc < 2) {
		printf("Command: ./task2 <base factorial>\n");
		return 0;
	}

	int (*calc)(const int);

	calc = factorial;

	int base = atoi(argv[1]);

	printf("Value of %d!: %d\n", base, calc(base));

	return 0;
}
