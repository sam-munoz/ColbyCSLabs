/*
 * File: task3.cpp
 * Purpose: To show all the different data types in C++
 * Author: Samuel Munoz
 * Date: 03-03-2020
 */

#include <iostream>

class Person {
	public:
		Person(char *n) { name = n; }
		char * getName() { return name; }
		void setName(char *n) { name = n; } 
	private:
		char *name;
};

typedef struct {
	int num;
} Container;

enum Comparsion {
	Less,
	Greater,
	Equal
};

// unions are a way to create different 
typedef union {
	bool b;
	int i;
} Optional;

void printHelloWorld(); // derivated type
void printHelloWorld() { std::cout << "Hello World!" << std::endl; }
void printArray(int *head, int size) {
	std::cout << "[";
	for(int i=0;i<size;i++) {
		if(i != size-1)
			std::cout << head[i] << ", ";
		else
			std::cout << head[i] << "]" << std::endl;
	}
}

int main(int argc, char **argv) {
	
	// All primary data types
	// integers
	int i = 1; 
	// characters
	char c = 'a';
	// booleans
	bool b = false;
	// floats
	float f = 3.14;
	// doubles
	double d = 1.69;
	// wide char; has more bytes to expand the number of different chars inside the variable. Bigger than unsigned char
	wchar_t w = L'w';

	// Derivated types
	// functions
	int array[10] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // array (can be of any type)
	int *ptr; // pointer (can be of any type)
	// reference vs pointers
	// references must refer to a specific location of memory
	// references must be initialized when references are delcared
	// reference's values cannot be changed after a reference is created
	int &ref = array[0]; // reference (can be of any type)

	// User Defined Types
	char str[] = "Juan";
	// class
	Person p = Person(str);
	// struct
	Container container;
	container.num = 5;
	// union
	Optional o;
	o.b = false;
	// enumeration
	Comparsion en;

	std::cout << "Primary Data Types" << std::endl;
	std::cout << "Boolean\t\t\t" << "size: " << sizeof(bool) << "\t\tvalue: " << b << "\t\t!: " << !b << std::endl;
	std::cout << "Integer\t\t\tsize: " << sizeof(int) << "\t\tvalue: " << i << "\t\t+: " << i+1 << "\t-: " << i-1 << "\t*: " << i*2 << "\t/: " << i/2 << "\t%: " << i%2 << std::endl;
	std::cout << "Character\t\tsize: " << sizeof(char) << "\t\tvalue: " << c << "\t\t+: " << c+1 << "\t-: " << c-1 << "\t*: " << c*2 << "\t/: " << c/2 << "\t%: " << c%2 << std::endl;
	std::cout << "Float\t\t\tsize: " << sizeof(float) << "\t\tvalue: " << f << "\t\t+: " << f+1 << "\t-: " << f-1 << "\t*: " << f*2 << "\t/: " << f/2 << std::endl;
	std::cout << "Double\t\t\tsize: " << sizeof(int) << "\t\tvalue: " << d << "\t\t+: " << d+1 << "\t-: " << d-1 << "\t*: " << d*2 << "\t/: " << d/2 << std::endl;
	std::cout << "Wide Character\t\tsize: " << sizeof(wchar_t) << "\t\tvalue: " << w << "\t\t+: " << w+1 << "\t-: " << w-1 << "\t*: " << w*2 << "\t/: " << w/2 << "\t%: " << w%2 << std::endl;

	std::cout << "\n\nDerived Data Types" << std::endl;

	std::cout << "Function\t\t";
	printHelloWorld();
	std::cout << "Array\t\t\t";
	printArray(array, 10);
	std::cout << "Pointer\t\t\t" << ptr << std::endl;
	std::cout << "Reference\t\t" << ref << std::endl;
	
	std::cout << "\n\nUser-Defined Data Types" << std::endl;

	std::cout << "Classes\t\t\tName: " << p.getName() << std::endl;
	std::cout << "Structures\t\tNumber: " << container.num << std::endl;
	std::cout << "Union:" << std::endl;
	std::cout << "\t\t\t(Integer Corrupted)" << "\tBoolean: " << o.b << "\t\tInteger: " << o.i << std::endl;
	o.i = 34;
	std::cout << "\t\t\t(Boolean Corrupted)" << "\tBoolean: " << o.b << "\t\tInteger: " << o.i << std::endl;
	std::cout << "Enumeration\t\t" << "Less: " << (en=Less) << "\t\t\tGreater: " << (en=Greater) << "\t\tEqual: " << (en=Equal) << std::endl;

	return 0;
}

/*
Sources
https://www.geeksforgeeks.org/c-data-types/
https://www.tutorialspoint.com/cplusplus/cpp_references.htm
https://www.tutorialspoint.com/cprogramming/c_unions.htm
https://www.programiz.com/cpp-programming/enumeration
*/
