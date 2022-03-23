/*
 * File: task1.go
 * Purpose: To show all the rules for naming identifers, variables declarations, and identifier scope
 * Author: Samuel Munoz 
 * Date: 03-01-2020
 */
package main
import (
	"fmt"
)

func displayText() {
	i := 3
	fmt.Printf("in function: %d\n", i)
}

func main() {
	i := 0
	fmt.Printf("in main: %d\n", i)
	for i := 1;i < 2;i++ {
		fmt.Printf("in for loop 1: %d\n", i)
		for i := 2;i < 3;i++ {
			fmt.Printf("in for loop 2: %d\n", i)
			displayText()
		}
	}


}

/*
Sources
https://www.geeksforgeeks.org/identifiers-in-go-language/
https://golangbot.com/variables/
https://www.geeksforgeeks.org/scope-of-variables-in-go/
*/