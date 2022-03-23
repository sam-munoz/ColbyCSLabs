/*
 * File: hello_world.cpp
 * Prupose: To print the string 'hello world' and other command line arguments
 * Author: Samuel Munoz
 * Date: 02-24-2020
 */

// #include is a keyword that allows the programmer to import packages in the C/C++ libraries
#include <iostream>

// this the main method that runs when this code is compiled and executed
int main(int argc, char *argv[]) {
	
	// std::cout is used to print data types to the console
	// std::endl is used to show when a line end and print a new line
	std::cout << "Hello World!" << std::endl;
	
	std::cout << "Command Line Argments:" << std::endl;
	
	// this is a for loop. The syntax for this loop is exactly the same for languages like Java
	for(int i=0;i<argc-1;i++) {
		std::cout << i+1 << ": " << argv[i+1] << std::endl;
	}
	
	// this calls the exit status in C++ so C++ knows when the program ends
	return 0;
}
