import java.util.ArrayList;

import processing.core.PApplet;

public class Song {
	
	int t;
	int[] range;
	Note[] notes;
	PApplet p;
	ArrayList<Cluster> clusters;
	
	public Song(PApplet _p, Note[] _notes, int[] _range, int _t) {
		p = _p;
		notes = _notes;
		range = _range;
		t = _t;
		clusters = new ArrayList<Cluster>();
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
