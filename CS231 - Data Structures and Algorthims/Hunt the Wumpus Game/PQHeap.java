/**
* File: PQHeap.java
* Creates a priority queue
* Author: Samuel Munoz
* Course: CS231
* Date: 11-11-2019
*/
import java.util.Comparator;

public class PQHeap<T> {

	// holds the data for the heap
	private T[] data;
	// holds the size of the heap
	private int size;
	// comparator to compare elements
	private Comparator<T> comparator;
	// the default capacity for the array for ungiven capacity size
	private final int DEFAULT_CAPACITY = 1024;
	
	// constructor: creates a heap with empty fields. The array has the default capacity
	public PQHeap(Comparator<T> comparator) {
		this.data = (T[]) new Object[DEFAULT_CAPACITY];
		this.comparator = comparator;
		this.size = 0;
	} 

	// constructor: creates a heap with empty fields. The array has a given capacity
	public PQHeap(Comparator<T> comparator,int capacity) {
		this.data = (T[]) new Object[capacity];
		this.comparator = comparator;
		this.size = 0;
	}

	// returns the size of the heap
	public int size() {
		return this.size;
	}

	// add a new value to the heap
	public void add(T obj) {
		if(this.size == this.data.length)
			this.resize();
		this.data[this.size++] = obj;
		this.perculateUp(this.size - 1);
	}

	public T remove() {
		if(this.size > 0) {
			T removeMe = this.data[0];
			this.data[0] = this.data[--this.size];
			this.perculateDown(0);
			return removeMe;
		}
		return null;
	}
	
	// this method returns the index of T object in the heap
	public int find(Vertex v) {
		for(int i = 0;i < this.size;i++) {
			if(this.data[i].equals(v))
				return i;
		}
		return -1;
	}
	
	// this method updates an element inside the heap
	public void update(int index, T obj) {
		this.data[index] = obj;
		this.perculateUp(index);
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

	// swap T objects from id one and id two
	public void swap(int id1, int id2) {
		T holdID1 = this.data[id1];
		this.data[id1] = this.data[id2];
		this.data[id2] = holdID1;
	}

	// perculates up elements based on priority
	private void perculateUp(int childID) {
		if(this.parentID(childID) >= 0 && childID < this.size) {
			int compareValue = this.comparator.compare(this.data[childID],this.data[this.parentID(childID)]);
			// System.out.println(this.data[childID] + "\t" + this.data[this.parentID(childID)] + "\t" + compareValue);
			if(compareValue > 0) {
				this.swap(childID,this.parentID(childID));
				this.perculateUp(this.parentID(childID));
			}
		}
	}

	// perculates down elements based on priority
	private void perculateDown(int parentID) {
		if(this.leftChildID(parentID) < this.size) {
			int childID;
			T leftChild = this.data[this.leftChildID(parentID)];
			if(this.rightChildID(parentID) < this.size) {
				T rightChild = this.data[this.rightChildID(parentID)];
				int compareChild = this.comparator.compare(leftChild,rightChild);
				if(compareChild >= 0)
					childID = this.leftChildID(parentID);
				else
					childID = this.rightChildID(parentID);
			}
			else 
				childID = this.leftChildID(parentID);
			int compareValue = this.comparator.compare(this.data[childID],this.data[parentID]);
			if(compareValue == 1) {
				this.swap(childID,parentID);
				this.perculateDown(childID);
			}
		}
	}

	// resizes the array
	public void resize() {
		T[] newArray = (T[]) new Object[this.data.length * 2];
		for(this.size = 0;this.size < this.data.length;this.size++)
			newArray[this.size] = this.data[this.size];
		this.data = newArray;
	}
	
	// clear all the fields to empty values
	public void clear() {
		this.data = (T[]) new Object[this.data.length];
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

	// public static void main(String[] args) {
	// 	PQHeap<Integer> h = new PQHeap<Integer>(new Priority<Integer>(),10);

	// 	h.add(12);
	// 	h.add(11);
	// 	h.add(16);
	// 	h.add(19);
	// 	h.add(15);

	// 	System.out.println(h);

	// 	System.out.println("removed: " + h.remove() + "\n");

	// 	System.out.println(h);
	// }
}