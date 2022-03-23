/**
* File: PQHeap.java
* Creates a priority queue
* Author: Samuel Munoz
* Course: CS231
* Date: 11-11-2019
*/
import java.util.Comparator;

public class PQHeapStrIntPair {

	// holds the data for the heap
	private KeyValuePair<String,Integer>[] data;
	// holds the size of the heap
	private int size;
	// comparator to compare elements
	private Comparator<KeyValuePair<String,Integer>> comparator;
	// the default capacity for the array for ungiven capacity size
	private final int DEFAULT_CAPACITY = 1024;
	
	// constructor: creates a heap with empty fields. The array has the default capacity
	public PQHeapStrIntPair(Comparator<KeyValuePair<String,Integer>> comparator) {
		this.data = new KeyValuePair[DEFAULT_CAPACITY];
		this.comparator = comparator;
		this.size = 0;
	} 

	// constructor: creates a heap with empty fields. The array has a given capacity
	public PQHeapStrIntPair(Comparator<KeyValuePair<String,Integer>> comparator,int capacity) {
		this.data = new KeyValuePair[capacity];
		this.comparator = comparator;
		this.size = 0;
	}

	// returns the size of the heap
	public int size() {
		return this.size;
	}
	
	// returns the capacity of the array that holds the heap
	public int capacity() {
		return this.data.length;
	}

	// add a new value to the heap
	public void add(KeyValuePair<String,Integer> obj) {
		if(this.size == this.data.length)
			this.resize();
		this.data[this.size++] = obj;
		this.perculateUp(this.size - 1);
	}

	public KeyValuePair<String,Integer> remove() {
		if(this.size > 0) {
			KeyValuePair<String,Integer> removeMe = this.data[0];
			this.data[0] = this.data[--this.size];
			this.perculateDown(0);
			return removeMe;
		}
		return null;
	}

	// gets the parent ID from the child ID
	public int parentID(int childID) {
		return (childID-1)/2;
	} 

	// get the left child node from a parent ID
	public int leftChildID(int parentID) {
		return 2*parentID + 1;
	} 

	// get the right child node from a parent ID
	public int rightChildID(int parentID) {
		return 2*parentID + 2;
	} 

	// swap KeyValuePair<String,Integer> objects from id one and id two
	public void swap(int id1, int id2) {
		KeyValuePair<String,Integer> holdID1 = this.data[id1];
		this.data[id1] = this.data[id2];
		this.data[id2] = holdID1;
	}

	// perculates up elements based on priority
	private void perculateUp(int childID) {
		int compareValue = this.comparator.compare(this.data[childID],this.data[this.parentID(childID)]);
		// System.out.println(this.data[childID] + "\t" + this.data[this.parentID(childID)] + "\t" + compareValue);
		if(compareValue > 0) {
			this.swap(childID,this.parentID(childID));
			this.perculateUp(this.parentID(childID));
		}
	}

	// perculates down elements based on priority
	private void perculateDown(int parentID) {
		int lChild = this.leftChildID(parentID);
		int rChild = this.rightChildID(parentID);
		if(lChild < this.size && rChild < this.size) {
			KeyValuePair<String,Integer> leftChild = this.data[lChild];
			KeyValuePair<String,Integer> rightChild = this.data[rChild];
			int compareChild = this.comparator.compare(leftChild,rightChild);
			int childID;
			if(compareChild >= 0)
				childID = lChild;
			else
				childID = rChild;
			int compareValue = this.comparator.compare(this.data[childID],this.data[parentID]);
			if(compareValue == 1) {
				this.swap(childID,parentID);
				this.perculateDown(childID);
			}
		}
	}

	// resizes the array
	public void resize() {
		KeyValuePair<String,Integer>[] newArray = new KeyValuePair[this.data.length * 2];
		for(this.size = 0;this.size < this.data.length;this.size++)
			newArray[this.size] = this.data[this.size];
		this.data = newArray;
	}
	
	// clear all the fields to empty values
	public void clear() {
		this.data = new KeyValuePair[this.data.length];
		this.size = 0;
	}

	// recursive method to create the toString
	public void toString(String[] returnStringAndTabs,int index) {
		if(index < this.size) {
			if(index != 0)
				returnStringAndTabs[1] += "\t\t";

			returnStringAndTabs[0] += returnStringAndTabs[1] + this.data[index] + "\n";
			if(this.leftChildID(index) < this.size) {
				returnStringAndTabs[0] += "left: ";
				this.toString(returnStringAndTabs,this.leftChildID(index));
			}
			if(this.rightChildID(index) < this.size) {
				returnStringAndTabs[0] += "right: ";
				this.toString(returnStringAndTabs,this.rightChildID(index));
			}
			if(returnStringAndTabs[1].length() >= 2)
				returnStringAndTabs[1] = returnStringAndTabs[1].substring(0,returnStringAndTabs[1].length()-2);
		}
	} 

	// prints the heap in a binary tree manner
	public String toString() {
		String[] returnStringAndTabs = {"root: ",""};
		this.toString(returnStringAndTabs,0);
		return returnStringAndTabs[0];
	}

	public static void main(String[] args) {
		PQHeapStrIntPair h = new PQHeapStrIntPair(new PriorityStringIntegerPair(),10);

		h.add(new KeyValuePair<String,Integer>("a",10));
		h.add(new KeyValuePair<String,Integer>("b",11));
		h.add(new KeyValuePair<String,Integer>("c",16));
		h.add(new KeyValuePair<String,Integer>("d",19));
		h.add(new KeyValuePair<String,Integer>("e",15));

		System.out.println(h);

		System.out.println("removed: " + h.remove() + "\n");

		System.out.println(h);
	}
}