/**
* File: LinkedList.java
* Implements a linked list
* Author: Samuel Munoz
* Course: CS231
* Date: 09/30/2019
*/
import java.util.Iterator;    // defines the Iterator interface
import java.util.ArrayList;   
import java.util.Collections; // contains a shuffle function

public class LinkedList<T> implements Iterable<T> {
	
	// start of sub classes --------------------------------------------------------------------------------

	private class Node { //start of Node class
		private T node;
		private Node next;

		public Node(T item) {
			this.node = item;
			next = null;
		}
		public T getThing() { return node; }
		public void setNext(Node thing) { next = thing; } 
		public Node getNext() { return this.next; }
	} // end of Node class

	private class LLIterator implements Iterator<T> { // start the LLIterator class
		private Node cursor;

		public LLIterator(Node head) {
			this.cursor = head;
		}

		public boolean hasNext() { 
			return this.cursor != null; 
		}

		public T next() { 
			if(cursor != null) {
				T returnT = cursor.getThing();
				cursor = cursor.getNext();
				return returnT;
			}
			return null;
		}
	} // end of the LLIterator class
	
	public Iterator<T> iterator() {
		return new LLIterator(this.head);
	}
	
	// end of sub classes ----------------------------------------------------------------------------------
	
	
	// start of the LinkedList class -----------------------------------------------------------------------
	private Node head;
	private int size;

	public LinkedList() {
		head = null;
		size = 0;
	}

	public void clear() {
		head = null;
		size = 0;
	}

	public int size() { return this.size; }

	public void addFirst(T item) {
		Node firstNode = new Node(item);
		if(size != 0)
			firstNode.setNext(head);
		head = firstNode;
		this.size++;
	}

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


	public ArrayList<T> toShuffledList() {
		ArrayList<T> randList = this.toArrayList();
		Collections.shuffle(randList);
		return randList;
	}
	
	// end of the LinkedList class -------------------------------------------------------------------------
	
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i = 1;i < 11;i++)
			list.addLast(i);
		System.out.println(list);
	}
}

// footnote 1: https://stackoverflow.com/questions/18729529/java-how-to-access-outer-classs-instance-variable-by-using-this