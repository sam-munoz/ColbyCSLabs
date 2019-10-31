/**
* File: CheckoutAgent.java
* Class that represents a checkout line in a store. Holds Customer objects
* Author: Samuel Munoz
* Course: CS231
* Date: 10-07-2019
*/
import java.awt.Graphics;
import java.awt.Color;

public class CheckoutAgent {
	private int x; // holds the x position of where the checkout agent should be on the LandscapePanel
	private int y; // holds the y position of where the checkout agent should be on the LandscapePanel
	private MyQueue<Customer> queue; // holds the line for this checkout station. This queue holds Customer or child classes of the Customer class

    //constructor. The queue should be initialized to an empty MyQueue<Customer>.
    public CheckoutAgent(int x, int y) { 
    	queue = new MyQueue<Customer>();
		this.x = x;
    	this.y = y;
    }

	// add a Customer to its queue.
    public void addCustomerToQueue( Customer c ) { 
    	queue.offer(c);
    }

	// returns the number of Customers in its queue.
    public int getNumInQueue() { 
    	return queue.size();
    }

	// draws the CheckoutAgent as a rectangle near the bottom of the window with a height proportional to the number of Customers in the queue. A height of 10*N (where N is the number of customers in the queue) and width of 10 should work. 
	// You should assume that (this.x,this.y) is the bottom left corner of the rectangle.
    public void draw(Graphics g) { 
    	g.setColor(Color.RED);
    	g.fillRect(x-40,y - 45,20,60);
    	int count = 0;
    	for(Customer c : queue) {
			g.setColor(Color.GREEN);
    		g.fillRect(x-15, y - count*17,15,15);
			g.setColor(Color.BLACK); // extension
			g.drawChars(new char[] {(char) ('0' + c.getNumItems()%10)},0,1,x-11,y-(count-1)*17-5); // extension
    		count++;
    	}
	}
	
	// this method tells the Checkout agent how to update the Customers in the lines so the Customers in the line can leave the line over time
	public void updateState( Landscape scape ) {
		for(Customer c : this.queue)
			c.incrementTime();
		Customer c = this.queue.peek();
		if(c != null) {
			if(c.getNumItems() != 0)
				c.giveUpItem();
			else {
				Customer outOfLineCustomer = this.queue.poll();
				scape.addFinishedCustomer(outOfLineCustomer);
			}
		}
	}
}