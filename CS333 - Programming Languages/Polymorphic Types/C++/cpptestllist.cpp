/*
 * File: cpptestllist.cp
 * Purpose: To test my linked list
 * Author: Samuel Munoz
 * Date: 03-23-2020
 */

#include "cppllist.h"

template <typename T>
bool compFunc(T t1, T t2) {
	return t1 == t2;
}

template <typename T>
T mapfunc(T data) {
	return data + 1;
}

int main() {
	// create new char linked list
	LinkedList<char> *l = new LinkedList<char>();

	// create new int linked list
	LinkedList<int> *il = new LinkedList<int>();

	std::cout << "------------ For Char List ----------------------------" << std::endl;

	std::cout << "Empty List" << std::endl;	

	// popped the first element
	std::cout << "Pop: " << l->pop() << std::endl;

	// removed an element using the compFunc function
	std::cout << "Removed: " << l->remove('a', &compFunc<char>) << std::endl;

	std::cout << "\nNon-Empty List" << std::endl;

	// using push and append to add values to the list
	for(int i=97; i < 102; i++) {
		l->push((char) i);
	}
	for(int i=102; i < 107; i++) {
		l->append((char) i);
	}

	// popped the first element
	std::cout << "Pop: " << l->pop() << std::endl;

	// removed an element using the compFunc function
	std::cout << "Removed: " << l->remove('c', &compFunc<char>) << std::endl;

	// displaying info about list
	std::cout << "Linked List: ";
	l->print();
	std::cout << "\nSize: " << l->size() << std::endl;


	// applies the map function on the list
	std::cout << "\nMapped List" << std::endl;
	l->map(&mapfunc);
	std::cout << "Linked List: ";
	l->print();
	std::cout << "\nSize: " << l->size() << std::endl;

	// clears the list
	std::cout << "\nCleared List" << std::endl;
	l->clear();
	std::cout << "Linked List: ";
	l->print();
	std::cout << "\nSize: " << l->size() << std::endl;

	std::cout << "------------------------------------------------------" << std::endl;

	std:: cout << "\n-------------------For Int List ----------------------" << std::endl;

	std::cout << "Empty List" << std::endl;	

	// popped the first element
	std::cout << "Pop: " << il->pop() << std::endl;

	// removed an element using the compFunc function
	std::cout << "Removed: " << il->remove(1, &compFunc<int>) << std::endl;

	std::cout << "\nNon-Empty List" << std::endl;

	// using push and append to add values to the list
	for(int i=0; i < 5; i++) {
		il->push(i);
	}
	for(int i=5; i < 10; i++) {
		il->append(i);
	}

	// popped the first element
	std::cout << "Pop: " << il->pop() << std::endl;

	// removed an element using the compFunc function
	std::cout << "Removed: " << il->remove(8, &compFunc<int>) << std::endl;

	// displaying info about list
	std::cout << "Linked List: ";
	il->print();
	std::cout << "\nSize: " << il->size() << std::endl;

	// applies the map function on the list
	std::cout << "\nMapped List" << std::endl;
	il->map(&mapfunc);
	std::cout << "Linked List: ";
	il->print();
	std::cout << "\nSize: " << il->size() << std::endl;

	// clears the list
	std::cout << "\nCleared List" << std::endl;
	il->clear();
	std::cout << "Linked List: ";
	il->print();
	std::cout << "\nSize: " << il->size() << std::endl;

	std:: cout << "------------------------------------------------------" << std::endl;

	// this deallocates all the memory used by the list
	l->destroy();
	il->destroy();

	return 0;
}

/*
Source
https://www.cplusplus.com/doc/tutorial/classes/
https://stackoverflow.com/questions/36192499/how-do-i-manually-delete-an-instance-of-a-class
https://www.geeksforgeeks.org/memory-leak-in-c-and-how-to-avoid-it/
https://www.geeksforgeeks.org/generics-in-c/
https://stackoverflow.com/questions/1392869/how-to-return-null-from-a-method-in-a-template-class
*/
