CS333 - Project 8 - README
Samuel Munoz
05/08/2020

Directory Layout:
'Project 8'
├── C
│   ├── task1
│   ├── task1.c
│   └── task2
│       ├── bold.ppm
│       ├── colorize
│       ├── colorize.c
│       ├── IMG_4203.ppm
│       ├── kit.tar
│       ├── ppmIO.c
│       └── ppmIO.h
└── C++
    ├── task1
    ├── task1.cpp
    └── task2
        ├── bold.ppm
        ├── colorize
        ├── colorize.cpp
        ├── IMG_4203.ppm
        ├── kit.tar
        ├── ppmIO.cpp
        └── ppmIO.h


Run and build configuration for C:
gcc (Ubuntu 5.4.0-6ubuntu1~16.04.12) 5.4.0 20160609

Description for each subsection:
C:
  task1:
    This program sort an integer array populated with random values. The user can control the length of the array to sort and the number of theads to create to sort the array. Let the number of threads the user can create be some number N. The program only allows N to be greater than zero, but smaller than half the size the array. The program outputs the time it takes to run the program and the number of threads the program used. NOTE: I use the clock method to time my application. I am using a 32-bit computer, so running the program on a 64-bit computer my lead to inaccurate results on another computer.

    Compile:
  		gcc -o ./task1 task1.c -pthread
    Run:
  		./task1 <length_of_array number_of_threads>
    Output:
  		Time elasped: <integer> ms
      Number of threads created: <integer>

  task2:
    This program takes in a ppm file and modifies the file. The algorthim used to modify the image is the same algorthim included inside the C-kit. The program also allows the user to be to enter the number of threads the program should use. The program outputs the time it took run application and the number of the threads used. NOTE: The number of threads the user can create is limited to the same interval has in task1 and there are the same timing issues as in task1

    Compile:
      gcc -o ./colorize -I. colorize.c ppmIO.c -lm -pthread
    Run:
      ./colorize <file_name number_of_threads>
    Output:
      Time elapsed: <integer> ms

C++:
  task1:
    This program sort an integer array populated with random values. The user can control the length of the array to sort and the number of theads to create to sort the array. Let the number of threads the user can create be some number N. The program only allows N to be greater than zero, but smaller than half the size the array. The program outputs the time it takes to run the program and the number of threads the program used. NOTE: I use the clock method to time my application. I am using a 32-bit computer, so running the program on a 64-bit computer my lead to inaccurate results on another computer.

    Compile:
      g++ -o ./task1 task1.c -pthread -std=c++11
    Run:
      ./task1 <length_of_array number_of_threads>
    Output:
      Time elasped: <integer> ms
      Number of threads created: <integer>

  task2:
    This program takes in a ppm file and modifies the file. The algorthim used to modify the image is the same algorthim included inside the C-kit. The program also allows the user to be to enter the number of threads the program should use. The program outputs the time it took run application and the number of the threads used. NOTE: The number of threads the user can create is limited to the same interval has in task1 and there are the same timing issues as in task1. Also, there is some memory leakage in the program, but it comes from using C code in a C++ program.

    Compile:
      g++ -o ./colorize -I. colorize.cpp ppmIO.cpp -lm -pthread -std=c++11
    Run:
      ./colorize <file_name number_of_threads>
    Output:
      Time elapsed: <integer> ms

Extension:
  I undertook one extension for this project. If you have not already noticed, all my tasks suppport create N number of threads. To control the number of threads, you change the second command line argument. Some of the issues I ran into was creating more threads than resources, creating subarrays with size of one (hence why the number of threads is at most half the size of the array modified), and preventing the user from creating 0 or negative.