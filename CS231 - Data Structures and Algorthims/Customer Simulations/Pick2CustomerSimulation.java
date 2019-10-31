/**
* File: Pick2CustomerSimulation.java
* Class that runs a simulation of Pick2Customer entering and leaving lines in a store
* Author: Originally created by Stephanie Taylor, but modified by Samuel Munoz
* Course: CS231
* Date: 10-11-2019
*/
import java.util.Random;
import java.util.ArrayList;

public class Pick2CustomerSimulation {
    
    // test function that creates a new LandscapeDisplay and populates it with 5 checkouts and 99 Pick2Customer by default. This can be modified by agrument parameters
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
        	totalNumberOfTimeSteps = 10000;
        }
        try {
        	pauseTime = Integer.parseInt(args[1]);

        }
        catch (Exception e) {
        	pauseTime = 250;
        }

        for (int j = 0; j < totalNumberOfTimeSteps; j++) {
            Customer cust = new Pick2Customer(1+gen.nextInt(5));
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