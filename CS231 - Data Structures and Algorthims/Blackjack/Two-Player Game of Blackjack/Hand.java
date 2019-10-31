/**
* File: Hand.java
* Author: Samuel Munoz
* Date: 09/09/2019
*/

import java.util.ArrayList;
import java.util.Random; //only used to test/debug my code

public class Hand {
	private ArrayList<Card> hand;
	
	public Hand() {
		hand = new ArrayList<>();
	}

	public void reset() {
		hand.clear();
	}

	public void add(Card c) {
		hand.add(c);
	}

	public int size() {
		return hand.size();
	}

	public Card getCard(int index) {
		return hand.get(index);
	}

	public int getTotalValue() {
		int sum = 0;
		for(Card c : hand) {
			sum += c.getValue();
		}
		return sum;
	}

	public String toString() {
		String returnString = "[";
		for(int index = 0;index < hand.size();index++) {
			returnString += hand.get(index).getValue();
			if(index != hand.size()-1) {
				returnString += ", ";
			}
		}
		return returnString + "]";
	}

	public static void main(String[] args) { //testing/debugging the class
		Hand hand = new Hand();
		Random rand = new Random(); // only here to create random int values
		for(int i = 0;i < 5;i++) {
			hand.add(new Card(rand.nextInt(10)+2));
		}
		System.out.println(hand);
		System.out.println("Size of hand: " + hand.size() + "     First Card: " + hand.getCard(0).getValue() + "     Total Value: " + hand.getTotalValue());
		hand.reset();
		System.out.println(hand);
	}
}