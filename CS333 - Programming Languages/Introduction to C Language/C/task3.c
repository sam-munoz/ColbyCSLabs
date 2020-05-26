/*
* This file explores how memory is affected when constantly allocating memory to unsigned char pointer
*
* Author: Samuel Munoz
* Date: 02-07-2020
*/
#include <stdio.h>
#include <stdlib.h>

void memory_growth_without_free(unsigned char* ptr) {
	while(1) {
		ptr = malloc(sizeof(char));
	}
}

void memory_growth_with_free(unsigned char* ptr) {
	while(1) {
		ptr = malloc(sizeof(char));
		free(ptr);
	}
}

int main(int args, char* argv[]) {
	unsigned char* ptr;

	memory_growth_without_free(ptr);
	// memory_growth_with_free(ptr);

	return 0;
}
