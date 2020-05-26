/*
 * File: task1.go
 * Purpose: To show the explict memory management in Go
 * Author: Samuel Munoz
 * Date: 04-29-2020
 */
package main
import (
	"fmt"
)

type NewStruct struct {
	tag string
	age int
}

func main() {
	// first way to allocate memory: new
	// creates memory for a struct, but fields are intialized to zero values
	a := new(NewStruct)

	fmt.Printf("Tag: %s\nAge: %d\n", a.tag, a.age)

	// second way to allocate memory: make
	// only used to create memory for slices, maps, and channels
	// create memory for data types, but are intialized to non-zero values
	v := make([]string, 10) // slice that points to a string array

	for index, value := range v {
		fmt.Printf("%d: %s\n", index, value)
	}

	// memory deallocation is handled by the Go compiler/interrupter
}

/*
Sources
https://baihuqian.github.io/2018-02-24-golang-memory-allocation/
*/