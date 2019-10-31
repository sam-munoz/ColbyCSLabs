/**
* File: Landscape.java
* Class the holds all the checkout lines in a store and holds all the customers that leave a line
* Author: Samuel Munoz
* Course: CS231
* Date: 10-07-2019
*/
import java.awt.Graphics;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Landscape {
	private int width; // holds the width of the LandscapePanel
	private int height; // holds the height of the LandscapePanel
	private ArrayList<CheckoutAgent> checkouts; // ArrayList of the different lines that are represented by queues
	private LinkedList<Customer> checkedOutsCustomers;  // holds all the Customers that have left a line or queue in the checkout ArrayList

	// constructor. The list of finished customers should be initialized to an empty list.
    public Landscape(int w, int h, ArrayList<CheckoutAgent> checkouts ) { 
    	this.width = w;
    	this.height = h;
    	this.checkouts = checkouts;
		this.checkedOutsCustomers = new LinkedList<Customer>();
	}

	//return the height of the Landscape.
    public int getHeight() { 
    	return this.height;
    }

    // return the width of the Landscape.
    public int getWidth() { 
    	return this.width;
	}

	// return a string indicating how many checkouts and finished customers are in the landscape.
    public String toString() { 
    	return "Number of people in the lines: " + checkouts.size() + "\nNumber of people who have finished " + checkedOutsCustomers.size();
    }

    // add the Customer to the list of finished customers.
    public void addFinishedCustomer(Customer c ) { 
    	checkedOutsCustomers.addLast(c);
    }

    // loop through the CheckoutAgents, calling the draw method on each.
    public void draw( Graphics g ) { 
    	for(CheckoutAgent c : checkouts)
    		c.draw(g);
    }
	
	// calls the updateState method all the CheckoutAgents in the checkout variable so the lines can move foward
	public void updateCheckouts() {
		for(CheckoutAgent c : checkouts)
			c.updateState(this);
	}
	
	// prints out the statistics collected from the Customers
	public void printFinishedCustomerStatistics() {
		DecimalFormat df = new DecimalFormat("#.##");
	
		double time = 0.0;
		double stdtime = 0.0;
		double timePerItem = 0.0;
		double stdTimePerItem = 0.0;
		double lineLength = 0.0;
		double stdlineLength = 0.0;
		
		for(Customer c : this.checkedOutsCustomers) {
			time += (double) c.getTime();
			timePerItem += (double) (c.getTime()/c.getOriginalNumOfItems());
		}
		time = time/this.checkedOutsCustomers.size();
		timePerItem = timePerItem/this.checkedOutsCustomers.size();
		
		for(CheckoutAgent c : checkouts)
			lineLength += c.getNumInQueue();
		lineLength = lineLength/checkouts.size();
		
		for(Customer c : this.checkedOutsCustomers) {
			stdtime += Math.pow(c.getTime() - time,2);
			stdTimePerItem += Math.pow(c.getTime()/c.getOriginalNumOfItems() - timePerItem,2);
		}
		
		for(CheckoutAgent c : checkouts)
			stdlineLength += Math.pow(c.getNumInQueue() - lineLength,2);
		
		stdtime = Math.sqrt(stdtime/this.checkedOutsCustomers.size());
		stdTimePerItem = Math.sqrt(stdTimePerItem/this.checkedOutsCustomers.size());
		stdlineLength = Math.sqrt(stdlineLength/this.checkouts.size());
		System.out.println("Average Time: " + df.format(time));
		System.out.println("Standard Deviation for Time: " + df.format(stdtime));
		System.out.println("Average Time Per Item: " + df.format(timePerItem));
		System.out.println("Standard Deviation for Time Per Item: " + df.format(stdTimePerItem));
		System.out.println("Average Line Length: " + df.format(lineLength));
		System.out.println("Standard Deviation for Line Length: " + df.format(stdlineLength));
	}
}