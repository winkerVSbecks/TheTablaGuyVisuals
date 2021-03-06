
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

public class LonePyramid {
	PApplet p;
	PVector[] v;
	int[] cp = new int[14]; 
	int[] c = new int[5]; 
	float origPyHeight, origPyRadius;
	float pyHeight, pyRadius;
	float transparency; 
	PVector pos; 
	float rotX, rotY, rotZ; 
	float scl = 1.0f;

	LonePyramid(PApplet _p, float _pyHeight, float _pyRadius, int[] _cp) {
		p = _p;
		v = new PVector[5];
		pyHeight = _pyHeight;
		origPyHeight = pyHeight; 
		pyRadius = 2.0f*_pyRadius;
		origPyRadius = pyRadius; 
		pos = new PVector(p.width/2, p.height/2, -2000);
		transparency = 255;
		rotX = p.random(-2*p.width, 3*p.width); 
		rotY = p.random(-2*p.height, 2*p.height);
		rotZ = p.random(-2*p.width, 3*p.width);
		cp = _cp;
		updatePyramid();
		reColor();
	}

	void updatePyramid() {
		float base, top;
		base = -pyHeight/2; top = pyHeight/2;
				
		v[0] = new PVector(0, top, 0);
		v[1] = new PVector(pyRadius*PApplet.cos(PConstants.HALF_PI), base, pyRadius*PApplet.sin(PConstants.HALF_PI));  
		v[2] = new PVector(pyRadius*PApplet.cos(PConstants.PI), base, pyRadius*PApplet.sin(PConstants.PI));            
		v[3] = new PVector(pyRadius*PApplet.cos(1.5f*PConstants.PI), base+pyHeight/4, pyRadius*PApplet.sin(1.5f*PConstants.PI));  
		v[4] = new PVector(pyRadius*PApplet.cos(PConstants.TWO_PI), base, pyRadius*PApplet.sin(PConstants.TWO_PI));    
		
		// Normalize base points to the surface of a sphere
		for (int i = 1; i < v.length; i++) {
			v[i].normalize();
			v[i].mult(base);
		}
		// Do the same for the top
		v[0].normalize();
		v[0].mult(top);
	}

	void update(float d) {		
		//scale it instead � no pyramid update needed
		scl = 1.0f+d/2.0f;
		// Display all pyramids/only the ones which were triggered
		display(d);
		// change colors
		if(p.frameCount % 240 == 0) reColor();
	}
	
	void display(float d) {
		transparency = 245;
 		p.pushMatrix(); 
			p.translate(pos.x, pos.y, -250);
			p.scale(scl);
			// Rotate pyramids
			p.rotateY(rotY + p.frameCount*0.005f); 
			p.rotateX(rotX - p.frameCount*0.004f); 
			p.rotateZ(rotZ - p.frameCount*0.003f);
			// draw the 4 side triangles of the pyramid
			p.stroke(255, PApplet.map(d, 0, 1, 15, 100));
			// Main side
			p.beginShape(PConstants.TRIANGLE_FAN); 
				for (int i=0; i<5; i++) {
					p.fill(c[i], transparency);
					p.vertex(v[i].x, v[i].y, v[i].z); 
				}
				p.fill(c[1], transparency);
				// add the 'first base vertex' to close the shape
				p.vertex(v[1].x, v[1].y, v[1].z);
			p.endShape();
			// Flipped side
			p.beginShape(PConstants.TRIANGLE_FAN); 
				for (int i=0; i<5; i++) {
					p.fill(c[i], transparency);
					if(i==0) p.vertex(v[i].x, -v[i].y/2, v[i].z); else p.vertex(v[i].x, v[i].y, v[i].z); 
				}
				p.fill(c[1], transparency);
				// add the 'first base vertex' to close the shape
				p.vertex(v[1].x, v[1].y, v[1].z, 0.0f, 0.0f);
			p.endShape();
		p.popMatrix(); 
	}
	
	void reColor() {
		for (int i = 0; i < c.length; i++) {
			c[i] = cp[(int)p.random(13)];
		}
	}

}
