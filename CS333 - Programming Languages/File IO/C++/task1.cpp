/*
 * File: task1.cpp
 * Purpose: To create a program that will read a file and store the number of times a word is used
 * Author: Samuel Munoz
 * Date: 04-13-2020
 */

#include <iostream>
#include <fstream> // package used to read and write from files
#include <cstring>
#include <string>
#include <cctype>
#include <cstdlib>
#include <vector>

void clear(char *string, int size) {
	for(int i = 0; i < size; i++)
		string[i] = '\0';
}

int find(std::pair<char *, int> **array, int size, char *key) {
	for(int i = 0; i < size; i++) {
		if(strcmp(array[i]->first, key) == 0) 
			return i;
	}
	return -1;
}

void sort(std::pair<char *, int> **array, int size) {
	for(int i = 0; i < size; i++) {
		for(int j = 0; j < size - i - 1; j++) {
			if(array[j]->second < array[j+1]->second) {
				std::pair<char *, int> *temp = array[j];
				array[j] = array[j+1];
				array[j+1] = temp; 
			}
		}
	}
}

int main(int args, char *argv[]) {
	// checks if an argument is passed into the prgram and if not, throw an error message and terminate the program
	if(args < 2) {
		std::cout << "command: ./task1 <filename>" << std::endl;
		return 0;
	}

	// creates an array to store Pairs
	std::pair<char *, int> **pair_array = (std::pair<char *, int> **) malloc(sizeof(std::pair<char *, int>) * 256);
	int size = 0;

	// class from fstream; creates a read stream for a file
	std::ifstream file(argv[1]);
	if(file.is_open()) {
		char c;
		char *word = (char *) malloc(sizeof(char) * 64);
		// clear(word, 64);
		int index = 0;
		while(file.get(c)) {
			if((int) c == 46 || (int) c == 44) {
				word[index] = '\0';
				file.get(c);
			}
			// if character is not a whitespace, comma, or peroid character 
			if((int) c == 32 || (int) c == 46 || (int) c == 44) {
				word[index] = '\0';

				// if the map has a pre-existing identical key, then modify the orginial key
				// int index = find(pair_array, 256, word);
				int location = find(pair_array, size, word);
				if(location != -1) {
					pair_array[location]->second += 1;
				}
				// if the map does not have the key inside, add new key
				else {
					std::pair<char *, int> *new_pair = (std::pair<char *, int> *) malloc(sizeof(std::pair<char *, int>));
					new_pair->first = (char *) malloc(sizeof(char) * 64);
					// clear(new_pair.first, 64);
					strcpy(new_pair->first, word);
					new_pair->second = 1;
					pair_array[size++] = new_pair;
				}
				// clear(word, 64);
				index = 0;
			}
			else {
				// std::cout << (int) c << std::endl;
				word[index++] = (char) tolower(c);
			}

		}
		file.close();	

		std::cout << "Size: " << size << std::endl;

		sort(pair_array, size);

		for(int i = 0; i < 20; i++) {
			std::cout << pair_array[i]->first << "\t\t\t" << pair_array[i]->second << std::endl;
		}

		free(word);
		for(int i = 0; i < size; i++) {
			free(pair_array[i]->first);
			free(pair_array[i]);
		}
	}

	else
		std::cout << "Unable to open the file" << std::endl;
	
	return 0;
} 

/*
Sources
https://en.cppreference.com/w/cpp/filesystem/exists
https://en.cppreference.com/w/cpp/filesystem/exists
https://www.cplusplus.com/reference/map/map/operator[]/
https://www.cplusplus.com/reference/string/string/c_str/
https://www.cplusplus.com/reference/string/string/c_str/
*/