/*
 * File: task1.c
 * Purpose: To determine the average time to allocate memory in C
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
		array = (Small **) malloc(sizeof(Small *) * 10000);

		// simulation start -----------------------------------------------------------------------

		// start timer
		t = clock();

		// allocate memory for array
		for(int i = 0; i < 10000; i++)
			array[i] = (Small *) malloc(sizeof(Small));

		// end timer
		t = clock() - t;

		// simulation ends -----------------------------------------------------------------------

		// print out overall time to allocate memory and time to allocate a single Small value
		printf("Time elapsed: %.3f ms\n", (double) t/CLOCK_PER_MILISECOND);
		printf("Average time to allocate memory: %.5f ms\n", (double) t/CLOCK_PER_MILISECOND/10000);

		// free up all the memory used by the array
		for(int i = 0; i < 10000; i++)
			free(array[i]);
		free(array);
	}

	// run with medium data types
	else if(*argv[1] == '2') {
		// create an array of Small pointer to allocate memory to
		Medium **array;
		array = (Medium **) malloc(sizeof(Medium *) * 10000);

		// simulation start -----------------------------------------------------------------------

		// start timer
		t = clock();

		// allocate memory for array
		for(int i = 0; i < 10000; i++)
			array[i] = (Medium *) malloc(sizeof(Medium));

		// end timer
		t = clock() - t;

		// simulation ends -----------------------------------------------------------------------

		// print out overall time to allocate memory and time to allocate a single Small value
		printf("Time elapsed: %.3f ms\n", (double) t/CLOCK_PER_MILISECOND);
		printf("Average time to allocate memory: %.5f ms\n", (double) t/CLOCK_PER_MILISECOND/10000);

		// free up all the memory used by the array
		for(int i = 0; i < 10000; i++)
			free(array[i]);
		free(array);	
	}

	// run with large data types
	else if(*argv[1] == '3') {
		// create an array of Small pointer to allocate memory to
		Large **array;
		array = (Large **) malloc(sizeof(Large *) * 10000);

		// simulation start -----------------------------------------------------------------------

		// start timer
		t = clock();

		// allocate memory for array
		for(int i = 0; i < 10000; i++)
			array[i] = (Large *) malloc(sizeof(Large));

		// end timer
		t = clock() - t;

		// simulation ends -----------------------------------------------------------------------

		// print out overall time to allocate memory and time to allocate a single Small value
		printf("Time elapsed: %.3f ms\n", (double) t/CLOCK_PER_MILISECOND);
		printf("Average time to allocate memory: %.5f ms\n", (double) t/CLOCK_PER_MILISECOND/10000);

		// free up all the memory used by the array
		for(int i = 0; i < 10000; i++)
			free(array[i]);
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