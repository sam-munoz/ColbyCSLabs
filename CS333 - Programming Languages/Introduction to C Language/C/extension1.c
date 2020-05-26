/*
 * File: extension1.c
 * Purpose: To create hacked and safe strings using the command line
 * Author: Samuel Munoz
 * Date: 02-17-2020
 */
#include <stdio.h>
#include <string.h>

int find_length(char *str) {
	int i=0;
	while(*(str+i) != '\0') {
		i++;
	}

	return i;
}

void safe_hacked(char *str, int length) {
	char field[5];
	strcpy(field, str);

	if(length < 5) {
		printf("%s\tSafe\n", field);
	}
	else {
		printf("%s\tHacked\n", field);
	}
}

int main(int args, char *argv[]) {
	if(args == 2) {
		char *str = argv[1];

		safe_hacked(str, find_length(str));
	}
	else {
		printf("Please enter one command line argument");
	}
	return 0;
}
