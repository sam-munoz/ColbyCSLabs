/**
* File: LifeSimulation.java
* Author: Samuel Munoz
* Date: 09/21/2019
*/

// https://stackoverflow.com/questions/2550310/can-a-main-method-of-class-be-invoked-from-another-class-in-java#2550372 -> not used, but I considered using this
																   			  // It is considering running the main method of other classes, outside of this one

public class LifeSimulation {
	
	public static void main(String[] args) throws InterruptedException { // main method that will run the life simluation
																		 // takes in two arguments which are going the set the size of the array
																		 // runs the pattern method from Landscape to set the starting cells
																		 // that are going to be alive and the while loop runs the simulation
		
		Landscape grid = new Landscape(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		grid.pattern();
		LandscapeDisplay panel = new LandscapeDisplay(grid, 10);
		while(true) {
			grid.advance();
			Thread.sleep(250);
			panel.repaint();
		}
	}
}