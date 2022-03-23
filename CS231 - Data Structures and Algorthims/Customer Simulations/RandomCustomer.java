/**
* File: RandomCustomer.java
* Class that represents Customers who randomly select a line when they go to checkout to buy their items
* Author: Samuel Munoz
* Course: CS231
* Date: 10-07-2019
*/
import java.util.ArrayList;

public class RandomCustomer extends Customer {

	// constructor. This should call the super class's constructor with the given number of items and 1 as the initial value for the time steps. 
	// This is meant to simulate the amount of time the RandomCustomer spends choosing a line. The RandomCustomer spends one time-step choosing a line, so it needs to start its counter at 1.
    public RandomCustomer( int num_items ) { 
    	super(num_items,1);
    }

    // returns an integer randomly chosen from the range 0 (inclusive) to the lenght of the list (exclusive).
    public int chooseLine(ArrayList<CheckoutAgent> checkouts) { 
    	return (int) (Math.random() * checkouts.size());
    }
}