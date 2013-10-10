import processing.core.PApplet;


public class Cluster {
	
	Note note;
	PApplet p;
	
	public Cluster(PApplet _p, Note _note) {
		p = _p;
		note = _note;
	}
	
	public void draw() {
		float d = p.map(note.getAttack(), 0.0f, 1.0f, 50.0f, 150.0f);
		p.noStroke();
		p.fill(255, 0, 0);
		p.ellipse(p.width/2, p.height/2, d, d);		
	}

}
