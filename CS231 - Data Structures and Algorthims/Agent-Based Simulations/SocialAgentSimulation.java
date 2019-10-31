/**
*
*/

public class SocialAgentSimulation {
	public static void main(String[] args) throws InterruptedException {
		if(args.length > 2) {
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			int numCells = Integer.parseInt(args[2]);
			Landscape scape = new Landscape(width,height);
			for(int i = 0;i < numCells;i++)
				scape.addAgent(new SocialAgent(Math.random() * width,Math.random() * height,20));
			LandscapeDisplay display = new LandscapeDisplay(scape);
			// while(true) {
				// Thread.sleep(250);
				// scape.updateAgents();
				// display.repaint();
			// }
			
			for(int i = 0; i < 200;i++) {
				Thread.sleep(10);
				scape.updateAgents();
				display.repaint();
			}
		}
		else {
			System.out.println("You must three or more arguments.");
		}
	}
}