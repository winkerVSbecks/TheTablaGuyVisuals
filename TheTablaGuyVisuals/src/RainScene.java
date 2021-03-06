import processing.core.PApplet;
import processing.core.PConstants;
import java.util.ArrayList;

public class RainScene {

	PApplet p;
	String name;
	int track;
	ArrayList<Rain> drops = new ArrayList<Rain>();
	ArrayList<Splash> splashes = new ArrayList<Splash>();

	public RainScene(PApplet _p, String _name, int _track) {
		p = _p;
		name = _name;
		track = _track;
		drops.add( new Rain(p) );
	}

	public void draw(float d) {
		// Gives the drops a little tail
		p.fill(0, 100);
		p.noStroke();
		p.rect(p.width/2, p.height/2, p.width, p.height);
		// Add more drops every few milliseconds
		if(p.frameCount % 5 == 0 && drops.size() < 550) drops.add( new Rain(p) );
//		if(drops.size() < PApplet.map(d, 0, 1, 0, 250)) {
//			drops.add( new Rain(p) );
//		}

		// Update drop position, draw it. 
		// Also, check for collision and trigger a splash
		for(int i=0 ; i<drops.size() ; i++) {
			Rain drop = (Rain) drops.get(i);
			drop.draw(d);
			
			// check for height
			if(drop.position.y >= p.height) {		      
				for(int k = 0 ; k < p. random(5, 10) ; k++) {
					splashes.add( new Splash(p, drop.position.x, p.height, false) );
				}
				drops.remove(i);
				float rand = p.random(0, 100);
				if (rand > 10 && drops.size() < 150)
					drops.add( new Rain(p) );
			}
			
			// check for collision with umbrella
			if(drop.position.x>=p.width/2-300 && drop.position.x<=p.width/2+300) {
				float dist = PApplet.sq(p.width/2-drop.position.x) + PApplet.sq(p.height/2-drop.position.y);
				if(dist <= 22525) {
					drops.remove(i);
					if(d>0.2) {
						for(int k = 0 ; k < p. random(5, 10) ; k++) {
							splashes.add( new Splash(p, drop.position.x, drop.position.y, true) );
						}
					}
				}
				
			}
		}

		// Draw splashes
		for(int i=0 ; i<splashes.size() ; i++) {
			Splash splash = (Splash) splashes.get(i);
			splash.draw(d);
			float heightTest = splash.isUmbrella ? p.height/2-80 : p.height;
			if (splash.position.y > heightTest) splashes.remove(i);
		}
		
		// Draw the umbrella
		drawUmbrella();
	}
	
	private void drawUmbrella() {
		p.pushMatrix();
			p.fill(255);
			p.noStroke();
			p.translate(p.width/2, p.height/2, 0);
			p.rotateZ(PConstants.PI);
			p.arc(0, 0, 300, 300, 0, PConstants.PI, PConstants.CHORD);
			p.fill(0);
			p.translate(0, -10, 0);
			p.ellipse(-40, 0, 80, 80);
			p.ellipse(40, 0, 80, 80);
			p.ellipse(115, 0, 80, 80);
			p.ellipse(-115, 0, 80, 80);
		p.popMatrix();
	}

}
