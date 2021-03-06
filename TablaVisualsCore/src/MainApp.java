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
    Key[] keys = new Key [14];
    float amplitude;
    int[] freqs = {67, 63, 62, 60, 56, 55, 51, 50, 48, 44, 43, 39, 38, 36};
    boolean isGrid, showConsole, useSustain = false;
    boolean doFade = true;
    int fade = 25;
    String messageString = "";
    
    //**********************************************************
    // SETUP
    //**********************************************************
    public void setup() {
        size(640, 640);
        smooth();
        rectMode(CENTER);
        
        MidiBus.list();
        myBus = new MidiBus(this, 0, 0); //"Java Sound Synthesizer"
        
        // Build Note Handlers
        for (int i = 0; i < notes.length; i++) {
			notes[i] = new Note(this, i);
		}
        
        for (int i = 0; i < keys.length; i++) {
			keys[i] = new Key(this, i, freqs[i]);
		}
        
        // Setup liveOSC
        oscP5 = new OscP5(this, 9001);
        myRemoteLocation = new NetAddress("localhost", 9000);
        oscP5.plug(this, "meters", "/live/track/meter");
        oscP5.plug(this, "master", "/live/master/meter");
    }
 
    
    //**********************************************************
    // DRAW
    //**********************************************************
    public void draw() {
        if (doFade) { 
        	background(0, 43, 54);
        } else {
        	fill(0, 43, 54, fade);
        	rect(width/2, height/2, width, height);
        	noStroke();
        }
        if (isGrid) {
	         // Update all midi notes 
	        for (int i = 0; i < notes.length; i++) {
				notes[i].update(amplitude, isGrid, useSustain);
			}
        } else {   
	        //  Update midi notes and render keys
	        for (int i = 0; i < keys.length; i++) {
	        	int ik = keys[i].freq;
	        	notes[ik].update(amplitude, false, useSustain);
	        	keys[i].update(notes[ik].ampNow, notes[ik].isNoteOn);
			}
        }
        
        if (showConsole) {
			fill(0, 43, 54);
	    	rect(width/2, height/2, width, height);
	    	noStroke();
	    	fill(220, 50, 47);
			text(messageString, width/2, height/2);
		}
    }
    
    
    //**********************************************************
    // Handle MIDI Calls
    //**********************************************************
    public void noteOn(int channel, int pitch, int velocity) {
    	println("note on: "+pitch +" "+ velocity+ " " + amplitude);
    	notes[pitch].on(amplitude);
	}

    public void noteOff(int channel, int pitch, int velocity) {
		println("note off: "+pitch +" "+ velocity);
		notes[pitch].off(amplitude);
	}
    
    
    //**********************************************************
    // Handle OSC Calls
    //**********************************************************
    public void oscEvent(OscMessage theOscMessage) {
    	println("addrpattern: "+theOscMessage.addrPattern());
    	messageString = "addrpattern: "+theOscMessage.addrPattern();
	}
    
    public void meters(int track, int channel, float value) {
		amplitude = value;
		println("track: "+track+" channel: "+ channel+" value: "+value);
		println("-----");
    }
    
    public void master(int channel, float value) {
//		amplitude = value;
//		println("channel: "+ channel+" value: "+value);
//		println("****");
    }
    
    
    //**********************************************************
    // Input Handelers
    //**********************************************************
    public void keyPressed() {
    	if (key == 'm' || key == 'M') {
    		isGrid = !isGrid;
    	}
    	if (key == 's' || key =='S') {
    		useSustain = !useSustain;
    	}
    	
    	if (key == 'f' || key == 'F') {
    		doFade = !doFade;
    	}
    	
    	if (key == 'c' || key == 'C') {
    		showConsole = !showConsole;
    	}
    	
    	if (keyCode == UP) {
    		if (fade<255) fade += 5;
    	} 
    	if (keyCode == DOWN) {
    		if (fade>0) fade-=5;
    	}
    }
    
    
    //**********************************************************
    // MAIN
    //**********************************************************
    public static void main(String args[]) {
    	PApplet.main(new String[] { /*"--present",*/ "MainApp" });
    }
}







