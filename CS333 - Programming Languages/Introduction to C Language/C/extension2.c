/*
 * File: extension2.c
 * Purpose: To show other runtime errors in c
 * Author: Samuel Munoz
 * Date: 02-19-20
 */
#include <stdio.h>
#include <stdlib.h>

int main(int args, char *argv[]) {
	char a = *argv[1];
	char b = *argv[2];
	
	printf("%c\t%c\n", a, b);
	
	return 0;
}
