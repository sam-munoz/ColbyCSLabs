/**
* File: MyQueue.java
* Class creates a node-based queue
* Author: Samuel Munoz
* Course: CS231
* Date: 10-07-2019
*/
import java.util.Iterator;    // defines the Iterator interface

public class MyQueue<T> implements Iterable<T> {
	private Node head; // holds the head of the Queue
	private Node tail; // holds the tail of the Queue
	private int size; // holds the size of the queue

	// start of subclasses + iterators ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// this Node class wraps around a T-typed variable and has pointers to the previous and next T-typed variables in the list
	private class Node { //start of Node class 
		private T node; // holds the T-typed variable in the Node class
		private Node prev; // points to previous Node in the list (if the list has a preivous Node)
		private Node next;	// points to the next Node in the list (if the list has a next Node)

		// constructor that stores a T-typed variable into the Node and makes the pointers in the class point to null
		public Node(T item) {
			this.node = item;
			this.prev = null;
			this.next = null;
		}

		// returns the T-typed data stored in this class
		public T get() { return this.node; }

		// set the T-typed variable stored by this class to a new value
		public void set(T thing) { this.node = thing; }

		// sets the previous Node pointer to a new pointer
		public void setPrev(Node thing) { this.prev = thing; }

		// sets the next Node pointer to a new pointer
		public void setNext(Node thing) { this.next = thing; }

		// returns Node that the previous Node pointer is pointing to 
		public Node getPrev() { return this.prev; }

		// returns Node that the next Node pointer is pointing to
		public Node getNext() { return this.next; }
	} // end of Node class


	// this class implements the Iterator interface and creates an iterator that the user can iterate through the list
	private class QIterator implements Iterator<T> { // start the QIterator class
		private Node cursor; // Node pointer that allows the iterator to know what location the iterator is at in the list

		// constructor that set the pointer of this class to point to the head Node in the parameters of this constructor
		public QIterator(Node head) {
			this.cursor = head;
		}

		// this methods allows the iterator to know if there is another value at the list can iterate to
		public boolean hasNext() { 
			return this.cursor != null; 
		}

		// this method returns the current T-typed value in the current Node that the iterator is currently pointing to before allowing the iterator to iterate back to the previous element in the list. Influenced by background knowledge of C++ iterators
		public T prev() {
			if(cursor != null) {
				T returnT = cursor.get();
				cursor = cursor.getPrev();
				return returnT;
			}
			return null;
		}

		// this method returns the current T-typed value in the current Node that the iterator is currently pointing to before allowing the iterator to iterate forword to the next element in the list
		public T next() { 
			if(cursor != null) {
				T returnT = cursor.get();
				cursor = cursor.getNext();
				return returnT;
			}
			return null;
		}
	} // end of the QIterator class

	// this creates a new iterator that points the head element of the queue
	public Iterator<T> iterator() { 
		return new QIterator(this.head);
	}

	// end of subclasses + iterator ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------









	// start of MyQueue class --------------------------------------------------------------------------
	
	// this constructor creates an empty queue
	public MyQueue() {
		this.head = null;
		this.tail = null;
		this.size = 0; 
	}

	// this method clears the queue by reseting the fields of the queue to values of an empty queue
	public void clear() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	// returns the size of the queue
	public int size() { return this.size; }

	// adds T-typed variable to the tail of the queue
	public void offer(T newThing) {
		Node lastNode = new Node(newThing);
		if(this.size != 0) {
			Node cursor = this.head; // travels from the head to the tail
			while(cursor.getNext() != null)
				cursor = cursor.getNext();
			lastNode.setPrev(cursor);
			cursor.setNext(lastNode);
		}
		else
			this.head = lastNode;	

		this.tail = lastNode;
		this.size++;
	}

	// this removes and returns the head element of the queue. Changes the head pointer to the new head value of the queue
	public T poll() {
		if(this.head != null) {
			T returnT = this.head.get();
			this.head = this.head.getNext();
			if(head != null)
				this.head.setPrev(null);
			this.size--;
			return returnT;
		}
		return null;
	}

	// this returns the T-typed variable stored in the head of the list. Does not remove the head element of the queue
	public T peek() { 
		if(this.head != null)
			return head.get();
		return null;
	}

	// this returns a String representation of the queue
	public String toString() {
		String returnString = "tail -> ";
		Node cursor = this.tail;
		while(cursor != null) {
			returnString += cursor.get();
			if(cursor.getPrev() != null )
				returnString += ", ";
			cursor = cursor.getPrev();
		}
		return returnString + " <- head";
	}

	// end of MyQueue class ----------------------------------------------------------------------------


	// debugging main method
	public static void main(String[] args) {
		MyQueue<Integer> queue = new MyQueue<Integer>();
		for(int i = 1;i <= 33;i++) {
			queue.offer(new Integer(i));
		}
		
		for(Integer i : queue)
			System.out.println(i.intValue() + " ");
		
		System.out.println(queue + " " + queue.size());
		System.out.println("Who's in front? " + queue.peek());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println("Who just left the queue? " + queue.poll());
		System.out.println(queue + " " + queue.size());
	}
}