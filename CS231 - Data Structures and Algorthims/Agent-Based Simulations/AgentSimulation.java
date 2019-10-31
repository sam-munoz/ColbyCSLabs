/**
*
*/

public class AgentSimulation {
	public static void main(String[] args) throws InterruptedException {
		if(args.length > 5) {
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			int numSocialAgents = Integer.parseInt(args[2]);
			int numCat1Agents = Integer.parseInt(args[3]);
			int numCat2Agents = Integer.parseInt(args[4]);
			int radius = Integer.parseInt(args[5]);
			Landscape scape = new Landscape(width,height);
			
			for(int i = 0;i < numSocialAgents;i++) 
				scape.addAgent(new SocialAgent(Math.random() * width,Math.random() * height,radius));
			for(int i = 0;i < numCat1Agents;i++)
				scape.addAgent(new CatSocialAgent(Math.random() * width,Math.random() * height,radius,1));
			for(int i = 0;i < numCat2Agents;i++)	
				scape.addAgent(new CatSocialAgent(Math.random() * width,Math.random() * height,radius,2));
			
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
			System.out.println("You must six or more arguments.");
		}
	}
}