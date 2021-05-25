/*
 * File: Samuel Munoz
 * Purpose: To create a program that reads a file and prints the most frequency in a text file
 * Author: Samuel Munoz
 * Date: 04-13-2020
 */
using System;
using System.Collections.Generic; // package holds the dictionary class
using System.IO; // package holding the file object

namespace task1
{
    class Program
    {
    	static void updateTable(Dictionary<string, int> d, string key) {
    		// if the key already exist inside the hash table, then increase the frequency by one
    		if(d.ContainsKey(key)) {
    			d[key] = d[key] + 1;
    		}
    		// if the key does not exist, create a new key with value of one (frequency)
    		else {
    			d.Add(key, 1);
    		}
    	}

    	// sorts an array
    	static void bubbleSort(KeyValuePair<string, int>[] array) {
    		int length = array.Length;
    		for(int i=0; i < length-1; i++) {
    			for(int j=0; j < length-i-1; j++) {
    				int value1 = array[j].Value;
    				int value2 = array[j+1].Value;
    				if(value1 < value2) {
    					KeyValuePair<string, int> temp = array[j];
    					array[j] = array[j+1];
    					array[j+1] = temp;
    				}
    			}
    		}
    	}

        static void Main(string[] args)
        {
        	// if an argument is not given, throw error and exit program
        	if(args.Length < 1) {
        		Console.WriteLine("command: dotnet run <filename>");
        		return;
        	}
        	// if the file does not exist, throw error and exit the program
        	if(!File.Exists("./" + args[0])) {
        		Console.WriteLine("File does not exist. Please pass a file that exists");
        		return;
        	}

        	// this will store all the words as keys and their frequencies as values
            Dictionary<string, int> d = new Dictionary<string, int>();

         	// create read stream to read all the words in the file and store the words into the hash table
         	using(StreamReader sr = File.OpenText("./" + args[0])) {
         		string line;
         		while((line = sr.ReadLine()) != null) {
         				String[] words = System.Text.RegularExpressions.Regex.Split(line, @"[(\s)|(,\s)(.\s)]");
         				foreach(string word in words) {
         					// Console.WriteLine(word);
         					updateTable(d, word.ToLower());
         				}
         		}
         	}

         	KeyValuePair<string, int>[] entries = new KeyValuePair<string, int>[d.Count];
         	int index = 0;
         	foreach(KeyValuePair<string, int> kvp in d) {
         		entries[index] = kvp;
         		index++;
         	}

         	bubbleSort(entries);
         	for(index = 1; index < 21; index++)
         		Console.WriteLine("{0}\t\t\t{1}", entries[index].Key, entries[index].Value);
        }
    }
}

/*
Sources:
https://www.tutorialspoint.com/csharp/csharp_hashtable.htm
https://docs.microsoft.com/en-us/dotnet/api/system.collections.hashtable?view=netframework-4.8
https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/types/boxing-and-unboxing
https://docs.microsoft.com/en-us/dotnet/api/system.string.split?view=netframework-4.8
*/