/**
* File: Hunter.java
* Object that represents a hunter in the game
* Author: Samuel Munoz
* Course: CS231
* Date: 11-27-2019
*/
import java.awt.Color;
import java.awt.Graphics;

public class Hunter extends Agent {
	// Vertex to hold the Hunter's current position
	private Vertex location;
	// boolean that sets if the hunter is firing an arrow or if the hunter is moving
	private boolean fire;
	
	// constructor: creates a hunter with a given location
	public Hunter(Vertex v) {
		super(v.getX(), v.getY());
		this.location = v;
		this.visible();
		this.fire = false;
	}
	
	// takes the agent stored in the Vertex and makes that agent visible on the LandscapePanel
	public void visible() {
		this.location.setVisible(true);
	}
	
	// returns the fire field
	public boolean fire() {
		return this.fire;
	}
	
	// sets the fire field to the boolean inside the argument of this method
	public void setFire(boolean b) {
		this.fire = b;
	}
	
	// returns the vertex the hunter is at
	public Vertex location() {
		return this.location;
	}

	// changes the Vertex which the Hunter is on
	public void move(Vertex v) {
		this.location = v;
		this.visible();
	} 
	
	// overrides the draw method in the Agent class
	public void draw(Graphics g, int scale) {
		int x = this.getX();
		int y = this.getY();
		g.setColor(Color.BLUE);
		g.fillOval(x*scale + (int) scale/4 - 2, y*scale + (int) scale/4 - 2, (int) scale/2, (int) scale/2);
	}
}