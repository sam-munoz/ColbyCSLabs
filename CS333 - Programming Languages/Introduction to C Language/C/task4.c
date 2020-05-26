/*
 * File: task4.c
 * Purpose: Looks at the byte order of structs
 * Author: Samuel Munoz
 * Date: 02-06-2020
 */
#include <stdio.h>
#include <stdlib.h>

typedef struct User {
	char grade;
	short age;
	double height; // in meters 
} USER;

int main(int arg, char* argv[]) {
	struct User user1;

	user1.grade = 'A';
	user1.age = 34;
	user1.height = 1.52;

	unsigned char* ptr = (unsigned char*) &user1;

	// printf("User's grade is %c\n", user1.grade);
	// printf("User's age is %d\n", user1.age);
	// printf("User's height is %1.2f meters\n", user1.height);

	printf("Char length: %d\n",(int) sizeof(char));
	printf("Short length: %d\n",(int) sizeof(short));
	printf("Double length: %d\n",(int) sizeof(double));

	for(int i=0;i<sizeof(USER);i++) {
		printf("%d: %02X\n", i, ptr[i]);
	}

	return 0;
}
