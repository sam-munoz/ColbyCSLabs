/*
 * File: task2.go
 * Purpose: To create a binary search algorithm in Go
 * Author: Samuel Munoz
 * Date: 02-27-2020
 */

package main

import (
	"fmt"
	"os"
	"strconv"
)

func binary_search(array []int, value int) {

	position, left, right := len(array)/2, 0, len(array)

	fmt.Printf("position: %d\tleft: %d\tright: %d\tvalue: %d\n", position, left, right, array[position])
	for array[position] != value {
		if position == left {
			fmt.Printf("Value not found.\n")
			break
		}

		if array[position] < value {
			left = position
		} else if array[position] > value {
			right = position
		}

		position = (right-left)/2 + left

		fmt.Printf("position: %d\tleft: %d\tright: %d\tvalue: %d\n", position, left, right, array[position])
	}

}

func main() {
	if len(os.Args) == 3 {
		size, _ := strconv.Atoi(os.Args[1])
		array := make([]int, size, size)

		for i := 0;i < size;i++ {
			array[i] = i
		}

		if size > 0 {
			if value, error1 := strconv.Atoi(os.Args[2]); error1 == nil {
				binary_search(array[:], value) 
			}
		}
	} else {
		fmt.Printf("You must enter the following two arguments: <length of array> <search value>")
	}
}

// https://golang.org/pkg/strconv/