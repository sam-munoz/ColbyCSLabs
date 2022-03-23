/*
 * File: cstk.h
 * Purpose: To create a prototype Stack struct
 * Author: Samuel Munoz
 * Date: 02-29-2020
 */

#ifndef cstk_h
#define cstk_h

#include <stdlib.h>
#include <stdio.h>

extern int CSTK_MAX;

// creates the data types of a stack
typedef struct {
	int *stack; // an array of integer values using dynamic memory allocation
	int top; // index to the next free location in the slack
	int capacity; // holds the size of the array
} Stack;

Stack *stk_create(int);
void stk_destroy(Stack *);
void stk_push(Stack *, int);
int stk_pop(Stack *);
void stk_display(Stack *, int);
Stack * stk_merge(Stack *, Stack *);

#endif

/*
Source:
https://www.programiz.com/c-programming/c-structures-pointers
*/
