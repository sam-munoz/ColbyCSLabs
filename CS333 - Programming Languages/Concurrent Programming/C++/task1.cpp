/*
 * File: task1.cpp
 * Purpose: To create a multithreaded array sorting algorthim in C++
 * Author: Samuel Munoz
 * Date: 05-03-2020
 */

#include <iostream>
#include <string>
#include <thread>
#include <ctime>
#include <cstdlib>
#include <unistd.h>

// global variable to store the number of threads the program should use
int NUM_OF_THREADS = 1;

// sorts a 1D array using a bubble sort algorthim
void bubble_sort(int* a, int size) {
	for(int i = 0; i < size; i++) {
		for(int j = 0; j < size-i-1; j++) {
			if(a[j] > a[j+1]) {
				int temp = a[j];
				a[j] = a[j+1];
				a[j+1] = temp;
			}
		}
	}
}

// finds the next smallest value in subarray
int find_smallest(int **sub, int *indices, int sub_length) {
	int min = 0;
	int index = 0;
	
	// find the first element to check
	for(int i = 0; i < sub_length; i++) {
		if(indices[i] != -1) {
			min = sub[i][indices[i]];
			index = i;
			break;
		}
	}

	// compare the most recent smallest element to all other elements to find smallest next value
	for(int i = index+1; i < sub_length; i++) {
		if(indices[i] != -1) {
			if(sub[i][indices[i]] < min) {
				min = sub[i][indices[i]];
				index = i;
			}
		}
	}

	// return the array with the next smallest value
	return index;
}

// method to sort an array using concurrent program
void array_sort(int* a, int a_size) {
	// determine the number of subarrays to create
	int sbc = NUM_OF_THREADS;

	// determine the length of the average subarray
	int sbl = a_size/sbc;

	// create 2D array of subarrays
	int **subarrays = new int*[sbc];
	int l_subarrays[sbc];

	// populate the subarrays with copies of the original array
	for(int i = 0; i < sbc; i++) {
		// determines the remaining elements in the array
		int sub_size = sbl;
		
		// if there is less than the average subarray size element left in the array, create  subarray with the number of elements remaining
		if(i == sbc-1)
			sub_size = a_size - i*sbl;

		// create memory for new array in the subarray var
		subarrays[i] = new int[sub_size];
		l_subarrays[i] = sub_size;

		// copy values from old array into subarray
		for(int j = 0; j < sub_size; j++)
			subarrays[i][j] = a[i*sbl + j];
	}

	// create array of threads to create threads
	std::thread threads[NUM_OF_THREADS];

	// run all threads with function
	for(int i = 0; i < sbc; i++) {
		try {
			threads[i] = std::thread(bubble_sort, subarrays[i], l_subarrays[i]);		
		}
		catch(std::runtime_error &e) {
			std::cout << "You tried to create too many threads. This computer can only support up to " << (i-1) << " threads at this time." << std::endl;
			exit(1);
		}
	}

	// suspend program for 10ms to give time to the threads to run their functions
	sleep(0.01);

	// close all threads
	for(int i = 0; i < sbc; i++)
		threads[i].join();

	// prints the values from subarray
	// for(int i = 0; i < sbc; i++) {
	// 	std::cout << i << ": ";
	// 	for(int j = 0; j < l_subarrays[i]; j++)
	// 		std::cout << subarrays[i][j] << ", ";
	// 	std::cout << std::endl;
	// }

	// create indices array to keep track of what elements have been passed back into the sort array
	int indices[sbc]; 
	for(int i = 0; i < sbc; i++)
		indices[i] = 0;

	// this iterates through all the elements to be sorted and finds the smallest from the subarrays and adds it the original array
	for(int i = 0; i < a_size; i++) {
		int index = find_smallest(subarrays, indices, sbc);
		// std::cout << "Array: " << index << "\t\tIndex: " << indices[index] << "\t\tValue: " << subarrays[index][indices[index]] << std::endl;
		a[i] = subarrays[index][indices[index]];
		if(++indices[index] >= l_subarrays[index])
			indices[index] = -1;
	}

	// release all memory used by subarrays
	for(int i = 0; i < sbc; i++)
		delete[] subarrays[i];
	delete[] subarrays;
}

int main(int args, char **argv) {
	// checks to make sure that there are command line arguments
	if(args < 2) {
		std::cout << "command: ./task1 <length_of_array, number_of_threads>" << std::endl;
		return 0;
	}

	// try to read the length of the array from the command line
	int size;
	try {
		size = std::stoi(argv[1]);
		if(size <= 1) {
			std::cout << "You cannot create an array with less than two elements. Please enter an array size with at least two elements" << std::endl;
			return 0;
		}
	}
	catch(std::exception const &e) {
		std::cout << "\"" << argv[1] << "\" is not an integer. Please only enter integers into the command line" << std::endl;
		return 0;
	}

	// create memory for the array
	int *array = new int[size];

	// populate array with random values
	srand(time(NULL));
	for(int i = 0; i < size; i++)
		array[i] = rand() % 100;

	// populate the number of threads global variable
	try {
		NUM_OF_THREADS = std::stoi(argv[2]);
	}
	catch(std::exception const &e) {
		std::cout << "\"" << argv[2] << "\" is not an integer. Please only enter integers into the command line" << std::endl;
		return 0;
	}

	// stop the program if there is more threads than array size
	if(NUM_OF_THREADS > size/2) {
		std::cout << "You cannot create more threads than half of array elements. Make sure that you create that the thread count is at least half the size of the array" << std::endl;
		return 0;
	}

	// stop the program if the thread count is zero or negative 
	if(NUM_OF_THREADS <= 0) {
		std::cout << "You cannot create zero or some negative number amount of threads. Please enter positive values for thread count" << std::endl;
		return 0;
	}

	// sort the array and measure time
	clock_t t;
	t = clock();
	if(NUM_OF_THREADS != 1)
		array_sort(array, size);
	else
		bubble_sort(array, size);
	t = clock() - t;

	// print the time elapsed to run algorthim and print the number of the threads used
	std::cout << "Time elapsed: " << t/1000 << " ms" << std::endl;
	std::cout << "Number of threads: " << NUM_OF_THREADS << std::endl;

	// free the memory created for the array
	delete[] array;

	return 0;
}

/*
Sources:
https://www.cplusplus.com/reference/cstdlib/rand/
https://www.tutorialspoint.com/cplusplus/cpp_multithreading.htm
https://stackoverflow.com/questions/41373040/stdthread-and-exception-handling
*/