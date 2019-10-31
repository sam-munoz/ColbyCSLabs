/**
* File: Landscape.java
* Author: Samuel Munoz
* Date
*/
import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Scanner;

public class Landscape {

	private Cell[][] grid; // the grid used to hold the cell objects

	public Landscape(int row,int col) { // this constructor initalizes the grid field and fill the grid with dead cells
		grid = new Cell[row][col];
		for(int i = 0;i < row;i++) {
			for(int j = 0;j < col;j++)
				grid[i][j] = new Cell();
		}
	}

	public void reset() { // this class resets every cell in the grid, calling the reset method from the cell class on every cell object in the array
		int rows = grid.length;
		int cols = grid[0].length;
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < cols;j++)
				grid[i][j].reset();
		}
	}

	public int getRows() { return grid.length; } // this get the number of rows in the grid field

	public int getCols() { return grid[0].length; } // this get the number of columns in the grid field

	public Cell getCell(int row,int col) { return grid[row][col]; } // this gets the cell object from the grid field

	public String toString() { // this method returns backs the state of the grid by printing row by row, all the cell's states. Each row starts 
							   // has a "[" at the beginning and a "]" at the end
		String returnString = "";
		int row = grid.length;
		int col = grid[0].length;
		for(int i = 0;i < row;i++) {
			for(int j = 0;j < col;j++) {
				if(j == 0)
					returnString += "[";
				returnString += grid[i][j];
				if(j != col-1)
					returnString += ", ";
				else
					returnString += "]\n";
			}
		}
		return returnString;
	}

	public ArrayList<Cell> getNeighbors(int row,int col) { // this places a reference of every neighbor cell inside the array 
														   // (I really should use the clone method, but I am too lazy to actually do that)
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		int rows = grid.length;
		int cols = grid[0].length;
		if(row-1 >= 0)
			neighbors.add(grid[row-1][col]);
		
		if(row-1 >= 0 && col+1 < cols)
			neighbors.add(grid[row-1][col+1]);
		
		if(col+1 < cols)
			neighbors.add(grid[row][col+1]);
		
		if(row+1 < rows && col+1 < cols)
			neighbors.add(grid[row+1][col+1]);
		
		if(row+1 < rows)
			neighbors.add(grid[row+1][col]);
		
		if(row+1 < rows && col-1 >= 0)
			neighbors.add(grid[row+1][col-1]);
		
		if(col-1 >= 0)
			neighbors.add(grid[row][col-1]);
		
		if(row-1 >= 0 && col-1 >= 0)
			neighbors.add(grid[row-1][col-1]);
		
		return neighbors;
	}

	public void draw( Graphics g, int gridScale ) { // draw all the cells. This is a copy and paste method from the lab instructions webpage
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				this.grid[i][j].draw(g, i*gridScale, j*gridScale, gridScale);
			}
		}
	}
	
	public void pattern() { // this is my own method. This method runs before running the simulation, and it is console asking the user
							// to enter the row, then the column of the cells to turn to alive. Entering "-1" will terminate this method
		
		Scanner scan = new Scanner(System.in); // why is this object sometimes a little buggy? That is my experience with this object in the past.
		
		ArrayList<Cell> makeCellsAlive = new ArrayList<Cell>();
		int row, col;
		while(true) {
			
			while(true) {
				try { // this prevents the program from crashing when entering 
					System.out.println("Enter -1 to quit.");
					System.out.print("Enter the row the cell to turn on: ");
					row = Integer.parseInt(scan.nextLine());
					break;
				}
				catch(Exception e) { // https://www.w3schools.com/java/java_try_catch.asp; forgot what goes inside in the catch parameters
					System.out.println("You must enter an integer.");
				} 
			}
			
			if(row == -1)
				break;
			
			while(true) {
				try {
					System.out.println("Enter -1 to quit.");
					System.out.print("Enter the column the cell to turn on: ");
					col = Integer.parseInt(scan.nextLine());
					break;
				}
				catch(Exception e) {
					System.out.println("You must enter an integer.");
				}
			}

			if(col == -1)
				break;
			else
				makeCellsAlive.add(grid[row][col]);
		}
		for(Cell tempCell : makeCellsAlive) {
			tempCell.setAlive(true);
		}
	}

	public void advance() { // this method goes through every cell and determines whether or not the cell is a alive in the next generation. This
							// uses the clone method from the cell class to prevent the program from mistakely updating the old grid has we create a new grid
		int rows = this.grid.length;
		int cols = this.grid[0].length;
		Cell[][] newGrid = new Cell[rows][cols];
		Cell newCell = new Cell();
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < cols;j++) {
				neighbors.clear();
				neighbors = getNeighbors(i,j);
				newCell = this.grid[i][j].clone();
				newCell.updateState(neighbors);
				newGrid[i][j] = newCell;
			}
		}
		this.grid = newGrid;
	}

	public static void main(String[] args) { // main method for mainly debugging reasons
		Landscape graphic1 = new Landscape(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		graphic1.pattern();
		for(int i = 0;i < 10;i++) {
			System.out.println(graphic1);
			graphic1.advance();
		}
	}


}
