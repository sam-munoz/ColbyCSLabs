/**
* File: Customer.java
* Class that forms the foundation of Customer objects. Cannot create an object from this class
* Author: Samuel Munoz
* Course: CS231
* Date: 10-07-2019 
*/
import java.util.ArrayList;

public abstract class Customer {
	private int numOfItems; // holds the number of items a customer has in a line
	private int originalNumOfItems; // variable created from extension: holds the number of items a Customer has once the Customer enters a line. Customer has not paid for any items yet
	private int timeSteps; // holds the steps that have passed from when the Cusomter enters the line to when the Customer leaves the line. This value increases slowly to its end value

    // constructor method with a default value for time steps at zero.)
    public Customer(int num_items) { 
    	this.numOfItems = num_items;
		this.originalNumOfItems = num_items;
    	this.timeSteps = 0;
    }

    // constructor method with a starting timeStep value
    public Customer(int num_items, int time_steps) { 
    	this.numOfItems = num_items;
		this.originalNumOfItems = num_items;
    	this.timeSteps = time_steps;
    }

    //increments the number of time steps.
    public void incrementTime() { 
    	this.timeSteps++;
    }

    //returns the number of time steps
    public int getTime() { 
    	return this.timeSteps;
    }

    //decrements the number of items (indicating another item has been paid for).
    public void giveUpItem() { 
    	this.numOfItems--;
    }

    //returns the number of items.
    public int getNumItems() { 
    	return this.numOfItems;
    }
	
	// returns the original number of items a customer had
	public int getOriginalNumOfItems() { 
		return this.originalNumOfItems;
	}

	//since this is an abstract method, there is no body. This does get implmented in other child classes and this method tells the instance of that Customer object how to choose what line to enter
    public abstract int chooseLine(ArrayList<CheckoutAgent> checkouts); 
}