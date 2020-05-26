/*
 * File: task3.go
 * Purpose: To create a bubble sort algorithm in Go
 * Author: Samuel Munoz
 * Date: 03-07-2020
 */

package main
import (
	"fmt"
	"os"
	"math/rand"
	"time"
	"strconv"
)

func bubbleSort(slice []int) {
	length := len(slice)
	for i := 0; i < length-1; i++ {
		for j := 0; j < length-i-1; j++ {
			if slice[j] < slice[j+1] {
				temp := slice[j]
				slice[j] = slice[j+1]
				slice[j+1] = temp
			}
		}
	}
}

func main() {
	if len(os.Args) > 1 {
		// seed the random number generator
		// inside New method, that whole thing is the seed. It is based on the time when the code runs
		random := rand.New(rand.NewSource(time.Now().UnixNano()))

		// creating a slice to store our data
		// I am choosing slice over array because a slice is more dyanmic in its sizing and it is more easier to pass a slice through a function
		length, _ := strconv.Atoi(os.Args[1])
		slice := make([]int, length)

		// populate the slice with random values
		for i, _ := range slice {
			slice[i] = random.Intn(10)
		}

		fmt.Print("Slice: ", slice, "\n")

		bubbleSort(slice)

		fmt.Print("Sorted Slice: ", slice, "\n")

	} else {
		fmt.Printf("command: ./task3 <length of slice>")
	}
}

/*
Sources
https://gobyexample.com/random-numbers
The Little Go Book
*/