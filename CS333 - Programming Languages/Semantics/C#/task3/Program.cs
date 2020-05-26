/*
 * File: Program.cs
 * Purpose: To create a bubble sort algorithm in C#
 * Author: Samuel Munoz
 * Date: 03-07-2020
 */

using System;

namespace task3
{
    class Program
    {
    	static void printArray(int[] array) {
    		int length = array.Length;
    		for(int i=0;i < length;i++) {
    			if(i != length-1)
    				Console.Write(array[i] + ", ");
    			else
    				Console.WriteLine(array[i]);
    		}
    	}
    	static void bubbleSort(int[] array) {
    		int length = array.Length;
    		for(int i=0;i<length-1;i++) {
    			for(int j=0;j < length-i-1;j++) {
    				if(array[j] < array[j+1]) {
    					int temp = array[j];
    					array[j] = array[j+1];
    					array[j+1] = temp;
    				}
    			}
    		}

    	}
        static void Main(string[] args)
        {
        	if(args.Length < 1) {
        		Console.WriteLine("command: dotnet run <length of array>");
        	}
        	else {
	        	// create a instance of the Random class  
	        	Random rand = new Random();


	            // generate a random number
	            // int value = rand.Next(0, 2);

	            // Console.WriteLine("Value: " + value);

	            // create an array to sort
	            int[] array = new int[Int32.Parse(args[0])];
	            
	            // popualte array with random values and prints the array
	            for(int i=0;i < array.Length;i++)
	            	array[i] = rand.Next(0, 10);
	            Console.Write("Array: ");
	            printArray(array);

	            // sort array with bubble sort
	            bubbleSort(array);

	            // reprints the sorted array
	            Console.Write("Sorted Array: ");
	            printArray(array);
        	}
        }
    }
}

/*
Sources
https://stackoverflow.com/questions/8886665/how-can-i-print-a-string-to-the-console-without-a-newline-at-the-end
https://www.tutorialspoint.com/generating-random-numbers-in-chash
*/