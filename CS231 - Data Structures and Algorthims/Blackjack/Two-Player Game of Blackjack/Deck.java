/**
* File: Deck.java
* Author: Samuel Munoz
* Date: 09/10/2019
*/
import java.util.ArrayList;
import java.util.Random;

public class Deck {

	ArrayList<Card> deck;

	public Deck() {
		deck = new ArrayList<>();
		build();
	}

	public void build() {
		deck.clear();
		for(int cardValue = 2;cardValue <= 9;cardValue++) {
			for(int numDuplicates = 1;numDuplicates <= 4;numDuplicates++) {
				deck.add(new Card(cardValue));
			}
		}

		for(int numDuplicates = 1;numDuplicates <= 16;numDuplicates++) {
			deck.add(new Card(10));
		}

		for(int numDuplicates = 1;numDuplicates <= 4;numDuplicates++) {
			deck.add(new Card(11));
		}
	}
	public int size() {
		return deck.size();
	}

	public Card deal() {
		Card returnCard = deck.get(0);
		deck.remove(0);
		return returnCard;
	}

	public Card pick(int i) {
		Card returnCard = deck.get(i);
		deck.remove(i);
		return returnCard;
	}

	public void shuffle() {
		Random rand = new Random(/*(long) System.currentTimeMillis()*/);
		Card tempCard;
		int tempIndex;
		for(int i = deck.size()-1;i > 0;i--) {
			tempIndex = rand.nextInt(deck.size());
			tempCard = deck.get(tempIndex);
			deck.set(tempIndex,deck.get(i));
			deck.set(i,tempCard);
		}
	}

	public String toString() {
		String returnString = "[";
		for(int i = 0;i < deck.size();i++) {
			returnString += deck.get(i).getValue();
			if(i != deck.size()-1)
				returnString += ", ";
		}
		return returnString + "]";
	}

	public static void main(String[] args) {
		Deck d = new Deck();
		d.shuffle();
		System.out.println(d);
		// for(int i = 0;i < 10;i++) {
		// 	d.shuffle();
		// 	System.out.println(d);
		// }

		System.out.println("The size of the deck is " + d.size() + ".\nThe first card in the deck is " + d.deal().getValue() + ".\nThe 11th card in the deck is " + d.pick(11).getValue());
	}
}