/**
* File: Sudoku.java
* Implements a Stack to Solve a Sudoku Problem
* Author: Samuel Munoz
* Course: CS231: Data Structures and Algortims
* Semester: Fall Semester of 2019
* Date: 09/27/2019
*/
import java.util.Random;
import java.awt.Graphics;

public class Sudoku {
	
	private Board board; // creates a board to play Sudoku
	private LandscapeDisplay display; // creates a JPanel to make it easy to see the grid
	
	public Sudoku() { // create an empty array of Cells with a JPanel
		board = new Board();
		display = new LandscapeDisplay(this.board,5);
	}
	
	public Sudoku(int n) { // this fills up the Board n number of Cells with locked values and creates a JPanel
		if(n < 42) { // this is the maximum number of Cells that be filled in without cause too much lag
			Random rand = new Random();
			board = new Board();
			int[] indices = new int[81]; // this is only a container used to create
			for(int i = 0;i < 81;i++)
				indices[i] = i;

			for(int i = 0;i < n;i++) {
				while(true) {
					int randIndex = rand.nextInt(81);
					if(indices[randIndex] != -1) {
						// set a value here -----------------------------------
						int row = randIndex/9;
						int col = randIndex%9;
						while(true) {
							int randValue = rand.nextInt(9)+1;
							if(this.board.validValue(row,col,randValue)) {
								this.board.set(row,col,randValue,true);
								break;
							}
						}
						//-----------------------------------------------------
						indices[randIndex] = -1;
						break;
					}
				}
			}
		}
		else { 
			board = new Board();
		}
		display = new LandscapeDisplay(this.board,5);
	}
	
	// an array-based stack
	private class Stack<T> {
		
		private T[] stack;
		private int top;
		
		public Stack() {
			stack = (T[]) new Object[81];
			top = 0;
		}
		
		public T peek() { // returns the top item on the stack, but does not remove the item from the stack
			return stack[top-1];
		}
		
		public T pop() { // removes an item on the stack and returns that item
			return stack[--top];
		}
		
		public void push(T value) { // places a new item on the stack
			stack[top++] = value;
		}
		
		public void clear() { // removes all items in the stack
			top = 0;
		}

		public int size() { // returns the number of items in the stack
			return top;
		}

		public String toString() { // prints out from left to right where the furtherest left is the top element in the stack to the furtherest right is the bottom element of the stack
			String returnString = "[";
			if(top > 0) {	
				for(int i = top-1;i > 0;i--) {
					returnString += stack[i] + ":";
				}
				return (returnString + stack[0] + "]");
			}
			else {
				return "empty stack";
			}
		}
	}

	public boolean solve(int delay) { // this solves a Sudoku problem using two stacks	
		int numLockedValue = 0;
		Stack<Cell> unsolvedStack = new Stack();
		Stack<Cell> solvedStack = new Stack();

		// finds the number of locked Cells and it also places all the unlocked Cells into the unsolvedStack cell ----------
		for(int i = 0;i < 81;i++) {
			int r = i/9;
			int c = i%9;
			if(this.board.isLocked(r,c))
				numLockedValue++;
			else
				unsolvedStack.push(this.board.get(r,c)); //references to Cells
		}
		// -------------------------------------------

		while(unsolvedStack.size() != 0) {
			if(delay >= 0) {
				try {
					Cell currentCell = unsolvedStack.pop();
					for(int value = currentCell.getValue()+1;value <= 9;value++) {
						if(this.board.validValue(currentCell.getRow(),currentCell.getCol(),value)) {
							currentCell.setValue(value);
							solvedStack.push(currentCell);
							break;
						}
						else if(value >= 9) {
							if(solvedStack.size() == 0) 
								return false;
							else {
								currentCell.setValue(0);
								unsolvedStack.push(currentCell);
								unsolvedStack.push(solvedStack.pop());
							}
						}
					}
					Thread.sleep(delay);
				}
				catch(InterruptedException ex) {
					System.out.println("Interrupted");
				}	
				display.repaint();
			}
		}
		return true;
	}

	public boolean validSolution() { // checks if the solution on the board is right
		return this.board.validSolution();
	}

	public void read(String filename) { // reads a file from arguments
		this.board.read(filename);
	}

	public String toString() { // inherits the toString method of Board 
		return board.toString();
	}

	public static void main(String[] args) {
		// Sudoku s = new Sudoku();
		// if(args.length > 0)
			// s.read(args[args.length-1]);
		// boolean solved = s.solve(0);
		// if(solved) {
			// System.out.println("Solved: \n" + s);
		// }
		// else {
			// System.out.println("This problem cannot be solved");
		// }

		Sudoku s = new Sudoku(Integer.parseInt(args[0]));

		// if(args.length > 0)
	    	// s.read( args[0] );
		boolean trash = s.solve(10);
	}
}

//https://stackoverflow.com/questions/4105331/how-do-i-convert-from-int-to-string#4105406 an idea that occured to me 