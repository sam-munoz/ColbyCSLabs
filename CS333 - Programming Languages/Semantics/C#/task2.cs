/*
 * File: Program.cs
 * Purpose: To show how delegates in C# are similar to function pointers
 * Author: Samuel Munoz
 * Date: 03-07-2020
 */

using System;

delegate int MyDelegate(int a, int b);

namespace task2
{
    class Program
    {
    	static int add(int a, int b) {
    		return a+b;
    	}

    	static int subtract(int a, int b, int c, MyDelegate d) {
    		return a-d(b,c);
    	}

        static void Main(string[] args)
        {
        	if(args.Length <= 2) {
        		Console.WriteLine("command: dotnet run <integer> <integer> <integer>");
        	}
        	else {
	        	// this is an instance of the delegate
	        	// all delegates are object-oriented, so they must be create like an object
	        	// the parameter is the name of the function that delegate will refer to
	            MyDelegate md = new MyDelegate(add);
	            int v1 = Int32.Parse(args[0]);
	            int v2 = Int32.Parse(args[1]);
	            int v3 = Int32.Parse(args[2]);

	            Console.WriteLine("Sum: " + md(v1, v2));
	            Console.WriteLine("Difference: " + subtract(v1, v2, v3, md));
        	}
        }
    }
}

/*
Source
https://www.tutorialspoint.com/csharp/csharp_delegates.htm
https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/delegates/using-delegates
*/