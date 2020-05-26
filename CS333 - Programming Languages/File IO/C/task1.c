/*
 * File: task1.c
 * Purpose: To create a program that reads a file and store each word's frequency
 * Author: Samuel Munoz
 * Date: 04-15-2020
 */
#include <stdio.h> // standard and file input and output package
#include <ctype.h> // provides methods to handle char arrays
#include <unistd.h> // provides method to check if a file exists
#include <string.h> // provides more methods to handle char arrays

// struct to store array
typedef struct {
	char word[100];
	int frequency;
} Pair;

int findKey(Pair *array, int size, char *key) {
	for(int i = 0; i < size; i++) {
		if(strcmp(array[i].word, key) == 0)
			return i;
	}
	return -1;
}

void sort(Pair *array, int size) {
	for(int i = 0; i < size; i++) {
		for(int j = 0; j < size - i - 1; j++) {
			if(array[j].frequency < array[j+1].frequency) {
				Pair temp = array[j];
				array[j] = array[j+1];
				array[j+1] = temp;
			}
		}
	}
}

int main(int args, char* argv[]) {
	// if the argument is not give for the program, terminate program
	if(args < 2) {
		printf("command: ./task1 <filename>\n");
		return 0;
	}

	// checks if the file exists
	if(access(argv[1], R_OK) == -1) {
		printf("The program is unable to read the file.");
		return 0;
	}

	// creates Pair array to store Pairs
	Pair array[1000];
	int index = 0;

	// creates a FILE struct
	FILE *file;
	file = fopen(argv[1], "r");

	
	// char array to stores words
	char word[100];

	while(fscanf(file, "%s", word) == 1) {
		// // convert word into lowercase and removes any commas + periods
		for(int i = 0; (int) word[i] != 0 && i < sizeof(word)/sizeof(char); i++) {
			if((int) word[i] == 44 || (int) word[i] == 46)
				word[i] = '\0';
			else
				word[i] = tolower(word[i]);
		}
	
		int locate = findKey(array, index, word);
		if(locate != -1)
			array[locate].frequency += 1;
		else {
			strcpy(array[index].word, word);
			array[index++].frequency = 1;
		}
	}

	sort(array, index);

	for(int i = 0; i < 20; i++)
		printf("%s\t\t\t%d\n", array[i].word, array[i].frequency);

	printf("Size: %d\n", index);

	fclose(file);

	return 0;
}