/*
 * File: testllist.c
 * Purpose: To test and debug my generic linked list
 * Author: Samuel Munoz
 * Date: 03-18-2020
 */
#include <stdio.h>
#include "cllist.h"

// a print method that prints the content of linked list filled with integer values
void printLList(LinkedList *l) {
	printf("Linked List: ");
	Node *cursor = l->head;
	if(cursor == NULL)
		printf("Empty");
	while(cursor != NULL) {
		int *value = cursor->value;
		printf("%d, ", *value);
		cursor = cursor->next;
	}
	printf("\n");
}

// a print method that prints the content of linked list filled with integer values
void printCLList(LinkedList *l) {
	printf("Linked List: ");
	Node *cursor = l->head;
	if(cursor == NULL)
		printf("Empty");
	while(cursor != NULL) {
		char *value = cursor->value;
		printf("%c, ", *value);
		cursor = cursor->next;
	}
	printf("\n");
}

// the comparsion function to pass through the ll_remove function
int compfunc(void *ptr1, void *ptr2) {
	int *v1 = ptr1;
	int *v2 = ptr2;
	if(*v1 == *v2)
		return 1;
	else
		return 0;
}

// the map function to pass through the linked list ll_map function
// adds one to every integer value
void map(void *ptr) {
	int *i_ptr = ptr;
	*i_ptr = *i_ptr + 1;
}

// main method
int main() {
	LinkedList *l = ll_create();
	printf("------------ Int List ----------------\n");
	printLList(l);

	// ------Empty List -----------------------
	int *pop = ll_pop(l);
	int *b = malloc(sizeof(int));
	*b = 12;
	int *remove = ll_remove(l, b, &compfunc);
	printf("Size: %d\n", ll_size(l));
	ll_clear(l);
	ll_map(l, map);
	
	if(pop == NULL)
		printf("Pop: NULL\n");
	else
		printf("Pop: %d\n", *pop);
	if(remove == NULL)
		printf("Remove: NULL\n");
	else
		printf("Remove: %d\n", *remove);
	printLList(l);
	// ----------------------------------------


	// ------Non Empty List -------------------
	for(int i=0; i < 5; i++) {
		int *a = malloc(sizeof(int));
		*a = i+1;
		ll_append(l, a);
	}

	int *c = malloc(sizeof(int));
	*c = 0;
	ll_push(l, c);

	printLList(l);

	pop = ll_pop(l);
	*b = 9;
	remove = ll_remove(l, b, &compfunc);
	ll_map(l, map);

	if(pop == NULL)
		printf("Pop: NULL\n");
	else
		printf("Pop: %d\n", *pop);
	if(remove == NULL)
		printf("Remove: NULL\n");
	else
		printf("Remove: %d\n", *remove);

	printLList(l);
	printf("Size: %d\n", ll_size(l));

	printf("Cleared List\n");
	printLList(l);
	// ---------------------------------------

	ll_destroy(l);
	free(b);
	free(c);
	printf("---------------------------------------\n");

	printf("\n------------ Char List ----------------\n");
	l = ll_create();
	printCLList(l);

	// ------Empty List -----------------------
	char *cpop = ll_pop(l);
	char *cb = malloc(sizeof(char));
	*cb = 'c';
	char *cremove = ll_remove(l, cb, &compfunc);
	printf("Size: %d\n", ll_size(l));
	ll_clear(l);
	ll_map(l, map);
	
	if(cpop == NULL)
		printf("Pop: NULL\n");
	else
		printf("Pop: %c\n", *cpop);
	if(cremove == NULL)
		printf("Remove: NULL\n");
	else
		printf("Remove: %c\n", *cremove);
	printLList(l);
	// ----------------------------------------

	// ------Non Empty List -------------------
	for(int i=98; i < 102; i++) {
		char *a = malloc(sizeof(char));
		*a = (char) i;
		ll_append(l, a);
	}

	char *cc = malloc(sizeof(char));
	*cc = 'z';
	ll_push(l, cc);

	printCLList(l);

	cpop = ll_pop(l);
	*cb = 'c';
	cremove = ll_remove(l, cb, &compfunc);
	ll_map(l, map);

	if(cpop == NULL)
		printf("Pop: NULL\n");
	else
		printf("Pop: %c\n", *cpop);
	if(cremove == NULL)
		printf("Remove: NULL\n");
	else
		printf("Remove: %c\n", *cremove);

	printCLList(l);
	printf("Size: %d\n", ll_size(l));
	
	printf("Cleared List\n");
	ll_clear(l);
	printCLList(l);
	// ---------------------------------------
	ll_destroy(l);
	free(cb);
	free(cc);
	free(cremove);

	printf("----------------------------------------\n");

	return 0;
}


/*
Sources
https://codescracker.com/cpp/cpp-function-definition.htm
https://stackoverflow.com/questions/16950448/print-values-of-void-pointer
http://www.c4learn.com/c-programming/c-nested-structure/
*/