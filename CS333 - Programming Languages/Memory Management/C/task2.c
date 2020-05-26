/*
 * File: task1.c
 * Purpose: To see if the time of memory management changes with the amount of memory allocated or deallocated in C
 * Author: Samuel Munoz
 * Date: 04-29-2020
 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

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
		printf("command: ./task1 <1 or 2 or 3>\n");
		return 0;
	}

	// create variables for simulation
	time_t t;

	// create three branches to any of these tasks
	
	// run with small data types
	if(*argv[1] == '1') {
		// create an array of Small pointer to allocate memory to
		Small **array;
		array = (Small **) malloc(sizeof(Small *) * 1000);

		// simulation start -----------------------------------------------------------------------

		// start timer
		t = clock();

		// display information for table
		printf("Set of Elements\t\tElapsed Time\n");

		// allocate memory for array, measuring time per 100 elements
		for(int i = 0; i < 10; i++) {
			// start timer
			t = clock();

			// allocate memory for 100 elements
			for(int j = 0; j < 100; j++)
				array[i*100 + j] = (Small *) malloc(sizeof(Small));

			// free memory for 100 elements
			for(int j = 0; j < 100; j++)
				free(array[i*100 + j]);

			// end timer
			t = clock() - t;

			// display results
			printf("[%d-%d]:\t\t%.3f ms\n", i*100, (i+1)*100, (double) t/CLOCK_PER_MILISECOND);
		}

		// simulation ends -----------------------------------------------------------------------

		// free up all the memory that remains unfreed
		free(array);
	}

	// run with medium data types
	else if(*argv[1] == '2') {
		// create an array of Small pointer to allocate memory to
		Medium **array;
		array = (Medium **) malloc(sizeof(Medium *) * 1000);

		// simulation start -----------------------------------------------------------------------

		// start timer
		t = clock();

		// display information for table
		printf("Set of Elements\t\tElapsed Time\n");

		// allocate memory for array, measuring time per 100 elements
		for(int i = 0; i < 10; i++) {
			// start timer
			t = clock();

			// allocate memory for 100 elements
			for(int j = 0; j < 100; j++)
				array[i*100 + j] = (Medium *) malloc(sizeof(Medium));

			// free memory for 100 elements
			for(int j = 0; j < 100; j++)
				free(array[i*100 + j]);

			// end timer
			t = clock() - t;

			// display results
			printf("[%d-%d]:\t\t%.3f ms\n", i*100, (i+1)*100, (double) t/CLOCK_PER_MILISECOND);
		}

		// simulation ends -----------------------------------------------------------------------

		// free up all the memory that remains unfreed
		free(array);	
	}

	// run with large data types
	else if(*argv[1] == '3') {
		// create an array of Small pointer to allocate memory to
		Large **array;
		array = (Large **) malloc(sizeof(Large *) * 1000);

		// simulation start -----------------------------------------------------------------------

		// start timer
		t = clock();

		// display information for table
		printf("Set of Elements\t\tElapsed Time\n");

		// allocate memory for array, measuring time per 100 elements
		for(int i = 0; i < 10; i++) {
			// start timer
			t = clock();

			// allocate memory for 100 elements
			for(int j = 0; j < 100; j++)
				array[i*100 + j] = (Large *) malloc(sizeof(Large));

			// free memory for 100 elements
			for(int j = 0; j < 100; j++)
				free(array[i*100 + j]);

			// end timer
			t = clock() - t;

			// display results
			printf("[%d-%d]:\t\t%.3f ms\n", i*100, (i+1)*100, (double) t/CLOCK_PER_MILISECOND);
		}

		// simulation ends -----------------------------------------------------------------------

		// free up all the memory that remains unfreed
		free(array);
	}

	// invalid input from the command line
	else {
		printf("Invalid option.\n");
		printf("command: ./task1 <1 or 2 or 3>\n");
		return 0;
	}

	return 0;
} 