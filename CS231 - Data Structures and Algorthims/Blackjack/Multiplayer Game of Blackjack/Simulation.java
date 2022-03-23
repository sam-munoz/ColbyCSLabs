/**
* File: Simulation.java
* Author: Samuel Munoz
* Date: 09/11/2019
*/
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Simulation {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("#.##");

		int[] playerWinTally = new int[Integer.parseInt(args[0])];
		int dealerWon = 0;
		int draws = 0;
		Blackjack bj = new Blackjack(playerWinTally.length);

		int singleGameWinner;
		int numOfbj = Integer.parseInt(args[1]);
		for(int i = 0;i < numOfbj;i++) {
			bj.reset(true);
			singleGameWinner = bj.game(false);
			if(singleGameWinner == 0)
				draws++;
			else if(singleGameWinner == -1)
				dealerWon++;
			else
				playerWinTally[singleGameWinner-1] += 1;
		}

		System.out.print("\t\t");
		for(int i = 1;i <= playerWinTally.length;i++)
			System.out.print("Player " + i + "\t");
		System.out.println("Dealer\t\tDraw");
		
		System.out.print("Frequency:\t");
		for(int playerWon : playerWinTally)
			System.out.print(playerWon + "\t\t");
		System.out.println(dealerWon + "\t\t" + draws);
		System.out.print("Precentage:\t");
		for(int playerWon : playerWinTally)
			System.out.print(df.format(((double) playerWon/numOfbj) * 100) + "%\t\t");
		System.out.println(df.format(((double) dealerWon/numOfbj) * 100) + "%\t\t" + df.format(((double) draws/numOfbj) * 100) + "%");
	}
}