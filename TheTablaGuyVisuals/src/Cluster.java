import java.util.ArrayList;

import processing.core.PApplet;

public class Cluster {
	
	Note note;
	PApplet p;
	ArrayList<FlyingPyramid> flyingPyramids = new ArrayList<FlyingPyramid> ();
	ArrayList<Pyramid> pyramids = new ArrayList<Pyramid> ();
	int NUMSHAPES = 12; 
	
	public Cluster(PApplet _p, Note _note, int _colors, int _texture) {
		p = _p;
		note = _note;
		for (int i=0; i<3; i++) {
//		    float r = p.random(25, 200);
//		    float f = p.random(2, 5);
		    float r = p.random(175, 200);
		    float f = p.random(2, 3);
		    pyramids.add( new Pyramid(p, f*r, r, _colors, _texture) );
		}
		
		for (int i=0; i<1; i++) { //NUMSHAPES/4
		    float r = p.random(25, 200);
		    float f = p.random(2, 5);
		    flyingPyramids.add( new FlyingPyramid(p, f*r, r) );
		}
	}
	
	public void draw() {		
		// perspective to see close shapes
//		p.perspective(PConstants.PI/3.0f, (float) p.width/p.height, 1, 1000000);
		// update and display all the pyramids
		if(Properties.FLYING_PYRAMIDS) {
			p.noLights();
			for (FlyingPyramid fpy : flyingPyramids) {
				fpy.update(note.getAttack());
			}
		} else {
//			p.lights();
			p.pushMatrix();
				p.translate(p.width/2, p.height/2, -2000);
				p.fill(255, 255, 250);
				p.noStroke();
				p.sphere(550);
			p.popMatrix();
			for (Pyramid py : pyramids) {
				py.update(note.getAttack());
			}
		}
		
	}

}
