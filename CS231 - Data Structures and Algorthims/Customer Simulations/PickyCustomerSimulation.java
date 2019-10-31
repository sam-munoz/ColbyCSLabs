/*
* File: PickyCustomerSimulation.java
* Class runs a simulation of PickyCustomer entering and leaving lines in a store.
* Author: Originally created by Stephanie Taylor, but modified by Samuel Munoz
* Course: CS231
* Date: 10-11-2019
*/
import java.util.Random;
import java.util.ArrayList;

public class PickyCustomerSimulation {
    // test function that creates a new LandscapeDisplay and populates it with 5 checkouts and 99 customers by default but can be changed with argument parameters.
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
        catch (Exception e) {
        	pauseTime = 250;
        }

        for (int j = 0; j < totalNumberOfTimeSteps; j++) {
            
            Customer cust = new PickyCustomer(1+gen.nextInt(5), checkouts.size());
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

    }

}