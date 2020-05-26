/*
 * File: task2.cs
 * Purpose: To create a binary search algorithm in C#
 * Author: Samuel Munoz
 * Date: 02-27-2020
 */

using System;

namespace task2
{
    class Program
    {
    	static void binary_search(int[] array, int value) {
    		int position = array.Length/2;
    		int left = 0;
    		int right = array.Length;

    		Console.WriteLine("position: " + position + "\tleft: " + left + "\tright: " + right + "\tvalue: " + array[position]);
    		while(array[position] != value) {
    			if(position == left) {
    				Console.WriteLine("Value not found");
                    break;
                }

    			if(array[position] < value)
    				left = position;
    			else if(array[position] > value)
    				right = position;

    			position = (right-left)/2 + left;

    			Console.WriteLine("position: " + position + "\tleft: " + left + "\tright: " + right + "\tvalue: " + array[position]);
    		}
    	}

        static void Main(string[] args)
        {
            if(args.Length == 2) {
                int size = Int32.Parse(args[0]);
                int[] array = new int[size];
                for(int i=0;i<size;i++) {
                	array[i] = i;
                }

                int value = Int32.Parse(args[1]);
                
                if(size > 0) {
                    binary_search(array, value);
                }
            }
            else {
                Console.WriteLine("You must enter the following two arguments: <length of array> <search value>");
            }
        }
    }
}

/*
Sources:
https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/arrays/
https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/types/how-to-convert-a-string-to-a-number

*/