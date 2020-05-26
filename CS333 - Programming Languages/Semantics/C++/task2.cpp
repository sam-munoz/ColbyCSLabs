/*
 * File: task2.cpp
 * Purpose: To show how I can make variables with data type function
 * Author: Samuel Munoz
 * Date: 03-07-2020
 */

#include <iostream>
#include <cstdlib>

int add(int a, int b) {
	return a+b;
}

int subtract(int a, int b, int c, int (*calc)(int, int)) {
	return a-calc(b,c);
}

int main(int args, char **argv) {
	if(args < 4) {
		printf("command: ./task2 <integer> <integer> <integer>\n");
		return 0;
	}
	
	int (*calc)(int, int);
	calc = add;

	int v1 = atoi(argv[1]);
	int v2 = atoi(argv[2]);
	int v3 = atoi(argv[3]);
	std::cout << "Add value: " << calc(v1, v2) << std::endl;

	std::cout << "Difference value: " << subtract(v1, v2, v3, calc);

	return 0;
}

/*
Source
https://www.cprogramming.com/tutorial/function-pointers.html
https://codeforwin.org/2017/12/pass-function-pointer-as-parameter-another-function-c.html
*/