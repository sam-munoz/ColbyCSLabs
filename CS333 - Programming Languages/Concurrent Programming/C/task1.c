/*
 * File: task1.c
 * Purpose: To create a mutlithreaded sorting algorthim in C
 * Author: Samuel Munoz
 * Date: 05-03-2020
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

// global variable to store the number of threads the program uses
int NUM_OF_THREADS = 1;

// struct to hold array with the capacity of the array
typedef struct {
	int *array;
	int size;
} IntArray;

// this method is a bubble sort method and it will sort an array inside an IntArray in ascending order
void *bubble_sort(IntArray *a) {
	for(int i = 0; i < a->size; i++) {
		for(int j = 0; j < a->size-i-1; j++) {
			if(a->array[j] > a->array[j+1]) {
				int temp = a->array[j];
				a->array[j] = a->array[j+1];
				a->array[j+1] = temp;
			}
		}
	}
}

// this method finds the array with the next smallest element in a set of arrays
int find_smallest(IntArray **sub, IntArray *indices) {
	// find the first usable element to store data
	int min = 0, index = 0;
	for(int i = 0; i < indices->size; i++) {
		if(indices->array[i] != -1) {
			min = sub[i]->array[indices->array[i]];
			index = i;
			break;
		}
	}

	// go through all elements in the array to find the array with the smallest value
	for(int i = index+1; i < indices->size; i++) { // note: the number of subarrays equals the size of the indices array 
		if(indices->array[i] != -1) {
			if(sub[i]->array[indices->array[i]] < min) {
				min = sub[i]->array[indices->array[i]];
				index = i;
			}
		}
	}

	// return the index of the array with the smallest value
	return index;
}

// this method frees the memory of any IntArray struct
void freeIA(IntArray *ptr) {
	free(ptr->array);
	free(ptr);
}

void array_sort(IntArray *a) {
	// determine the number of subarrays to create
	int sbc = NUM_OF_THREADS;

	// determine the average length of each subarray
	int sbl = a->size / sbc;

	// create an array of IntArrays to store all the subarrays
	IntArray **subarrays = (IntArray **) malloc(sizeof(IntArray *) * sbc);

	// copy values from the original array into the subarrays
	for(int i = 0; i < sbc; i++) {
		// determine the length of subarray
		int s = sbl;
		if(i == sbc-1)
			s = a->size - i*sbl;

		// create memory for new subarray
		subarrays[i] = (IntArray *) malloc(sizeof(IntArray));
		subarrays[i]->array = (int *) malloc(sizeof(int) * s);
		subarrays[i]->size = s;

		// copy values from the array into the subarray
		for(int j = 0; j < subarrays[i]->size; j++) {
			subarrays[i]->array[j] = a->array[i*4 + j];
		}
	}

	// create an array of threads to use
	pthread_t threads[NUM_OF_THREADS];

	// create a function to pass through the bubble method
	void *bub_sort = &bubble_sort;

	// run each thread with the bubble sort for every subarray
	for(int i = 0; i < sbc; i++) {
		int status;
		if((status = pthread_create(&threads[i], NULL, bub_sort, subarrays[i]))) {
			printf("Terminating program. The program can only create %d threads at most at this time\n", i-1);
			exit(1);
		}
	}

	// suspend program for one second
	sleep(0.01);

	// terminate all threads
	for(int i = 0; i < sbc; i++)
		pthread_join(threads[i], NULL);

	// print the subarrays
	// for(int i = 0; i < sbc; i++) {
	// 	printf("%d: ", i);
	// 	for(int j = 0; j < subarrays[i]->size; j++)
	// 		printf("%d, ", subarrays[i]->array[j]);
	// 	printf("\n");
	// }

	// create new array for indices
	IntArray *indices = (IntArray *) malloc(sizeof(IntArray));
	indices->array = (int *) malloc(sizeof(int) * sbc);
	indices->size = sbc;

	// populate all index with zero
	for(int i = 0; i < indices->size; i++)
		indices->array[i] = 0;

	// repopulates the original array with sorted values from the subarrays
	for(int i = 0; i < a->size; i++) {
		int index = find_smallest(subarrays, indices);
		// printf("array: %d\t\tindex: %d\t\tvalue: %d\n", index, indices->array[index], subarrays[index]->array[indices->array[index]]);
		a->array[i] = subarrays[index]->array[indices->array[index]];
		if(++(indices->array[index]) >= subarrays[index]->size)
			indices->array[index] = -1;
	}

	// free memory from all IntArrays
	for(int i = 0; i < sbc; i++) {
		freeIA(subarrays[i]);
	}
	freeIA(indices);
	free(subarrays);
}

int main(int args, char** argv) {
	// terminate program if there incorrent amount of command line arguments are entered
	if(args < 2) {
		printf("comand: ./task1 <length_of_array number_of_threads>\n");
		return 0;
	}

	// read length and number of threads from the command line
	int s = atoi(argv[1]);
	NUM_OF_THREADS = atoi(argv[2]);

	// check if s and NUM_OF_THREADS are valid choices
	if(NUM_OF_THREADS > s/2) {
		printf("Thread count must be at most half the size of array\n");
		return 0;
	}
	if(NUM_OF_THREADS <= 0) {
		printf("You cannot create zero or negative number of threads\n");
		return 0;
	}
	if(s < 2) {
		printf("You cannot create an array with less than 2 elements\n");
		return 0;
	}

	// create IntArray struct to store integers with the array size
	IntArray *a = (IntArray *) malloc(sizeof(IntArray));
	a->array = (int *) malloc(sizeof(int) * s);
	a->size = s;

	// populate array with random values
	srand(time(NULL));
	for(int i = 0; i < a->size; i++)
		a->array[i] = rand() % 100;


	// print the array onto the screen
	// printf("Unsorted array: ");
	// for(int i = 0; i < a->size; i++)
	// 	printf("%d, ", a->array[i]);
	// printf("\n");

	// sort the array and measure the time it takes to run the program
	clock_t t;
	t = clock();
	if(NUM_OF_THREADS != 1)
		array_sort(a);
	else
		bubble_sort(a);
	t = clock() - t;

	// print the array onto the screen
	// printf("Sorted array: ");
	// for(int i = 0; i < a->size; i++)
	// 	printf("%d, ", a->array[i]);
	// printf("\n");

	// print out the time elasped
	int const CLOCK_TICKS_PER_MILLISECOND = 1000;
	printf("Time elasped: %.0f ms\n", (double) t / CLOCK_TICKS_PER_MILLISECOND);
	printf("Number of threads created: %d\n", NUM_OF_THREADS);

	// free memory from IntArray created
	freeIA(a);

	return 0;
}

/*
Sources:
https://linux.die.net/man/3/pthread_create
https://www.cplusplus.com/reference/ctime/
https://www.cplusplus.com/reference/cfloat/

*/