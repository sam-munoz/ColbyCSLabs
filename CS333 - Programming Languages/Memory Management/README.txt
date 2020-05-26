CS333 - Project 7 - README
Samuel Munoz
04/28/2020

Directory Layout:
'Project 7'
|
|__/C/
|  |
|  |__/cstk.h
|  |__/cstk.c
|  |__/cstktest.c
|  |__/cstktest2.c
|
|__/rust/
|  |
|  |__/task*.rs
|
|__/scala/
|  |
|  |__/task*.scala

Run and build configuration for C:
OSX El Capitan 10.11.6 - Apple LLVM version 7.3.0 (clang-703.0.31)

Description for each subsection:
C:
  This is the C programming task. The cstk.h contain the necessary 
	structures and declarations of member functions, which meets the 
	specifications of task 1. The cstk.c file contain the implementation 
	of the member functions declared in the cstk.h file, which meets the 
	specifications of task 2.
  
  Compile:
		gcc -o cstktest cstktest.c cstk.c
  Run:
		./cstktest
  Output:
		The original list: 0 1 2 3 4 5 6 7 8 9
		The reversed list: 9 8 7 6 5 4 3 2 1 0

  Extension:
    I took two extensions for this project. First ... Then, ... Finally ...
    The test file to showcase the functionality is named cstktest2.
		
     Compile:
		 	gcc -o cstktest2 cstktest2.c cstk.c
     Run:
		 	./cstktest2
     Output:
		 	...

Scala:
  This is the selected language programming task in Scala. 

  To compile any task, execute
  	scalac task*.scala 

  To run any task, simply execute 
  	scala task*

  task1:
    task1.scala showcases the declaration and scoping rules. A more complete
    description can be found in the source file. The output of the program
    is two variables, x and y, printed to the screen multiple times. The first
    both are local references. The second is in a nested scope where x is 
		non-local and y is a local reference that shadows the outer scope.

  task2:
    task2.scala is the binary search and will output the index of 2 first in
    the array [0,1,2,3,4,5,6,7,8,9], and the next in an empty array.

  task3:
    task3.scala shows the basic types and operations. The output is only
    string concatenation as that is the only interesting output.


Rust:
   This is the selected language programming task in Rust. 

   To compile any task, execute
  	rustc task*.rs 

   To run any task, simply execute 
  	./task*

   task1:
	 	...

   task2:
	 	...

   task3:
	 	...