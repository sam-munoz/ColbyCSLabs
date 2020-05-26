/*
 * File: task1.c
 * Purpose: To see if the time of memory management changes with the amount of memory allocated or deallocated in C
 * Author: Samuel Munoz
 * Date: 04-29-2020
 */
#include <iostream>
#include <ctime>

#define CLOCK_PER_MILISECOND 1000

// struct to store our small data type
// byte size of struct: 20 bytes
typedef struct {
	int field[5];
} Small;

// struct to store our medium data type
// byte size of struct: 10,000 bytes
typedef struct {
	int field[2500];
} Medium;

// struct to store our large data type
// byte size of struct: 1,000,000 bytes
typedef struct {
	int field[250000];
} Large;

int main(int args, char** argv) {
	// terminate the program if there is no command line arguments given
	if(args < 2) {
		std::cout << "command: ./task1 <1 or 2 or 3>" << std::endl;
		return 0;
	}

	// create variables for simulation
	time_t t;

	// create three branches to any of these tasks
	
	// run with small data types
	if(*argv[1] == '1') {
		// start timer
		t = clock();

		// display information for table
		std::cout << "Set of Elements\t\tElapsed Time" << std::endl;

		// allocate memory for array, measuring time per 100 elements
		for(int i = 0; i < 100; i++) {
			// start timer
			t = clock();

			// allocate memory for 1000 elements
			Small *array = new Small[1000];

			// free memory for 100 elements
			delete[] array;

			// end timer
			t = clock() - t;

			// display results
			std::cout << "[" << i*100 << "-" << (i+1)*100 << "]:\t\t" << (double) t/CLOCK_PER_MILISECOND << " ms" << std::endl;
		}
	}

	// run with medium data types
	else if(*argv[1] == '2') {
		// start timer
		t = clock();

		// display information for table
		std::cout << "Set of Elements\t\tElapsed Time" << std::endl;

		// allocate memory for array, measuring time per 100 elements
		for(int i = 0; i < 100; i++) {
			// start timer
			t = clock();

			// allocate memory for 1000 elements
			Medium *array = new Medium[1000];

			// free memory for 100 elements
			delete[] array;

			// end timer
			t = clock() - t;

			// display results
			std::cout << "[" << i*100 << "-" << (i+1)*100 << "]:\t\t" << (double) t/CLOCK_PER_MILISECOND << " ms" << std::endl;
		}
	}

	// run with large data types
	else if(*argv[1] == '3') {
		// start timer
		t = clock();

		// display information for table
		std::cout << "Set of Elements\t\tElapsed Time" << std::endl;

		// allocate memory for array, measuring time per 100 elements
		for(int i = 0; i < 100; i++) {
			// start timer
			t = clock();

			// allocate memory for 100 elements
			Large *array = new Large[1000];

			// free memory for 100 elements
			delete[] array;

			// end timer
			t = clock() - t;

			// display results
			std::cout << "[" << i*100 << "-" << (i+1)*100 << "]:\t\t" << (double) t/CLOCK_PER_MILISECOND << " ms" << std::endl;
		}
	}

	// invalid input from the command line
	else {
		std::cout << "Invalid option." << std::endl;
		std::cout << "command: ./task1 <1 or 2 or 3>" << std::endl;
		return 0;
	}

	return 0;
} 