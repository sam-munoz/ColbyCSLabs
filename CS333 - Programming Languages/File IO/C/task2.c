/*
 * File: task2.c
 * Purpose: To show how C handles signals
 * Author: Samuel Munoz
 * Date: 04-17-2020
 */
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

void sigint(int signum) {
	printf("Interrupted! (%d)", signum);
	exit(1);
}

void sigfpe(int signum) {
	printf("Invalid operation. Don't do that. Just DON'T!\n");
	exit(1);
	// return;
}

void sigsegv(int signum) {
	printf("Invalid access to memory. DON'T TRY TO MESS WITH ME!\n");
	exit(1);
}

void subtask1() {
	signal(SIGINT, sigint);

	while(1) {
		printf("Hello world!\n");
		sleep(1);
	}
}

void subtask2() {
	signal(SIGFPE, sigfpe);

	int v1 = 12;
	int v2 = 4;
	int v3 = 0;
	int v4 = 2;

	printf("%d\n", v1/v2);
	printf("%d\n", v2/v3);
	printf("%d\n", v3/v4);
}

void subtask3() {
	signal(SIGSEGV, sigsegv);

	char word[] = "someone is distracted\0";
	char *copy = NULL;

	strcpy(copy, word);

	printf("%s\n%s\n", word, copy);
}

int main(int args, char *argv[]) {
	if(args < 2) {
		printf("command: ./task2 <\"1\" or \"2\" or \"3\">\n");
		return 0;
	}
	int c = *argv[1];
	if(c == 49)
		subtask1();
	else if(c == 50)
		subtask2();
	else if(c == 51)
		subtask3();

	return 0;
}