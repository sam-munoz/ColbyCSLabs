/**
* File: FindTrends.java
* Heap that sets priority on frequency of words
* Author: Samuel Munoz
* Course: CS231
* Date: 11-15-2019
*/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class FindTrends {
	
	// this holds the words we want to find trends for
	private KeyValuePair<String,Double>[] data; // NOTE: this file is not meant to be used with large amounts of data!!!
	// this points to the next empty array slot
	private int pointer;
	
	// constructor: creates an empty array for new words
	public FindTrends(int capacity) {
		this.data = new KeyValuePair[capacity];
		this.pointer = 0;
	}
	
	// adding/updating data to the array
	public void add(String key, Double value) {
		// System.out.println("Adding at " + this.pointer);
		if(this.pointer == this.data.length)
			this.resize();
		if(value == null)
			value = 0.0;
		this.data[this.pointer++] = new KeyValuePair<String,Double>(key,value);
	}
	
	// returns the pairs stored in the array
	public String toString() {
		String returnString = "";
		for(int index = 0;index < this.pointer;index++) {
			returnString += this.data[index].getKey() + " " + this.data[index].getValue() + "\n";
		}
		return returnString;
	}
	
	// writes completed arrays to the csv file
	public void write(String filename, int words) throws FileNotFoundException, IOException {
		FileWriter writer = new FileWriter(filename);
		// for first line
		writer.write(",2008,2009,2010,2011,2012,2013,2014,2015\n");
		for(int i = 0;i < words;i++) {
			writer.write(this.data[i].getKey() + ",");
			for(int j = i;j < this.pointer;j += words) {
				writer.write(this.data[j].getValue() + ",");
			}
			writer.write("\n");
		}
		writer.close();
	}
	
	// writes completed arrays to the csv file
	public void writeVertical(String filename, int words) throws FileNotFoundException, IOException {
		FileWriter writer = new FileWriter(filename);
		// for first line
		writer.write(",");
		for(int i = 0;i  < words;i++)
			writer.write(this.data[i].getKey() + ",");
		writer.write("\n");
		for(int i = 0;i < 8;i++) {
			writer.write((2008 + i) + ",");
			for(int j = words * i;j < words * i + words;j++) {
				writer.write(this.data[j].getValue() + ",");
			}
			writer.write("\n");
		}
		writer.close();
	}
	
	// resizes the array
	public void resize() {
		KeyValuePair<String,Double>[] newArray = new KeyValuePair[2 * this.data.length];
		for(this.pointer = 0;this.pointer < this.data.length;this.pointer++)
			newArray[this.pointer] = this.data[this.pointer];
		this.data = newArray;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		FindTrends f = new FindTrends(args.length - 3);
		WordCounter wc = new WordCounter();
		int start = Integer.parseInt(args[1]);
		int end = Integer.parseInt(args[2]);
		for(int i = start;i <= end;i++) {
			String filename = args[0] + Integer.toString(i) + ".txt";
			wc.readWordCountFile(filename);
			for(int j = 3;j < args.length;j++) {
				f.add(args[j], wc.getFrequency(args[j]) * 100);
			}
			System.out.println("Finished: " + filename);
		}
		// System.out.println("\n" + f);
		f.write("trends.csv",args.length - 3);
	}
}
	