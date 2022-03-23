/*
 * File: colorize.cpp
 * Purpose: To use mutlithreading to modify the pixel colors of an image
 * Author: Samuel Munoz
 * Date: 05-06-2020
 */

#include <iostream>
#include <cstdlib>
#include <thread>
#include <ctime>
#include "ppmIO.h"

// THERE IS SOME MEMORY LEAK WITH THIS PROGRRAM
// RUN THIS PROGRAM AT YOUR OWN RISK

// struct to hold the parameter for colorize method
typedef struct {
	Pixel *src;
	int start, extent; 
	int colors;
} PixelArray;

// this does whatever colorize does
void *colorize(PixelArray *p) {
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

	// read image and check for errors
	src = ppm_read( &rows, &cols, &colors, argv[1] );
	if( !src ) {
		std::cout << "Unable to read file " << argv[1] << std::endl;
		exit(-1);
	}

	// check usage
	if( argc < 3 ) {
		std::cout << "Usage: " << argv[0] << " <image_filename number_of_threads>" << std::endl;
		exit(-1);
	}

	// read image and check for errors
	src = ppm_read( &rows, &cols, &colors, argv[1] );
	if( !src ) {
		std::cout << "Unable to read file " << argv[1] << std::endl;
		exit(-1);
	}

	// read the number of threads for application and check if it is a valid number
	try {
		NUM_OF_THREADS = atoi(argv[2]);
	}
	catch(std::exception const &e) {
		std::cout << "\"" << argv[2] << "\" is not an integer. Thread size can only be an integer" << std::endl;
	}
	if(NUM_OF_THREADS <= 0) {
		std::cout << "You cannot create zero or less threads" << std::endl;
		exit(-1);
	}
	if(NUM_OF_THREADS > (rows * cols)/2) {
		std::cout << "Threads count must at most half the size of the array." << std::endl;
		exit(-1);
	}

	// if the user wants the program to be single threaded
	if(NUM_OF_THREADS == 1) {
		PixelArray p;
		p.start = 0;
		p.extent = rows * cols;
		p.colors = colors;
		p.src = new Pixel[p.extent];
		for(int i = p.start; i < p.extent; i++)
			p.src[i] = src[i];
		t = clock();
		colorize(&p);

		for(int i = p.start; i < p.extent; i++)
			src[i] = p.src[i];

		t = clock() - t;

		delete[] p.src;
	}
	// if the user wants the program to be multi-threaded
	else {
		std::thread threads[NUM_OF_THREADS];
		PixelArray arrays[NUM_OF_THREADS];

		// determine the average amount of rows each subarray should analyze
		int average = rows / NUM_OF_THREADS;

		// keep track of where a subarray ends
		int end_count = 0;

		// divide the Pixel into several different arrays
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			arrays[i].start = end_count;
			arrays[i].extent = average*cols;
			if(i == NUM_OF_THREADS-1)
				arrays[i].extent = rows * cols - arrays[i].start;
			end_count = arrays[i].start + arrays[i].extent;

			arrays[i].src = new Pixel[arrays[i].extent];
			for(int j = arrays[i].start; j < arrays[i].start + arrays[i].extent; j++)
				arrays[i].src[j - arrays[i].start] = src[j];

			arrays[i].colors = colors;
		}

		// start timer
		t = clock();

		// create threads to run colorize method
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			try {
				threads[i] = std::thread(colorize, &arrays[i]);
			}
			catch(std::runtime_error &e) {
				std::cout << "You tried to create too many threads. You can only create " << (i-1) << " threads at this moment" << std::endl;
				exit(-1);
			}
		}

		// end the threads from running
		for(int i = 0; i < NUM_OF_THREADS; i++)
			threads[i].join();

		// copy arrays into orginal array
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			for(int j = arrays[i].start; j < arrays[i].start + arrays[i].extent; j++)
				src[j] = arrays[i].src[j - arrays[i].start];
		}

		// end timer
		t = clock() - t;

		// free all the memory created in the src field for all PixelArrays
		for(int i = 0; i < NUM_OF_THREADS; i++)
			delete[] arrays[i].src;
	}

	// write out the image
	ppm_write( src, rows, cols, colors, "bold.ppm" );

	// display time 
	std::cout << "Time elapsed: " <<  (int) t/1000 << " ms" << std::endl;

	free(src);

	return(0);
}

/*
Sources:
https://www.tutorialspoint.com/cplusplus/cpp_multithreading.htm
https://stackoverflow.com/questions/41373040/stdthread-and-exception-handling
*/