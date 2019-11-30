/**
* File: Hunter.java
* Object that represents a hunter in the game
* Author: Samuel Munoz
* Course: CS231
* Date: 11-28-2019
*/

public class HuntTheWumpus {
	// holds the landscape for the game
	private Landscape scape;
	// holds the graph for the game
	private Graph graph;
	// holds the Hunter object for the game
	private Hunter hunter;
	// holds the Wumpus object for the game
	private Wumpus wumpus;
	// holds the LandscapePanel to allow the game to be seen in window
	private InteractiveLandscapeDisplay display;
	
	// construct empty constructor
	public HuntTheWumpus(int scale) {
		this.scape = new Landscape(scale*10, scale*7);
		this.graph = new Graph(scale/4);
		this.constructGraph(graph);
		this.hunter = new Hunter(this.findStartingPosition());
		this.wumpus = new Wumpus(this.findStartingPosition());		
		this.scape.addForegroundAgent(hunter);
		this.scape.addForegroundAgent(wumpus);
		this.graph.shortestPath(this.wumpus.location());
		// System.out.println(this.graph);
		this.display = new InteractiveLandscapeDisplay(this.scape);
	}
	
	// this returns a valid starting vertex for either a hunter or wumpus
	public Vertex findStartingPosition() {
		while(true) {
			Vertex v = this.graph.get((int) (Math.random() * 16));
			if(v.getNeighbors().size() > 0)
				return v;
		}
	}
	
	// this method constructs the graph with all of it's edges
	// remember to add Vertices to both the Graph and the Landscape object
	public void constructGraph(Graph g) {
		// implement later
		// simple version
		int side = 49;
		for(int a = 0;a < side;a++) {
			int root = (int) Math.sqrt(side);
			int row = a/root;
			int col = a%root;
			this.graph.add(new Vertex(row,col));
			this.scape.addBackgroundAgent(this.graph.get(a));
			// System.out.println("(" + row + "," + col + ")");
			if(!(row%2 == 1 && col%2 == 1))
				this.graph.get(a).setVisible(true);
			if(row != 0 && col%2 == 0) {
				this.graph.addEdge(this.graph.get(a),Vertex.Direction.WEST,this.graph.get(a-root));
			}
			if(col != 0 && row%2 == 0) {
				this.graph.addEdge(this.graph.get(a),Vertex.Direction.NORTH,this.graph.get(a-1));
			}
		}
	}

	// this method moves the Hunter based on keyboard input
	public void move() {
		InteractiveLandscapeDisplay.Move m = this.display.currentState();
		Vertex moveTo = null;

		if(m == InteractiveLandscapeDisplay.Move.LEFT)
			moveTo = this.hunter.location().getNeighbor(Vertex.Direction.WEST);
		else if(m == InteractiveLandscapeDisplay.Move.RIGHT)
			moveTo = this.hunter.location().getNeighbor(Vertex.Direction.EAST);
		else if(m == InteractiveLandscapeDisplay.Move.UP)
			moveTo = this.hunter.location().getNeighbor(Vertex.Direction.NORTH);
		else if(m == InteractiveLandscapeDisplay.Move.DOWN)
			moveTo = this.hunter.location().getNeighbor(Vertex.Direction.SOUTH);
		
		if(moveTo != null)
			this.hunter.move(moveTo);
		this.display.rest();

	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		HuntTheWumpus game = new HuntTheWumpus(64);
		// int i = 0;
		while (game.display.state() == InteractiveLandscapeDisplay.PlayState.PLAY) {
            System.out.println("Hunter's position: " + game.hunter.location());
            game.move();
            game.display.repaint();
            Thread.sleep(33);
        }
        System.out.println("Disposing window");
        game.display.dispose();
	}
}