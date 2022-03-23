/**
* File: AscendingString.java
* This file allows Strings to be compared and organize strings in some order
* Author: Samuel Munoz
* Course: CS231
* Date: 10-14-2019
*/
import java.util.Comparator;

public class AscendingString implements Comparator<String> {

	public AscendingString() {}

	public int compare(String str1, String str2) {
		// finds the smaller string --------------------------------------
		int length;
		if(str1.length() < str2.length())
			length = str1.length();
		else 
			length = str2.length();
		// ---------------------------------------------------------------


		// compares the two strings --------------------------------------
		for(int index = 0;index < length;index++) {
			if(str1.charAt(index) < str2.charAt(index))
				return -1;
			else if(str1.charAt(index) > str2.charAt(index))
				return 1;
		}
		if(str1.length() == str2.length())
			return 0;
		else if(str1.length() < str2.length())
			return -1;
		else
			return 1;
		// ---------------------------------------------------------------
	} 

	public static void main(String[] args) {
		if(args.length > 1) {
			String str1 = args[0];
			String str2 = args[1];
			AscendingString a = new AscendingString();
			System.out.println(a.compare(str1,str2));
		}
		else
			System.out.println("Needs more command line arguments");
	}
}