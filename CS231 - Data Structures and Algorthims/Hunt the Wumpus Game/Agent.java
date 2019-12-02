/**
* File: Agent.java
* Creates agents to control
* Author: Samuel Munoz
* Course: CS231
* Date: 11-27-2019 (Earliest update for this file)
*/
import java.awt.Graphics;

public class Agent {
	private int x;
	private int y;

	public Agent(int x0,int y0) {
		this.x = x0;
		this.y = y0;
	}
	
	// my getter methods for x and/or y values of the agent's position
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int[] getPair() { return new int[] {this.x, this.y}; }
	
	// my setter methods for x and/or y values of the agent's position
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setPair(int x, int y) { this.x = x; /* and */ this.y = y; }

	// my toString method
	public String toString() { return "(" + this.x + "," + this.y + ")"; }
	
	public void draw(Graphics g, int scale) {
		this.draw(g, scale);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Agent a = new Agent(1,1);
		
		System.out.println(a);
		
		a.setPair(2,5);
		
		System.out.println("(" + a.getX() + "," + a.getY() + ")\n");

		for(int i : a.getPair())
			System.out.println(i);
	}
}