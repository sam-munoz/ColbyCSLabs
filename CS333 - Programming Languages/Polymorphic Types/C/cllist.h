/*
 *  File: llist.h
 *  Purpose: To create a prototype from a doubly-linked list
 *  Author: Samuel Munoz
 *  Date: 03-18-2020
 */

# ifndef cllist_h
# define cllist_h

#include <stdlib.h>

// this struct defines what a Node should contain
typedef struct Node {
	// holds the data for the Node
	void *value;
	// holds the previous Node in the list
	struct Node *prev;
	// holds the previous Node in the list
	struct Node *next;
} Node;

typedef struct {
	// holds the size of the linked list 
	int size;
	// holds the head of the linked list
	Node *head;
	// holds teh tail of the linked list
	Node *tail;
} LinkedList;

LinkedList *ll_create();
void ll_destroy(LinkedList *list);
void ll_push(LinkedList *list, void *data);
void *ll_pop(LinkedList *list);
void ll_append(LinkedList *list, void *data);
void *ll_remove(LinkedList *list, void *target, int (*compfunc)(void*, void*));
int ll_size(LinkedList *list);
void ll_clear(LinkedList *list);
void ll_map(LinkedList *list, void (*mapfunc)(void*)); 

Node *create_node(void*);
void link_nodes(Node*, Node*);

# endif
