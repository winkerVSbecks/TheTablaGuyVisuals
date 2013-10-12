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

	Pyramid(PApplet _p, float _pyHeight, float _pyRadius, int _c) {
		
		p = _p;
		v = new PVector[5];
		origPyHeight = pyHeight = _pyHeight; 
		origPyRadius = pyRadius = _pyRadius; 
		speed = p.random(MAXSPEED/8, MAXSPEED); 
		updatePyramid(); 
		pos = new PVector(p.width/2, p.height/2, -1000);
		transparency = 255;
		rotX = p.random(-2*p.width, 3*p.width); 
		rotY = p.random(-2*p.height, 2*p.height);
		rotZ = p.random(-2*p.width, 3*p.width);
		solidColor = _c;
	}

	void updatePyramid() {
//		v[0] = Properties.PYRAMID_MODE == 0 ? new PVector(0, pyHeight, 0) : new PVector(0, -pyHeight, 0);
		v[0] = new PVector(0, pyHeight, 0);
		v[1] = new PVector(pyRadius*PApplet.cos(PConstants.HALF_PI), origPyHeight, pyRadius*PApplet.sin(PConstants.HALF_PI));  // base point 1
		v[2] = new PVector(pyRadius*PApplet.cos(PConstants.PI), origPyHeight, pyRadius*PApplet.sin(PConstants.PI));            // base point 2
		v[3] = new PVector(pyRadius*PApplet.cos(1.5f*PConstants.PI), origPyHeight, pyRadius*PApplet.sin(1.5f*PConstants.PI));  // base point 3
		v[4] = new PVector(pyRadius*PApplet.cos(PConstants.TWO_PI), origPyHeight, pyRadius*PApplet.sin(PConstants.TWO_PI));    // base point 4
		
		// Normalize points to the surface of a square
		for (int i = 1; i < v.length; i++) {
			v[0].normalize();
			v[0].mult(origPyHeight);
		}
		
		v[0].normalize();
		v[0].mult(pyHeight);
	}

	void update(float d) {
		pyHeight = (1.0f+d+0.05f)*origPyHeight;
		pyRadius = (1.0f+d/2.0f)*origPyRadius;
		updatePyramid();
		switch (Properties.PYRAMID_MODE) {
		case 1:
			if(pyHeight>=1.1f*origPyHeight) display(d);
			break;
		case 2:
			display(d);
			break;
		default:
			if(pyHeight>=1.1f*origPyHeight) display(d);
			break;
		}
		
//		if(p.frameCount%60 == 0) reset();
	}
	
	void display(float d) {
		p.pushMatrix(); 
			p.translate(pos.x, pos.y, -2000);
			
			if(Properties.ROTATE_PYRAMIDS) {
				p.rotateY(rotY + p.frameCount*0.02f); 
				p.rotateX(rotX + p.frameCount*0.01f); 
				p.rotateZ(rotZ + p.frameCount*0.01f);
			} else {
				p.rotateY(rotY); 
				p.rotateX(rotX); 
				p.rotateZ(rotZ); 
			}
			
			// draw the 4 side triangles of the pyramid
			p.fill(solidColor);
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
				p.vertex(v[1].x, v[1].y, v[1].z);
			p.endShape(); 
	
			// draw the base quad of the pyramid
			p.beginShape(PConstants.QUADS);
				for (int i=1; i<5; i++) {
					p.vertex(v[i].x, v[i].y, v[i].z);
				}
			p.endShape(); 

		p.popMatrix(); 
	}

	void displayGradient() {
//		transparency = pos.z < -2500 ? PApplet.map(pos.z, -5000, -2500, 0, 255) : 255;
		p.pushMatrix(); 

			p.translate(pos.x, pos.y, -2000);
			
			if(Properties.ROTATE_PYRAMIDS) {
				p.rotateY(rotY + p.frameCount*0.02f); 
				p.rotateX(rotX + p.frameCount*0.01f); 
				p.rotateZ(rotZ + p.frameCount*0.01f);
			} else {
				p.rotateY(rotY); 
				p.rotateX(rotX); 
				p.rotateZ(rotZ); 
			}
			
			p.fill(0);
			// draw the 4 side triangles of the pyramid
			p.beginShape(PConstants.TRIANGLE_FAN); 
				for (int i=0; i<5; i++) {
					// use the color, but with the given z-based transparency
					p.fill(p.random(255), transparency); 
					// set the vertices based on the object coordinates defined in the createShape() method
					p.vertex(v[i].x, v[i].y, v[i].z); 
				}
				// add the 'first base vertex' to close the shape
				p.fill(c[1], transparency);
				p.vertex(v[1].x, v[1].y, v[1].z);
			p.endShape(); 
	
			// draw the base quad of the pyramid
			p.fill(c[1], transparency);
			p.beginShape(PConstants.QUADS);
				for (int i=1; i<5; i++) {
					p.vertex(v[i].x, v[i].y, v[i].z);
				}
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
