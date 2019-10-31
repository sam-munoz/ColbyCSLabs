/**
* stuff here
*/
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class CatSocialAgent extends SocialAgent {
	private int category;
	
	public CatSocialAgent(double x0, double y0, int radius, int cat) {
		super(x0,y0,radius);
		this.category = cat;
	}
	
	public int getCategory() { return this.category; }
	public String toString() { return Integer.toString(this.category); }
	
	public void draw(Graphics g) {
		if(this.getMoved()) { // if the Agent moved in the last update, then the color used will be a darker color
			if(category == 1)
				g.setColor(new Color(0,255,150));
			else if(category == 2)
				g.setColor(new Color(0,0,255));
			g.fillOval((int) this.getX(),(int) this.getY(),5,5);
		}
		else {
			if(category == 1)
				g.setColor(new Color(150,255,40));
			else if(category == 2)
				g.setColor(new Color(150,0,220));
			g.fillOval((int) this.getX(),(int) this.getY(),5,5);
		}
	}
	
	public void updateState(Landscape scape) {
		ArrayList<Agent> neighbors = scape.getNeighbors(this.getX(),this.getY(),this.getRadius());
		int sameCat = 0;
		for(Agent a : neighbors) {
			if(a instanceof CatSocialAgent) { // https://www.webucator.com/how-to/how-check-object-type-java.cfm
				if(((CatSocialAgent) a).getCategory() == this.category)
					sameCat++;
			}
		}
		Random rand = new Random();
		double randNumber = rand.nextDouble() * 100;
		double shiftX = rand.nextDouble() * 20 - 10;
		double shiftY = rand.nextDouble() * 20 - 10;
		if(neighbors.size() < 3 || (neighbors.size() >= 3 && sameCat <= neighbors.size()/2) || randNumber < 1.0) { // is not happy or they are happy, but the majority of the cells are not of the same catagory, or the randNumber falls inside the one percent interval
				if(this.getX() + shiftX < this.getRadius() - 1 || this.getX() + shiftX > scape.getWidth() - this.getRadius()-1)
					this.setX(this.getX() - shiftX);
				else 
					this.setX(this.getX() + shiftX);
				if(this.getY() + shiftY < this.getRadius()-1 || this.getY() + shiftY > scape.getHeight() - this.getRadius()-1)
					this.setY(this.getY() - shiftY);
				else 
					this.setY(this.getY() + shiftY);
				this.setMoved(true);
		}
		else { // is happy and the randNumber falls outside the one percent interval
			this.setMoved(false);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		if(args.length > 2) {	
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			int numCells = Integer.parseInt(args[2]);
			Landscape scape = new Landscape(width,height);
			for(int i = 0;i < numCells;i++)
				scape.addAgent(new CatSocialAgent(Math.random() * width,Math.random() * height,20,(int) (Math.random() * 2) + 1));
			LandscapeDisplay display = new LandscapeDisplay(scape);
			while(true) {
				Thread.sleep(250);
				scape.updateAgents();
				display.repaint();
			}
		}
		else {
			System.out.println("Enter more argments");
		}
	}
}