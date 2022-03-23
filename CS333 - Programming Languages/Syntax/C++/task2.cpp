/*
 * File: task2.cpp
 * Purpose: To create a binary search algorithm in C++
 * Author: Samuel Munoz
 * Date: 02-27-2020
*/
#include <iostream>
#include<stdlib.h>

// function that search for a value in an integer array
void binary_search(int array[], int length, int value) {
	int position = length/2;
	int left = 0;
	int right = length;

	std::cout << "position: " << position << "\tleft: " << left << "\tright: " << right << "\tvalue: " << array[position] << std::endl;
	while(array[position] != value) {
		if(position == left) {
			std::cout << "Value not found" << std::endl;
			break;
		}

		if(value < array[position])
			right = position;
		else if(value > array[position])
			left = position;

		position = (right-left)/2 + left;

		std::cout << "position: " << position << "\tleft: " << left << "\tright: " << right << "\tvalue: " << array[position] << std::endl;
	}
}

int main(int argc, char *argv[]) {
	if(argc == 3) {
		int size = atoi(argv[1]);
		int array[size];
		for(int i = 0; i < size; i++) {
			array[i] = i;
		}

		int value = atoi(argv[2]);
		if(size > 0)
			binary_search(array, size, value);
	}
	else
		std::cout << "You must enter the following two arguments: <length of array> <search value>" << std::endl;
	
	return 0;
}
