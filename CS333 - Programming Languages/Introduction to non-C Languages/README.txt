CS333 - Project 3 - README
Samuel Munoz
02/26/2020

Directory Layout:
'Project 2'
|
│   README.txt
│
├───C#
│   └───HelloWorld
│       │   HelloWorld.csproj
│       │   Program.cs
│       │
│       ├───bin
│       │   └───Debug
│       │       └───netcoreapp3.1
│       │               HelloWorld.deps.json
│       │               HelloWorld.dll
│       │               HelloWorld.exe
│       │               HelloWorld.pdb
│       │               HelloWorld.runtimeconfig.dev.json
│       │               HelloWorld.runtimeconfig.json
│       │
│       └───obj
│           │   HelloWorld.csproj.nuget.cache
│           │   HelloWorld.csproj.nuget.dgspec.json
│           │   HelloWorld.csproj.nuget.g.props
│           │   HelloWorld.csproj.nuget.g.targets
│           │   project.assets.json
│           │
│           └───Debug
│               └───netcoreapp3.1
│                       HelloWorld.AssemblyInfo.cs
│                       HelloWorld.AssemblyInfoInputs.cache
│                       HelloWorld.assets.cache
│                       HelloWorld.csproj.FileListAbsolute.txt
│                       HelloWorld.csprojAssemblyReference.cache
│                       HelloWorld.dll
│                       HelloWorld.exe
│                       HelloWorld.pdb
│
├───C++
│       hello_world.cpp
│       hello_world.exe
│
├───Flex
│       encode
│       encode.yy
│       extension1
│       extension1.yy
│       extension2
│       extension2.yy
│       file.txt
│       file3.txt
│       file4.txt
│       lex.yy.c
│       task2
│       task2.yy
│       task3
│       task3.yy
│       task4
│       task4.yy
│
└───Go
        hello_world.exe
        hello_world.go


Run and build configuration for Flex:
flex 2.6.4 (written using Ubuntu 7.4.0)

Description for each subsection:
	Flex:
		task1:
			This task was to create an encode using flex where every character entered into the console was shifted over 13 characters. I achieve this task by converting character values over to integers and do some modular math so that when a character value is shifted, the modular arithmetic will allow flex to know how far away this new character is from either 'a' or 'A'. Then I would add that value to the integer value of 'a' or 'A'.

			Compile:
				flex ./Flex/encode.yy ; gcc -o ./Flex/encode ./Flex/lex.yy.c -lfl
			Run:
				echo <input> | ./Flex/encode
			Output
				...

		task2:
			This task was about writing a flex script that could tell how many rows, characters, and vowels were in one text file. What I did to achieve this task was in the first section of the flex code, I created some varibables that would store the number of rows, characters, and vowels. Then, in the next section of the flex code, whenever certain tokens were read, the code will modify the variables. In the last section of the flex code where C code is written, I print out the number of lines, vowels, and characters in a file

			Compile:
				flex ./Flex/task2.yy ; gcc -o ./Flex/task2 ./Flex/lex.yy.c -lfl
			Run:
				./Flex/task2 <input text file>
			Output:
				...

		task3:
			This task was about writing a flex script that removed the html tags from a file. All I did in this task was wrote a regex string that targeted all html tags. The regex string works by first targeting an open angle bracket ('<'), then target zero or more non close angle brackets ('>'), and end the string by targeting ('>'). Any token with that format was not printed out in the code. All other characters were printed out with the exception of tabs ('\t').

			Compile:
				flex ./Flex/task3.yy ; gcc -o ./Flex/task3 ./Flex/lex.yy.c -lfl
			Run:
				./Flex/task3 <input text file>
			Output:
				...

		task4:
			This task was about creating a mini parser with rules given in the instructions page. The big idea with this task was to figure out what order to place all rules so that the most abstracted idea held the highest priority and low level tasks held the lowest level task. Therefore, in my parser, the rules with the highest priority were things like keywords and identifier and rules with the lowest priority were rules about integers and floats.

			Compile: 
				flex ./Flex/task4.yy ; gcc -o ./Flex/task4 ./Flex/lex.yy.c -lfl
			Run:
				./Flex/task4 <input text file>
			Output:
				...

		extension1:
			This task is an extension to show different types of comments. The core difference between making comments in C and Flex, in Flex, only multi-line comments can be made anywhere throughout the source code. The only place where single line comments are allowed are in %{ %} blocks

			Comiple:
				flex ./Flex/extension1.yy ; gcc -o ./Flex/extension1 ./Flex/lex.yy.c -lfl
			Run:
				echo <char input> | ./Flex/extension1
			Output:
				...

		extension2:
			This task is an extension is to create a more complex encoder. I have made the encoder more complex by shifting every vowel to next vowel (i.e. a->e or o->u or u->a). Additional, all other characters have been shifted over the next 100 characters in the alphabet. Note that the program only works if all characters are lowercase.

			Compile:
				flex ./Flex/extension2.yy ; gcc -o ./Flex/extension2 ./Flex/lex.yy.c -lfl
			Run:
				echo <char input>
			Output:
				... 

	C++:
		hello_world:
			This is a quick overview over how to compile my c++ code

			Compile:
				g++ ./C++/hello_world.cpp
			Run:
				./C++/hello_world.exe <command line arguments>

	C#:
		hello_world:
			This is a quick overview over how to compile my c# code

			Compile and Run:
				dotnet run ./C#/HelloWorld/ <command line arguments>

	Go (Extension):
		hello_world:
		This is a quick overview over how to complie my go code

		Compile:
			go build ./Go/hello_world.go
		Run:
			./Go/hello_world.exe <command line arguments>