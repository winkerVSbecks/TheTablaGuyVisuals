import java.util.ArrayList;
import processing.core.PVector;
import processing.core.PApplet;
import processing.core.PConstants;

public class Wiggler {
	
	PApplet p;
	// The PShape to be "wiggled"
	float x, y;
	// For 2D Perlin noise
	float yoff = 0;
	float yStep;
	float rad;

	// We are using an ArrayList to keep a duplicate copy of vertices original locations.
	ArrayList<PVector> original;

	Wiggler(PApplet _p, float _rad, float _yStep) {
		p = _p;
		x = p.width/2;
		y = p.height/2; 
		rad = _rad;
		yStep = _yStep;

		// The "original" locations of the vertices make up a circle
		original = new ArrayList<PVector>();
		for (float a = 0; a < PConstants.TWO_PI; a+=0.1) {
			PVector v = PVector.fromAngle(a);
			v.mult(rad);
			original.add(v);
		}
	}

	void draw(float nudge) {
		float xoff = 0;
		// Apply an offset to each vertex
		p.pushMatrix();
			p.translate(x, y, -2000);
			p.stroke(0xFF7F8C8D);
			p.noFill();
			p.beginShape();
			for (PVector pos : original) {
				// Calculate a new vertex location based on noise around "original" location
				float a = PConstants.TWO_PI*p.noise(xoff,yoff);
				PVector r = PVector.fromAngle(a);
				r.mult(25);
				r.add(pos);
				r.mult(PApplet.map(nudge, 0, 1, 1.0f, 1.02f));
				p.vertex(r.x, r.y, 0);
//				p.strokeWeight(2);
//				p.point(r.x, r.y, 0);
//				p.strokeWeight(1);
				// increment perlin noise x value
				xoff+= 0.5;
			}
			p.endShape(PConstants.CLOSE);
			// Increment perlin noise y value
			yoff += yStep;
		p.popMatrix();
	}
	
}
