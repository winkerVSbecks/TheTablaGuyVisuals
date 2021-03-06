
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

public class SpectrumPyramid {
	PApplet p;
	PVector[] v, left, right;
	int[] c = new int[14]; 
	float origPyHeight, origPyRadius;
	float pyHeight, pyRadius;
	float transparency; 
	PVector pos; 
	float rotX, rotY, rotZ; 
	float scl = 1.0f;
	Note[] notes;

	SpectrumPyramid(PApplet _p, float _pyHeight, float _pyRadius, int[] _c, Note[] _notes) {
		p = _p;
		v = new PVector[5];
		left = new PVector[14]; right = new PVector[14];
		pyHeight = _pyHeight;
		origPyHeight = pyHeight; 
		pyRadius = 2.0f*_pyRadius;
		origPyRadius = pyRadius; 
		pos = new PVector(p.width/2, p.height/2, -2000);
		transparency = 255;
		rotX = p.random(-2*p.width, 3*p.width); 
		rotY = p.random(-2*p.height, 2*p.height);
		rotZ = p.random(-2*p.width, 3*p.width);
		c = _c;
		notes = _notes;
		updatePyramid();
	}

	void updatePyramid() {
		float base, top;
		base = -pyHeight/2; top = pyHeight/2;
				
		v[0] = new PVector(0, top, 0);
		v[1] = new PVector(pyRadius*PApplet.cos(PConstants.HALF_PI), base, pyRadius*PApplet.sin(PConstants.HALF_PI));  
		v[2] = new PVector(pyRadius*PApplet.cos(PConstants.PI), base, pyRadius*PApplet.sin(PConstants.PI));            
		v[3] = new PVector(pyRadius/2*PApplet.cos(1.5f*PConstants.PI), base, pyRadius/2*PApplet.sin(1.5f*PConstants.PI));  
		v[4] = new PVector(pyRadius/2*PApplet.cos(PConstants.TWO_PI), base, pyRadius/2*PApplet.sin(PConstants.TWO_PI));    
		
		for (int i = 0; i < 14; i++) {
			left[i] = new PVector( PApplet.lerp(v[0].x, v[1].x, i/14.0f),
								   PApplet.lerp(v[0].y, v[1].y, i/14.0f),
								   PApplet.lerp(v[0].z, v[1].z, i/14.0f) );
		}
		for (int i = 0; i < 14; i++) {
			right[i] = new PVector( PApplet.lerp(v[0].x, v[2].x, i/14.0f),
									PApplet.lerp(v[0].y, v[2].y, i/14.0f),
									PApplet.lerp(v[0].z, v[2].z, i/14.0f) );
		}
	}

	void update(float d) {		
		//scale it instead � no pyramid update needed
		scl = 1.0f+d/2.0f;
		// Display all pyramids/only the ones which were triggered
		display(d);
		updatePyramid();
	}
	
	void display(float d) {
		transparency = 245;
 		p.pushMatrix(); 
			p.translate(pos.x, pos.y, -300);
//			p.scale(scl);
			// Rotate pyramids
//			p.rotateY(rotY + p.frameCount*0.005f); 
//			p.rotateX(rotX - p.frameCount*0.004f); 
//			p.rotateZ(rotZ - p.frameCount*0.003f);
			p.rotateX(p.mouseY * 0.01f);
			p.rotateY(p.mouseX * 0.01f);
			// draw the 4 side triangles of the pyramid
			p.noStroke();
			// Triangle faces
			p.beginShape(PConstants.TRIANGLE_FAN); 
				for (int i=0; i<5; i++) {
					if(i!=1) {
						p.fill(c[i], transparency);
						p.vertex(v[i].x, v[i].y, v[i].z);
					}
				}
				p.fill(c[1], transparency);
				// add the 'first base vertex' to close the shape
				p.vertex(v[1].x, v[1].y, v[1].z);
			p.endShape();
			// draw the base QUAD of the pyramid
			p.beginShape(PConstants.QUADS);
				for (int i=1; i<5; i++) {
					p.fill(c[i], transparency);
					p.vertex(v[i].x, v[i].y, v[i].z);
				}
			p.endShape(); 
			// Point markers
//			for (int i = 0; i < 14; i++) {
//				p.stroke(0);
//				p.strokeWeight(5);
//				p.point(left[i].x, left[i].y, left[i].z);
//				p.point(right[i].x, right[i].y, right[i].z);
//			}
			// Build the spectrum
			displaySpectrum();
		p.popMatrix(); 
	}
	
	void displaySpectrum() {
		int h = 100;
		p.noStroke();
		// Wider Edge
		p.beginShape(PConstants.QUADS);
			p.fill(c[1], transparency);
			p.vertex(v[1].x, v[1].y, v[1].z);
			p.fill(c[2], transparency);
			p.vertex(v[2].x, v[2].y, v[2].z);
			p.fill(c[2], transparency);
			p.vertex(v[2].x, v[2].y+h/4, v[2].z+h);
			p.vertex(v[1].x-h, v[1].y+h/4, v[1].z);
		p.endShape();
		
		float hh = h;
		for (int i=1; i<left.length; i++) {
			if(i!=0) hh = PApplet.map(notes[i].getAttack(), 0, 1, h/2, h);
			p.beginShape(PConstants.QUADS);
				p.fill(c[1], transparency);
				p.vertex(left[i].x, left[i].y, left[i].z);
				p.fill(c[2], transparency);
				p.vertex(right[i].x, right[i].y, right[i].z);
				p.fill(c[2], transparency);
				p.vertex(right[i].x, right[i].y+hh/4, right[i].z+hh);
				p.vertex(left[i].x-hh, left[i].y+hh/4, left[i].z);
			p.endShape();
		}
	}
	
}









