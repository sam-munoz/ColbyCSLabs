/**
* File: PickyCustomer.java
* Class that represents a customer that will look at the lines in the store and enter the shortest line
* Author: Samuel Munoz
* Course: CS231
* Date: 10-10-2019
*/
import java.util.ArrayList;

public class PickyCustomer extends Customer {
	
	// constructor that allows the instance of the PickyCustomer to know how many items the Customer has along with the number of lines the Customer has to check
	public PickyCustomer( int num_items, int num_lines ) {
		super(num_items,num_lines);
	}

	// choose the shortest line
	public int chooseLine(ArrayList<CheckoutAgent> checkouts) { 
		int indexWithShortestLine = 0;
		int shortestLineSize = checkouts.get(0).getNumInQueue();
		for(int i = 1;i < checkouts.size();i++) {
			if(checkouts.get(i).getNumInQueue() < shortestLineSize) {
				indexWithShortestLine = i;
				shortestLineSize = checkouts.get(i).getNumInQueue();
			}
		}
		return indexWithShortestLine;
	}
}