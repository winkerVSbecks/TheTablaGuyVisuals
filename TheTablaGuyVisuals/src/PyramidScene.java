/**********************************************************************************************

 	Each scene consists of the following parameter:
 		- Expected MIDI notes to monitor (coming from Ableton)
 		- Audio channel to monitor
 		- The type of visuals to render

**********************************************************************************************/	

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class PyramidScene {
	
	int t;
	int[] range;
	Note[] notes;
	PApplet p;
	String name;
	ArrayList<Cluster> clusters;
	FlyingPyramid lonePyramid;
	
	int colors[] = { 0xFF1ABC9C, 0xFF2ECC71, 0xFFE74C3C, 0xFF16A085,
					 0xFFF1C40F, 0xFFE67E22, 0xFF3498DB,  0xFF9B59B6,
					 0xFF27AE60, 0xFF2980B9, 0xFF8E44AD, 0xFFF39C12,
					 0xFFD35400, 0xFFC0392B };  
	
	public PyramidScene(PApplet _p, String _name, Note[] _notes, int[] _range, int _t) {
		clusters = new ArrayList<Cluster>();
		name = _name;
		p = _p;
		notes = _notes;
		range = _range;
		t = _t;
		for (int i = 0; i < range.length; i++) {
			Note note = notes[range[i]];
			clusters.add( new Cluster(p, note, colors[i]) );
		}
		
		float r = p.random(25, 200);
	    float f = p.random(2, 5);
	    lonePyramid = new FlyingPyramid(p, f*r, r);
	}
	
	public void draw() {
		p.background(0xFFECF0F1);
//		p.lights();
		if(Properties.LONE_PYRAMID) {
			for(Cluster cluster: clusters) {
				cluster.draw();
			}
		} else {
			lonePyramid.update(notes[range[0]].getAttack());
			PApplet.println(notes[range[0]].getAttack());
		}
	}
}
