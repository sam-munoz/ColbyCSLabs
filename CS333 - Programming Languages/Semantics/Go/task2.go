/*
 * File: task2.go
 * Purpose: To show function variables works in Go
 * Author: Samuel Munoz
 * Date: 03-07-2020
 */

package main
import ( 
	"fmt"
	"os"
	"strconv"
)

func add(a, b int) int { return a+b }
func subtract(a, b, c int, calc func(int, int) int) int { return a-calc(b,c) }

func main() {
	if len(os.Args) < 3 {
		fmt.Printf("command: ./task2 <integer> <integer> <integer>")
	} else {
		var calc func(int, int) int
		calc = add
		a, _ := strconv.Atoi(os.Args[1])
		b, _ := strconv.Atoi(os.Args[2])
		c, _ := strconv.Atoi(os.Args[3])
		fmt.Printf("Sum: %d\n", calc(a, b))
		fmt.Printf("Difference: %d\n", subtract(a, b, c, calc))
	}
}