/**
* File: Vertex.java
* Creates a vertex for a graph
* Author: Samuel Munoz
* Course: CS231
* Date: 11-18-2019
*/
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Comparable;

public class Vertex extends Agent {
	// holds all adjacent vertices --> maps index to directions
	private Vertex[] adjacentVertices;
	// true if the vertex has been visited
	private boolean marked;
	// holds the distance from the Wumplus
	private int distance;
	// true if the vertex is visible on the LandscapeDisplay
	private boolean isVisible;
	// holds comparable to compare Vertices
	private CompareVertex comparable;

	// enumeration for Direction
	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	private class CompareVertex implements Comparable<Vertex> {
		public int compare(Vertex v1, Vertex v2) {
			if(v1.distance == v2.distance)
				return 0;
			else if(v1.distance < v2.distance)
				return -1;
			else
				return 1;
		}

		public int compareTo(Vertex v2) {
			Vertex v1 = Vertex.this; // https://stackoverflow.com/questions/1816458/getting-hold-of-the-outer-class-object-from-the-inner-class-object
			if(v1.distance == v2.distance)
				return 0;
			else if(v1.distance < v2.distance)
				return -1;
			else
				return 1;
		}
	}

	// constructor: creates an empty vertex with a given adjacent vertices and distance to the wumplus
	public Vertex(int x, int y) {
		super(x,y);
		this.adjacentVertices = new Vertex[4];
		this.marked = false;
		this.isVisible = false;
		this.comparable = new CompareVertex();
		this.distance = 10;
	}

	// this method will return the opposite direction of the argument direction
	public static Direction opposite(Direction d) {
		switch(d) {
			case NORTH:
				return Direction.SOUTH;

			case SOUTH:
				return Direction.NORTH;

			case EAST:
				return Direction.WEST;

			case WEST:
				return Direction.EAST;
		}
		return null;
	}

	// returns the visible boolean
	public boolean visible() {
		return this.isVisible;
	}
	
	// sets the visible boolean to what the boolean argument is
	public void setVisible(boolean b) {
		this.isVisible = b;
	}

	// returns the costs of the Vertex
	public int cost() {
		return this.distance;
	}

	// increments the distance field by one
	public void setCost(int d) {
		this.distance = d;
	}

	// returns the marked field
	public boolean marked() {
		return this.marked;
	}

	public void setMarked(boolean b) {
		this.marked = b;
	}

	// this connects a this vertex to the other vertex given a direction
	public void connect(Vertex other, Direction dir) {
		this.adjacentVertices[dir.ordinal()] = other; // https://stackoverflow.com/questions/32199253/how-to-use-an-enum-as-an-array-index-in-java
	}

	// this returns the adjacent Vertex at the given direction 
	public Vertex getNeighbor(Direction dir) {
		return this.adjacentVertices[dir.ordinal()];
	}

	// returns a collection of adjacent vertices
	public ArrayList<Vertex> getNeighbors() {
		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
		for(Vertex v : this.adjacentVertices) {
			if(v != null)
				neighbors.add(v);
		}
		return neighbors;
	}
	
	// this method draws Vertex on the JLandscapePanel
	public void draw(Graphics g, int scale) {
		if(this.isVisible) {
			int x = this.getX();
			int y = this.getY();
			g.setColor(Color.BLACK);
			g.drawRect(x*scale, y*scale, 1 * scale - 5, 1 * scale - 5);
			if(this.distance <= 2) {
				g.setColor(Color.RED);
				g.drawRect(x*scale, y*scale, 1 * scale - 5, 1 * scale - 5);
			}
			g.setColor(Color.BLACK);
			if(this.adjacentVertices[Direction.NORTH.ordinal()] != null) {
				g.fillRect(x*scale + (int) (0.5*scale) - 7,y*scale - 10,10,15);
			}
			if(this.adjacentVertices[Direction.SOUTH.ordinal()] != null) {
				g.fillRect(x*scale + (int) (0.5*scale) - 7,y*scale + scale - 10,10,15);
			}
			if(this.adjacentVertices[Direction.EAST.ordinal()] != null) {
				g.fillRect(x*scale + scale - 10,y*scale + (int) (0.5*scale) - 7,15,10);
			}
			if(this.adjacentVertices[Direction.WEST.ordinal()] != null) {
				g.fillRect(x*scale-10,y*scale + (int) (0.5*scale) - 7,15,10);
			}
		}
	}	

	// string that holds information about the number of neighbors, the distance from the wumplus, and if the vertex is marked
	public String toString() {
		String returnString = "(" + this.getX() + "," + this.getY() + ")\t";
		int existingNeighbors = 0;
		for(Vertex v : this.adjacentVertices) {
			if(v != null)
				existingNeighbors++;
		}
		returnString += "N: " + existingNeighbors + ", ";
		returnString += "D: " + this.distance;
		return returnString;
	}

	// allows the vertices to compare themselves using a static method
	public int compare(Vertex v1, Vertex v2) {
		return this.comparable.compare(v1,v2);
	}

	// allows this vertex to compare itself to other vertex
	public int compareTo(Vertex other) {
		return this.comparable.compare(this,other);
	}

	// this converts a number into a Direction as follows 
	public static Direction convertInt(int num) {
		if(num == 0)
			return Direction.NORTH;
		else if(num == 1)
			return Direction.SOUTH;
		else if(num == 2)
			return Direction.EAST;
		else if(num == 3)
			return Direction.WEST;
		return null;
	} 
}