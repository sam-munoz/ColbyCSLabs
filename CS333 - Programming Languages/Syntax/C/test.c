/*
 * File: test.c
 * Purpose: To check if my stack works and try some new functions on the stack
 * Author: Samuel Munoz
 * Date: 03-02-2020
 */

#include "cstk.h"

int main(int argc, char **argv) {
	if(argc == 3) {
		Stack *s = stk_create(1);
		Stack *t = stk_create(1);

		int length = atoi(argv[1]);
		for(int i=0;i<length;i++)
			stk_push(s, i+1);

		length = atoi(argv[2]);
		for(int i=0;i<length;i++)
			stk_push(t, i+1);

		Stack *st = stk_merge(s, t);

		printf("\nStack s (reverse order): ");
		stk_display(s, 1);
		printf("\nStack t (reverse order): ");
		stk_display(t, 1);

		printf("\n");

		printf("Print merged stack in reverse: ");
		stk_display(st, 1);
		printf("\n");

		stk_destroy(s);
		stk_destroy(t);
		stk_destroy(st);
	}
	else {
		printf("Arguments needed: <size_of_stack_a> <size_of_stack_b>\n");
	}
	return 0;
}
