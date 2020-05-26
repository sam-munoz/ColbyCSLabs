/*
* File: Program.cs
* Purpose: To create a hello world program and print out command line arguments
* Author: Samuel Munoz
* Date: 02/26/2020
*/

// NOTE: this code was in part created by the dotnet framework which explains why some of this code where exist

// the keyword 'using' allows C# to import packages from the C# library
using System;

// the keyword 'namespace' is used to link this Program file is linked to the HelloWorld dotnet project I created
namespace HelloWorld
{
    class Program
    {
        // here is where main method that runs at runtime
        static void Main(string[] args)
        {
            // Console.WriteLine allows C# to print data types onto the console
            Console.WriteLine("Hello World!");
            Console.WriteLine("Command Line Arguments:");
            // for loops and the syntax for 'for' loops is the same as Java code
        	for(int i=0;i < args.Length;i++) {
                // one aspect about Console.WriteLine is that WriteLine can print any type of data, not just strings
        		Console.WriteLine(i + ": " + args[i]);
        	}
        }
    }
}
