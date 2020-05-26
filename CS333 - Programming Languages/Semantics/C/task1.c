/**
 * Given an array of random integers, sort it in such a way that the even 
 * numbers appear first and the odd numbers appear later. The even numbers 
 * should be sorted in descending order and the odd numbers should be sorted 
 * in ascending order.
 *
 * Ying Li
 * 08/02/2016
 *
 *
 * File: task1.c
 * Purpose: To create a quick sort algorithm using the qsort method from the standard c library
 * Author: Samuel Munoz
 * Date: 03-06-2020
 */

#include <stdio.h>
#include <stdlib.h>

/* the comparator funciton used in qsort */
// -1 -> p is first; 1 -> q is first
int comparator (const void *p, const void *q) {
	// if both values are even or odds
	int a = *((int *) p);
	int b = *((int *) q);
	if(a%2 == 0 && b%2 == 0)
		return b-a;
	else if (a%2 != 0 && b%2 != 0)
		return a-b;
	// if p is even and q is odd
	else if(a%2 == 0)
		return -1;
	// if q is even and p is odd
	else if(b%2 == 0)
		return 1;
}

int main (int argc, char **argv) {
	int ary[] = {10, 11, 1, 8, 9, 0, 13, 4, 2, 7, 6, 3, 5, 12};
	
	int size = sizeof(ary) / sizeof(int);
	
	qsort((void *) ary, size, sizeof(int), comparator);

	printf("The sorted array is: ");
	for (int i = 0; i < size; i++) {
		printf("%d ", ary[i]);
	}
	printf("\n");
	
	return 0;
}

/*
Sources
https://www.geeksforgeeks.org/comparator-function-of-qsort-in-c/
*/
