/***********************************************
 	The Tabla Guy Visuals
 	by Varun Vachhar (Oct 2013)
 	http://lambdafunction.ca
 	
 	Copyright (c) 2013 Varun Vachhar
 	All rights reserved
 ***********************************************/

import netP5.*;
import processing.core.*;
import themidibus.*;
import oscP5.*;

@SuppressWarnings("serial")
public class MainApp extends PApplet {
	 
    MidiBus myBus;
    OscP5 oscP5;
    NetAddress myRemoteLocation;
    
    Note[] notes = new Note [128];
    float amplitude;
    int activeSong = 0;
    int trackToMonitor;
    PyramidScene pyramidScene;
    
    
    //**********************************************************
    // SETUP
    //**********************************************************
    public void setup() {
        size(1000, 700, P3D);
        smooth(6);
        rectMode(CENTER);
        noStroke();
        
        // Setup Midi Listener
        MidiBus.list();
        myBus = new MidiBus(this, 0, 0);
        
        // Setup liveOSC
        oscP5 = new OscP5(this, 9001);
        myRemoteLocation = new NetAddress("localhost", 9000);
        oscP5.plug(this, "meters", "/live/track/meter");
        
        // Build Note Handlers
        for (int i = 0; i < notes.length; i++) {
			notes[i] = new Note(this);
		}
        
        // Define and build the various Songs
        
        pyramidScene = new PyramidScene(this,
        		"Flying Pyramids",
        		notes,
				new int[] { 67, 63, 62, 60, 56, 55, 51, 50, 48, 44, 43, 39, 38, 36 },
				1);
        
        // Choose initial track to monitor
        trackToMonitor = pyramidScene.t;
        
       // perspective to see close shapes
//       perspective(PConstants.PI/3.0f, (float) width/height, 1, 1000000);
    } 
    
    
    //**********************************************************
    // DRAW
    //**********************************************************
    public void draw() {
//    	background(255);
    	// Update all Midi Notes
	    for (int i = 0; i < notes.length; i++) {
	    	notes[i].update();
		}
	    
	    // Render the active scene
   	 	switch (activeSong) {
			case 0:
				pyramidScene.draw();
				// Display window title with frame-rate, etc.
			    frame.setTitle("Tabla Visuals | " + pyramidScene.name + " | " + (int)frameRate + " fps"); 
				break;
			
			case 1:
				break;
	
			case 2:
				break;
				
			default:
				System.out.print("Scene" + activeSong  + ": not found");
				break;
		}
	    
    }
    
    
    public void changeScene(int newSceneNumber) {
    	activeSong = newSceneNumber;
    }
    
    
    //**********************************************************
    // Handle MIDI Calls
    //**********************************************************
    public void noteOn(int channel, int pitch, int velocity) {
    	// println("note on: "+pitch +" "+ velocity);
    	notes[pitch].on();
	}

    public void noteOff(int channel, int pitch, int velocity) {
    	// println("note off: "+pitch +" "+ velocity);
		notes[pitch].off();
	}
    
    
    //**********************************************************
    // Handle OSC Calls
    //**********************************************************
    public void meters(int track, int channel, float value) {
    	if (track == trackToMonitor) {
    		amplitude = value;
    	}
    	 println("track: "+track+" channel: "+ channel+" value: "+value);
    }
    
    
    //**********************************************************
    // Input Handlers
    //**********************************************************
    public void keyPressed() {
    	if (key == 's' || key =='S') Properties.USE_SUSTAIN = !Properties.USE_SUSTAIN;
    	
    	if (key == '1') Properties.PYRAMID_MODE = 1;
    	if (key == '2') Properties.PYRAMID_MODE = 2;
    	if(key == 'r' || key =='R') Properties.ROTATE_PYRAMIDS = !Properties.ROTATE_PYRAMIDS;
    	if(key == 'f' || key =='F')Properties.FLYING_PYRAMIDS = !Properties.FLYING_PYRAMIDS;
    	if(key == 'l' || key =='l')Properties.LONE_PYRAMID = !Properties.LONE_PYRAMID;
    }
    
    
    //**********************************************************
    // MAIN
    //**********************************************************
    public static void main(String args[]) {
    	PApplet.main(new String[] { /*"--present",*/ "MainApp" });
    }
}







