CS333 - Project 4 - README
Samuel Munoz
03/11/2020

Directory Layout:
'Project 4'
|
│   README.txt
│
├───C
│       task1
│       task1.c
│       task2
│       task2.c
│
├───C#
│   ├───extension1
│   │   │   extension1.csproj
│   │   │   Program.cs
│   │   │
│   │   ├───bin
│   │   │   └───Debug
│   │   │       └───netcoreapp3.1
│   │   │               extension1.deps.json
│   │   │               extension1.dll
│   │   │               extension1.exe
│   │   │               extension1.pdb
│   │   │               extension1.runtimeconfig.dev.json
│   │   │               extension1.runtimeconfig.json
│   │   │
│   │   └───obj
│   │       │   extension1.csproj.nuget.cache
│   │       │   extension1.csproj.nuget.dgspec.json
│   │       │   extension1.csproj.nuget.g.props
│   │       │   extension1.csproj.nuget.g.targets
│   │       │   project.assets.json
│   │       │
│   │       └───Debug
│   │           └───netcoreapp3.1
│   │                   extension1.AssemblyInfo.cs
│   │                   extension1.AssemblyInfoInputs.cache
│   │                   extension1.assets.cache
│   │                   extension1.csproj.FileListAbsolute.txt
│   │                   extension1.csprojAssemblyReference.cache
│   │                   extension1.dll
│   │                   extension1.exe
│   │                   extension1.pdb
│   │
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
│       task1.cpp
│       task1.exe
│       task2.cpp
│       task2.exe
│       task3.cpp
│       task3.exe
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
  This is the C programming task. The task1.c shows how programmers can create functions to pass into the qsort method to compare elements inside the qsort method. task2.c shows how function pointers can be used to run a function. In task2, the function pointer references a factorial function and runs it.
  When the base factorial becomes 12, 13, 14, ..., the values for those factorials become larger than the range for an integer, so because of the way bits and bytes add up (I have not taken CS232, so I don't really know what I am talking about), the values from the factorial values yield some random value that is not the answer.

  task1:
    Compile:
  		gcc -o ./task1 task1.c
    Run:
  		./task1 <base factorial>
    Output:
  		The sorted array is: 12 10 8 6 4 2 0 1 3 5 7 9 11 13

  task2:
    Compile:
      gcc -o ./task2 task2.c
    Run:
      ./task2
    Output

  Extension:
    I took two extensions for this project. First ... Then, ... Finally ...
    The test file to showcase the functionality is named cstktest2.
		
     Compile:
		 	gcc -o cstktest2 cstktest2.c cstk.c
     Run:
		 	./cstktest2 <base factorial>
     Output:
		 	Value of <base factorial>!: <value of the function>

Run and build configuration for C++:
g++.exe (MinGW.org GCC-8.2.0-5) 8.2.0

C++:
  This is the selected language programming task in C++. 

  task1:
    task1.cpp showcases the control flow statements in C++. The output is collection of print statements located in the blocks of code that the program control flow go through. In order to understand what print statements are doing, compare the print statements with the source code; sections of my print should be easily found in source code.

    Compile:
      g++ -o ./task1 task1.cpp
    Run:
      ./task1
    Output:
      ...

  task2:
    task2.cpp showcase how function pointers can be used to refernces functions and how those function pointers can be passed into the source. I have made some add methods to display the function pointers

    Compile:
      g++ -o ./task2 task2.cpp
    Run:
      ./task2 <integer> <integer> <integer>
    Output:
      ...    

  task3:
    task3.cpp shows how a bubble sort algorithm looks in C++. The program creates an array with each index having some random value between [0,9]. Then, the bubble sort algorithm sorts the array in descending order.

    Compile:
      g++ -o ./task3 task3.cpp
    Run:
      ./task3 <length of array>
    Output:
      ...

C#:
   This is the selected language programming task in C#. 

   To compile and run any task, make sure to be inside the directory of one of the task and execute:
  	dotnet run 


   task1:
    task1 showcases the control flow statements in C#. The output is collection of print statements located in the blocks of code that the program control flow go through. In order to understand what print statements are doing, compare the print statements with the source code; sections of my print should be easily found in source code.

   task2:
    task2 showcase how delegates can be used to create prototypes to store functions/methods. Once delegate instance are created, those instances can be used to store and run functions/methods. I have made some add methods to display the function pointers

    Command Line Arguements:
     dotnet run <integer> <integer> <integer>

    Output:
     Sum: <value>
     Difference: <value>

   task3:
    task3 shows how a bubble sort algorithm looks in C#. The program creates an array with each index having some random value between [0,9]. Then, the bubble sort algorithm sorts the array in descending order.

    Command Line Arguements:
     dotnet run <length of array>

    Output:
     Array: <array>
     Sorted Array: <sorted array>

   extension1:
    If the name of the directory does not imply this, this is an extension I took with C#. extension1 shows how polymorphism works in C#. Specifically, it shows some static polymorphism which is about overloading functions and some dynamic polymorphism which is about overriding functions from an abstract class.

    Output:
     ...


Run and build configuration for Go:    
go version go1.14 windows/amd64

Go:
   This is the language I choose to do as an extension.

   task1:
    task1.go showcases the control flow statements in Go. The output is collection of print statements located in the blocks of code that the program control flow go through. In order to understand what print statements are doing, compare the print statements with the source code; sections of my print should be easily found in source code.

    Compile:
     go build task1.go
    Run:
     ./task1
    Output:
     ...

   task2:
    task2.go showcase how function pointers can be used to refernces functions and how those function pointers can be passed into the source. I have made some add methods to display the function pointers

    Compile:
     go build task2.go
    Run:
     ./task2 <integer> <integer> <integer>
    Output:
     Sum: <value>
     Difference: <value>

   task3:
    task3.cpp shows how a bubble sort algorithm looks in C++. The program creates a slice with each index having some random value between [0,9]. Then, the bubble sort algorithm sorts the slice in descending order.
    
    Compile:
     go build task3.go
    Run:
     ./task2 <length of slice>
    Output:
     Slice: <slice>
     Sorted Slice: <sorted slice>