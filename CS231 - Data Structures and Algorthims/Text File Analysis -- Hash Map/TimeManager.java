/**
* File: TimeManager.java
* Collects times and calculates averages
* Author: Samuel Munoz
* Course: CS231
* Date: 11-09-2019
*/
public class TimeManager {
	// holds min time value
	private long minTime;
	// holds max time value
	private long maxTime;
	// holds the index location of the min time value
	private int indexMin;
	// holds the index location of the max time value
	private int indexMax;
	// holds all time values
	private long[] data;
	// pointer pointing to the next empty array slot
	private int pointer;

	// constructor: initializes all fields to empty values
	public TimeManager(int capacity) {
		this.minTime = (long) 0.0;
		this.maxTime = (long) 0.0;
		this.indexMin = 0;
		this.indexMax = 0;
		this.pointer = 0;
		this.data = new long[capacity];
	}

	// records a new time value and updates all fields appropriately 
	public void enterTime(long time) { 
		if(this.pointer >= this.data.length)
			this.resize();
		
		if(this.pointer == 0) {
			this.minTime = time;
			this.indexMin = this.pointer;
			this.maxTime = time;
			this.indexMax = this.pointer;
		}
		
		if(time < this.minTime) {
			this.minTime = time;
			this.indexMin = this.pointer;
		}

		if(time > this.maxTime) {
			this.maxTime = time;
			this.indexMax = this.pointer;
		}
		
		this.data[this.pointer++] = time;
	}

	// calculate average time
	public long average() {
		int average = 0;
		for(int index = 0;index < this.pointer;index++) {
			if(index != this.indexMin && index != this.indexMax)
				average += this.data[index];
		}
		return average/(this.pointer-2);	
	}
	
	// empties all fields
	public void clear() {
		this.minTime = (long) 0.0;
		this.maxTime = (long) 0.0;
		this.indexMin = 0;
		this.indexMax = 0;
		this.pointer = 0;
		this.data = new long[this.data.length];
	}
	
	// resizes array. DOES NOT CHECK IF RESIZING IS NEEDED!!
	public void resize() {
		long[] newArray = new long[this.data.length * 2];
		for(int index = 0;index < this.pointer;index++)
			newArray[index] = this.data[index];
		this.data = newArray;
	}
	
	public static void main(String[] args) {
		TimeManager t = new TimeManager(5);
		t.enterTime(29020L);
		t.enterTime(77334L);
		t.enterTime(103513L);
		t.enterTime(92593L);
		t.enterTime(63510L);
		t.enterTime(56261L);
		t.enterTime(67887L);
		t.enterTime(64935L);
		System.out.println("min: " + t.minTime + "\tindex: " + t.indexMin);
		System.out.println("max: " + t.maxTime + "\tindex: " + t.indexMax);
		System.out.println("average: " + t.average());
	}
}