/*
*
*/

public class CatSocialAgentSimulation {
	public static void main(String[] args) throws InterruptedException {
		if(args.length > 2) {
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			int numCells = Integer.parseInt(args[2]);
			int radius = 80;
			Landscape scape = new Landscape(width,height);
			for(int i = 0;i < numCells;i++) {
				if(i%2 == 0)
					scape.addAgent(new CatSocialAgent(Math.random() * width,Math.random() * height,radius,1));
				else
					scape.addAgent(new CatSocialAgent(Math.random() * width,Math.random() * height,radius,2));
			}
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