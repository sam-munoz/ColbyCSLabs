/**
* File: Simulation.java
* Author: Samuel Munoz
* Date: 09/11/2019
*/
import java.text.DecimalFormat;


public class Simulation {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("#.##");

		int playerWon = 0;
		int dealerWon = 0;
		int draws = 0;
		Blackjack game = new Blackjack();

		int singleGameWinner;
		int numOfGame = Integer.parseInt(args[0]);
		for(int i = 0;i < numOfGame;i++) {
			game.reset((boolean) (i != numOfGame-1));
			singleGameWinner = game.game(false);
			if(singleGameWinner == 1)
				playerWon++;
			else if(singleGameWinner == -1)
				dealerWon++;
			else
				draws++;
		}

		System.out.println("\t\tPlayer\tDealer\tDraw");
		System.out.println("Frequency:\t" + playerWon + "\t" + dealerWon + "\t" + draws);
		System.out.println("Precentage:\t" + df.format(((double) playerWon/numOfGame) * 100) + "%\t" + df.format(((double) dealerWon/numOfGame) * 100) + "%\t" + df.format(((double) draws/numOfGame) * 100) + "%");
	}
}