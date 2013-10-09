import processing.core.PApplet;

public class Song {
	
	int t;
	int[] range;
	Note[] notes;
	PApplet p;
	
	
	public Song(PApplet _p, Note[] _notes, int[] _range, int _t) {
		p = _p;
		notes = _notes;
		range = _range;
		t = _t;
	}

	
	public void draw() {
		for (int i = 0; i < range.length; i++) {
			int noteToCheck = range[i];
			float d = p.map(notes[noteToCheck].getAttack(), 0.0f, 1.0f, 50.0f, 150.0f);
			p.noStroke();
			p.fill(255, 0, 0);
			p.ellipse(p.width/2, p.height/2, d, d);
//			p.println(d);
		}
	}
	

}