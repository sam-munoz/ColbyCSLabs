/*
 * This file is looks at the byte ordering of different data types
 *
 * Author: Samuel Munoz
 * Date: 02-06-2020
 */
#include <stdio.h>

// main or exexcutation method
int main(int args, char* argv[]) {
	int c = 257;
	long s = 2048;
	unsigned char* ptr = (unsigned char*) &c;

	printf("For integer\n");
	for(int i=0;i<sizeof(int);i++) {
		printf("%d: %02X\n", i, ptr[i]);
	}

	ptr = (unsigned char*) &s;

	printf("\nFor long\n");
	for(int i=0;i<sizeof(long);i++) {	
		printf("%d: %02X\n", i, ptr[i]);
	}

	return 0;
}
