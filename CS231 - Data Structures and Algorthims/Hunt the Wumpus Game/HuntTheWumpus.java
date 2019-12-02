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
	// enumeration that tellst the program if the user has won, lost, or if that state is undetermined
	public enum WinState { WIN,LOSE,UNKNOWN }
	// holds current WinState for the current game
	private WinState state;
	// holds the max width for the grid
	private int width;
	// holds the max height for the grid
	private int height;
	// holds our grid of Vertices
	private Vertex[] grid;
	
	// construct empty constructor
	public HuntTheWumpus(int width, int height, int scale, int selection) {
		this.width = width;
		this.height = height;
		this.grid = new Vertex[this.width * this.height];
		this.scape = new Landscape(scale*(this.height+1), scale*(this.width+1));
		this.graph = new Graph();
		this.display = new InteractiveLandscapeDisplay(this.scape);
		if(selection == 1)
			this.constructSimpleGraph(graph);
		if(selection == 2)
			this.constructGraph();
		// System.out.println(this.graph);
		this.hunter = new Hunter(this.findStartingPosition());
		this.wumpus = new Wumpus(this.findStartingPosition(this.hunter));		
		this.scape.addForegroundAgent(hunter);
		this.scape.addForegroundAgent(wumpus);
		this.graph.shortestPath(this.wumpus.location());
		this.state = WinState.UNKNOWN;
	}
	
	// this returns a valid starting vertex for a hunter
	public Vertex findStartingPosition() {
		while(true) {
			Vertex v = this.graph.get((int) (Math.random() * this.graph.vertexCount()));
			if(v != null && v.getNeighbors().size() > 0)
				return v;
		}
	}
	
	// this returns a valid starting vertex for a wumpus
	public Vertex findStartingPosition(Agent a) {
		while(true) {
			Vertex v = this.graph.get((int) (Math.random() * this.graph.vertexCount()));
			if(v != null && v.getNeighbors().size() > 0 && !(v.getX() == a.getX() && v.getY() == a.getY()))
				return v;
		}
	}
	
	// this method constructs the graph with all of it's edges
	// remember to add Vertices to both the Graph and the Landscape object
	public void constructGraph() {
		int count = (int) Math.sqrt(this.width * this.height) - 1;
		int[] pairsUsed = new int[count];
		for(int i = 0;i < count;i++) {
			while(true) {
				int index = (int) (Math.random() * (this.width * this.height));
				if(this.grid[this.convert(index/this.height, index%this.height)] == null) {
					this.grid[this.convert(index/this.height, index%this.height)] = new Vertex(index/this.height, index%this.height);
					Vertex v = this.grid[this.convert(index/this.height, index%this.height)];
					// v.setVisible(true);
					this.graph.add(v);
					this.scape.addBackgroundAgent(v);
					pairsUsed[i] = index;
					break;
				}
			}
		}
		
		// for(int i = 0;i < count;i++)
			// System.out.println(i + ": " + pairsUsed[i] + "\t(" + pairsUsed[i]/this.height + "," + pairsUsed[i]%this.height + ")");
		
		for(int i = 0;i < count;i++) {
			int first = pairsUsed[i];
			int second = pairsUsed[(i+1)%count];
			this.findPath(first/this.height, first%this.height, second/this.height, second%this.height);
		}
	}
	
	// this is a method that was used to construct a garph which I tested/debugged other methods
	public void constructSimpleGraph(Graph g) {
		int side = this.width * this.width;
		for(int a = 0;a < side;a++) {
			int root = (int) Math.sqrt(side);
			int row = a/root;
			int col = a%root;
			this.graph.add(new Vertex(row,col));
			this.scape.addBackgroundAgent(this.graph.get(a));
			// System.out.println("(" + row + "," + col + ")");
			// if(!(row%2 == 1 && col%2 == 1))
				// this.graph.get(a).setVisible(true);
			if(row != 0 && col%2 == 0) {
				this.graph.addEdge(this.graph.get(a),Vertex.Direction.WEST,this.graph.get(a-root));
			}
			if(col != 0 && row%2 == 0) {
				this.graph.addEdge(this.graph.get(a),Vertex.Direction.NORTH,this.graph.get(a-1));
			}
		}
	}	
	
	// this method decides whether if the next key board input should have the hunter moving or firing an arrow
	public void input() {
		this.hunter.setFire(this.display.getArmed());
		if(this.hunter.fire()) 
			this.shootArrow();
		else
			this.move();
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
		
		if(moveTo != null) {
			if(this.wumpus.getX() == moveTo.getX() && this.wumpus.getY() == moveTo.getY()) {
				this.state = WinState.LOSE;
				this.display.updateWinState(this.state);
				this.wumpus.makeVisible();
				this.endGame();
			}
				
				this.hunter.move(moveTo);
		}
		this.display.rest();
	}
	
	public void shootArrow() {
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
		
		if(moveTo != null) {
			if(moveTo.getX() == this.wumpus.getX() && moveTo.getY() == this.wumpus.getY()) {
				this.wumpus.killed();
				this.state = WinState.WIN;
				this.display.updateWinState(this.state);
			}
			else {
				this.state = WinState.LOSE;
				this.wumpus.makeVisible();
				this.display.updateWinState(this.state);
			}
			this.endGame();
		}
		// this.display.rest();
	}
	
	// this method sets up the end game display for the game
	public void endGame() {
		for(int index = 0;index < this.graph.vertexCount();index++) {
			if(this.graph.get(index).getNeighbors().size() > 0)
				this.graph.get(index).setVisible(true);
		}
	}
	
	// this method takes a coordinate and turns into an index for the graph
	public int convert(int x, int y) {
		return x * this.height + y;
	}
	
	// this method finds the shortest path between two points
	public void findPath(int x0, int y0, int xf, int yf) {
		// System.out.println("Entered findPath");
		// System.out.println("x0: " + x0 + "\ny0: " + y0 + "\nxf: " + xf + "\nyf: " + yf);
		int x = x0;
		int y = y0;
		Vertex old = this.grid[this.convert(x,y)];
		if(old == null)
			old = new Vertex(x,y);
		// old.setVisible(true);
		this.grid[this.convert(x,y)] = old;
		this.graph.add(old);
		this.scape.addBackgroundAgent(old);
		Vertex.Direction d1, d2;
		if(x < xf)
			d1 = Vertex.Direction.EAST;
		else
			d1 = Vertex.Direction.WEST;
		if(y < yf)
			d2 = Vertex.Direction.SOUTH;
		else
			d2 = Vertex.Direction.NORTH;
		while(x != xf || y != yf) {
			old = this.grid[this.convert(x,y)];
			if(old == null) {
				this.grid[this.convert(x,y)] = new Vertex(x,y);
				old = this.grid[this.convert(x,y)];
				this.graph.add(old);
			}
			// System.out.println("old: " + old);
			Vertex.Direction dir = null;
			boolean change = false;
			int rand = (int) (Math.random() * 2);
			// movement on the x-axis
			if((x > 0 || x < this.width-1) && rand == 0 && x != xf) {
				if(d1 == Vertex.Direction.EAST)
					x++;
				else if(d1 == Vertex.Direction.WEST)
					x--;
				dir = d1;
				change = true;
			}
			// movemnet on the y-axis
			else if((y < this.height-1 || y > 0) && rand == 1 && y != yf) {
				if(d2 == Vertex.Direction.SOUTH)
					y++;
				else if(d2 == Vertex.Direction.NORTH)
					y--;
				dir = d2;
				change = true;
			}
			if(change) {
				Vertex newV = this.grid[this.convert(x,y)];
				if(newV == null) {
					this.grid[this.convert(x,y)] = new Vertex(x,y);
					newV = this.grid[this.convert(x,y)];
					this.graph.add(newV);
				}
				// System.out.println("new: " + newV);
				// newV.setVisible(true);
				this.graph.add(newV);
				this.scape.addBackgroundAgent(newV);
				this.graph.addEdge(old, dir, newV);
			}
		}
		// System.out.println("End findPath");
	}
	
	// this method checks if the selection value is valid
	public static boolean checkSelection(int s) {
		if(s == 1 || s == 2)
			return true;
		return false;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		int s = 0;
		int w = 4;
		int h = 4;
		if(args.length > 2)	{
			s = Integer.parseInt(args[2]);
			w = Integer.parseInt(args[0]);
			h = Integer.parseInt(args[1]);
		}
		if(HuntTheWumpus.checkSelection(s)) {
			HuntTheWumpus game = new HuntTheWumpus(w, h, 64, s);
			while (game.display.state() == InteractiveLandscapeDisplay.PlayState.PLAY) {
				if(game.state == WinState.UNKNOWN)
					game.input();
				game.display.winLoseMessage();
				// System.out.println(game.hunter);
				game.display.repaint();
				Thread.sleep(33);
			}
			System.out.println("Disposing window");
			game.display.dispose();
			System.exit(0);
		}
		System.out.println("Requires these arguments: <width of grid (integer)> <height of grid (integer)> <version to run: 1 is base project, 2 includes the extension (integer)>");
	}
}