/*
 * File: LinkedList.cs
 * Purpose: To create a linked list in C#
 * Author: Samuel Munoz
 * Date: 03-22-2020
 */

public delegate bool CompFunc<T>(T t1, T t2);
public delegate T MapFunc<T>(T data);

namespace Project_5 
{
	public partial class Program 
	{
		public class LinkedList<T> {

			// this is my sub class node where I store all data points 
			private class Node {
				// all my fields for Node class
				private T data;
				private Node prev;
				private Node next;

				// constructor: creates a Node given data
				public Node(T data) {
					this.data = data;
					this.next = null;
					this.prev = null;
				}

				// getter method for data field
				public T get() { return this.data; }

				// setter method for data field
				public void set(T data) { this.data = data; }

				// getter method for prev field
				public Node getPrev() { return this.prev; }

				// setter method for prev field
				public void setPrev(Node prev) { this.prev = prev; }

				// getter method for next field
				public Node getNext() { return this.next; }

				// setter method for next field
				public void setNext(Node next) { this.next = next; } 
			}

			// all my fields for LinkedList
			private Node head;
			private Node tail;
			private int s;

			// constructor: create an empty linked list
			public LinkedList() {
				this.head = null;
				this.tail = null;
				this.s = 0;
			}

			// push method: this adds an element to the front
			// NOTE: the front of this list is the head field
			public void push(T data) {
				Node new_node = new Node(data);
				// if the list is empty
				if(this.s == 0) {
					this.head = new_node;
					this.tail = new_node;
				}
				// if the list is not empty
				else {
					this.head.setPrev(new_node);
					new_node.setNext(this.head);
					this.head = new_node;
				}
				this.s++;
			}

			// pop method: this removes the element at head
			public T pop() {
				// if the list is not empty
				if(this.s > 0) {
					Node returnNode = this.head;
					this.head = this.head.getNext();
					this.s--;
					return returnNode.get();
				}

				// if the list is empty
				return default(T);
			}

			// append method: this adds an element at the end of the list
			// NOTE: the end the of the list is at the tail
			public void append(T data) {
				Node new_node = new Node(data);
				// if the list is empty
				if(this.s == 0) {
					this.head = new_node;
					this.tail = new_node;
				}
				// if the list is not empty
				else {
					this.tail.setNext(new_node);
					new_node.setPrev(this.tail);
					this.tail = new_node;
				}
				this.s++;
			}

			// remove method: this removes the first instance where the target matches a Node defined by the delegate CompFunc
			public T remove(T target, CompFunc<T> compfunc) {
				Node cursor = this.head;
				while(cursor != null) {
					bool condition = compfunc(cursor.get(), target);
					// if the condition finds a match
					if(condition) {
						cursor.getPrev().setNext(cursor.getNext());
						cursor.getNext().setPrev(cursor.getPrev());
						this.s--;
						return cursor.get();
					}
					// if the condition is not a match
					cursor = cursor.getNext();
				}

				// if there is no matches in the list
				return default(T);
			}

			// size method: returns the length of the linked list
			public int size() {
				return this.s;
			}

			// clear method: clears the link list
			public void clear() {
				this.s = 0;
				this.head = null;
				this.tail = null;
				// all the nodes are deleted via the garbage manager of c#
			}

			// map method: this method changes all the values inside the linked list
			public void map(MapFunc<T> mapfunc) {
				Node cursor = this.head;
				while(cursor != null) {
					cursor.set(mapfunc(cursor.get()));
					cursor = cursor.getNext();
				}
			}

			public override string ToString() {
				string returnString = "";
				Node cursor = this.head;
				if(this.s == 0)
					returnString += "Empty  ";
				while(cursor != null) {
					returnString += cursor.get().ToString() + ", ";
					cursor = cursor.getNext();
				}
				return returnString.Substring(0, returnString.Length-2);
			}
		}
	}
}