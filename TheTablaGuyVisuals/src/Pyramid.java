/***********************************************
 	Based on:
 	Custom 3D Geometry by Amnon Owed (May 2013)
 	https://github.com/AmnonOwed
 	http://vimeo.com/amnon
 ***********************************************/

import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

public class Pyramid {
	PApplet p;
	PVector[] v;
	int[] c = new int[5]; 
	float origPyHeight, origPyRadius;
	float pyHeight, pyRadius;
	float speed, transparency; 
	PVector pos; 
	float rotX, rotY, rotZ; 
	float MAXSPEED = 50;
	int solidColor;
	float scl = 1.0f;
	int tt;

	Pyramid(PApplet _p, float _pyHeight, float _pyRadius, int _c, int _tt) {
		
		p = _p;
		v = new PVector[5];
		pyHeight = _pyHeight;
		origPyHeight = pyHeight;  //p.random(1.0f, 3.0f)*pyHeight; 
		pyRadius = 2.0f*_pyRadius;
		origPyRadius = pyRadius; 
		speed = p.random(MAXSPEED/8, MAXSPEED); 
//		pos = new PVector(p.width/2+p.random(-p.width, p.width), p.height/2+p.random(-p.height, p.height), -1000);
		pos = new PVector(p.width/2, p.height/2, -2000);
		transparency = 255;
		rotX = p.random(-2*p.width, 3*p.width); 
		rotY = p.random(-2*p.height, 2*p.height);
		rotZ = p.random(-2*p.width, 3*p.width);
		solidColor = _c;
		tt = _tt;
		updatePyramid();
	}

	void updatePyramid() {
		float base, top;
//		if(Properties.IS_INWARD) { base = pyHeight; top = origPyHeight; }
//		else { base = origPyHeight; top = pyHeight; }
		
		if(Properties.IS_INWARD) { base = origPyHeight + pyHeight/3; top = origPyHeight - 2*pyHeight/3; }
		else { base = origPyHeight - pyHeight/3; top = origPyHeight + 2*pyHeight/3;  }
				
		v[0] = new PVector(0, top, 0);
		v[1] = new PVector(pyRadius*PApplet.cos(PConstants.HALF_PI), base, pyRadius*PApplet.sin(PConstants.HALF_PI));  // base point 1
		v[2] = new PVector(pyRadius*PApplet.cos(PConstants.PI), base, pyRadius*PApplet.sin(PConstants.PI));            // base point 2
		v[3] = new PVector(pyRadius*PApplet.cos(1.5f*PConstants.PI), base, pyRadius*PApplet.sin(1.5f*PConstants.PI));  // base point 3
		v[4] = new PVector(pyRadius*PApplet.cos(PConstants.TWO_PI), base, pyRadius*PApplet.sin(PConstants.TWO_PI));    // base point 4

		
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
		// Modify height and radius based on amplitude 
//		pyHeight = (1.0f+d+0.05f)*origPyHeight;
//		pyRadius = (1.0f+d/2.0f)*origPyRadius;
		scl = 1.0f+d*0.75f;
		
		// Recalculate the pyramid mesh
//		updatePyramid();
		// Display all pyramids/only the ones which were triggered 
//		if(Properties.SHOW_UNTRIGGERED) { display(d); } 
//		else { /*if(pyHeight>=1.1f*origPyHeight) display(d);*/ if(d>0.1f) display(d); }
		
		if(Properties.SHOW_UNTRIGGERED) { displayTextured(d); } 
		else { if(d>0.1f) displayTextured(d); }
		
		// if(p.frameCount%60 == 0) reset();
	}
	
	void display(float d) {
//		transparency = PApplet.map(pos.z, -500, 500, 0, 255);
//		transparency = PApplet.map(d, 0, 1, 0, 255);
		transparency = 175;
 		p.pushMatrix(); 
			p.translate(pos.x, pos.y, -2000);
			p.scale(scl);
			// Rotate pyramids
			if(Properties.DO_ROTATE_PYRAMIDS) {
				p.rotateY(rotY + p.frameCount*0.02f); 
				p.rotateX(rotX + p.frameCount*0.01f); 
				p.rotateZ(rotZ + p.frameCount*0.01f);
			} else {
				p.rotateY(rotY); 
				p.rotateX(rotX); 
				p.rotateZ(rotZ); 
			}
			// draw the 4 side triangles of the pyramid
			p.fill(solidColor, transparency);
			if(d>0.5) {
				p.stroke(0xFFECF0F1);
			} else {
				p.stroke(255, 15);
			}
			p.beginShape(PConstants.TRIANGLE_FAN); 
				for (int i=0; i<5; i++) {
					p.vertex(v[i].x, v[i].y, v[i].z); 
				}
				// add the 'first base vertex' to close the shape
				p.vertex(v[1].x, v[1].y, v[1].z, 0.0f, 0.0f);
			p.endShape(); 
			// draw the base quad of the pyramid
			p.beginShape(PConstants.QUADS);
				for (int i=1; i<5; i++) {
					p.vertex(v[i].x, v[i].y, v[i].z);
				}
			p.endShape(); 
		p.popMatrix(); 
	}
	
	void displayTextured(float d) {
 		p.pushMatrix(); 
			p.translate(pos.x, pos.y, -2000);
			p.scale(scl);
			// Rotate pyramids
			if(Properties.DO_ROTATE_PYRAMIDS) {
				p.rotateY(rotY + p.frameCount*0.02f); 
				p.rotateX(rotX + p.frameCount*0.01f); 
				p.rotateZ(rotZ + p.frameCount*0.01f);
			} else {
				p.rotateY(rotY); 
				p.rotateX(rotX); 
				p.rotateZ(rotZ); 
			}
			// draw the 4 side triangles of the pyramid
			p.noFill();
			p.stroke(0x22ECF0F1);
			p.beginShape(PConstants.TRIANGLE_FAN); 
				p.texture(Properties.tex[tt]);
				for (int i=0; i<5; i++) {
					float uu = 1.0f;
					float vv = 1.0f;
					
					if(i != 0) {
						if(i%2 == 0) {
							uu = 1.0f; vv = 0.0f; 
						} else {
							uu = 0.0f; vv = 0.0f; 
						}
					}
					
					p.vertex(v[i].x, v[i].y, v[i].z, uu, vv); 
				}
				// add the 'first base vertex' to close the shape
				p.vertex(v[1].x, v[1].y, v[1].z, 0.0f, 0.0f);
			p.endShape(); 
			// draw the base quad of the pyramid
			p.beginShape(PConstants.QUADS);
				p.texture(Properties.tex[tt]);
				p.vertex(v[1].x, v[1].y, v[1].z, 0.0f, 0.0f);
				p.vertex(v[2].x, v[2].y, v[2].z, 1.0f, 0.0f);
				p.vertex(v[3].x, v[3].y, v[3].z, 0.0f, 1.0f);
				p.vertex(v[4].x, v[4].y, v[4].z, 1.0f, 1.0f);
			p.endShape(); 
		p.popMatrix(); 
	}
	

	void reset() {
		c[0] = p.color(p.random(150, 255), p.random(150, 255), p.random(150, 255)); 
		// randomly set the 4 colors in the base of the shape
		for (int i=1; i<5; i++) {
			c[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}
	
}
