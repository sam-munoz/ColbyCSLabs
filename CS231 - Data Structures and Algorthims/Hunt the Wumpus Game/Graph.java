/**
* File: Vertex.java
* Creates a graph
* Author: Samuel Munoz
* Course: CS231
* Date: 11-20-2019
*/
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph {
	// holds all the vertices in the graph
	private Vertex[] vertices;
	// integer pointing to the next empty index in the array
	private int size;
	// default capacity size for the array
	private final int DEFAULT_CAPACITY = 10;

	// constructor: creates an empty graph with an array with a default capacity 
	public Graph() {
		this.vertices = new Vertex[DEFAULT_CAPACITY];
		this.size = 0; 
	}

	// constructor: creates an empty graph with an array with a given capacity
	public Graph(int capacity) {
		this.vertices = new Vertex[capacity];
		this.size = 0; 
	}

	// adds a new vertex into the graph
	public void add(Vertex v) {
		if(this.size == this.vertices.length)
			this.resize();
		this.vertices[this.size++] = v;
	}
	
	// gets a vertex based on indices
	public Vertex get(int index) {
		return this.vertices[index];
	}	

	// returns the number of vertices in the graph
	public int vertexCount() {
		return this.size;
	}

	// connects two vertices in a bi-directional edge
	public void addEdge(Vertex v1, Vertex.Direction dir, Vertex v2) {
		v1.connect(v2,dir);
		v2.connect(v1,Vertex.opposite(dir));
	}
	
	// this method resizes the array when the array is full
	public void resize() {
		Vertex[] newArray = new Vertex[this.vertices.length * 2];
		for(int index = 0;index < this.size;index++)
			newArray[index] = this.vertices[index];
		this.vertices = newArray;
	}

	// implements Dijkstra's algorthim to find the shortest path to some vertex. NOTE: this version assume that all edges have the same weight
	public void shortestPath(Vertex v0) {
		// creates a heap stores Vertices inside the heap, with the Vertex having large distance, except the starting vertex, which has a distance of 0 if it exists inside the Vertex 
		PQHeap<Vertex> pq = new PQHeap<Vertex>(new CompareVertex(), this.size);
		boolean exists = true;
		
		for(int index = 0;index < this.size;index++) {
			this.vertices[index].setCost(9999);
			this.vertices[index].setMarked(false);
			if(v0.equals(this.vertices[index])) {
				exists = false;
				this.vertices[index].setCost(0);
			}
			pq.add(this.vertices[index]);

		}
		
		// checks if starting vertex exists inside the Vertex array 
		if(exists)
			return;

		while(pq.size() > 0) {
			Vertex currentVertex = pq.remove();
			// System.out.println("Current Vertex: " + currentVertex);
			if(!currentVertex.marked())
				currentVertex.setMarked(true);
			ArrayList<Vertex> neighbors = currentVertex.getNeighbors();
			int currentCost = currentVertex.cost();
			for(Vertex v : neighbors) {
				// System.out.println(currentVertex.marked() + "\t" + (currentCost+1) + " : " + v.cost());
				if(currentVertex.marked() && currentCost + 1 < v.cost()) {
					int index = pq.find(v);
					v.setCost(currentCost + 1);
					pq.update(index, v);
				}
			}
			// System.out.println(pq + "\n");
		}
	}

	public String toString() {
		String returnString = "";
		for(int index = 0;index < this.size;index++)
			returnString += this.vertices[index] + "\n";
		return returnString;
	}

	public static void main(String[] args) {
		Graph g = new Graph(10);
		g.add(new Vertex(0,0)); // 0
		g.add(new Vertex(0,1)); // 1
		g.add(new Vertex(0,2)); // 2
		g.add(new Vertex(1,0)); // 3
		g.add(new Vertex(1,2)); // 4
		g.add(new Vertex(2,2)); // 5
		g.add(new Vertex(3,2)); // 6
		g.add(new Vertex(1,1)); // 7
		g.add(new Vertex(2,1)); // 8
		g.add(new Vertex(3,1)); // 9

		/*
		Graph Used:
		2 - 4 - 5 - 6
		|		|	|
		1	7 - 8 - 9	
		|	|	
		0 - 3
		*/
		g.addEdge(g.vertices[0],Vertex.Direction.NORTH,g.vertices[1]);
		g.addEdge(g.vertices[1],Vertex.Direction.NORTH,g.vertices[2]);
		g.addEdge(g.vertices[2],Vertex.Direction.EAST,g.vertices[4]);
		g.addEdge(g.vertices[4],Vertex.Direction.EAST,g.vertices[5]);
		g.addEdge(g.vertices[5],Vertex.Direction.EAST,g.vertices[6]);
		g.addEdge(g.vertices[7],Vertex.Direction.EAST,g.vertices[8]);
		g.addEdge(g.vertices[6],Vertex.Direction.SOUTH,g.vertices[9]);
		g.addEdge(g.vertices[3],Vertex.Direction.NORTH,g.vertices[7]);
		g.addEdge(g.vertices[0],Vertex.Direction.EAST,g.vertices[3]);
		g.addEdge(g.vertices[8],Vertex.Direction.NORTH,g.vertices[5]);
		g.addEdge(g.vertices[9],Vertex.Direction.WEST,g.vertices[8]);

		g.shortestPath(g.vertices[0]);

		// for(int i = 0;i < g.vertices.length;i++)
		// 	System.out.println(i + ": " + g.vertices[i]);
	}	
}