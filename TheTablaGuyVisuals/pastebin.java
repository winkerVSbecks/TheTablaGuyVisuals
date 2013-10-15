import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

		// Inward
//		v[0] = new PVector(0, origPyHeight, 0);
//		v[1] = new PVector(pyRadius*PApplet.cos(PConstants.HALF_PI), pyHeight, pyRadius*PApplet.sin(PConstants.HALF_PI));  // base point 1
//		v[2] = new PVector(pyRadius*PApplet.cos(PConstants.PI), pyHeight, pyRadius*PApplet.sin(PConstants.PI));            // base point 2
//		v[3] = new PVector(pyRadius*PApplet.cos(1.5f*PConstants.PI), pyHeight, pyRadius*PApplet.sin(1.5f*PConstants.PI));  // base point 3
//		v[4] = new PVector(pyRadius*PApplet.cos(PConstants.TWO_PI), pyHeight, pyRadius*PApplet.sin(PConstants.TWO_PI));    // base point 4
//		
//		// Normalize points to the surface of a square
//		for (int i = 1; i < v.length; i++) {
//			v[0].normalize();
//			v[0].mult(pyHeight);
//		}
//		
//		v[0].normalize();
//		v[0].mult(origPyHeight);


	void updatePyramid() {
		float base, top;
		if(Properties.IS_INWARD) { base = pyHeight; top = origPyHeight; }
		else { base = origPyHeight; top = pyHeight; }
		
//		if(Properties.IS_INWARD) { base = pyHeight/2; top = -pyHeight/2; }
//		else { base = -pyHeight/2; top = pyHeight/2; }
		
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