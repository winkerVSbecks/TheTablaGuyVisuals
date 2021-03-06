package processingTest;

import processing.core.*;
import themidibus.*;
import oscP5.*;
import controlP5.*;

@SuppressWarnings("serial")
public class MainApp extends PApplet {
	 
    float theta = 0;
    float speed = 0.1f;
 
    public void setup() {
        size(640, 480, P3D);
        smooth();
    }
 
    public void draw() {
        background(0, 43, 54);
        if (frameCount % 30 == 0) {
        	speed = random(0.01f, 0.05f);
        }
        theta += speed;
        
        fill(7, 54, 66);
        stroke(220, 50, 47);
        pushMatrix();
        translate(width * 0.5f, height * 0.5f, 0);
        rotateY(theta);
        rotateX(theta);
        box(100);
        popMatrix();
    }
    
    public static void main(String args[]) {
      PApplet.main(new String[] { /*"--present",*/ "processingTest.MainApp" });
    }
}