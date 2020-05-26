/*
 * File: task1.cpp
 * Purpose: To show all the control flow in C++
 * Date: 03-08-2020
 */
#include<iostream>

int main() {

	// variables to use to experiment with
	short i = 12;
	char c = 'r';
	bool b = false;
	int array[10] = {1, 2, 3, 4,5, 6, 7, 8, 9, 10};


	std::cout << "---Selection Statements: If-Else Blocks---\n" << std::endl;
	// depending on whether the conditional inside the if is true, the conditional will decide whether run if block or else block
	if(i < 20) {
		std::cout << "(1) Ran If-Block" << std::endl;
	} else {
		std::cout << "(1) Ran Else-Block" << std::endl;
	}

	if(i > 20) {
		std::cout << "(2) Ran If-Block" << std::endl;
	} else {
		std::cout << "(2) Ran Else-Block" << std::endl;
	}

	// with a if-else statement, if the first condiitonal is false and the first if-else condition is true, then the if-else block runs 
	if(i > 20) {
		std::cout << "(3) Ran If-Block" << std::endl;
	} else if(i > 10 && i < 20) {
		std::cout << "(3) Ran If-Else-Block" << std::endl;
	} else {
		std::cout << "(3) Ran Else-Block" << std::endl;
	}

	// the point of the if statement is have blocks of code to only run during certain conditions

	std::cout << "-------------------------------------------\n" << std::endl;

	std::cout << "------------Iteration Statements-----------\n" << std::endl;
	// while loop: loops a block code until a condition is false. Conditional loop checked before a block of code runs
	std::cout << "While Loop: ";
	while(i > 0) {
		std::cout << i-- << " ";
	}

	// do while loop: loops a block code until a condition is false. Conditional loop checked after a block of code runs
	std::cout << "\nDo-While Loop: ";
	do {
		std::cout << ++i << " ";
	} while(i < 12);
	// for loop: loops a block of code and allows declaration variables to be created
	std::cout << "\nFor Loops: ";
	for(int i = 12;i < 30;i += 2) {
		std::cout << i << " ";
	}
	// range-based for loop: this is a for loop where the loops through an iterative data type
	std::cout << "\nRange-Based For Loop: ";
	for(int element : array) {
		std::cout << element << " ";
	}
	std::cout << std::endl;
	std::cout << "-------------------------------------------\n" << std::endl;

	std::cout << "----------------Jump Statements------------\n" << std::endl;
	// this statements jump the control flow from one section of some code to another
	std::cout << "Break: ";
	for(int element : array) {
		if(element == 3) {
			std::cout << "Break occured!" << std::endl;
			break;
		}
		std::cout << element << " ";
	}
	std::cout << "Continue: ";
	for(int element = 0; element < 4; element++) {
		if(element < 2) {
			std::cout << "Skipped!" << " ";
			continue;
		}
		std::cout << element << " ";
	}
	std::cout << std::endl << "Goto: ";
	new_label:
		std::cout << i-- << " ";
		if(i > 0) { goto new_label; }
	std::cout << std::endl; 	
	std::cout << "-------------------------------------------\n" << std::endl;

	std::cout << "---------Other Types of Statements---------\n" << std::endl;
	i = 3;
	std::cout << "Swtiches: ";
	switch(i) {
		case 1:
			std::cout << "Found case 1" << std::endl;
			break;
		case 2:
			std::cout << "Found case 2" << std::endl;
			break;
		case 3:
			std::cout << "Found case 3" << std::endl;
			break;
		default:
			std::cout << "Found default" << std::endl;
			break;
	}
	std::cout << "-------------------------------------------" << std::endl;

	return 0;
}

/*
Source: https://www.cplusplus.com/doc/tutorial/control/
*/