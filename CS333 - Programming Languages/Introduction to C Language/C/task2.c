/*
 * This file looks at the byte order of an unsigned char pointer
 *
 * Author: Samuel Munoz
 * Date: 02-06-2020
 */
#include <stdio.h>
#include <stdlib.h>

 // main/executuable method
 int main(int arg, char* argv[]) {
	int a = 7;
	int c = 2;
	int b = 15;
	 
	unsigned char* ptr;
	ptr = (unsigned char*) &ptr;

	for(int i=0;i<200;i++) {
		if(i%4 == 0 && i != 0) {
			printf("\n");
		}
		printf("%d: %02X\n", i, ptr[i]);
	}

	return 0;
 } 
