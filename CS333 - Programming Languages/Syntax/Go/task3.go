/*
 * File: task3.go
 * Purpose: To show all the different data types in Go
 * Author: Samuel Munoz
 * Date: 03-03-2020
 */

package main
import (
	"fmt"
)

// interface ------------------------------
type s_interface interface {
	displayInfo()
}

// creates a function for the interface to refer to
func (s s) displayInfo() {
	fmt.Printf("(%s, %d)", s.str, s.id)
}

// main function that is uses input interface to get a result
func displayInfo(s s_interface) {
	s.displayInfo()
}
// end interface --------------------------

// structures
type s struct {
	str string
	id int
}

// functions
func add(a, b int) int {
	return a+b
}

// for channel
func populate(c chan int) {
	c <- 1
}

func main() {
	// Boolean type
	var boolean bool = true

	// Numberic Types

	// Unsigned 8-bit integers (0 to 255)
	var ui8 uint8 = 1
	// Unsigned 16-bit integers (0 to 65535)
	var ui16 uint16 = 2
	// Unsigned 32-bit integers (0 to 4294967295)
	var ui32 uint32 = 3
	// Unsigned 64-bit integers (0 to 18446744073709551615)
	var ui64 uint64 = 4
	// Signed 8-bit integers (-128 to 127)
	var i8 int8 = 5
	// Signed 16-bit integers (-32768 to 32767)
	var i16 int16 = 6
	// Signed 32-bit integers (-2147483648 to 2147483647)
	var i32 int32 = 7
	// Signed 64-bit integers (-9223372036854775808 to 9223372036854775807)
	var i64 int64 = 8
	// IEEE-754 32-bit floating-point numbers
	var f32 float32 = 9
	// IEEE-754 64-bit floating-point numbers
	var f64 float64 = 10
	// Complex numbers with float32 real and imaginary parts
	var c64 complex64 = 11
	// Complex numbers with float64 real and imaginary parts
	var c128 complex128 = 12
	// Unsigned 8-bit integers (0 to 255) (same as uint)
	var b byte = 13
	// Unsigned 32-bit integers (0 to 4294967295) (same uint32)
	var r rune = 14
	// 32 or 64 bits integer
	var ui uint = 15
	// integer type
	var i int = 16

	// string types
	var str string = "Hello!"
	// arrays (can use any types)
	var array [3]int
	// slice (can use any types)
	slice := array[1:]
	// generic pointer
	var i_ptr *int = &i
	// structure
	structure := s{
		str: "Name",
		id: 123456,
	}
	// map (this is a hash table)
	// keyword make: allocates memory
	m := make(map[string]int)
	m["1"] = 1
	m["2"] = 2
	// channel
	var c chan int = make(chan int)
	// need keyword 'go' to start routines needed for channels
	go populate(c)

	// an unsigned integer to store the uninterpreted bits of a pointer value
	// var uiptr uintptr -- not a safe type to use

	fmt.Printf("Numberic and Boolean Types\n")
	fmt.Printf("Boolean\t\t\tValue: %t\t!: %t\n", boolean, !boolean)
	fmt.Printf("Uint8\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", ui8, (ui8+1), (ui8-1), (ui8*2), (ui8/2), (ui8%2))
	fmt.Printf("Uint16\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", ui16, (ui16+1), (ui16-1), (ui16*2), (ui16/2), (ui16%2))
	fmt.Printf("Uint32\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", ui32, (ui32+1), (ui32-1), (ui32*2), (ui32/2), (ui32%2))
	fmt.Printf("Uint64\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", ui64, (ui64+1), (ui64-1), (ui64*2), (ui64/2), (ui64%2))
	fmt.Printf("Int8\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", i8, (i8+1), (i8-1), (i8*2), (i8/2), (i8%2))
	fmt.Printf("Int16\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", i16, (i16+1), (i16-1), (i16*2), (i16/2), (i16%2))
	fmt.Printf("Int32\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", i32, (i32+1), (i32-1), (i32*2), (i32/2), (i32%2))
	fmt.Printf("Int64\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", i64, (i64+1), (i64-1), (i64*2), (i64/2), (i64%2))
	fmt.Printf("Float32\t\t\tValue: %g\t+: %g\t-: %g\t*: %g\t/: %g\n", f32, (f32+1), (f32-1), (f32*2), (f32/2))
	fmt.Printf("Float64\t\t\tValue: %g\t+: %g\t-: %g\t*: %g\t/: %g\n", f64, (f64+1), (f64-1), (f64*2), (f64/2))	
	fmt.Printf("Complex64\t\tValue: %g\t+: %g\t-: %g\t*: %g\t/: %g\n", c64, (c64+1), (c64-1), (c64*2), (c64/2))
	fmt.Printf("Complex128\t\tValue: %g\t+: %g\t-: %g\t*: %g\t/: %g\n", c128, (c128+1), (c128-1), (c128*2), (c128/2))
	fmt.Printf("Byte\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", b, (b+1), (b-1), (b*2), (b/2), (b%2))
	fmt.Printf("Rune\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", r, (r+1), (r-1), (r*2), (r/2), (r%2))
	fmt.Printf("Uint\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", ui, (ui+1), (ui-1), (ui*2), (ui/2), (ui%2))
	fmt.Printf("Int\t\t\tValue: %d\t+: %d\t-: %d\t*: %d\t/: %d\t%%: %d\n", i, (i+1), (i-1), (i*2), (i/2), (i%2))
	fmt.Printf("\nAggregate Types\n")
	fmt.Printf("String\t\t\tValue: %s\n", str)
	fmt.Printf("Arrays\t\t\tValue: ")
	for i := 0; i < len(array); i++ {
		if i == len(array)-1 {
			fmt.Printf("%d\n", array[i])
		} else {
			fmt.Printf("%d, ", array[i])
		}
	}
	fmt.Printf("Slice\t\t\tValue: ")
	for i := 0; i < len(slice); i++ {
		if i == len(slice)-1 {
			fmt.Printf("%d\n", slice[i])
		} else {
			fmt.Printf("%d, ", slice[i])
		}
	}
	fmt.Printf("Pointer\t\t\tValue: %d\n", *i_ptr)
	fmt.Printf("Structure\t\tValue:")
 	displayInfo(structure)
	fmt.Printf("\nFunction\t\tValue: %d\n", add(i, i))
	fmt.Printf("Map\t\t\tValue: %d\n", m["1"])
	fmt.Printf("Channel\t\t\tValue: %d\n", <-c)
}

/*
Sources
https://www.tutorialspoint.com/go/go_interfaces.htm
https://www.tutorialspoint.com/go/go_maps.htm
https://golangbot.com/channels/
*/