/*
 * File: cppllist.h
 * Purpose: To prototype my own version of a linked list
 * Author: Samuel Munoz
 * Date: 03-22-2020
 */

#include <iostream>
#include <cstdlib>

// this is a doubly linked list in C++
// note that ll_create metthod is replaced by the LinkedList constructor
template <typename T>
class LinkedList {
	public:
		// constructor: creates an empty list with pointers storing NULL values and size of the list is zero
		LinkedList() : head(NULL), tail(NULL), s(0) {}
		
		// this method adds new elements to the front of the list
		// NOTE: the front of the list starts at head
		void push(T data) {
			LinkedList<T>::Node *new_node = new LinkedList<T>::Node(data);
			
			// if the list is empty
			if(this->s == 0) {
				this->head = new_node;
				this->tail = new_node;
			}

			// if the list is not empty
			else {
				this->head->setPrev(new_node);
				new_node->setNext(this->head);
				this->head = new_node;
			}

			this->s++;
		}


		// this method removes the element at the head location
		T pop() {
			// if the list is not empty
			if(this->s > 0) {
				T returnT = this->head->get();
				this->head = this->head->getNext();
				delete(this->head->getPrev());
				this->head->setPrev(NULL);
				this->s--;
				return returnT;
			}

			// if the list is empty, then we will return none
			return -1;
		}

		// this method add new elements at the end of the list
		// NOTE: the end of the list starts at tail
		void append(T data) {
			LinkedList<T>::Node *new_node = new LinkedList<T>::Node(data);

			// if the list is empty
			if(this->s == 0) {
				this->head = new_node;
				this->tail = new_node;
			}

			// if the list is not empty
			else {
				this->tail->setNext(new_node);
				new_node->setPrev(this->tail);
				this->tail = new_node;
			}

			this->s++;
		}

		// this method returns the value in the node that returns true in the function pointer
		T remove(T target, bool (*compfunc)(T, T)) {
			LinkedList<T>::Node *cursor = this->head;
		
			// traverses the list and testing each node with the function pointer
			while(cursor != NULL) {
				bool b = compfunc(target, cursor->get());

				// if the node matches the target
				if(b) {
					T returnT = cursor->get();
					cursor->getPrev()->setNext(cursor->getNext());
					cursor->getNext()->setPrev(cursor->getPrev());
					delete(cursor);
					this->s--;
					return returnT;
				}

				// if the node does not match the target
				cursor = cursor->getNext();
			}

			// if there are no matches
			return -1;
		}

		// returns the size of the list
		int size() {
			return this->s;
		}

		// this method removes and deallocates the memory of Nodes
		void clear() {
			LinkedList<T>::Node *cursor = this->head;

			// travserse through the list and removes and deallocates all the Nodes stored in the list
			while(cursor != NULL) {
				LinkedList<T>::Node *remove_node = cursor;
				cursor = cursor->getNext();
				delete(remove_node);
			}

			// set the field's values to empty list values
			this->head = NULL;
			this->tail = NULL;
			this->s = 0;
		}

		void map(T (*mapfunc)(T)) {
			LinkedList<T>::Node *cursor = this->head;

			// traverses through the list and applies the function on a Node
			while(cursor != NULL) {
				cursor->set(mapfunc(cursor->get()));
				cursor = cursor->getNext();
			}
		}
		
		// this method deallocates all the memory 
		void destroy() {
			// sets the list's fields to empty list values 
			LinkedList<T>::Node *cursor = this->head;

			// deallocates the memory from all the nodes in the list
			while(cursor != NULL) {
				LinkedList<T>::Node *delete_node = cursor;
				cursor = cursor->getNext();
				delete(delete_node);
			}

			// deallocates the memory used for the class instance
			delete(this);
		}

		void print() {
			LinkedList<T>::Node *cursor = this->head;
			
			// if the list is empty
			if(this->s == 0) {
				std::cout << "Empty";
			}

			// tranverses through the list and prints all values
			while(cursor != NULL) {
				std::cout << cursor->get() << ", ";
				cursor = cursor->getNext();
			}
		}

		class Node;
	private:
		Node *head;
		Node *tail;
		int s;
};


// this is a generic Node for the linked list
template <typename T>
class LinkedList<T>::Node {
	public:
		Node(T t) : next(NULL), prev(NULL), data(t) {}
		T get() { return this->data; }
		void set(T data) { this->data = data; }
		Node *getNext() { return this->next; }
		Node *getPrev() { return this->prev; }
		void setNext(Node *next) { this->next = next; }
		void setPrev(Node *prev) { this->prev = prev; }
	private:
		T data;
		Node *prev;
		Node *next;
};
