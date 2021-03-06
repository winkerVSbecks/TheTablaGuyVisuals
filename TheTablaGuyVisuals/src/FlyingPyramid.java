/***********************************************
 	Based on:
 	Custom 3D Geometry by Amnon Owed (May 2013)
 	https://github.com/AmnonOwed
 	http://vimeo.com/amnon
 ***********************************************/
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

public class FlyingPyramid {
	PApplet p;
	PVector[] v;
	int[] c = new int[5]; 
	float ptHeight, ptRadius;
	float speed, transparency; 
	float x, y, z; 
	float MAXSPEED = 50;

	FlyingPyramid(PApplet _p, float ptHeight, float ptRadius) {
		
		p = _p;
		v = new PVector[5];
		this.ptHeight = ptHeight/2; 
		this.ptRadius = ptRadius; 
		speed = p.random(MAXSPEED/8, MAXSPEED); 
		createPyramid(); 
		z = p.random(-5000, 750); 
		reset(); 
	}

	void createPyramid() {
		v[0] = new PVector(0, -ptHeight, 0);                                      							               // top of the pyramid
		v[1] = new PVector(ptRadius*PApplet.cos(PConstants.HALF_PI), ptHeight, ptRadius*PApplet.sin(PConstants.HALF_PI));  // base point 1
		v[2] = new PVector(ptRadius*PApplet.cos(PConstants.PI), ptHeight, ptRadius*PApplet.sin(PConstants.PI));            // base point 2
		v[3] = new PVector(ptRadius*PApplet.cos(1.5f*PConstants.PI), ptHeight, ptRadius*PApplet.sin(1.5f*PConstants.PI));  // base point 3
		v[4] = new PVector(ptRadius*PApplet.cos(PConstants.TWO_PI), ptHeight, ptRadius*PApplet.sin(PConstants.TWO_PI));    // base point 4
	}
	
	void update(float d) {
		respondToMusic(d);
		fly();
		display(d);
	}
	
	void respondToMusic(float d) {
		float adjustedHeight = (1.0f+d)*ptHeight;
		float adjustedRadius = (1.0f+d/2.0f)*ptHeight;
		v[0] = new PVector(0, -adjustedHeight, 0);                                      							               
		v[1] = new PVector(adjustedRadius*PApplet.cos(PConstants.HALF_PI), adjustedHeight, adjustedRadius*PApplet.sin(PConstants.HALF_PI));
		v[2] = new PVector(adjustedRadius*PApplet.cos(PConstants.PI), adjustedHeight, adjustedRadius*PApplet.sin(PConstants.PI));          
		v[3] = new PVector(adjustedRadius*PApplet.cos(1.5f*PConstants.PI), adjustedHeight, adjustedRadius*PApplet.sin(1.5f*PConstants.PI));
		v[4] = new PVector(adjustedRadius*PApplet.cos(PConstants.TWO_PI), adjustedHeight, adjustedRadius*PApplet.sin(PConstants.TWO_PI));  
	}

	void fly() {
		z += speed; 
		// if beyond the camera, reset() and start again
		if (z > 750) { z = -5000; reset(); } 
		// far away slowly increase the transparency, within range is fully opaque
		transparency = z < -2500 ? PApplet.map(z, -5000, -2500, 0, 255) : 255; 
	}
	
	void display(float d) {
		p.pushMatrix(); 
			if(d<0.1) {
				p.strokeWeight(1);
				p.stroke(0x44BDC3C7);
				p.fill(0xFFECF0F1);
				
				p.translate(x, y, z); 
				p.rotateY(x + p.frameCount*0.01f); 
				p.rotateX(y + p.frameCount*0.02f); 
		
				// draw the 4 side triangles of the pyramid
				p.beginShape(PConstants.TRIANGLE_FAN); 
					for (int i=0; i<5; i++) {
						p.vertex(v[i].x, v[i].y, v[i].z); // set the vertices based on the object coordinates defined in the createShape() method
					}
					// add the 'first base vertex' to close the shape
					p.vertex(v[1].x, v[1].y, v[1].z);
				p.endShape(); 
		
				// draw the base QUAD of the pyramid
				p.beginShape(PConstants.QUADS);
					for (int i=1; i<5; i++) {
						p.vertex(v[i].x, v[i].y, v[i].z);
					}
				p.endShape(); 
			} else {
				p.stroke(255, 25);
				p.translate(x, y, z); 
				p.rotateY(x + p.frameCount*0.01f); 
				p.rotateX(y + p.frameCount*0.02f); 
		
				// draw the 4 side triangles of the pyramid
				p.beginShape(PConstants.TRIANGLE_FAN); 
					for (int i=0; i<5; i++) {
						p.fill(c[i], transparency); // use the color, but with the given z-based transparency
						p.vertex(v[i].x, v[i].y, v[i].z); // set the vertices based on the object coordinates defined in the createShape() method
					}
					// add the 'first base vertex' to close the shape
					p.fill(c[1], transparency);
					p.vertex(v[1].x, v[1].y, v[1].z);
				p.endShape(); 
		
				// draw the base QUAD of the pyramid
				p.fill(c[1], transparency);
				p.beginShape(PConstants.QUADS);
					for (int i=1; i<5; i++) {
						p.vertex(v[i].x, v[i].y, v[i].z);
					}
				p.endShape(); 
			}

		p.popMatrix(); 
	}

//	void display(float d) {
//		p.pushMatrix(); 
//			if(d>0.5) {
//				p.strokeWeight(2);
//				p.stroke(255, 255, 0, 55);
//			} else {
////				p.stroke(255, 10);
//				p.noStroke();
//			}
//			p.translate(x, y, z); 
//			p.rotateY(x + p.frameCount*0.01f); 
//			p.rotateX(y + p.frameCount*0.02f); 
//	
//			// draw the 4 side triangles of the pyramid
//			p.beginShape(PConstants.TRIANGLE_FAN); 
//				for (int i=0; i<5; i++) {
//					p.fill(c[i], transparency); // use the color, but with the given z-based transparency
//					p.vertex(v[i].x, v[i].y, v[i].z); // set the vertices based on the object coordinates defined in the createShape() method
//				}
//				// add the 'first base vertex' to close the shape
//				p.fill(c[1], transparency);
//				p.vertex(v[1].x, v[1].y, v[1].z);
//			p.endShape(); 
//	
//			// draw the base QUAD of the pyramid
//			p.fill(c[1], transparency);
//			p.beginShape(PConstants.QUADS);
//				for (int i=1; i<5; i++) {
//					p.vertex(v[i].x, v[i].y, v[i].z);
//				}
//			p.endShape(); 
//
//		p.popMatrix(); 
//	}
	
	void reset() {
		x = p.random(-2*p.width, 3*p.width); 
		y = p.random(-p.height, 2*p.height); 
		c[0] = p.color(p.random(150, 255), p.random(150, 255), p.random(150, 255)); 
		// randomly set the 4 colors in the base of the shape
		for (int i=1; i<5; i++) {
			c[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}
	
}
