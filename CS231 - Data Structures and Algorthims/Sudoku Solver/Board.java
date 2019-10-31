/**
* File: Board.java
* Board Object to Represent a Sudoku Board Holding Cell Objects
* Author: Samuel Munoz
* Course: CS231: Data Structures and Algortims
* Semester: Fall Semester of 2019
* Date: 09/23/2019
*/
import java.io.*;
import java.awt.Graphics;

public class Board {

	private Cell[][] board; // 2D array of Cells to hold
	private static final int SIZE = 9; // maximum size of 9 elements per row can allocated on the board field

	public Board() { // creates a 2D array of empty Cells
		board = new Cell[Board.SIZE][Board.SIZE];
		for(int i = 0;i < Board.SIZE;i++) {
			for(int j = 0;j < Board.SIZE;j++)
				board[i][j] = new Cell(i,j,0);
		}
	}

	public String toString() { // returns a String with grid-like representation of the board. The major grid lines are shown
		String returnString = "";
		for(int i = 0;i < getRows();i++) {
			for(int j = 0;j < getCols();j++) {
				if(j == 3 || j == 6)
					returnString += "| ";
				returnString += board[i][j].getValue() + " ";
				if(j == 8)
					returnString += "\n";
			}
			if(i == 2 || i == 5)
				returnString += "---------------------\n";
		}
		return returnString;
	}


    public int getCols() { return board[0].length; } //returns the number of columns

    public int getRows() { return board.length; } //returns the number of rows

    public Cell get(int r, int c) { return board[r][c]; } //returns the Cell at location r, c.

    public boolean isLocked(int r, int c) { return board[r][c].isLocked(); } //returns whether the Cell at r, c, is locked.

    public int numLocked() { //returns the number of locked Cells on the board.
    	int lockedCells = 0;
       	for(int r = 0;r < this.getRows();r++) {
    		for(int c = 0;c < this.getCols();c++) {
    			//System.out.println(r + " " + c + " "+ this.getRows() + " " + this.getCols());
    			if(board[r][c].isLocked())
    				lockedCells++;
    		}
    	}
    	return lockedCells;
    }

    public int value(int r, int c) { return board[r][c].getValue(); } //returns the value at Cell r, c.

    public void set(int r, int c, int value) { board[r][c].setValue(value); }//sets the value of the Cell at r, c.

    public void set(int r, int c, int value, boolean locked) { //sets the value and locked fields of the Cell at r, c.
    	board[r][c].setValue(value);
    	board[r][c].setLocked(locked);
    }

    public boolean validValue(int row, int col, int value) { // this checks whether or not a value at a certain row and column index if it can be placed on the board
	
    	//checking row --------------------------------------------------------------------------------
     	for(int c = 0;c < this.getCols();c++) {
     		if(this.value(row,c) == value)
     			return false;
     	}
     	// --------------------------------------------------------------------------------------------

     	// checking colums-----------------------------------------------------------------------------
     	for(int r = 0;r < this.getRows();r++) {
     		if(this.value(r,col) == value)
     			return false;
     	}
     	//---------------------------------------------------------------------------------------------

     	// checking square grouping -------------------------------------------------------------------
        for(int r = row/3 * 3;r < row/3 *3+3;r++) {
     		for(int c = col/3 * 3;c < col/3 *3+3;c++) {
                //System.out.println(r + " " + c + " " + this.value(r,c));
     			if(this.value(r,c) == value)
     				return false;
     		}
     	}
     	//----------------------------------------------------------------------------------------------

     	return true;
    }

    public boolean read(String filename) { // tjis reads a text file and places the numbers in the text file on to the board
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader reader = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader buffer = new BufferedReader(reader);

            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = buffer.readLine();

            int row = 0;
            int col = 0;
            // start a while loop that loops while line isn't null
            
            
            while(line != null) {
                col = 0;
                String[] lines = line.split("[ ]+");
                // System.out.println(row + " " + col);
                for(int i = 0;i < lines.length;i++) {
					int value = Integer.parseInt(lines[i]);
                    if(row < 9 && col < 9 && lines[i] != " ") {
                        if(value != 0)
							this.set(row,col,value,true);
						else if(value == 0)
							this.set(row,col,value);
						col++;
					}
					if(col == 9)
						row++;
				}
				
                line = buffer.readLine();
            }


            buffer.close();
            return true;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }

        return false;
    }
	
	public boolean validSolution() { // checks the whole to see if the grid as solution of to the Sudoku problem
		for(int row = 0;row < 9;row++) {
            for(int col = 0;col < 9;col++) {
                int value = this.board[row][col].getValue();
                this.set(row,col,0);
                // System.out.println("(" + row + "," + col + ") " + (boolean) (!this.validValue(row,col,value)));
                if(!this.validValue(row,col,value))
                    return false;
            }
        }
	
		return true;
	}

    public void draw(Graphics g, int scale) { // draws the board onto the JPanel; Major grid lines are shown
        int startX = 3;
        int startY = 3;
		g.drawLine(62,0,62,170);
		g.drawLine(125,0,125,170);
		g.drawLine(10,55,180,55);
		g.drawLine(10,115,180,115);
		for(int r = 0;r < 9;r++) {
			startX = 3;
			for(int c = 0;c < 9;c++) {
				this.board[r][c].draw(g,startX,startY,scale);
				startX += 3;
				if(c%3 == 2 && c != 0)
					startX += 3;
        }
		startY += 3;
		if(r% 3 == 2 && r != 0) 
			startY += 3;
		}
	}

	public static void main(String[] args) {
		Board b = new Board();
		b.read(args[0]);
        System.out.println(b);
		System.out.println(b.validSolution());
	}
}