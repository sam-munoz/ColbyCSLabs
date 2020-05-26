CS333 - Project 3 - README
Samuel Munoz
03/04/2020

Directory Layout:
'Project 3'
|
│   README.txt
│
├───C
│       cstk.c
│       cstk.h
│       cstktest
│       cstktest.c
│       test
│       test.c
│       test.exe
│
├───C#
│   ├───task1
│   │   │   Program.cs
│   │   │   task1.csproj
│   │   │
│   │   ├───bin
│   │   │   └───Debug
│   │   │       └───netcoreapp3.1
│   │   │               task1.deps.json
│   │   │               task1.dll
│   │   │               task1.exe
│   │   │               task1.pdb
│   │   │               task1.runtimeconfig.dev.json
│   │   │               task1.runtimeconfig.json
│   │   │
│   │   └───obj
│   │       │   project.assets.json
│   │       │   task1.csproj.nuget.cache
│   │       │   task1.csproj.nuget.dgspec.json
│   │       │   task1.csproj.nuget.g.props
│   │       │   task1.csproj.nuget.g.targets
│   │       │
│   │       └───Debug
│   │           └───netcoreapp3.1
│   │                   task1.AssemblyInfo.cs
│   │                   task1.AssemblyInfoInputs.cache
│   │                   task1.assets.cache
│   │                   task1.csproj.FileListAbsolute.txt
│   │                   task1.csprojAssemblyReference.cache
│   │                   task1.dll
│   │                   task1.exe
│   │                   task1.pdb
│   │
│   ├───task2
│   │   │   Program.cs
│   │   │   task2.csproj
│   │   │
│   │   ├───bin
│   │   │   └───Debug
│   │   │       └───netcoreapp3.1
│   │   │               task2.deps.json
│   │   │               task2.dll
│   │   │               task2.exe
│   │   │               task2.pdb
│   │   │               task2.runtimeconfig.dev.json
│   │   │               task2.runtimeconfig.json
│   │   │
│   │   └───obj
│   │       │   project.assets.json
│   │       │   task2.csproj.nuget.cache
│   │       │   task2.csproj.nuget.dgspec.json
│   │       │   task2.csproj.nuget.g.props
│   │       │   task2.csproj.nuget.g.targets
│   │       │
│   │       └───Debug
│   │           └───netcoreapp3.1
│   │                   task2.AssemblyInfo.cs
│   │                   task2.AssemblyInfoInputs.cache
│   │                   task2.assets.cache
│   │                   task2.csproj.FileListAbsolute.txt
│   │                   task2.csprojAssemblyReference.cache
│   │                   task2.dll
│   │                   task2.exe
│   │                   task2.pdb
│   │
│   └───task3
│       │   Program.cs
│       │   task3.csproj
│       │
│       ├───bin
│       │   └───Debug
│       │       └───netcoreapp3.1
│       │               task3.deps.json
│       │               task3.dll
│       │               task3.exe
│       │               task3.pdb
│       │               task3.runtimeconfig.dev.json
│       │               task3.runtimeconfig.json
│       │
│       └───obj
│           │   project.assets.json
│           │   task3.csproj.nuget.cache
│           │   task3.csproj.nuget.dgspec.json
│           │   task3.csproj.nuget.g.props
│           │   task3.csproj.nuget.g.targets
│           │
│           └───Debug
│               └───netcoreapp3.1
│                       task3.AssemblyInfo.cs
│                       task3.AssemblyInfoInputs.cache
│                       task3.assets.cache
│                       task3.csproj.FileListAbsolute.txt
│                       task3.csprojAssemblyReference.cache
│                       task3.dll
│                       task3.exe
│                       task3.pdb
│
├───C++
│       a.exe
│       task1.cpp
│       task2.cpp
│       task3.cpp
│
└───Go
        task1.go
        task2.exe
        task2.go
        task3.exe
        task3.go

Run and build configuration for C:
gcc.exe (MinGW.org GCC-8.2.0-5) 8.2.0

Description for each subsection:
C:
  cstktest.c:
    This set of files tests my implementation of the stack. cstk.h holds the stack for my stack and all the prototypes for my functions. cstk.c holds all the member functions along with the global variable. cstktest.c tests the stack
    
    Compile:
  		gcc -o cstktest cstktest.c cstk.c
    Run:
  		./cstktest
    Output:
  		The original list: 0 1 2 3 4 5 6 7 8 9
  		The reversed list: 9 8 7 6 5 4 3 2 1 0

  Extension:
    I took one extension that involving the stack. In my stack, I programmed the stack to expand and added a merge function. The merge function takes in two stacks and creates new stack that contains the top element of stack a on the top of new stack, then followed by the top of stack b, then the second element in stack a, then the second element in stack b. Here is an example. Stack a: top->[3,2,1], stack b: top->[4,5,6]. The stack in the function's first argument location has priority meaning that its elements are on top of the other stack. In this case, stack a has priority. Result: top->[3, 4, 2, 5, 1, 6] 
		
     Compile:
		 	gcc -o ./test test.c cstk.c
     Run:
		 	./test <size_of_stack_a> <size_of_stack_b>
     Output:
		 	...

C++:
  This is the selected language programming task in C++. 

  The root directory for this project is: 'Project 3'/C++

  task1:
    task1.cpp show all the different identifier rules, variable declaration rules, and scope rules
    
    Compile:
      gcc -o ./task1 task2.cpp
    Run:
      ./task1
    Output:
      in main: 1
      ...
      in function (global_var): 12

  task2:
    task2.cpp is a binary search that searches the value given in the command line. The array created in this program has every element equal to its index plus one. 

    Compile:
      gcc -o ./task2 task2.cpp
    Run:
      ./task2 <size_of_array> <value_to_find>
    Output:
      ...

  task3:
    task3.cpp displays all the different data types and all the operators that can be used with those data types.

    Compile:
      gcc -o ./task3 task3.cpp
    Run:
      ./task3
    Output:
      ...

C#:
  This is the selected language for the programming task

  Each task has its own root directory because of the way how .DOTNET Core creates different C# projects

  task1:
    task1 show all the different identifier rules, variable declaration rules, and scope rules
    
    The root directory for this task is: 'Project 3'/C#/task1

    Compile and Run:
      dotnet run
    Output:
      in main: 0
      ...
	  in function: 3

  task2:
    task2 is a binary search that searches the value given in the command line. The array created in this program has every element equal to its index plus one. 
    
    The root directory for this task is: 'Project 3'/C#/task2

    Compile and Run:
      dotnet run
    Output:
      ...

  task3:
    task3 displays all the different data types and their corresponding operators 
    
    The root directory for this task is: 'Project 3'/C#/task3

    Compile and Run:
      dotnet run
    Output:
      ...

Go:
  This is a third and extension for this project.

  The root directory for this project is: 'Project 3'/Go

  task1:
    task1.go show all the different identifier rules, variable declaration rules, and scope rules
    
    Compile:
      go build task1.go
    Run:
      ./task1
    Output:
      in main: 0
      ...
      in function: 3

  task2:
    task2.go is a binary search that searches the value given in the command line. The array created in this program has every element equal to its index plus one. 

    Compile:
      go build task2.go
    Run:
      ./task2 <length_of_array> <search_value>
    Output:
      ...

  task3:
    task3.go displays all the different data types and all the operators that can be used with those data types.

    Compile:
      go build task3.go
    Run:
      ./task3
    Output:
      ...
