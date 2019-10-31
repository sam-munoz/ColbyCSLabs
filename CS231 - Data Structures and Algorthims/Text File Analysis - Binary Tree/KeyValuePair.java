/**
* File: KeyValuePair.java
* Implements a map set that maps one value to another value
* Author: Samuel Munoz
* Course: CS231
* Date: 10-14-2019
*/

// this class is a single point or one input and output set
public class KeyValuePair<Key,Value> {
	private Key key; // the key value or input for this set
	private Value value; // the output of this set

	// constructor, creates key and value set
	public KeyValuePair(Key k, Value v) {
		this.key = k;
		this.value = v;
	}

	// returns the value key of this set
	public Key getKey() { return this.key; }

	// returns the variable value of this set or returns the output of this set
	public Value getValue() { return this.value; }

	// changes the field key value to a new value
	public void setKey(Key k) { this.key = k; }

	// updates the field value to a new value
	public void setValue(Value v) { this.value = v; }

	// creates a String representation of the set
	public String toString() {
		return "(" + this.key + "," + this.value + ")";
	}

	// debugging main method
	public static void main(String args[]) {
		KeyValuePair<Integer,String> set = new KeyValuePair<>(new Integer(12),"This is a string I think");
		System.out.println(set.getKey() + " " + set.getValue());

		set.setKey(new Integer(1));
		set.setValue("PRINT ME!!");

		System.out.println(set);
	}

}