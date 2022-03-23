/**
* File: Blackjack
* Author: Samuel Munoz
* Date: 09/11/2019
*/
import java.util.ArrayList;

public class Blackjack {
	
	private Deck deck;
	private Hand dealerHand;
	private ArrayList<Hand> allPlayers;
	
	public Blackjack(int numOfPlayers) {
		deck = new Deck();
		deck.shuffle();
		dealerHand = new Hand();
		allPlayers = new ArrayList<>();
		for(int i = 0;i < numOfPlayers;i++) {
			allPlayers.add(new Hand());
		}
	}
	
	public void reset(boolean newDeck) {
		dealerHand.reset();
		for(Hand playerHand : allPlayers)
			playerHand.reset();
		
		if(newDeck) {
			deck.build();
			deck.shuffle();
		}
	}
	
	public void deal() {
		if(deck.size() == 0) {
			deck.build();	
			deck.shuffle();
		}
		dealerHand.add(deck.deal());
		for(Hand playerHand : allPlayers) {
			if(deck.size() == 0) {
				deck.build();
				deck.shuffle();
			}
			playerHand.add(deck.deal());
		}
	}
	
	public String toString() {
		String returnString = "Dealer's Hand:\t\t\t[";
		for(int i = 0;i < dealerHand.size();i++) {
			returnString += dealerHand.getCard(i).getValue();
			if(i != dealerHand.size()-1) {
				returnString += ", ";
			}
		}

		for(int i = 0;i < allPlayers.size();i++) {
			returnString += "]\nPlayer " + (i+1) + "'s Hand:\t\t[";
			for(int j = 0;j < allPlayers.get(i).size();j++) {
				returnString += allPlayers.get(i).getCard(j).getValue();
				if(j != allPlayers.get(i).size()-1) {
					returnString += ", ";
				}
			}
		}

		return returnString + "]";
	}

	public boolean playerTurn(int index) {
		int totalPoints = 0;
		for(int i = 0;i < allPlayers.get(index).size();i++) 
			totalPoints += allPlayers.get(index).getCard(i).getValue();
		Card drawnCard;
		while(totalPoints <= 16) {
			if(deck.size() == 0) {
				deck.build();
				deck.shuffle();
			}
			drawnCard = deck.deal();
			allPlayers.get(index).add(drawnCard);
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
			if(deck.size() == 0) {
				deck.build();
				deck.shuffle();
			}
			drawnCard = deck.deal();
			dealerHand.add(drawnCard);
			totalPoints += drawnCard.getValue();
		}
		return (boolean) (totalPoints <= 21);
	}

	public int game(boolean verbose) {
		boolean dealerIn;
		int returnStatus;
		ArrayList<Integer> allScores = new ArrayList<>();
		ArrayList<Boolean> allPlayersIn = new ArrayList<>();
		for(int i = 0;i < allPlayers.size();i++)
			allPlayersIn.add(new Boolean(false));

		deal(); deal();

		if(verbose)
			System.out.println("STARTING HAND: \n" + toString());
		
		for(int i = 0;i < allPlayers.size();i++)
			allPlayersIn.set(i,playerTurn(i));
		dealerIn = dealerTurn();

		ArrayList<Hand> notBusted = new ArrayList<>();
		ArrayList<Integer> indexOfNotBusted = new ArrayList<>();
		for(int i = 0;i < allPlayers.size();i++) {
			if(allPlayersIn.get(i) == true) {
				notBusted.add(allPlayers.get(i));
				indexOfNotBusted.add(i);
			}
		}
		if(dealerIn) {
			notBusted.add(dealerHand);
			indexOfNotBusted.add(-1);
		}
		
		if(notBusted.size() == 0) {
			if(verbose) {
				System.out.println("\nFINAL RESULTS:");
				System.out.println(toString());
				System.out.println("Everyone busted.");
			}
			return 0;
		}
			
		
		for(int i = 0;i < notBusted.size();i++) {
			allScores.add(new Integer(0));
			for(int j = 0;j < notBusted.get(i).size();j++)
				allScores.set(i,allScores.get(i).intValue() + notBusted.get(i).getCard(j).getValue());
		}
		
		// for(int i = 0;i < allScores.size();i++)
			// System.out.print((indexOfNotBusted.get(i).intValue() + 1) + ": " + allScores.get(i).intValue() + "\t");
		
		int tempMax = allScores.get(0);
		ArrayList<Integer> allMaxIndex = new ArrayList<>();
		allMaxIndex.add(indexOfNotBusted.get(0).intValue());
		for(int i = 1;i < allScores.size();i++) {
			if(allScores.get(i).intValue() > tempMax) {
				tempMax = allScores.get(i).intValue();
				allMaxIndex.clear();
				allMaxIndex.add(indexOfNotBusted.get(i));
			}
			else if(allScores.get(i).intValue() == tempMax)
				allMaxIndex.add(indexOfNotBusted.get(i));
		}
		
		//System.out.println("\ntempValue = " + tempMax);
		
		if(allMaxIndex.size() == 1)
			if(allMaxIndex.get(0).intValue() != -1)
				returnStatus = allMaxIndex.get(0).intValue() + 1;
			else
				returnStatus = -1;
		else {
			returnStatus = 0;
		}
		
		
		if(returnStatus == -1) {
			if(verbose) {
				System.out.println("\nFINAL RESULTS:");
				System.out.println(toString());
				System.out.println("The dealer won.");
			}
			return -1;
		}
		
		else if(returnStatus == 0) {
			if(verbose) {
				System.out.println("\nFINAL RESULTS:");
				System.out.println(toString());
				for(int i = 0;i < allMaxIndex.size();i++) {
					if(allMaxIndex.get(i).intValue() != -1)
						System.out.print("Player " + (allMaxIndex.get(i).intValue() + 1) + " ");
					else
						System.out.print("the dealer ");
					if(i != allMaxIndex.size() - 1)
						System.out.print("and ");
				}
				System.out.println("drew.");
			}
			return 0;
		}

		else {
			if(verbose) {
				System.out.println("\nFINAL RESULTS:");
				System.out.println(toString());
				System.out.println("Player " + returnStatus + " won.");
			}
			return returnStatus;
		}
	}

	public static void main(String[] args) {
		Blackjack bj = new Blackjack(Integer.parseInt(args[0]));

		int singleGameWinner;
		int numGames = Integer.parseInt(args[1]);
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