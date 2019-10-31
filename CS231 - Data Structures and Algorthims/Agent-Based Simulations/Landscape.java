/**
* stuff here
*/
import java.util.ArrayList;
import java.awt.Graphics;

public class Landscape {
	private int width;
	private int height;
	private LinkedList<Agent> llist;
	
    public Landscape(int w, int h) { //a constructor that sets the width and height fields, and initializes the agent list.
		this.width = w;
		this.height = h;
		this.llist = new LinkedList();
	}
	
    public int getHeight() { return this.height; } //returns the height.
    public int getWidth() { return this.width; } //returns the width.
    
	public void addAgent( Agent a )  { llist.addFirst(a); } //inserts an agent at the beginning of its list of agents.
    
	public String toString() { return Integer.toString(this.llist.size()); } //returns a String representing the Landscape. It can be as simple as indicating the number of Agents on the Landscape.
    
	public ArrayList<Agent> getNeighbors(double x0, double y0, double radius) { //returns a list of the Agents within radius distance of the location x0, y0.
		ArrayList<Agent> returnList = new ArrayList<Agent>();
		for(Agent a : this.llist) {
			double distance = Math.sqrt(Math.pow(a.getX() - x0,2) + Math.pow(a.getY() - y0,2));
			if(distance <= radius)
				returnList.add(a);
		}
		return returnList;
 	}
	
    public void draw(Graphics g) { //Calls the draw method of all the agents on the Landscape.
		for(Agent a : this.llist)
			a.draw(g);
	}
	
	public ArrayList<Agent> getArrayList() { return llist.toArrayList(); }
	
	public void updateAgents() {
		ArrayList<Agent> randList = llist.toShuffledList();
		for(Agent a : randList)
			a.updateState(this); // this will fail
	}
	
	public static void main(String[] args) {
		Landscape l = new Landscape(1,1);
		for(int i = 0;i < 10;i++)
			l.addAgent(new Agent(i,i));
		System.out.println(l.getHeight() + " " + l.getWidth());
		System.out.println(l);
		ArrayList<Agent> list = l.getNeighbors(2,2,2);
		for(Agent a : list)
			System.out.println(a);
	}
}