/**********************************************************************************************

 	Each scene consists of the following parameter:
 		- Expected MIDI notes to monitor (coming from Ableton)
 		- Audio channel to monitor
 		- The type of visuals to render

**********************************************************************************************/	

import java.util.ArrayList;

import processing.core.PApplet;

public class PyramidScene {
	
	int t;
	int[] range;
	Note[] notes;
	PApplet p;
	String name;
	ArrayList<Cluster> clusters;
	
	public PyramidScene(PApplet _p, String _name, Note[] _notes, int[] _range, int _t) {
		clusters = new ArrayList<Cluster>();
		name = _name;
		p = _p;
		notes = _notes;
		range = _range;
		t = _t;
		for (int i = 0; i < range.length; i++) {
			Note note = notes[range[i]];
			clusters.add(new Cluster(p, note));
		}
	}
	
	public void draw() {
		for(Cluster cluster: clusters) {
			cluster.draw();
		}
	}
}