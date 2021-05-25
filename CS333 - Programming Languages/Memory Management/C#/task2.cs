/*
 * File: Program.cs
 * Purpose: To see when garbage collecting occurs in C#
 * Author: Samuel Munoz
 * Date: 04-29-2020
 */

using System;
using System.Diagnostics;
using System.Collections.Generic;

namespace task2
{
    class Program
    {
		// this method will create memory for a bunch of integer in an
		static void createMemory() {
			// create variables we need to run the simulation
			List<int> l = new List<int>(1000000);

			// start simulation ----------------------------
			for(int i = 0; i < 1000000; i++) {
				l.Add(0);
			}
			// end simulation ------------------------------

			// C# GC will deallocate the memory for the list at the end of the method
		}

        static void Main(string[] args)
        {
        	// print information for the table
        	Console.WriteLine("Set of Elements\t\tTime Elapsed");
	        
        	// create timer to measure time elapsed
	        Stopwatch timer = new Stopwatch();

	        // creates 100 integers and measure time to allocate and deallocate that memory
        	for(int i = 0; i < 100; i++) {
	        	// simulation starts -----------
	        	timer.Start();
	            Program.createMemory();
	            timer.Stop();
	            // simuulation ends ------------

	            // display results
            	Console.WriteLine("[{0}M-{1}M]:\t\t{2} ms", i, i+1, timer.ElapsedMilliseconds);

            	// reset the timer to get new measurement
	            timer.Reset();
	        }

	        // footnote at the bottom of the table
	        Console.WriteLine("*NOTE: M stands for a million");
        }
    }
}
