/*
 * File: task3.cpp
 * Purpose: To create a bubble sort algorithm in C++
 * Author: Samuel Munoz
 * Date: 03-07-2020
 */

#include <iostream>
#include <cstdlib>
#include <ctime>

void printArray(int *array, int length) {
	for(int i=0;i < length;i++)
		if(i != length-1)
			std::cout << array[i] << ", "; 
		else
			std::cout << array[i];
}

int generateRandNumber(int *seed, int min, int max) {
	int value = rand() % (max-min+1) + min;
	(*seed)++;
	return value;
}

void bubbleSort(int *array, int length) {
	for(int i=0;i < length-1;i++) {
		for(int j=0;j < length-i-1;j++) {
			if(array[j] < array[j+1]) {
				int temp = array[j];
				array[j] = array[j+1];
				array[j+1] = temp;
			}
		}
	}
}


int main(int args, char **argv) {
	if(args < 2) {
		std::cout << "command: ./task3 <length of array>" << std::endl;
		return 0;
	}
	
	// initialize a random seed
	int seed = time(NULL);
	srand(seed);

	// Debuging stuff ------------------------------------------------------
	// std::cout << "\t\tSeed: " << seed << std::endl;

	// generate random number between 1-100 inclusive
	// for(int i=0;i<5;i++) {
	// 	int value = generateRandNumber(&seed, 19, 21);
	// 	std::cout << "Value: " << value << "\tSeed: " << seed << std::endl;
	// }
	//  --------------------------------------------------------------------

	// generate an array for sorting
	int length = atoi(argv[1]);
	int array[length];

	// populate the array with random values
	for(int i=0;i < length;i++)
		array[i] = generateRandNumber(&see, 0, 9);

	/-/;
	printArray(array, length);
	std::cout << std::endl;
	
	// sorts array with bubble sort algorithm
	bubbleSort(array, length);

	// reprints the array sorted
	std::cout << "Sorted Array: ";
	printArray(array, length);
	std::cout << std::endl;

	return 0;
}

/*
Sources
https://www.geeksforgeeks.org/bubble-sort/
https://www.cplusplus.com/reference/cstdlib/srand/
https://www.cplusplus.com/reference/ctime/
*/