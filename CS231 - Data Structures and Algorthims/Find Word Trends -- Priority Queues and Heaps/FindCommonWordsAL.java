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

public class FindCommonWordsAL {
	
	// holds array list holds the top N most frequent words in the comments
	private ArrayList<KeyValuePair<String,Integer>> list;
	
	private int totalWords;
	private int uniqueWords;
	
	// constructor: creates an empty hash map with given capacity
	public FindCommonWordsAL() {
		this.list = new ArrayList<KeyValuePair<String,Integer>>();
		this.totalWords = 0;
		this.uniqueWords = 0;
	}

	// constructor: creates an empty hash map with given capacity
	public FindCommonWordsAL(int capacity) {
		this.list = new ArrayList<KeyValuePair<String,Integer>>(capacity);
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
			int size = this.list.size();
			this.add(key,value);
			if(size != this.list.size())
				this.uniqueWords++;
			line = buffer.readLine();
		}
		buffer.close();
	}
	
	// this method adds elements into the arraylist
	public void add(String key, Integer value) {
		if(this.list.size() == 0) {
			this.list.add(new KeyValuePair<String,Integer>(key,value));
			return;
		}
		
		int low = 0;
		int high = this.list.size() - 1;
		int index = this.midpoint(low,high);
		while(index >= 0 && index < this.list.size()) {
			if(index == 0 || index == this.list.size() - 1)
				this.list.add(index, new KeyValuePair<String,Integer>(key,value));
			
			int compare = Integer.compare(value,this.list.get(index).getValue());
			if(compare == 0) {
				this.list.add(index, new KeyValuePair<String,Integer>(key,value));
				return;
			}
			else if(compare > 0) {
				high = index;
				index = this.midpoint(low,high);
			}
			else {
				low = index;
				index = this.midpoint(low,high);
			}
		}
	}

	// this is the midpoint between two values
	public int midpoint(int low, int high) {
		return (low + high) / 2;
	}
	
	// this clears the heap
	public void clear() {
		this.list = new ArrayList<KeyValuePair<String,Integer>>(this.list.size());
	}
	
	// this main method uses the following command line arguments format: N to represent what number of words the user want to see; file names to read
	public static void mainMethod1(String[] args) throws FileNotFoundException, IOException {
		if(args.length >= 1) {
			long start, end;
			start = System.currentTimeMillis();
			FindCommonWordsAL f = new FindCommonWordsAL();
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
				
				System.out.println("File: " + args[count]);
				for(int i = 0;i < topNumWords;i++) {
					System.out.println((i+1) + ": " + f.list.get(i));
				}
				// System.out.println("\nNumber of words: " + f.totalWords);
				// System.out.println("Number of unique words: " + f.uniqueWords);
				f.clear();
				if(count == args.length - 1)
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