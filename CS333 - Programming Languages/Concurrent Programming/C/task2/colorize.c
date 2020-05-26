/*
 * File: colorize.c
 * Purpose: To create a mutlithreaded program to modify a PPM image in C
 * Author: Samuel Munoz
 * Date: 05-03-2020
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>
#include "ppmIO.h"

// command to run the code:
// gcc -o ./colorize -I. colorize.c ppmIO.c -lm -pthread

// strcut to hold the parameter for colorize method
typedef struct {
	Pixel *src;
	int start, extent; 
	int colors;
} PixelArray;

// this does whatever colorize does
void *colorize(void *pointer) {
	PixelArray *p = (PixelArray *) pointer;
	for(int i = 0; i < p->extent; i++) {
		p->src[i].r = p->src[i].r > 128 ? (220+p->src[i].r)/2 : (30+p->src[i].r)/2;
		p->src[i].g = p->src[i].g > 128 ? (220+p->src[i].g)/2 : (30+p->src[i].g)/2;
		p->src[i].b = p->src[i].b > 128 ? (220+p->src[i].b)/2 : (30+p->src[i].b)/2;
	}
}

int main(int argc, char *argv[]) {
	Pixel *src;
	int rows, cols, colors;
	int NUM_OF_THREADS = 1;
	clock_t t;

	// check usage
	if( argc < 3 ) {
		printf("Usage: %s <image_filename number_of_threads>\n", argv[0]);
		exit(-1);
	}

	// read image and check for errors
	src = ppm_read( &rows, &cols, &colors, argv[1] );
	if( !src ) {
		printf("Unable to read file %s\n", argv[1]);
		exit(-1);
	}

	// read the number of threads for application and check if it is a valid number
	NUM_OF_THREADS = atoi(argv[2]);
	if(NUM_OF_THREADS <= 0) {
		printf("You cannot create zero or less threads\n");
		exit(-1);
	}
	if(NUM_OF_THREADS > (rows * cols)/2) {
		printf("Threads count must at most half the size of the array.\n");
		exit(-1);
	}

	// if the user wants the program to be single threaded
	if(NUM_OF_THREADS == 1) {
		PixelArray p;
		p.src = (Pixel *) malloc(sizeof(Pixel) * rows * cols); 
		for(int i = 0; i < rows * cols; i++)
			p.src[i] = src[i];
		p.start = 0;
		p.extent = rows * cols;
		p.colors = colors;
		t = clock();
		colorize(&p);

		for(int i = 0; i < rows * cols; i++)
			src[i] = p.src[i];

		t = clock() - t;

		free(p.src);
	}
	// if the user wants the program to be multi-threaded
	else {
		pthread_t threads[NUM_OF_THREADS];
		PixelArray arrays[NUM_OF_THREADS];

		// determine the average amount of rows each subarray should analyze
		int average = rows / NUM_OF_THREADS;

		// store the last end row count
		int end_row = 0;

		// divide the Pixel into several different arrays
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			arrays[i].start = end_row;
			arrays[i].extent = average*cols;
			if(i == NUM_OF_THREADS-1)
				arrays[i].extent = rows * cols - arrays[i].start;
			end_row = arrays[i].start + arrays[i].extent;

			// printf("Size: %d\n", size);
			arrays[i].src = (Pixel *) malloc(sizeof(Pixel) * arrays[i].extent);
			for(int j = arrays[i].start; j < arrays[i].start + arrays[i].extent; j++)
				arrays[i].src[j - arrays[i].start] = src[j];

			arrays[i].colors = colors;
		}

		// create function pointer
		void *func_ptr = &colorize;

		// start timer
		t = clock();

		// create threads to run colorize method
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			int status;
			if((status = pthread_create(&threads[i], NULL, func_ptr, &arrays[i]))) {
				printf("Terminating program. You tried to create too many threads. This computer can only create %d threads at most\n", i-1);
				exit(1);
			}
		}

		// end the threads from running
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			pthread_join(threads[i], NULL);
		}

		// copy arrays into orginal array
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			for(int j = arrays[i].start; j < arrays[i].start + arrays[i].extent; j++)
				src[j] = arrays[i].src[j - arrays[i].start];
		}

		// end timer
		t = clock() - t;

		// free memory
		for(int i = 0; i < NUM_OF_THREADS; i++)
			free(arrays[i].src);
	}

	// write out the image
	ppm_write( src, rows, cols, colors, "bold.ppm" );

	// display time 
	printf("Time elapsed: %.0f ms\n", (double) t/1000);

	free(src);

	return(0);
}

/*
Sources:
https://linux.die.net/man/3/pthread_create
https://stackoverflow.com/questions/9074229/detecting-memory-leaks-in-c-programs
*/