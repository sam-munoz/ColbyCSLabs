CS333 - Project 6 - README
Samuel Munoz
04/27/2020

Directory Layout:
'Project 6'
│   README.txt
│
├───C
│       README.txt
│       task1.c
│       task1.exe
│       task2.c
│       task2.exe
│
├───C#
│   └───task1
│       │   Program.cs
│       │   README.txt
│       │   task1.csproj
│       │
│       ├───bin
│       │   └───Debug
│       │       └───netcoreapp3.1
│       │               task1.deps.json
│       │               task1.dll
│       │               task1.exe
│       │               task1.pdb
│       │               task1.runtimeconfig.dev.json
│       │               task1.runtimeconfig.json
│       │
│       └───obj
│           │   project.assets.json
│           │   project.nuget.cache
│           │   task1.csproj.nuget.dgspec.json
│           │   task1.csproj.nuget.g.props
│           │   task1.csproj.nuget.g.targets
│           │
│           └───Debug
│               └───netcoreapp3.1
│                       task1.AssemblyInfo.cs
│                       task1.AssemblyInfoInputs.cache
│                       task1.assets.cache
│                       task1.csproj.CoreCompileInputs.cache
│                       task1.csproj.FileListAbsolute.txt
│                       task1.csprojAssemblyReference.cache
│                       task1.dll
│                       task1.exe
│                       task1.genruntimeconfig.cache
│                       task1.pdb
│
├───C++
│       README.txt
│       task1
│       task1.cpp
│       task1.exe
│
└───Go
        README.txt
        task1.exe
        task1.go


Run and build configuration for C:
gcc.exe (MinGW.org GCC Build-20200227-1) 9.2.0

Description for each subsection:
C:
  task1:
    This is the word sort algorithm in C. This program takes a file name as a command line argument and displays back the top 20 most frequent words in the file. The program displays C file I/O capablities and some string/character manipulation
    
    Compile:
  		gcc -o ./task1 task1.c
    Run:
  		./task1 <filename>
    Output:
  		Output sorted in file: 'Project 6'/C/output_task1.txt

  task2: 
    This file shows the different signal handling in C. The program split into three subparts. Each subpart represents one of the subtasks for question 2. The first subtask shows how C handles a SIGINT signal; the second handles a SIGFPE signal; and the third handles a SIGSEGV.

    Compile:
      gcc -o ./task2 task2.c
    Run:
      ./task2 <1 or 2 or 3> (the number correspond to the subtasks in question 2)
    Output:
      Output sorted in file: 'Project 6'/C/output_task2.txt

C++:
  This is the selected language programming task in C++. 

  task1.cpp:
    task1.cpp is a program that reads a file and prints back the top 20 most frequent words in that file. The program achieves this goal by using the fstream package to use C++ file I/O capabilities and I use some string/character manipulation to sort the words

  Compile:
    gcc -o ./task1 task1.cpp
  Run:
    ./task1 <filename>
  Output:
    Output sorted in file: 'Project 6'/C++/output_task1.txt

C#:
   This is the selected language programming task in C#. 

   task1:
	 	task1 is a C# program that reads a file and prints back the top 20 most frequent words in a file. To achieve this goal, I used the C# System.IO package to read the files and I use the built-in regex tools to sort the words out.

    Compile and Run:
     (Inside the 'Project 6'/C#/task1)
     dotnet run <filename>

    Run:
      Output is stored in file: 'Project 6'/C#/task1/output_task1.txt

Go:
  This is an extension language programing task in Go.

  task1:
    task1.go is a program that reads a file and prints out the top 20 most frequent words in a file. The program uses the io/ioutil package to read a file and I use some of the built-in string methods to sort out the words

    Compile:
      go build task1.go
    Run:
      ./task1 <filename>
    Output:
      Output stored in file: 'Project 6'/Go/output_task1.txt

  extension1:
    Built in the task1.go file, the program using the go's built-in hash map data structure called map. The map allows me to easily create keys and has a set of useful methods to see what keys are exists and what keys do not exist. Inside the go file, I use map to easily creates keys store the frequency of a word and use a method that checks if a key exists in the hash map.