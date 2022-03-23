/*
 * File: Program.cs
 * Purpose: To show all control flow in C#
 * Author: Samuel Munoz
 * Date: 03-09-2020
 */

using System;

namespace task1
{
    class Program
    {
        static void Main(string[] args)
        {
        	// variables to use to experiment with
	short i = 12;
	int[] array = new int[] {1, 2, 3, 4,5, 6, 7, 8, 9, 10};


	Console.WriteLine("---Selection Statements: If-Else Blocks---\n");
	// depending on whether the conditional inside the if is true, the conditional will decide whether run if block or else block
	if(i < 20) {
		Console.WriteLine("(1) Ran If-Block");
	} else {
		Console.WriteLine("(1) Ran Else-Block");
	}

	if(i > 20) {
		Console.WriteLine("(2) Ran If-Block");
	} else {
		Console.WriteLine("(2) Ran Else-Block");
	}

	// with a if-else statement, if the first condiitonal is false and the first if-else condition is true, then the if-else block runs 
	if(i > 20) {
		Console.WriteLine("(3) Ran If-Block");
	} else if(i > 10 && i < 20) {
		Console.WriteLine("(3) Ran If-Else-Block");
	} else {
		Console.WriteLine("(3) Ran Else-Block");
	}

	// the point of the if statement is have blocks of code to only run during certain conditions

	Console.WriteLine("-------------------------------------------\n");

	Console.WriteLine("------------Iteration Statements-----------\n");
	// while loop: loops a block code until a condition is false. Conditional loop checked before a block of code runs
	Console.WriteLine("While Loop: ");
	while(i > 0) {
		Console.Write(i-- + " ");
	}

	// do while loop: loops a block code until a condition is false. Conditional loop checked after a block of code runs
	Console.Write("\nDo-While Loop: ");
	do {
		Console.Write(++i + " ");
	} while(i < 12);
	// for loop: loops a block of code and allows declaration variables to be created
	Console.Write("\nFor Loops: ");
	for(int j = 12;j < 30;j += 2) {
		Console.Write(j + " ");
	}
	// range-based for loop: this is a for loop where the loops through an iterative data type
	Console.Write("\nForeach Loop: ");
	foreach(int element in array) {
		Console.Write(element + " ");
	}
	Console.WriteLine();
	Console.WriteLine("-------------------------------------------\n");

	Console.WriteLine("----------------Jump Statements------------\n");
	// this statements jump the control flow from one section of some code to another
	Console.Write("Break: ");
	foreach(int element in array) {
		if(element == 3) {
			Console.Write("Break occured!");
			break;
		}
		Console.Write(element + " ");
	}
	Console.Write("\nContinue: ");
	for(int element = 0; element < 4; element++) {
		if(element < 2) {
			Console.Write("Skipped!" + " ");
			continue;
		}
		Console.Write(element + " ");
	}

	Console.WriteLine("\n-------------------------------------------\n");

	Console.WriteLine("---------Other Types of Statements---------\n");
	i = 3;
	Console.Write("Swtiches: ");
	switch(i) {
		case 1:
			Console.WriteLine("Found case 1");
			break;
		case 2:
			Console.WriteLine("Found case 2");
			break;
		case 3:
			Console.WriteLine("Found case 3");
			break;
		default:
			Console.WriteLine("Found default");
			break;
	}

	Console.Write("More Concise Swtiches: ");
	int concise_switch = i switch {
		1 => 10,
		2 => 20,
		3 => 30,
		_ => 0 // this is the default case
	};
	Console.WriteLine(concise_switch);

	Console.WriteLine("-------------------------------------------");
        }
    }
}

// http://zetcode.com/lang/csharp/flowcontrol/