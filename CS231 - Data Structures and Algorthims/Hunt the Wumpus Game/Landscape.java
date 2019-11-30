/**
* File: Landscape.java
* A container for agents
* Author: Samuel Munoz
* Course: CS231
* Date: 11-27-2019 (Earliest update for this file)
*/
import java.util.ArrayList;
import java.awt.Graphics;

public class Landscape {
	private int width;
	private int height;
	private LinkedList<Agent> background;
	private LinkedList<Agent> foreground;
	
	// a constructor that sets the width and height fields, and initializes the agent list.
    public Landscape(int w, int h) { 
		this.width = w;
		this.height = h;
		this.background = new LinkedList<Agent>();
		this.foreground = new LinkedList<Agent>();
	}
	
	// returns the height.
    public int getHeight() { return this.height; }
    // returns the width.
    public int getWidth() { return this.width; }
    
    // add a vertex inside the background linked link
    public void addBackgroundAgent(Vertex v) {
    	this.background.addLast(v);
    }
	
	// add a vertex inside the foreground linked link
    public void addForegroundAgent(Agent v) {
    	this.foreground.addLast(v);
    }
    
    //returns a String representing the Landscape. It can be as simple as indicating the number of Agents inside the background and foreground linked list.
	public String toString() { 
		String returnString = "background :" + this.background.size() + "\n";
		returnString += "foreground: " + this.foreground.size();
		return returnString;
	}

	
	// Calls the draw method of all the agents on the Landscape
    public void draw(Graphics g, int scale) {
		for(Agent a : this.background)
			a.draw(g,scale);
		for(Agent a : this.foreground)
			a.draw(g,scale);
	}
}