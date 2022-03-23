/*
 * File: task1.go
 * Purpose: To create a program that reads a file and store the word frequency of each word in the file
 * Author: Samuel Munoz
 * Date: 04-14-2020
 */
package main
import (
	"fmt"
	"strings"
	"io/ioutil"
	"os"
)

type KeyValuePair struct {
	key string
	value int
}

func updateMap(m map[string]int, key string) {
	// if the state (represents if key given exists in the map) is true, then key exist and the value needs updating
	// otherwise, create new key with value one
	if _, state := m[key]; state {
		m[key] += 1
	} else {
		m[key] = 1
	}
}

func splitString(str string) []string {
	// splits string into the different lines
	a1 := strings.Split(str, "\n")
	a2 := make([]string, 0)
	a3 := make([]string, 0)
	a4 := make([]string, 0)
	for _, element := range a1 {
		temp := strings.Split(element, ". ")
		for _, s := range temp {
			a2 = append(a2, s)
		}
	}
	for _, element := range a2 {
		temp := strings.Split(element, ", ")
		for _, s := range temp {
			a3 = append(a3, s)
		}
	}
	for _, element := range a3 {
		temp := strings.Split(element, " ")
		for _, s := range temp {
			a4 = append(a4, s)
		}
	}
	return a4
}

func bubbleSort(array []*KeyValuePair) {
	length := len(array)
	for i := 0; i < length; i++ {
		for j := 0; j < length - i - 1; j++ {
			if array[j].value < array[j+1].value {
				temp := array[j]
				array[j] = array[j+1]
				array[j+1] = temp
			}
		}
	}
}

func main() {
	// stops the program if an argument is missing
	if(len(os.Args) < 2) {
		fmt.Printf("command: ./task1 <filename>\n")
		return
	}
	
	data, err := ioutil.ReadFile(os.Args[1]);
	// checks if there was an error in reading the file
	if err != nil {
		fmt.Printf("The program is unable to read the file.\n")
		return
	}

	// this map will hold all the 
	m := make(map[string]int)

	a := splitString(string(data))
	for _, element := range a {
		// update map with new keys
		updateMap(m, strings.ToLower(element))
	}

	array := make([]*KeyValuePair, 0)
	for key, value := range m {
		array = append(array, &KeyValuePair {
			key: key,
			value: value,
		})
	}

	bubbleSort(array)

	for i := 0; i < 20; i++ {
		fmt.Printf("%s\t\t\t%d\n", array[i].key, array[i].value)
	}
}

/*
Sources
https://blog.golang.org/maps
https://golang.org/pkg/strings/#Split
https://yourbasic.org/golang/convert-string-to-byte-slice/
https://tour.golang.org/moretypes/6
https://stackoverflow.com/questions/2050391/how-to-check-if-a-map-contains-a-key-in-go
https://gobyexample.com/reading-files
*/