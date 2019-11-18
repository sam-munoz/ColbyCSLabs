/**
* File: Priority.java
* Creates compartor that orders things by priority
* Author: Samuel Munoz
* Course: CS231
* Date: 11-11-2019
*/
import java.util.Comparator;

public class PriorityStringIntegerPair implements Comparator<KeyValuePair<String,Integer>> {
	// this method will only compare two object in numberical order if T is a number or alphabetical order if T is some text
	public int compare(KeyValuePair<String,Integer> obj1, KeyValuePair<String,Integer> obj2) {
		int c = obj1.getValue().compareTo(obj2.getValue());
		
		if(c == 0)
			return 0;
		else if(c > 0)
			return 1;
		else
			return -1;
	}

	public static void main(String[] args) {
		PriorityStringIntegerPair p = new PriorityStringIntegerPair();

		if(args.length >= 0) {
			KeyValuePair<String,Integer> a = new KeyValuePair<String,Integer>("a",23);
			KeyValuePair<String,Integer> b = new KeyValuePair<String,Integer>("b",12);
			
			System.out.println(a.getValue() + " " + b.getValue());
			
			System.out.println(p.compare(a,b));
		}
		else
			System.out.println("You need two arguments.");
	}
}