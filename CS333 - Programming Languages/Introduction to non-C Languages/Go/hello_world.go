/*
* File: hello_world.go
* Purpose: To create a hello world program and print off all command line arguments
* Author: Samuel Munoz
* Date: 02/25/2020
*/

// the keyword 'package' is needed to guide the go compiler to the starting point of the code which is the main function
package main

// the keyword 'import' is used to import all the packages into this application
import (
	"fmt"
	"os"
)

// this is the main method for the program and runs during runtime. All go files need a main method
func main() {
	// fmt.Println is the command used to print data types
	fmt.Println("Hello World!")
	fmt.Println("Command Line Arguments:")

	// for this for loop, there are many interesting parts to this code
	// := is an assignment type in Go that will create variables without having to tell Go what type a variable is. 
	// Only applies to creating new variables, not existing variables
	for i := 1; i < len(os.Args); i++ {
		// fmt.Printf is another way to print data types onto the console. Go is a C-like language, so it is not surprising to see this command
		fmt.Printf("%d: %s\n", i, os.Args[i])
	}
}