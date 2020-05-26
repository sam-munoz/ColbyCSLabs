/*
 * File: task1.go
 * Purpose: To show all the control flow in Go
 * Author: Samuel Munoz
 * Date: 03-08-2020
 */
package main
import ( "fmt" )

func main() {
	integer := 2;
	array := [5]int{1,2,3,4,5}

	fmt.Printf("---Selection Statements: If-Else Blocks---\n\n")
	// if block runs if conditional is true 
	if integer < 4 {
		fmt.Printf("(1) Ran If-Block\n")
	}

	// if block runs if conditional is true; this ones creates variable with declaration scope
	if n := 10; n < 20 {
		fmt.Printf("(2) Ran If-Block\n")
	}

	// depending on whether the conditional inside the if is true, the conditional will decide whether run if block or else block
	if integer > 20 {
		fmt.Printf("(3) Ran If-Block\n")
	} else {
		fmt.Printf("(3) Ran Else-Block\n")
	}

	// with a if-else statement, if the first condiitonal is false and the first if-else condition is true, then the if-else block runs 
	if integer > 20 {
		fmt.Printf("(4) Ran If-Block\n")
	} else if integer < 10 {
		fmt.Printf("(4) Ran If-Else Block\n")
	} else {
		fmt.Printf("(4) Ran Else-Block\n")
	}
	fmt.Printf("-------------------------------------------\n\n")


	fmt.Printf("------------Iteration Statements-----------\n")
	// for loop: loops a block of code and allows declaration variables to be created
	fmt.Printf("\nFor Loops: ")
	for i := 0; i < 10; i++ {
		fmt.Printf("%d ", i)
	}

	// for loop: loops a block of code and this loop has no init and post statements
	fmt.Printf("\nFor Loop; No Init and Post Statements: ")
	for ; integer < 4; {
		fmt.Printf("%d ", integer)
		integer++
	} 

	// for loop: same as before just with no semicolon
	fmt.Printf("\nGo's While Loop: ")
	for integer < 8 {
		fmt.Printf("%d ", integer)
		integer++
	}

	// range-based for loop: this is a for loop where the loops through an iterative data type
	fmt.Printf("\nRange-Based For Loop: ")
	for _, element := range array { // range returns both an index and an element, but I don't use the index, so I must replace with the black identifier to let my code run
		fmt.Printf("%d ", element)
	}
	fmt.Printf("\n-------------------------------------------\n\n")

	fmt.Printf("----------------Jump Statements------------\n\n")
	// this statements jump the control flow from one section of some code to another
	fmt.Printf("Break: ")
	for i := 0; i < 10; i++ {
		if i == 5 {
			fmt.Printf("Break Occured!\n")
			break
		}
		fmt.Printf("%d ", i)
	}
	
	fmt.Printf("Continue: ")
	for i := 3; i < 7; i++ {
		if i <= 4 {
			fmt.Printf("Skipped! ")
			continue
		}
		fmt.Printf("%d ", i)
	}

	fmt.Printf("\nGoto: ")
	integer = 0
	label:
		fmt.Printf("%d ", integer)
		integer++
		if integer < 9 {
			goto label
		}
	
	fmt.Printf("\n") 	
	fmt.Printf("-------------------------------------------\n")

	fmt.Printf("---------Other Types of Statements---------\n\n")
	// note that switch evaulutes each case top bottom meaning that if there is invalid case that is bellow the used case, the invalid case is not checked
	fmt.Printf("Swtiches: ")
	switch i := 4; i {
	case 1:
		fmt.Printf("Found case 1\n")
	case 2:
		fmt.Printf("Found case 2\n")
	case 3: 
		fmt.Printf("Found case 3\n")
	case 4:
		fmt.Printf("Found case 4\n")
	default:
		fmt.Printf("Found default\n")	 
	}

	fmt.Printf("Swtiches; No conditional: ")
	switch {
	case 2 == 4:
		fmt.Printf("Found case 1\n")
	case false:
		fmt.Printf("Found case 2\n")
	default:
		fmt.Printf("Found default\n")	 
	}	
	fmt.Printf("-------------------------------------------\n")
}


/*
Sources
https://tour.golang.org/flowcontrol
https://stackoverflow.com/questions/7782411/is-there-a-foreach-loop-in-go
*/