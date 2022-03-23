/*
 * File: llist.c
 * Purpose: To implement a doubly linked list in C
 * Author: Samuel Munoz
 * Date: 03-18-2020
 */

#include "cllist.h"
#include <stdlib.h>

// this function creates a new linked list with size zero and stores zero nodes
LinkedList *ll_create() {
	LinkedList *list = (LinkedList *) malloc(sizeof(LinkedList));
	list->size = 0;
	list->head = NULL;
	list->tail = NULL;
	return list;
}

// this function adds a Node at the front of the list
// **NOTE: I am defining the front of the list to be head
// 	   and the end of the list is the tail
void ll_push(LinkedList *list, void *data) {
	Node *new_node = create_node(data);
	if(list->size != 0) {
		list->head->prev = new_node;
		new_node->next = list->head;
	}
	else {
		list->tail = new_node;
	}
	list->head = new_node;
	list->size++;
}

// this function adds a Node to the end of the list
void ll_append(LinkedList *list, void *data) {
	Node *new_node = create_node(data);
	if(list->size != 0) {
		list->tail->next = new_node;
		new_node->prev = list->tail;
	}
	else {
		list->head = new_node;
	}
	list->tail = new_node;
	list->size++;
}


// this function removes the Node at the front of the list and returns the vaule stored by the removed Node
void *ll_pop(LinkedList *list) {
	// if the linked list is not empty
	if(list->size != 0) {
		void *return_data = list->head->value;
		Node *next_node = list->head->next;
		free(list->head);
		list->head = next_node;
		list->size--;
		return return_data;
	}

	// if the list is empty
	return NULL;
}

// this function removes the first Node that returns true from the compfunc and returns the value stored by the Node
void *ll_remove(LinkedList *list, void *target, int (*compfunc)(void *, void *)) {
	Node *cursor = list->head;
	while(cursor != NULL) {
		int i = compfunc(cursor->value, target);

		// if comparsion is true
		if(i != 0) {
			void *return_data = cursor->value;
			if(cursor->prev->next != NULL)
				cursor->prev->next = cursor->next;
			if(cursor->next->prev != NULL)
				cursor->next->prev = cursor->prev;
			free(cursor);
			list->size--;
			return return_data;
		}
		
		cursor = cursor->next;
	}

	return NULL;
}

// this method frees all the memory used up by the linked list
void ll_destroy(LinkedList *list) {
	Node *cursor = list->head;
	ll_clear(list);
	free(list);
}

// clears the list of all Nodes
void ll_clear(LinkedList *list) {
	Node *cursor = list->head;
	list->head = NULL;
	list->tail = NULL;
	while(cursor != NULL) {
		int *value = cursor->value;
		Node *new_node = cursor->next;
		free(cursor);
		list->size--;
		cursor = new_node;
	}
}

// returns the size of the list
int ll_size(LinkedList *list) {
	return list->size;
}

// this function maps all the Node's value to whatever the mapfunc returns
void ll_map(LinkedList *list, void (*mapfunc)(void *)) {
	Node *cursor = list->head;
	while(cursor != NULL) {
		mapfunc(cursor->value);
		cursor = cursor->next;
	}
}

// this function creates a Node struct
Node *create_node(void *data) {
	Node *node = (Node *) malloc(sizeof(Node));
	node->value = data;
	node->prev = NULL;
	node->next = NULL;
	return node;
}

// this function will doubly link two nodes together
void link_nodes(Node *n1, Node *n2) {
	n1->next = n2;
	n2->prev = n1;
}
