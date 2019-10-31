/**
* File: LinkedList.java
* Implements a singly-linked node-based list
* Author: Samuel Munoz
* Course: CS231
* Date: 09/30/2019
*/
import java.util.Iterator;    // defines the Iterator interface
import java.util.ArrayList;   
import java.util.Collections; // contains a shuffle function

public class LinkedList<T> implements Iterable<T> {
	private Node head; // holds the head of a linked list
	private int size; // holds the size of the linked list

	// this Node class wraps around a T-typed variable and points to the next Node in the linked list
	private class Node { //start of Node class
		private T node; // holds the T-typed variable
		private Node next; // pointer to the next Node in the list

		// constructor that creates a Node with the Node pointing to no Nodes
		public Node(T item) {
			this.node = item;
			next = null;
		}

		// returns the T-typed variable the class wraps around
		public T getThing() { return node; }

		// set the next pointer to a new value
		public void setNext(Node thing) { next = thing; } 
		
		// returns whatever the next pointer is pointing at
		public Node getNext() { return this.next; }
	} // end of Node class

	// this LLIterator class is implementation of an Iterator that allows the user to iterate through the linked list
	private class LLIterator implements Iterator<T> { // start the LLIterator class
		private Node cursor; // Node pointer that points to the current location of the Iterator

		// constructor that sets cursor to point to Node stored in head (head of the parameter, not the head of the list)
		public LLIterator(Node head) {
			this.cursor = head;
		}

		// this checks whether the iterator have iterator can iterate to the next value (if there is one) of the linked list
		public boolean hasNext() { 
			return this.cursor != null; 
		}

		// this returns the current T value stored in Node of the current location of the iterator before moving the iterator to next value in the list
		public T next() { 
			if(cursor != null) {
				T returnT = cursor.getThing();
				cursor = cursor.getNext();
				return returnT;
			}
			return null;
		}
	} // end of the LLIterator class

	// this constructor creates an empty linked list
	public LinkedList() {
		head = null;
		size = 0;
	}

	// this method clears out the linked list by reseting our field values to values of an empty list
	public void clear() {
		head = null;
		size = 0;
	}

	// returns the size of the linked list
	public int size() { return this.size; }

	// adds a T-type variable to the front (or head) of the list 
	public void addFirst(T item) {
		Node firstNode = new Node(item);
		if(size != 0)
			firstNode.setNext(head);
		head = firstNode;
		this.size++;
	}

	// adds a T-type variable to the end of the list 
	public void addLast(T item) {
		Node lastNode = new Node(item);
		if(size != 0) {
			Node cursor = head;
			while(cursor.getNext() != null) {
				cursor = cursor.getNext();
			}
			cursor.setNext(lastNode);
		}
		else
			head = lastNode;
		this.size++;
	}

	// adds a T-type variable to a given index position
	public void add(int index,T item) {
		if(index > 0 && index <= size) {
			Node newNode = new Node(item);
			Node cursor = head;
			for(int i = 0;i < index-1;i++)
				cursor = cursor.getNext();
			Node storeNode = cursor.getNext();
			cursor.setNext(newNode);
			newNode.setNext(storeNode);
			this.size++;
		}
		else if(index == 0)
			this.addFirst(item);
		else 
			System.out.println("Error");
	}

	// removes an element of the list at a given index position
	public T remove(int index) {
		Node returnNode;
		if(index != 0) {
			Node node = head;
			for(int i = 0;i < index-1;i++)
				node = node.getNext();
			returnNode = node.getNext();
			node.setNext(returnNode.getNext());
			this.size--;
			return returnNode.getThing();
		}
		else {
			returnNode = head;
			head = head.getNext();
			this.size--;
			return returnNode.getThing();
		}
	}

	// creates an iterator with the iterator pointing at the head of the list
	public Iterator<T> iterator() {
		return new LLIterator(this.head);
	}
	
	// returns a String with the list-list representation of the list
	public String toString() {
		String returnString = "[";
		Node cursor = head;
		while(cursor != null) {
			returnString += cursor.getThing();
			if(cursor.getNext() != null)
				returnString += ", ";
			cursor = cursor.getNext();
		}
		return returnString + "]";
	}

	// returns an ArrayList with all the elements in the linked list copied (not clones of the elements, but pointers to the objects are in the ArrayList) into the returned ArrayList
	public ArrayList<T> toArrayList() {
		ArrayList<T> arraylist = new ArrayList<>();
		Node cursor = head;
		if(size > 0) {
			while(cursor !=  null) {
				arraylist.add(cursor.getThing());
				cursor = cursor.getNext();
			}
		}
		return arraylist;
	}

	// calls the toArrayList method and takes the elements in the ArrayList are shuffled
	public ArrayList<T> toShuffledList() {
		ArrayList<T> randList = this.toArrayList();
		Collections.shuffle(randList);
		return randList;
	}
	
	// debugging main method (old)
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i = 1;i < 11;i++)
			list.addLast(i);
		System.out.println(list);
	}
}

// footnote 1: https://stackoverflow.com/questions/18729529/java-how-to-access-outer-classs-instance-variable-by-using-this