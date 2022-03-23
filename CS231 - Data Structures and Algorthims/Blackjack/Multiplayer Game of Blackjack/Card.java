/**
* File: Card.java
* Author: Samuel Munoz
* Date: 09/09/2019
*/

public class Card {
	private int value;

	public Card(int v) {
		if(v >= 2 && v <= 11) {
			value  =v;
		}
		else {
			System.out.println("Hey, you cannot have a card with a value that is smaller than 2 or greater than 11!");
		}
	}

	public int getValue() { return value; }

	public static void main(String[] args) {
		Card card1 = new Card(3);
		Card card2 = new Card(14);
		System.out.println(card1.getValue());
		System.out.println(card2.getValue());
	}
}