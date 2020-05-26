/*
 * File: extension3.c
 * Purpose: To find a floating point number if I add one to it, the value of the new variable will be zero
 * Author: Samuel Munoz
 * Date: 02-19-20
 */
#include <stdio.h>
#include <stdlib.h>

float max_value() {
	float i = 1.0;
	for(;;) {
		if(i+1 == i) {
			return i;
		}
		i += 1.0;
	}
}

int main() {
	float MAX_VALUE = max_value();

	printf("%f\n", MAX_VALUE);
	
	return 0;
}
