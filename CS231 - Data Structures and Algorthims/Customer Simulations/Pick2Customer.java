/**
* File: Pick2Customer.java
* Class that represents a customer that will randomly select two lines to enter in and enters in the line that is the shortest of the two
* Author: Samuel Munoz
* Course: CS231
* Date: 10-11-2019
*/
import java.util.ArrayList;

public class Pick2Customer extends Customer {


	// constructor. This should call the super class's constructor with the given number of items and 2 as the initial value for the time steps. The Pick2Customer spends two time-step choosing a line because it randomly examines two lines before picking one.
    public Pick2Customer( int num_items ) {
    	super(num_items,2);
    }

    // returns the index of the shorter of two randomly chosen queues. (Note: write your code to ensure that there are two different lines chosen.)
    public int chooseLine(ArrayList<CheckoutAgent> checkouts) { // this has some cool parts
    	
    	// This will randomly select two lines if possible and places the indexes of those lines into a linkedlist  ----------------------
    	int[] openLines = {-1,0}; 
    	if(checkouts.size() > 2) {
    		while(true) {
	    		int randLine = (int) (Math.random() * checkouts.size());
	    		if(openLines[0] == -1) {
	    			openLines[0] = randLine;
	    			continue;
	    		}
	    		if(randLine != openLines[0]) {
	    			openLines[1] = randLine;
	    			break;
	    		}
	    	}
    	}
    	else { 
    		if(checkouts.size() >= 2)
   				openLines[1] = 1;
    		if(checkouts.size() >= 1)
    			openLines[0] = 0;
    		if(checkouts.size() == 0)
    			throw new IndexOutOfBoundsException();
    	}
    	// -------------------------------------------------------------------------------------------------------------------------------

    	// find which of the two lines are smalller and returns that index ---------------------------------------------------------------
    	if(checkouts.get(openLines[0]).getNumInQueue() < checkouts.get(openLines[1]).getNumInQueue())
    		return openLines[0];
    	else
    		return openLines[1];
    	// -------------------------------------------------------------------------------------------------------------------------------
    }

}