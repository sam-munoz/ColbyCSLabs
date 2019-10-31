/*
* File: CustomerSimulation.java
* Original written by Stephanie Taylor (Spring 2019). Modified by Samuel Munoz
* Holds the method to run a simulation of customer entering and leaving lines in a store. Customer here have mixed strageties, but there is roughly equiable numebr of Customer for each stragety
* Course: CS231
* Date: 10-11-2019
*/

import java.util.Random;
import java.util.ArrayList;

public class CustomerSimulation {
    // test function that creates a new LandscapeDisplay and populates it with 5 checkouts and 99 customers.
    public static void main(String[] args) throws InterruptedException {
        Random gen = new Random();
        ArrayList<CheckoutAgent> checkouts = new ArrayList<CheckoutAgent>(5);

        for(int i=0;i<5;i++) {
            CheckoutAgent checkout = new CheckoutAgent( i*100+50, 480 );
            checkouts.add( checkout );
        }
        Landscape scape = new Landscape(500,500, checkouts);
        LandscapeDisplay display = new LandscapeDisplay(scape);
        
        int totalNumberOfTimeSteps, pauseTime;
        try {
        	totalNumberOfTimeSteps =  Integer.parseInt(args[0]);
        }
        catch (Exception e) {
        	totalNumberOfTimeSteps = 1000;
        }
		try {
        	pauseTime = Integer.parseInt(args[1]);			
		}
		catch(Exception e) {
        	pauseTime = 250;
		}

        for (int j = 0; j < totalNumberOfTimeSteps; j++) {
            Customer cust;
			int maxItems = 6; // six is the maximum number
			if(j%3 == 0)
				cust = new RandomCustomer(1+gen.nextInt(maxItems));
			else if(j%3 == 1)
				cust = new PickyCustomer(1+gen.nextInt(maxItems), checkouts.size());
			else
				cust = new Pick2Customer(1+gen.nextInt(maxItems));
            int choice = cust.chooseLine( checkouts );
            checkouts.get(choice).addCustomerToQueue( cust );
			scape.updateCheckouts();
            display.repaint();
			if((j+1)%100 == 0) {
				System.out.println("After " + String.format("%,d", j+1) + " time steps:"); // https://stackoverflow.com/questions/5323502/how-to-set-thousands-separator-in-java
				scape.printFinishedCustomerStatistics();
				System.out.println();
			}
            Thread.sleep( pauseTime );
        }

        // unfinshed idea, but this was suppose to be called whenever control C was pressed or when someone hits the "x" button on the JFrame
		// System.out.println("After 100,000 time steps:");
		// scape.printFinishedCustomerStatistics();
    }

}