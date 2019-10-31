/**
* stuff here
*/
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class SocialAgent extends Agent {
	private boolean moved;
	private int radius;
	
	public SocialAgent(double x0,double y0,int radius) {
		super(x0,y0);
		this.radius = radius;
		this.moved = false;
	}
	
	public void setRadius(int radius) { this.radius = radius; }
	public int getRadius() { return this.radius; }
	public void setMoved(boolean b) { this.moved = b; }
	public boolean getMoved() { return this.moved; }
	public void draw(Graphics g) {
		if(moved) { // if the Agent moved in the last update, then the color used will be a darker color
			g.setColor(new Color(255,0,0));
			g.fillOval((int) super.getX(),(int) super.getY(),5,5);
		}
		else {
			g.setColor(new Color(255,40,70));
			g.fillOval((int) super.getX(),(int) super.getY(),5,5);
		}
	}
	
	public void updateState(Landscape scape) {
		ArrayList<Agent> neighbors = scape.getNeighbors(this.getX(),this.getY(),this.getRadius());
		Random rand = new Random();
		double randNumber = rand.nextDouble() * 100;
		double shiftX = rand.nextDouble() * 20 - 10;
		double shiftY = rand.nextDouble() * 20 - 10;
		if(neighbors.size() <= 3 || randNumber < 1.0) { // is not happy or they are happy, but the randNumber falls inside the one percent interval
			if(this.getX() + shiftX < this.getRadius() - 1 || this.getX() + shiftX > scape.getWidth() - this.getRadius()-1)
				this.setX(this.getX() - shiftX);
			else 
				this.setX(this.getX() + shiftX);
			if(this.getY() + shiftY < this.getRadius()-1 || this.getY() + shiftY > scape.getHeight() - this.getRadius()-1)
				this.setY(this.getY() - shiftY);
			else 
				this.setY(this.getY() + shiftY);
			this.moved = true;
		}
		else { // is happy and the randNumber falls outside the one percent interval
			this.moved = false;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		int width = 500;
		int height = 500;
		Landscape scape = new Landscape(width,height);
		Random rand = new Random();
		// scape.addAgent(new SocialAgent(10,10,10));
		// scape.addAgent(new SocialAgent(11,11,10));
		// scape.addAgent(new SocialAgent(12,12,10));
		// scape.addAgent(new SocialAgent(13,13,10));
		// SocialAgent watch = new SocialAgent(150,150,10);
		// scape.addAgent(watch);
		for(int i = 0;i < 50;i++)
			scape.addAgent(new SocialAgent(rand.nextDouble() * width,rand.nextDouble() * height,50));
		LandscapeDisplay display = new LandscapeDisplay(scape);
		while(true) {
			scape.updateAgents();
			display.repaint();
			Thread.sleep(250);		
		}
	}
}