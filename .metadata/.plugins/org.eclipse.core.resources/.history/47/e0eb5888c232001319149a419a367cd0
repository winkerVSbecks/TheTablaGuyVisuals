import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;

public class Cluster {
	
	Note note;
	PApplet p;
	ArrayList<FlyingPyramid> flyingPyramids = new ArrayList<FlyingPyramid> ();
	int NUMSHAPES = 12; 
	
	public Cluster(PApplet _p, Note _note) {
		p = _p;
		note = _note;
		for (int i=0; i<NUMSHAPES; i++) {
		    float r = p.random(25, 200);
		    float f = p.random(2, 5);
		    flyingPyramids.add( new FlyingPyramid(p, f*r, r) );
		}
	}
	
	public void draw() {		
		// perspective to see close shapes
		p.perspective(PConstants.PI/3.0f, (float) p.width/p.height, 1, 1000000);
		// update and display all the shapes
		for (FlyingPyramid p : flyingPyramids) {
			p.update(note.getAttack());
		}
	}

}
