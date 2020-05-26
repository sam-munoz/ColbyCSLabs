CS333 - Project 1 - README
Samuel Munoz
02/06/2020

Directory Layout:
(Quick note: for some reason, the command 'tree' was not working, so this is written manually)
Project\ 1/
|--extension1.c
|--extension2.c
|--extension3.c
|--README.txt
|--task1.c
|--task2.c
|--task3.c
|--task4.c
|--task5.c

Run and build configuration for C:
gcc (Ubuntu 7.4.0-1ubuntu1~18.04.1) 7.4.0

Description for each subsection:
C:
	task1:
		a. This machine is a little-endian machine
		
		b. I know because if when I byte values from an integer, when I order the byte values from first byte value to the last value, the resulting hexadecimal value backwards to integer I entered.
		ex. when looking the number 1025, the byte values are byte 0: 01, byte 1: 04. 0104 in hexadecimal is 260 in decimal which is the wrong value. Flip the order of the bytes, 0401 in hexadecimal is 1025, which is the value I entered. That means this machine is a little-endian machine.

		Compile: 
			gcc task1.c
		Run: 
			./a.out
		Output: 
			For integer
			0: 01
			...
			7: 00

	task2: 	
		a. Towards the end of the process, the byte values become zero for a large number of bytes. However, after looking 120 bytes in, the byte change to non-zero values. I not sure what that stuff is, but it looks computer junk.
		
		b. Depending on the computer that I run my code, sometimes I see other variables. On my Rasperry Pi, I was able to see to other variables writen in, but when I ran the code on Linux machines inside the robotics labs, I cannot see the variables I created.

		Compile: 
			gcc task2.c
		Run: 
			./a.out
		Output:
			 ...

	task3: 
		When not using free when calling malloc, I saw the percentage of memory need for the program began to raise. The memory required increases continuously until I stoped the program to prevent the computer from using too much memory. When I inserted the free function after calling malloc, the memory requirements for the program remained at zero for the entire runtime.

		Compile: 
			gcc task3.c
		Run:
			./a.out
		Output:
			(No output)

	task4:	
		a. No, the sizeof results did not match I thought was going to happen. I thought that the struct would need 15 bytes, not 16 bytes. I got 15 bytes from 4 bytes for store the address location, 1 byte for the char, 2 bytes for the short, and 8 bytes for the double. Instead, there are 16 bytes in the struct. I am not totally sure why that is case, but if I were guess, my guess the last byte might be used to tell the computer the end of the struct.
		
		b. When I was looking at the Raspberry Pi version of the code, there were gaps between the different variables.

		Compile:
			gcc task4.c
		Run:
			./a.out
		Output:
			Char length: 1
			...
			15: 3F

	task5:
		I tried this task on many computers to see what happened. Genearlly speaking, when I ran the code, if the string was smaller than the size of the decision variable, then the code printed 'safe'. Otherwise, the program printed 'hacked'. However, some computers crashed when hacked happened (Macs) while other computers continued to print out more characters that exist beyond the bounds of deicision variables (Raspberry Pi).  

		Compile:
			gcc task5.c

		Run:
			./a.out
		Output: 
			...

	extension1: 
		This extension is about creating safe and hacked strings from the command line. The modified made from task 5 is that instead of having a char array, I use a char pointer to store the string and I create function that can find the length of the string using the char pointer. Using those two values, I can see if the string will create either a safe or hacked string.

		Compile: 
			gcc extension1.c
		Run:
			./a.out
		Output:
			...

	extension2:
		This extension is about creating runtime errors from simple c programs. In this program, in order to this program, this program requires two command line arguments. However, I can create a runtime error by giving command line arguments.

		Compile: 
			gcc extension2.c
		Run:
			./a.out
		Output:
			Segmentation fault (core dumped)

	extension3: 
		This extension is about finding a floating point value to find a value where if added one to that value, the output of the addition would equal the value (i.e. value+1 == value). In order to solve this problem, I created a function that takes in no arguments and it creates a local float variable that starts at 1. Then I run a infinite loop adding one to the float. Once the varibale equals itself plus one, the loop stops and returns the float value. That float value is the solution to the problem

		Compile:
			gcc extension3.c
		Run:
			./a.out
		Output:
			16777216.000000