CS333 - Project 5 - README
Samuel Munoz
04/08/2020

Directory Layout:
'Project 5'
│   README.txt
│
├───C
│       cllist.c
│       cllist.h
│       clltest.c
│       clltest.exe
│       testcllist.c
│       testcllist.exe
│
├───C#
│   └───LinkedList
│       │   LinkedList.cs
│       │   LinkedList.csproj
│       │   output.txt
│       │   Program.cs
│       │
│       ├───bin
│       │   └───Debug
│       │       └───netcoreapp3.1
│       │               LinkedList.deps.json
│       │               LinkedList.dll
│       │               LinkedList.exe
│       │               LinkedList.pdb
│       │               LinkedList.runtimeconfig.dev.json
│       │               LinkedList.runtimeconfig.json
│       │
│       └───obj
│           │   LinkedList.csproj.nuget.dgspec.json
│           │   LinkedList.csproj.nuget.g.props
│           │   LinkedList.csproj.nuget.g.targets
│           │   project.assets.json
│           │   project.nuget.cache
│           │
│           └───Debug
│               └───netcoreapp3.1
│                       LinkedList.AssemblyInfo.cs
│                       LinkedList.AssemblyInfoInputs.cache
│                       LinkedList.assets.cache
│                       LinkedList.csproj.CoreCompileInputs.cache
│                       LinkedList.csproj.FileListAbsolute.txt
│                       LinkedList.csprojAssemblyReference.cache
│                       LinkedList.dll
│                       LinkedList.exe
│                       LinkedList.genruntimeconfig.cache
│                       LinkedList.pdb
│
├───C++
│       cppllist.h
│       cpptestllist.cpp
│       cpptestllist.exe
│       output.txt
│
└───Go
    │   testllist.go
    │
    └───llist
            gollist.go


Run and build configuration for C:
gcc.exe (MinGW.org GCC Build-20200227-1) 9.2.0

Description for each subsection:
C:
	Linked List:
		This is an implementation of a linked list using C. The linked list is split into two files: cllist.h and cllist.c. cllist.h holds the prototypes for the linked list. I do not think it is necassary to have this header file, but I think it is good practice to include the header file. cllist.c holds the implementation for the methods prototyped in cllist.h. There are two different test files. testcllist.exe is test file I created to test the linked list and clltest.exe is the online test file for the project.

		Compile:
	  		For clltest.c: 
				gcc -o ./clltest clltest.c cllist.c
			For testcllist.c
				gcc -o ./testcllist testcllist.c cllist.c
		Run:
			For clltest.c:
				./clltest
			For testcllist:
				./testcllist
	  Output:
			...



Run and build configuration for C++:
g++.exe (MinGW.org GCC Build-20200227-1) 9.2.0

C++:
	This is the selected language programming task in C++. 
	
	Linked List:
		For C++, I have created a generic linked list. The entire list is prototyped and implemented inside cppllist.h. Originally, my plan was to prototype the linked list inside the header and create seperate file that implements all the prototyped methods. However, I ran into issues and eventually opted to implement my linked list in this format because it would be easier. The linked list uses templates to generic lists. 

		Compile:
			g++ -o ./cpptestllist cpptestllist.cpp
		Run:
			./cpptestllist
		Output:
			Look at file 'Project 5'/C++/output.txt
			
C#:
	This is the selected language programming task in C#. 

  	Linked List:
  		For C#, I have implemented my generic linked list by using the type parameters in creating my linked list. The type parameter function very similarly to how generics work in Java. The linked list is implemented inside the LinkedList.cs file. I think it is important that although the linked list is implmented in seperate file from main method, it is important to know that in C#, all C# project have to encapulated inside one container class. That means in LinkedList.cs, I have extended the scope of the class Program from Program.cs into LinkedList.cs. This also means the LinkedList is a child of the Program class

  		Compile and run:
			goto: 'Project 5'/C#/LinkedList
  			run command: dotnet run

  		Output: 
  			look under 'Project 5'/C#/LinkedList/output.txt