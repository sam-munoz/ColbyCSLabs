/**
* File: Cell.java
* Cell Object For a Sudoku Board
* Author: Samuel Munoz
* Course: CS231: Data Structures and Algorthims
* Semester: Fall Semester of 2019
* Date: 09/23/2019
*/
import java.awt.Graphics;

public class Cell {

	private int value; //holds the value of a Cell in the board
	private int row; // holds the row number that the cell is located in
	private int col; // holds the column number that the cell is located in
	private boolean isLocked; // holds whether or not if the number can be modified; checks whether this is a value you started with or not

	public Cell() { // constructor that makes an empty Cell with row and column being 0 and can be modified
		this.value = 0;
		this.row = 0;
		this.col = 0;
		this.isLocked = false;
	}

	public Cell(int row, int col, int value) { // constructor that will make a Cell given all field values minus the boolean; Cell can be modified
		this.value = value;
		this.row = row;
		this.col = col;
		this.isLocked = false;
	}

	public Cell(int row, int col, int value, boolean locked) { // constructor that will make a Cell given all field values
		this.value = value;
		this.row = row;
		this.col = col;
		this.isLocked = locked;
	}


    public int getRow() { return this.row; } //return the Cell's row index.

    public int getCol() { return this.col; } //return the Cell's column index.

    public int getValue() { return this.value; } //return the Cell's value.

    public void setValue(int newval) { this.value = newval; } //set the Cell's value.

    public boolean isLocked() { return this.isLocked; } //return if the Cell is locked.

    public void setLocked(boolean lock) { this.isLocked = lock; } //set the Cell's locked value.

    public Cell clone() { return new Cell(this.row,this.col,this.value,this.isLocked); } //clones the object into a new Cell object

    public String toString() { // returns the a String that has the Cell's value
        return Integer.toString(this.value); 
    }

    public void draw(Graphics g,int x0, int y0, int scale) { // writes the Cell's value on the board in a certain place
        char[] array = { (char)('0' + this.value) };
        g.drawChars(array,0,1,x0 * scale,y0 * scale);  
    }

    public static void main(String[] args) {
    	Cell a = new Cell();
    	Cell b = new Cell(0,0,4);
    	Cell c = new Cell(1,1,9,true);
    	System.out.println(a);
    	System.out.println(b.getRow() + " " + b.getCol() + " " + b.getValue() + " " + b.isLocked());
    	a.setValue(2);
    	a.setLocked(true);
    	Cell d = a.clone();
    	System.out.println(a);
    	System.out.println(d);
    }
}