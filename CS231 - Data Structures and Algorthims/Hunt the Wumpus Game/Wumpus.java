/**
* File: Hunter.java
* Object that represents a wumpus in the game
* Author: Samuel Munoz
* Course: CS231
* Date: 11-28-2019
*/
import java.awt.Color;
import java.awt.Graphics;

public class Wumpus extends Agent {
	// Vertex to hold the Hunter's current position
	private Vertex location;
	// boolean to store if the Wumpus is alive or dead
	private boolean isAlive;
	// boolean to store if the Wumpus has been killed by the hunter
	private boolean killed;
	
	// constructor: creates a hunter with a given location
	public Wumpus(Vertex v) {
		super(v.getX(), v.getY());
		this.location = v;
		this.isAlive = true;
		this.killed = false;
	}
	
	// returns the Vertex location of the wumpus
	public Vertex location() {
		return this.location;
	}
	
	// overrides the draw method in the Agent class
	public void draw(Graphics g, int scale) {
		int x = this.getX();
		int y = this.getY();
		if(!this.isAlive) {
			g.setColor(Color.RED);
			g.fillOval(x*scale + (int) scale/4 - 2, y*scale + (int) scale/4 - 2, (int) scale/2, (int) scale/2);
		}
		if(this.killed) {
			g.setColor(Color.RED);
			g.drawLine(x*scale, y*scale,x*scale + scale - 5,y*scale + scale - 5);
			g.drawLine(x*scale + scale - 5,y*scale, x*scale, y*scale + scale -5);
		}
	}
	
	// this method returns the life state (is alive (true) or dead (false)) of the wumpus
	public boolean lifeState() {
		return !this.killed;
	}
	
	// this method is called to make the wumpus dead
	public void killed() {
		this.killed = true;
		this.isAlive = false;
	}
	
	// this method makes the wumpus visible on the LandscapePanel
	// this is a method used at the end of a game
	public void makeVisible() {
		this.isAlive = false;
	}	
}