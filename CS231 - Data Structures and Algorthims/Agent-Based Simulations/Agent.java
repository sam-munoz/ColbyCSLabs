/**
* stuff here
*/
import java.awt.Graphics;

public class Agent {
	private double x;
	private double y;
	private int pixelCluster;
	
	public Agent(double x0,double y0) {
		this.x = x0;
		this.y = y0;
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }

	public int getPixelCluster() { return this.pixelCluster; }
	public void setPixelCluster(int radius) { this.pixelCluster = radius; }
	
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
	
	public void updateState(Landscape scape) {}
	public void draw(Graphics g) {}
	
	public static void main(String[] args) throws InterruptedException {
		Agent a = new Agent(1.2,1.1);
		
		System.out.println(a);
		
		a.setX(3.2);
		a.setY(0.2);
		
		System.out.println(a.getX() + " " + a.getY());
	}
}