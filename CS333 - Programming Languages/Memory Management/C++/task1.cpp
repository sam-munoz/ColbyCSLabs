/*
 * File: task1.cpp
 * Purpose: To show the explict allocative and deallocative memeory management in C++
 * Author: Samuel Munoz
 * Date: 04-28-2020
 */
#include <iostream>

void printArray(int *a, int size) {
	for(int i = 0; i < size; i++) {
		std::cout << a[i];
		if(i != size-1)
			std::cout << ", ";
	}
}

int main() {
	// allocate memory is only for pointers and it works with basic data types and aggregate data types
	
	// create new integer without initialize it
	int *a = new int;

	// create new integer with initialize it
	int *b = new int(12);

	// create new character without initialize it
	char *c = new char;

	// create new character with initialize it
	char *d = new char('w');

	// print out variables
	std::cout << "a: " << *a << "\nb: " << *b << "\nc: " << *c << "\nd: " << *d << std::endl;

	// delete integer
	delete a;
	delete b;

	// delete characters
	delete c;
	delete d;

	// create new integer array -- does not intialize values (agregate data type)
	int *array = new int[20];

	// print array
	std::cout << "\nArray without intialization" << std::endl;
	printArray(array, 20);
	std::cout << std::endl;

	// intiazling values
	for(int i = 0; i < 20; i++)
		array[i] = 40 + i*2;

	// print array
	std::cout << "\nArray with intialization" << std::endl;
	printArray(array, 20);
	std::cout << std::endl;

	// we can use the identifiers with no value to point a values inside the array
	a = array+8;
	b = array+11;

	// assign new memory to integers a and b
	*a = 12;
	*b = 0;

	// print out variables
	std::cout << "\na: " << *a << "\nb: " << *b << std::endl;
	std::cout << "\nArray: " << std::endl;
	printArray(array, 20);

	// delete memory from a and b
	delete a;
	delete b;

	// delete integer array
	delete[] array;

	return 0;
}

/*
Sources:
https://www.geeksforgeeks.org/new-and-delete-operators-in-cpp-for-dynamic-memory/
*/