/**
* File: FindCommonWords.java
* Heap that sets priority on frequency of words
* Author: Samuel Munoz
* Course: CS231
* Date: 11-15-2019
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FindCommonWords {
	
	// holds the heap for this heap
	private PQHeapStrIntPair heap;
	// holds default capacity for heap
	private final int DEFAULT_CAPACITY = 1024;
	
	private int totalWords;
	private int uniqueWords;
	
	// constructor: creates an empty hash map with given capacity
	public FindCommonWords() {
		this.heap = new PQHeapStrIntPair(new PriorityStringIntegerPair(), DEFAULT_CAPACITY);
		this.totalWords = 0;
		this.uniqueWords = 0;
	}

	// constructor: creates an empty hash map with given capacity
	public FindCommonWords(int capacity) {
		this.heap = new PQHeapStrIntPair(new PriorityStringIntegerPair(), capacity);
		this.totalWords = 0;
		this.uniqueWords = 0;
	}
	
	// reads from a file written in writeWordCountFile and creates the tree from that
	public void readWordCountFile( String filename ) throws FileNotFoundException, IOException {
		FileReader reader = new FileReader(filename);
		BufferedReader buffer = new BufferedReader(reader);
		String line = buffer.readLine(); // on first line
		line = buffer.readLine(); // on second line; skip first line
		line = buffer.readLine(); // on third line; skip second line
		line = buffer.readLine(); // on fourth line; skip third line
		
		while(line != null) {
			String[] lines = line.split(" ");
			String key = lines[0].trim().toLowerCase();
			Integer value = Integer.parseInt(lines[1].trim().toLowerCase());
			this.totalWords += value;
			int size = this.heap.size();
			this.heap.add(new KeyValuePair<String,Integer>(key,value));
			if(size != this.heap.size())
				this.uniqueWords++;
			line = buffer.readLine();
		}
		buffer.close();
	}
	
	// this clears the heap
	public void clear() {
		this.heap = new PQHeapStrIntPair(new PriorityStringIntegerPair(), this.heap.capacity());
	}
	
	// removes the most frequent word in the heap
	public KeyValuePair<String,Integer> remove() {
		KeyValuePair<String,Integer> removed = this.heap.remove();
		if(removed != null) {
			this.totalWords -= removed.getValue();
			this.uniqueWords--;
		}
		return removed;
	}
	
	// this main method uses the following command line arguments format: N to represent what number of words the user want to see; file names to read
	public static void mainMethod1(String[] args) throws FileNotFoundException, IOException {
		if(args.length >= 1) {
			long start, end;
			start = System.currentTimeMillis();
			FindCommonWords f = new FindCommonWords(10);
			int topNumWords = 0;
			try {
				topNumWords = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e) { // https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html
				System.out.println("The first argument must be an integer N that tells the program to tell N most frequent words.\nTerminating program....");
				System.exit(0);
			}
			for(int count = 1;count < args.length;count++) {
				f.readWordCountFile(args[count]);
				// System.out.println(f.heap);
				
				KeyValuePair<String,Integer>[] topWords = new KeyValuePair[topNumWords];
				System.out.println("File: " + args[count]);
				for(int i = 0;i < topNumWords;i++) {
					topWords[i] = f.remove();
					System.out.println((i+1) + ": " + topWords[i]);
				}
				// System.out.println("\nNumber of words: " + f.totalWords);
				// System.out.println("Number of unique words: " + f.uniqueWords);
				f.clear();
				if(count != args.length - 1)
					System.out.println();
			}
			end = System.currentTimeMillis();
			System.out.println("Time taken: " + (end-start) + "\n");
		}
		else
			System.out.println("You need one argument.");
	} 



	public static void main(String[] args) throws FileNotFoundException, IOException {
		FindCommonWords.mainMethod1(args);
	}
}