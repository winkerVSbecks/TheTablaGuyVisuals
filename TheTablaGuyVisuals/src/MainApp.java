import java.util.ArrayList;

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
    boolean useSustain;
    ArrayList<Song> songs;
    
    
    //**********************************************************
    // SETUP
    //**********************************************************
    public void setup() {
        size(1000, 700);
        smooth();
        rectMode(CENTER);
        
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
        
        // Build Songs
        songs = new ArrayList<Song>();
        
        songs.add(new Song(this,
        		notes,
				new int[] {67},
				1));
        
        // Choose initial track to monitor
        trackToMonitor = songs.get(activeSong).t;
    } 
    
    //**********************************************************
    // DRAW
    //**********************************************************
    public void draw() {
    	background(255);
    	// Update all Midi Notes
	    for (int i = 0; i < notes.length; i++) {
	    	notes[i].update();
		}
	    
    	songs.get(activeSong).draw(); 
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
//    public void oscEvent(OscMessage theOscMessage) {
//    	println("addrpattern: "+theOscMessage.addrPattern());
//	}
    
    public void meters(int track, int channel, float value) {
    	if (track == trackToMonitor) {
    		amplitude = value;
    	}
    	// println("track: "+track+" channel: "+ channel+" value: "+value);
    }
    
    
    //**********************************************************
    // Input Handelers
    //**********************************************************
    public void keyPressed() {
    	if (key == 's' || key =='S') {
    		useSustain = !useSustain;
    	}
    }
    
    
    //**********************************************************
    // MAIN
    //**********************************************************
    public static void main(String args[]) {
    	PApplet.main(new String[] { /*"--present",*/ "MainApp" });
    }
}






