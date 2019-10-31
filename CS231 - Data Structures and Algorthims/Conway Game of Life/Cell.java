/**
* File: Cell.java
* Author: Samuel Munoz
* Date: 09/16/2019
*/
import java.awt.Graphics;
import java.util.ArrayList;

public class Cell {

	private boolean isAlive; // boolean holds the state of the cell with true being alive and false as being dead

	public Cell() { isAlive = false; }

	public boolean getAlive() { return isAlive; } // returns the boolean that holds the state of the cell

	public void reset() { isAlive = false; } // reset the state of the cell; false is the default reset value

	public void setAlive(boolean alive) { isAlive = alive; } // this setter method sets the state of the cell, with true being alive and false as being dead

	public String toString() { // prints the cell's state. "1" is printed if the cell is alive and "0" is printed if the cell is dead
		if(isAlive)
			return "1";
		return "0";
	}

	public void draw(Graphics g, int x, int y, int scale) { // this draws a cell onto the UI, with all cells getting border and the alive cells are filled in
		g.drawRect(x,y,scale,scale); //border
		if(isAlive)
			g.fillRect(x,y,scale,scale); //fills in cell
	}

	public void updateState(ArrayList<Cell> neighbors) { // this takes neighbors if an alive cell has 2 or 3 alive neighbors, that cell remains alive in the next generation
														 // if a dead cell has 3 alive neighbors, then the cell is alive in the next generation 
		int aliveCells = 0;
		boolean alive = this.getAlive();
		for(Cell oneCell : neighbors) {
			if(oneCell.getAlive())
				aliveCells++;
		}
		if(alive) {
			if(aliveCells == 2 || aliveCells == 3)
				this.setAlive(true);
			else
				this.setAlive(false);
		}
		else {
			if(aliveCells == 3)
				this.setAlive(true);
			else
				this.setAlive(false);
		}
	} 

	public Cell clone() { // this method clones this cell. This is here to prevent shallow copying (wording that Proffessor Layton uses)/aliasing 
						  // (language used in my previous CS course) from occuring when I am updating the grid field in the Landscape class. I do not
						  //  want the update a cell, with me accidently changing the old grid cells has a create the new grid. I hope this makes
						  //  this makes sense

		Cell newCell = new Cell();
		newCell.setAlive(this.getAlive());
		return newCell;
		
	}

	public static void main(String[] args) { // main method to test the methods in this class
		Cell c = new Cell();

		System.out.println(c);

		c.setAlive(true);

		System.out.println(c.getAlive());

		c.reset();

		System.out.println(c);
	}
}
