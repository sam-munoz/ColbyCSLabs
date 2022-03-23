/*
* File: task5.c
* Purpose: This file explores how strings that are too long can override decision variables
*
* Author: Samuel Munoz
* Date: 02-07-2020
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// fucntion that will copy the string in the function to a local char array 
void return_array(char str[], int length) {
	char field[5];
	strcpy(field, str);
	
	if(length <= 4) {
		printf("%s\tSafe\n", field);
	}
	else {
		printf("%s\tHacked\n", field);
	}
}

int main(int args, char *argv[]) {
	char a[] = "str";
	return_array(a, sizeof(a)/sizeof(char));
	
	char b[] = "strings";
	return_array(b, sizeof(b)/sizeof(char));

	return 0;
}
