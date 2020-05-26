/*
 * File: Program.cs
 * Purpose: To create a mutlithreaded program that will sort an array
 * Author: Samuel Munoz
 * Date: 05-02-2020
 */

using System;
using System.Diagnostics;
using System.Threading;

namespace task1
{
    class Program
    {
    	public static int[] array = null;
    	public static int[][] subarrays = null;
    	public static Thread[] threads = null;
    	public static int NUM_OF_THREADS = 1;

    	// this method sorts an array of 10 elements in ascending order
    	private static void bubble_sort(object o) {
    		// Console.WriteLine("Ran bubble sort");
    		int index = (int) o;
    		for(int i = 0; i < subarrays[index].Length; i++) {
    			for(int j = 0; j < subarrays[index].Length - i - 1; j++) {
    				if(subarrays[index][j] > subarrays[index][j+1]) {
    					int temp = subarrays[index][j];
    					subarrays[index][j] = subarrays[index][j+1];
    					subarrays[index][j+1] = temp;
    				}
    			}
    		}
    	}

    	// returns the index of the smallest values in the list
    	static int find_smallest(int[] l) {
    		// method keeps track of which element in the subarray is smallest at some given time (min) and the array that element in stored in (index)
    		int min = 0, index = 0;

    		// find the first element in the subarray that can be sorted into the array
    		for(int i = 0; i < l.Length; i++) {
    			if(l[i] != -1) {
    				min = subarrays[i][l[i]];
    				index = i;
    				break;
    			}
    		}

    		// goes through all subarrays and checks the smallest next value in the array. If the value is smaller than min, then min and index are updated to store the new value
    		for(int i = index+1; i < l.Length; i++) {
    			if(l[i] != -1) {
	    			if(subarrays[i][l[i]] < min) {
	    				min = subarrays[i][l[i]];
	    				index = i;
	    			}
	    		}
    		}

    		// returns the index of the subarray with the next smallest element
    		return index;
    	}

    	// this method sorts an array by using concurrent programming
    	static void array_sort() {
    		// determines the number of subarrays to be created (equals to the number of threads to be created)
    		int sbc = NUM_OF_THREADS;

    		// finds the length of the average subarray 
    		int sbl = array.Length/sbc;

    		// create 2D array to store references of the subarrays
    		subarrays = new int[NUM_OF_THREADS][];

    		// create memory for subarray and populate the subarray with the values from the array
    		for(int i = 0; i < sbc; i++) {
    			// find the length of the subarray
    			int nextLength = sbl;
    			if(i == sbc-1) 
    				nextLength = array.Length - i*sbl;
    			
    			// creates memory for the new array
    			subarrays[i] = new int[nextLength];

    			// populate the subarray with values from the array
    			for(int j = 0; j < nextLength; j++)
    				subarrays[i][j] = array[i*sbl + j];
    		}

    		// create an array of Thread object to create multiple threads
    		threads = new Thread[NUM_OF_THREADS];

    		// start each thread with sorting the subarrays
    		for(int i = 0; i < threads.Length; i++) {
    			threads[i] = new Thread(Program.bubble_sort);
    			threads[i].Start(i);
    		}

    		// give time for the threads to sort by stoping the main thread
    		Thread.Sleep(10);

    		// release the resources used by the thread
    		for(int i = 0; i < threads.Length; i++)
    			threads[i].Join();

    		// need a way to store the index with the next smallest value to input into the original large array
    		int[] indices = new int[subarrays.Length];

    		// sorts the subarrays back into the new array
    		for(int i = 0; i < array.Length; i++) {
    			int index = find_smallest(indices);
    			array[i] = subarrays[index][indices[index]];
    			if(++indices[index] >= subarrays[index].Length)
    				indices[index] = -1;
    		}


    	}

        static void Main(string[] args)
        {
        	// check if the correct amount of arguments are passed through the function
            if(args.Length < 2) {
            	Console.WriteLine("command: dotnet run <length_of_array num_of_threads>");
            	return;
            }

           // try to find the length of an array by reading the first command line argument
            try {
                int length = Int32.Parse(args[0]);
                if(length <= 1) {
                	Console.WriteLine("You cannot create an array with less than 2 elements");
                	return;
                } 
                array = new int[length];
            } 
            catch(FormatException) {
                Console.WriteLine("\"" + args[0] + "\" is not an integer. Only integers are allowed to determine the length of the array.");
                return;
            }

            // create array with length "length" and populate it with random values
            Random r = new Random();
            for(int i = 0; i < array.Length; i++)
                array[i] = r.Next(0, 100);

            // find the number of threads to create by reading the other command line
            try {
            	NUM_OF_THREADS = Int32.Parse(args[1]);
            }
            catch(FormatException) {
            	Console.WriteLine("\"" + args[1] + "\" is not an integer. Only integers are allowed to determine the number of threads to be created.");
            	return;
            }

            // check if the number of threads is smaller than the size of the array
            if(array.Length < NUM_OF_THREADS) {
                Console.WriteLine("You cannot create more threads than the number of elements inside the array");
                return;
            }

            // check if the number of threads is not zero or a negative number
            if(NUM_OF_THREADS <= 0) {
                Console.WriteLine("You cannot create a negative number or zero amount of threads");
                return;
            }

            // print the unsorted array
            // Console.Write("Unsorted array: ");
            // for(int i = 0; i < array.Length; i++) {
            // 	if(i == array.Length-1)
            // 		Console.WriteLine(array[i]);
            // 	else
            // 		Console.Write(array[i] + ", ");
            // }

            // create a stop watch object to time method
            Stopwatch timer = new Stopwatch();

            // call the sort method to sort the array and measure time
            timer.Start(); 
            array_sort();
            timer.Stop();

            // print out the subarrays (FOR DEBUGGING PURPOSES!!)
      // 		for(int i = 0; i < subarrays.Length; i++) {
    		// 	Console.Write((i+1) + ": ");
    		// 	for(int j = 0; j < subarrays[i].Length; j++) {
    		// 		if(j == subarrays[i].Length-1)
    		// 			Console.WriteLine(subarrays[i][j]);
    		// 		else
    		// 			Console.Write(subarrays[i][j] + ", ");
    		// 	}
    		// }

            // print out the value of the new array
    		// Console.Write("Sorted array: ");
    		// for(int i = 0; i < array.Length; i++) {
      //       	if(i == array.Length-1)
      //       		Console.WriteLine(array[i]);
      //       	else
      //       		Console.Write(array[i] + ", ");
      //       }

    		// print out some statistics
            Console.WriteLine("Time duration: " + timer.ElapsedMilliseconds + " ms");
            Console.WriteLine("Threads created: " + NUM_OF_THREADS);
        }
    }
}

/*
Sources:
https://www.tutorialspoint.com/csharp/csharp_multithreading.htm
https://docs.microsoft.com/en-us/dotnet/api/system.formatexception?view=netcore-3.1
https://stackoverflow.com/questions/5282999/reading-csv-file-and-storing-values-into-an-array
https://docs.microsoft.com/en-us/dotnet/api/system.threading.thread.join?view=netcore-3.1
https://stackoverflow.com/questions/2706500/how-do-i-generate-a-random-int-number
*/
