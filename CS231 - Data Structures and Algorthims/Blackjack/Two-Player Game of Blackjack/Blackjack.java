/**
* File: Blackjack
* Author: Samuel Munoz
* Date: 09/11/2019
*/

public class Blackjack {
	
	private Deck deck;
	private Hand dealerHand;
	private Hand playerHand;
	
	public Blackjack() {
		deck = new Deck();
		deck.shuffle();
		dealerHand = new Hand();
		playerHand = new Hand();
	}
	
	public void reset(boolean newDeck) {
		dealerHand.reset();
		playerHand.reset();
		
		if(newDeck) {
			deck.build();
			deck.shuffle();
		}
	}
	
	public void deal() {
		dealerHand.add(deck.deal());
		playerHand.add(deck.deal());
	}
	
	public String toString() {
		String returnString = "Dealer's Hand: [";
		for(int i = 0;i < dealerHand.size();i++) {
			returnString += dealerHand.getCard(i).getValue();
			if(i != dealerHand.size()-1) {
				returnString += ", ";
			}
		}
		
		returnString += "]\nPlayer's Hand: [";
		
		for(int i = 0;i < playerHand.size();i++) {
			returnString += playerHand.getCard(i).getValue();
			if(i != playerHand.size()-1) {
				returnString += ", ";
			}
		}

		return returnString + "]";
	}

	public boolean playerTurn() {
		int totalPoints = 0;
		for(int i = 0;i < playerHand.size();i++) 
			totalPoints += playerHand.getCard(i).getValue();
		Card drawnCard;
		while(totalPoints <= 16) {
			drawnCard = deck.deal();
			playerHand.add(drawnCard);
			totalPoints += drawnCard.getValue();
		}
		return (boolean) (totalPoints <= 21);
	}

	public boolean dealerTurn() {
		int totalPoints = 0;
		for(int i = 0;i < dealerHand.size();i++) 
			totalPoints += dealerHand.getCard(i).getValue();
		Card drawnCard;
		while(totalPoints <= 17) {
			drawnCard = deck.deal();
			dealerHand.add(drawnCard);
			totalPoints += drawnCard.getValue();
		}
		return (boolean) (totalPoints <= 21);
	}

	public int game(boolean verbose) {
		int playerScore = 0;
		int dealerScore = 0;
		boolean playerIn, dealerIn;
		int returnStatus;

		deal(); deal();

		if(verbose)
			System.out.println(toString());
		
		playerIn = playerTurn();
		dealerIn = dealerTurn();

		for(int i = 0;i < playerHand.size();i++) {
			playerScore += playerHand.getCard(i).getValue();
		}

		for(int i = 0;i < dealerHand.size();i++) {
			dealerScore += dealerHand.getCard(i).getValue();
		}

		if(playerIn || dealerIn) {
			if(playerIn && dealerIn) {
				if(playerScore > dealerScore)
					returnStatus = 1;
				else if(playerScore < dealerScore)
					returnStatus = -1;
				else
					returnStatus = 0;
			}
			else if(playerIn)
				returnStatus = 1;
			else
				returnStatus = -1;
		}
		else
			returnStatus = 0;
			
		
		if(returnStatus == -1) {
			if(verbose) {
				System.out.println(toString());
				System.out.println("The dealer won.");
			}
			return -1;
		}

		else if(returnStatus == 1) {
			if(verbose) {
				System.out.println(toString());
				System.out.println("The player won.");
			}
			return 1;
		}

		else {
			if(verbose) {
				System.out.println(toString());
				System.out.println("The game ended in a draw.");
			}
			return 0;
		}
	}
	
	public String getDeck() {
		return deck.toString();
	}

	public static void main(String[] args) {
		Blackjack bj = new Blackjack();

		int singleGameWinner;
		int numGames = Integer.parseInt(args[0]);
		for(int i = 0;i < numGames;i++) {
			bj.reset(true);
		 	singleGameWinner = bj.game(true);
		 	System.out.println("------------------------------------------------------------------");
		}
		
		// int run = Integer.parseInt(args[0]);
		// for(int i = 0;i < run;i++) {
			// System.out.println(bj.getDeck());
			// bj.deal(); bj.deal();
			// System.out.println(bj);
			// bj.reset(true);
		// }
	}
}