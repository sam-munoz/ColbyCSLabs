/*
 * File: testllist.go
 * Purpose: To test the two linked list created
 * Author: Samuel Munoz
 * Date: 03-24-2020
 */

package main
import (
	"fmt"
	"./llist"
)

func compfunc(a, b int64) bool { return a == b }
func mapfunc(i int64) int64 { return i * i }

func main() {
	l := llist.CreateLList()
	var c func(int64, int64) bool = compfunc
	var m func(int64) int64 = mapfunc

	fmt.Printf("Empty List\n")
	fmt.Printf("Linked List: %s\n", l.Print())
	fmt.Printf("Size: %d\n", l.Size())
	fmt.Printf("Pop: %d\n", l.Pop())
	fmt.Printf("Removed: %d\n", l.Remove(8, c))

	var i int64 = 0
	for i < 5 {
		l.Append(i+1)
		i++
	}
	for i < 10 {
		l.Push(i+1)
		i++
	}

	fmt.Printf("\nNon-Empty List\n")
	fmt.Printf("Linked List: %s\n", l.Print())
	fmt.Printf("Size: %d\n", l.Size())
	fmt.Printf("Pop: %d\n", l.Pop())
	fmt.Printf("Removed: %d\n", l.Remove(8, c))

	l.Map(m)

	fmt.Printf("\nMap List\n")
	fmt.Printf("Linked List: %s\n", l.Print())
	fmt.Printf("Size: %d\n", l.Size())
	fmt.Printf("Pop: %d\n", l.Pop())
	fmt.Printf("Removed: %d\n", l.Remove(8, c))

	l.Clear()

	fmt.Printf("\nClear List\n")
	fmt.Printf("Linked List: %s\n", l.Print())
	fmt.Printf("Size: %d\n", l.Size())
	fmt.Printf("Pop: %d\n", l.Pop())
	fmt.Printf("Removed: %d\n", l.Remove(8, c))
}

/*
Sources
The Little Go Book
https://appliedgo.net/generics/
https://golang.org/doc/code.html
https://stackoverflow.com/questions/35480623/how-to-import-local-packages-in-go
*/