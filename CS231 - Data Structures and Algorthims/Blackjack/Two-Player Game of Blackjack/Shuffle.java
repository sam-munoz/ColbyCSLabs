/**
* File: Shuffle.java
* Author: Samuel Munoz
* Date: 09/09/2019
*/

import java.util.ArrayList;
import java.util.Random;

public class Shuffle {
	public static ArrayList<Integer> Shuffle(ArrayList<Integer> list) {
		int tempIndex, tempValue;
		Random rand = new Random();
		for(int i = list.size()-1;i > 0;i--) {
			tempIndex = rand.nextInt(i);
			tempValue = list.get(tempIndex);
			list.set(tempIndex,list.get(i));
			list.set(i,tempValue);
		}
		return list;
	}
	
	public static void main(String[] args) {
		/*
		for(int j = 0;j < 10;j++) {
			ArrayList<Integer> list = new ArrayList<>();
			Random rand = new Random();
			for(int i = 0;i < 10;i++) {
				list.add(rand.nextInt(100));
			}
			int removeIndex = rand.nextInt(list.size());
			int removeValue = list.get(removeIndex);
			list.remove(removeIndex);
			System.out.println("ArrayList: " + list + "Index Removed: " + removeIndex + "\tValue Removed: " + removeValue);
		}
		*/
		
		ArrayList<Integer> newList = new ArrayList<>();
		for(int i = 1;i <= 10;i++) {
			newList.add(i);
		}
		System.out.println(newList);
		newList = Shuffle(newList);
		System.out.println(newList);
	}
}