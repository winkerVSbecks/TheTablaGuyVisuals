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
	int activeScene = 0;
	int trackToMonitor;
	PyramidScene pyramidScene;
	RainScene rainScene;

	float rotation=0;
	float[] rots = { random(1), random(1), random(1) };

	// HUD
	PGraphics gui;
	int on = 0xFF2ECC71; 
	int off = 0xFFE74C3C;


	//**********************************************************
	// SETUP
	//**********************************************************
	public void setup() {
		size(1000, 700, P3D);
		//    	size(1440, 900, P3D);
		smooth(6);
		rectMode(CENTER);
		noStroke();

		gui = createGraphics(264, 153);
		updateGUI();

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

		// Define and build the various Scenes/Songs
		pyramidScene = new PyramidScene(this,
				"Flying Pyramids",
				notes,
				new int[] { 67, 63, 62, 60, 56, 55, 51, 50, 48, 44, 43, 39, 38, 36 },
				1);
		rainScene = new RainScene(this,
				"Rain Drops and Umbrella",
				0);

		// Choose initial track to monitor
		//trackToMonitor = pyramidScene.track;
		trackToMonitor = rainScene.track;
		activeScene = 2;

		// perspective to see close shapes
		perspective(PConstants.PI/3.0f, (float) width/height, 1, 1000000);

		// Textures
		for (int i = 0; i < Properties.textures.length; i++) {
			Properties.tex[i] = loadImage(Properties.textures[i]);
		}
		textureMode(PConstants.NORMAL);

		// Lighting
		ambientLight(253, 254, 249);
		specular(253, 254, 249);
	} 


	//**********************************************************
	// DRAW
	//**********************************************************
	public void draw() {
		// Camera
		//    	float orbitRadius = 1000;
		//    	float ypos = sin(radians(rotation*rots[0]))*orbitRadius; 
		//    	float xpos = cos(radians(rotation*rots[1]))*orbitRadius;
		//    	float zpos = cos(radians(rotation*rots[2]))*orbitRadius;
		//    	camera(xpos, ypos, zpos, width/2, height/2, -4000, 0, -1, 0);
		//    	rotation++;

		// Update all Midi Notes
		for (int i = 0; i < notes.length; i++) {
			notes[i].update();
		}

		// Render the active scene
		switch (activeScene) {
		case 1:
			 pyramidScene.draw();
			 // Display window title with frame-rate, etc.
			 frame.setTitle("Tabla Visuals | " + pyramidScene.name + " | " + (int)frameRate + " fps"); 
			break;
		case 2:
			rainScene.draw(Properties.AMPLITUDE);
			// Display window title with frame-rate, etc.
			frame.setTitle("Tabla Visuals | " + rainScene.name + " | " + (int)frameRate + " fps"); 
			break;
		default:
			System.out.print("Scene" + activeScene  + ": not found");
			break;
		}

		// Draw GUI
		noLights();
		if(Properties.SHOW_GUI) image(gui, width-304, height-193); 
	}


	public void changeScene(int newSceneNumber) {
		activeScene = newSceneNumber;
	}


	// Draw and update the GUI to show a list of properties available and how to turn them on/off
	public void updateGUI() {
		int spacing = 20;
		gui.beginDraw();
		gui.noStroke();
		gui.background(236, 240, 241);
		gui.stroke(189, 195, 199);
		gui.fill(236, 240, 241);
		gui.rect(0, 0, gui.width-1, gui.height-1);
		// Properties
		gui.fill(0xFF2C3E50);
		gui.text("Pyramids Sphere: ", 10, spacing*1);
		gui.text("Rotate Pyramids In Sphere", 10, spacing*2);
		gui.text("Show/Hide Un-triggered Pyramids: ", 10, spacing*3);
		gui.text("Toggle Inward/Outward: ", 10, spacing*4);
		gui.text("Lone Pyramid: ", 10, spacing*5);
		gui.text("Flying Pyramids: ", 10, spacing*6);
		gui.text("Show/Hide Help Menu: ", 10, spacing*7);
		// Keys and states
		gui.fill(Properties.PYRAMIDS_SPHERE ? on: off); gui.text("S", 245, spacing*1);
		gui.fill(Properties.DO_ROTATE_PYRAMIDS ? on: off); gui.text("R", 245, spacing*2);
		gui.fill(Properties.SHOW_UNTRIGGERED ? on: off); gui.text("T", 245, spacing*3);
		gui.fill(Properties.IS_INWARD ? on: off); gui.text("I", 245, spacing*4);
		gui.fill(Properties.LONE_PYRAMID ? on: off); gui.text("L", 245, spacing*5);
		gui.fill(Properties.FLYING_PYRAMIDS ? on: off); gui.text("F", 245, spacing*6); 
		gui.fill(0xFF9B59B6); gui.text("?", 245, spacing*7);
		gui.endDraw();
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
			Properties.AMPLITUDE = value;
		}
		// println("track: "+track+" channel: "+ channel+" value: "+value);
	}


	
	//**********************************************************
	// Input Handlers
	//**********************************************************
	public void keyPressed() {
		// Audio
		if (key == 'u' || key =='U') Properties.USE_SUSTAIN = !Properties.USE_SUSTAIN;
		// Pyramids
		if(key == 't' || key == 'T') Properties.SHOW_UNTRIGGERED = !Properties.SHOW_UNTRIGGERED; 
		if(key == 'i' || key =='I') Properties.IS_INWARD = !Properties.IS_INWARD;
		if(key == 'r' || key =='R') Properties.DO_ROTATE_PYRAMIDS = !Properties.DO_ROTATE_PYRAMIDS;
		if(key == 'b' || key == 'B') Properties.IS_TEXTURE = !Properties.IS_TEXTURE;
		if(key == 'p' || key == 'P') {
			Properties.SPECTRUM_PYRAMID = true;
			Properties.PYRAMIDS_SPHERE = Properties.LONE_PYRAMID = Properties.FLYING_PYRAMIDS = false;
		}
		if(key == 's' || key =='S') { 
			Properties.PYRAMIDS_SPHERE = true; 
			Properties.SPECTRUM_PYRAMID = Properties.LONE_PYRAMID = Properties.FLYING_PYRAMIDS = false;
		}
		if(key == 'f' || key =='F') {
			Properties.FLYING_PYRAMIDS = true;
			Properties.SPECTRUM_PYRAMID  = Properties.LONE_PYRAMID = Properties.PYRAMIDS_SPHERE = false;
		}
		if(key == 'l' || key =='l') {
			Properties.LONE_PYRAMID = true;
			Properties.SPECTRUM_PYRAMID  = Properties.FLYING_PYRAMIDS = Properties.PYRAMIDS_SPHERE = false;
		}
		// Misc.
		if (key == '?') Properties.SHOW_GUI = !Properties.SHOW_GUI;
		// Update GUI to show state of various properties
		updateGUI();
		
		// Select scenes
		if(key == '1') changeScene(1);
		if(key == '2') changeScene(2);
	}


	//**********************************************************
	// MAIN
	//**********************************************************
	public static void main(String args[]) {
		PApplet.main(new String[] { /*"--present",*/ "MainApp" }); /*"--present",*/
	}
}






