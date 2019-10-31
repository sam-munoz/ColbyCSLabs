/**
* File: ArrayQueue.java
* Implementation of a queue using a circular-based array
* Author: Samuel Munoz
* Course: CS231
* Date: 10-12-2019
*/
import java.util.Iterator;

public class ArrayQueue<T> implements Iterable<T> {	
	// holds all the data in the queue
	private T[] queue;
	// hold the default capacity of the array	
	private final int DEFAULT_CAPACITY = 32;
	// index of the head element
	private int head;
	// index of the tail element
	private int tail;
	// holds the size of the queue
	private int size;
	
	// this class creates an iterator that allows the user to iterate over the queue
	private class AQIterator implements Iterator<T>{
		// holds a cursor index of the iterator to know where in the queue the iterator is at
		private int cursor;
		// since the queue uses a circular array model and circles have no start or end, this boolean checks whether or not I have made a complete iteration around the queue
		private boolean completeIteration;
		
		
		// creates an iterator starting at element pointer
		public AQIterator(int pointer) {
			this.cursor = pointer;
			this.completeIteration = false;
		}
		
		// checks if the queue has another value to iterator to
		public boolean hasNext() {
			if(this.cursor == tail) {
				this.completeIteration = true;
				return true;
			}
			return !this.completeIteration;
			
			
		}
		
		// allows the iterator to iterate to the next element in the queue
		public T next() {
			T returnT = queue[cursor];
			cursor = (cursor+1)%queue.length;
			return returnT;
		}
	} // end of the AQIterator class
	
	// creates an empty queue with the head and tail pointing at element 0
	public ArrayQueue() {
		this.queue = (T[]) new Object[DEFAULT_CAPACITY];
		this.head = 0;
		this.tail = 0;
		this.size = 0;
	}
	
	// creates an empty queue with the head and tail pointing at element 0. This takes in the size of the array
	public ArrayQueue(int capacity) {
		this.queue = (T[]) new Object[capacity];
		this.head = 0;
		this.tail = 0;
		this.size = 0;
	}
	
	// this empties an empty queue and resets the queue pointers and fields to their default value
	public void clear() { // this may need more work done on it
		this.head = 0;
		this.tail = 0;
		this.size = 0;
	}
	
	// this returns the size of the queue
	public int size() { return this.size; }
	
	// this add T element at the tail of the queue
	public void offer(T element) {
		if((this.tail+1)%this.queue.length == this.head) {
			T[] newQueue = (T[]) new Object[this.queue.length * 2];
			for(int index = 0;index < this.queue.length;index++)
				newQueue[index] = this.queue[index];
			this.queue = newQueue;
		}
		if(this.size != 0) {
			this.tail = (this.tail+1)%this.queue.length;
		}
		this.queue[this.tail] = element;
		this.size++;
	}
	
	// this removes the head element of the queue and returns the value at the head
	public T poll() {
		if(this.size != 0) {
			this.size--;
			T returnT = this.queue[this.head];
			this.head = (this.head+1)%this.queue.length;
			return returnT;
		}
		throw new IndexOutOfBoundsException();
	}
	
	// this returns the head element of the queue
	public T peek() {
		if(this.size != 0) {
			return this.queue[this.head];
		}
		throw new IndexOutOfBoundsException();
	}
	
	// this returns all the elements in the queue with the String have square brackets containing the list
	public String toString() {
		String returnString = "tail -> ";
		int cursor = this.tail;
		while(cursor != (this.head-1)%this.queue.length) {
			returnString += this.queue[cursor];
			if(cursor != this.head)
				returnString += ", ";
			cursor = (cursor-1)%this.queue.length;
		}
		return returnString + " <- head";
	}
	
	// this creates an iterator that points at the head element of the queue
	public AQIterator iterator() {
		return new AQIterator(this.head);
	}
	
	// debugging class of the array-based queue
	public static void main(String[] args) {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
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
	

	
	