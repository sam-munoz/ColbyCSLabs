/*
 * File: gointllist.go
 * Purpose: To implement a linked list in Go
 * Author: Samuel Munoz
 * Date: 03-24-2020
 */

package llist
import (
	"strconv"
)

// interface to use supercede the structs used for the linked list
type llinterface interface {
	CreateLList(data interface{})
	Push()
}

// classes do not exist in Go, so Go uses structs to emulate classes
// struct for a Node used to store elements
type Node struct {
	data int64
	next *Node
	prev *Node
}

// struct for the listed list
type LList struct {
	s int
	head *Node
	tail *Node
}

// functions like a constructor for a Node; creates a node with node storing data argument
func CreateNode(data int64) *Node {
	return &Node {
		data: data,
		next: nil,
		prev: nil,
	}
}

// function that acts like a constructor to create an empty linked list
func CreateLList() *LList {
	return &LList {
		s: 0,
		head: nil,
		tail: nil,
	}
}

// method for linked list: adds a new element at the front of the list
func (list *LList) Push(data int64) {
	new_node := CreateNode(data)

	// if the list is empty
	if(list.s == 0) {
		list.head = new_node
		list.tail = new_node
	} else {
	// if the list is not empty
		list.head.prev = new_node
		new_node.next = list.head
		list.head = new_node
	}
	list.s++
}

// method for linked list: adds a new element at the end of the list
func (list *LList) Append(data int64) {
	new_node := CreateNode(data)

	// if the list is empty
	if(list.s == 0) {
		list.head = new_node
		list.tail = new_node
	} else {
	// if the list is not empty
		list.tail.next = new_node
		new_node.prev = list.tail
		list.tail = new_node
	}
	list.s++
}

// method for linked list: removes the element at the front of the list
func (list *LList) Pop() int64 {
	// if the list is not empty
	if(list.s > 0) {
		data := list.head.data
		list.head = list.head.next
		list.s--
		return data
	}

	// if the list is empty
	return 0
}

// method for linked list: remove the first element that matches the given function
func (list *LList) Remove(target int64, compfunc func(int64, int64) bool) int64 {
	cursor := list.head

	// traverses through the list
	for cursor != nil {
		b := compfunc(cursor.data, target)

		if b {
		// if the function returns true
			node_data := cursor.data
			cursor.prev.next = cursor.next
			cursor.next.prev = cursor.prev
			list.s--
			return node_data
		}

		// if the function returns false
		cursor = cursor.next
	}

	// if there are no matches in the list
	return 0
}


// function linked with the linked list: returns the length of the list
// NOTE: this method is somewhat unnecessary since list can return field size
func (list *LList) Size() int {
	return list.s
}

// method for linked list: clears all Nodes from the list
func (list *LList) Clear() {
	list.s = 0
	list.head = nil
	list.tail = nil
	// Go has a garbage collecting so the Nodes will be taken care by that
}

// method for linked list: maps all values in Node to whatever the function pointer outputs
func (list *LList) Map(funcmap func(int64) int64) {
	cursor := list.head

	// travserses through the list
	for cursor != nil {
		cursor.data = funcmap(cursor.data)
		cursor = cursor.next
	}
}

// function links with the linked list struct: prints all nodes inside the list
func (list *LList) Print() string {
	returnString := ""
	cursor := list.head

	// if the list is empty
	if(list.s == 0) {
		returnString += "Empty"
	}

	// this traverses through the list and appends each node value onto the return string
	for cursor != nil {
		data_str := strconv.FormatInt(cursor.data, 10)
		returnString += data_str + ", "
		cursor = cursor.next
	}

	return returnString
}