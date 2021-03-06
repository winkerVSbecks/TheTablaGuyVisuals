/**********************************************************************************************

 	Each scene consists of the following parameter:
 		- Expected MIDI notes to monitor (coming from Ableton)
 		- Audio channel to monitor
 		- The type of visuals to render

**********************************************************************************************/	

import java.util.ArrayList;

import processing.core.*;

public class PyramidScene {
	
	int track;
	int[] range;
	Note[] notes;
	Note[] filteredNotes = new Note[14];
	PApplet p;
	String name;
	ArrayList<Cluster> clusters;
	LonePyramid lonePyramid;
	SpectrumPyramid spectrumPyramid;
	PShape ball;
	boolean once = true;
		
	int colors[] = { 0xFF2ECC71, 0xFF3498DB, 0xFF9B59B6, 0xFF34495E,
					 0xFFF1C40F, 0xFFE67E22, 0xFFE74C3C, 0xFF95A5A6,
					 0xFFAB948C, 0xFFF2F2F2, 0xFFFBD199, 0xFF981B48,
					 0xFFFBF6A7, 0xFFF8B39B };  
	
	public PyramidScene(PApplet _p, String _name, Note[] _notes, int[] _range, int _track) {
		name = _name;
		p = _p;
		notes = _notes;
		range = _range;
		track = _track;
		// Build clusters
		clusters = new ArrayList<Cluster>();
		for(int i = 0; i < range.length; i++) {
			Note note = notes[range[i]];
			clusters.add( new Cluster(p, note, colors[i], i) );
			filteredNotes[i] = note;
		}
		// Build lone pyramid
		float r = p.random(25, 200);
	    float f = p.random(2, 5);
	    lonePyramid = new LonePyramid(p, f*r, r, colors);
	    // Build Spectrum Pyramid
	    spectrumPyramid = new SpectrumPyramid(p, f*r, r, colors, filteredNotes);
	}
	
	public void draw() {
		if(Properties.PYRAMIDS_SPHERE) {
			// p.background(255, 255, 250);
			// for flat shader
			//p.background(255, 255, 82);
			// for other pattern
			// p.background(230, 240, 168);
			p.background(0xFFECF0F1);
		} else {
			// lightish grey
			p.background(0xFFECF0F1);
		}
		
		if(once) { ball = createIcosahedron(6); once = false; }
		if(Properties.LONE_PYRAMID) {
			lonePyramid.update(notes[range[0]].getAttack());
		} else if(Properties.SPECTRUM_PYRAMID) {
			spectrumPyramid.update(notes[range[0]].getAttack());
		} else {
			for(Cluster cluster: clusters) {
				cluster.draw();
			}
			// Textured Sphere
			if(Properties.PYRAMIDS_SPHERE) {
				p.pushMatrix();
					p.translate(p.width/2, p.height/2, -2000);
					p.rotateY(p.frameCount*0.02f); 
					p.rotateX(p.frameCount*0.01f); 
					p.rotateZ(p.frameCount*0.01f);
					if(Properties.IS_TEXTURE) { 
						p.pointLight(255, 255, 82, 2*(p.width*PApplet.sin(p.frameCount*0.02f)-p.width/2), 2*(p.height*PApplet.cos(p.frameCount*0.03f)-p.height/2), 500);
						p.scale(500); 
						//p.shape(ball); 
					} else { 
						p.noStroke();
						p.scale(1); 
						p.fill(255, 255, 82);
						//p.sphere(550);
					}
				p.popMatrix();
			}
		}
	}
	
	public PShape createIcosahedron(int level) {  
		// The icosahedron is created with positions, normals and texture coordinates in the above class
		Icosahedron ico = new Icosahedron (p, level);
		// Load the texture
		PImage tex = p.loadImage("flat.png"); 
		// Create the initial PShape
		PShape mesh = p.createShape();
		// Define the PShape type: TRIANGLES
		mesh.beginShape(PConstants.TRIANGLES); 
		mesh.noStroke();
		mesh.texture(tex);
		// Put all the vertices, uv texture coordinates and normals into the PShape
		for (int i=0; i<ico.positions.size(); i++) {
			PVector p = ico.positions.get(i);
			PVector t = ico.texCoords.get(i);
			PVector n = ico.normals.get(i);
			mesh.normal(n.x, n.y, n.z);
			mesh.vertex(p.x, p.y, p.z, t.x, t.y);
		}
		mesh.endShape();
		return mesh;
	}
}
