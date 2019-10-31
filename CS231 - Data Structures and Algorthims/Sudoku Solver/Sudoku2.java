/**
* File: Sudoku2.java
* Same Sudoku, but This Version Reads Text File As Input
* Author: Samuel Munoz
* Course: CS231: Data Structures and Algortims
* Semester: Fall Semester of 2019
* Date: 09/30/2019
*/

public class Sudoku2 {
	
	public static void main(String[] args) {
		Sudoku s = new Sudoku();
		if(args.length > 0)
	    	s.read( args[0] );
		boolean trash = s.solve(10);
	}
}