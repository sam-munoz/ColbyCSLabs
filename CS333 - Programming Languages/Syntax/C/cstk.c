/*
 * File: cstk.c
 * Purpose: To implement the memebers of the Stack struct
 * Author: Samuel Munoz
 * Date: 02-29-2020
 */

#include "cstk.h"

int CSTK_MAX = 50;

// function to create a stack
Stack *stk_create(int size) {
	Stack *s = (Stack *) malloc(sizeof(Stack));
	s->stack = (int *) malloc(sizeof(int) * size);
	s->top = 0;
	s->capacity = size;
}

// function to destroy a stack
void stk_destroy(Stack *s) {
	free(s->stack);
	free(s);
}

// function to add a value to the stack
void stk_push(Stack *s, int value) {
	if(s->top == s->capacity) {
		int size = s->top;
		int capacity = s->capacity * 2;
		int array[s->capacity];
		for(int i=0;i<s->capacity;i++)
			array[i] = s->stack[i];
		free(s->stack);
		s->stack = (int *) malloc(sizeof(int) * capacity);
		s->capacity = capacity;
		int array_length = sizeof(array)/sizeof(int);
		for(int i=0;i<array_length;i++)
			s->stack[i] = array[i];
		s->top = array_length;
	
	}
	s->stack[s->top++] = value;
}

// function to remove a value from the stack
int stk_pop(Stack *s) {
	if(s->top > 0)
		return s->stack[--s->top];
	
	return -1;
}

// function to print the stack
void stk_display(Stack *s, int value) {
	if(value == 1) {
		for(int i=s->top-1;i >= 0;i--) {
			if(i != 0)
				printf("%d, ", s->stack[i]);
			else
				printf("%d\n", s->stack[i]);
		}
	}
	else {
		for(int i=0;i<s->top;i++) {
			if(i != s->top-1)
				printf("%d, ", s->stack[i]);
			else
				printf("%d\n", s->stack[i]);
		}
	}
}

// function that merges together two stacks. Stack a elements have priority over stack b. Example: a = [1,2,3] and b=[4,5,6] => merged=[4,1,5,2,6,3]
Stack * stk_merge(Stack *a, Stack *b) {
	Stack *new_s = (Stack *) malloc(sizeof(Stack));
	new_s->capacity = a->top + b->top;	
	new_s->stack = (int *) malloc(sizeof(int) * new_s->capacity);
	new_s->top = new_s->capacity;
	int i = new_s->top;
	int a_top = a->top;
	int b_top = b->top;
        while(i > 0) {
		if(a_top != 0)
			new_s->stack[--i] = a->stack[--a_top];
		
		if(b_top != 0) 
			new_s->stack[--i] = b->stack[--b_top];
	}
	return new_s;
}
