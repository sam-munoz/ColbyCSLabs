/*
 * File: Program.cs
 * Purpose: To show the explict 
 * Author: Samuel Munoz
 * Date: 04-28-2020
 */

using System;

namespace task1
{
    class Program
    {
        static void Main(string[] args)
        {
        	// whenever a variable is called, the garbage collector (gc) makes memory for the variable
            string gen0 = "i'm gen 0";

            // overtime, whenever a variable is no longer called for the rest of the program, gc releases the memory for that variable
            int size = 10;
            int[] array = new int[size];
            // size will not be used again, so the value of size is released

            for(int i = 0; i < array.Length; i++)
            	array[i] = 100 + i*2;

            for(int i = 0; i < array.Length; i++)
            	Console.Write(array[i] + ", ");

            // array is no longer used, so its memory is released

            Console.WriteLine(gen0);

            // gen0 is no longer used, so its memory is released

        }
    }
}

/*
Source
https://docs.microsoft.com/en-us/dotnet/standard/automatic-memory-management
*/
