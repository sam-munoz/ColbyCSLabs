/*
 * File: Program.cs
 * Purpose: To show the different rules for naming identifier, variable declaration, and identifier scope
 * Author: Samuel Munoz
 * Date: 03-01-2020
 */
using System;

namespace task1
{
    class Program
    {
    	static void displayText() {
    		// function scope
    		int i = 3;
    		Console.WriteLine("in function: " + i);
    	}

        static void Main(string[] args)
        {
        	// block scope holds
            int @void = 10;
            int i = 0;
            Console.WriteLine("in main: " + i);
            for(i=1;i<2;i++) {
            	Console.WriteLine("in for loop 1: " + i);
            	for(i=2;i<3;i++) {
            		Console.WriteLine("in for loop 2: " + i);
            		displayText();
            	}
            }
        }
    }
}

/*
Sources:
https://www.programiz.com/csharp-programming/keywords-identifiers
https://www.tutorialspoint.com/csharp/csharp_variables.htm
https://www.geeksforgeeks.org/scope-of-variables-in-c-sharp/
*/