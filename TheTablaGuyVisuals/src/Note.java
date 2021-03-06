/**********************************************************************************************

 	Basically a handler for each MIDI note coming in from Ableton.
 	
 	The note gives access to:
 	 - Is note on or off
 	 - Attack: based on the amplitude from the chosen audio channel (0 - 1)
 	 - Sustain: time needed to fade out once the note turns off
 	   
 	 Sustain can be handles in two ways:
 	 	- Measuring amp. and frame count values on note on and note off. Then calculating the
 	 	  velocity as: (delta amplitude)/(delta frame-count)
 	 	- Simple fade-out at a constant velocity � independent of the amp change.
 	 	
**********************************************************************************************/	

public class Note {
	float ampStart, ampEnd;
	int fStart, fEnd;
	private float attack, sustain;
	boolean isOn;
	MainApp p;
	
	public Note(MainApp _p) {
		p = _p;
    }
	
	public float getAttack() {
		return this.attack;
	}
	
	public float getSustain() {
		return this.sustain;
	}
	
	// Handle MIDI note on
	public void on() {
		isOn = true;
		ampStart = Properties.AMPLITUDE;
		fStart = p.frameCount;
	}
	
	// Handle MIDI note off
	public void off() {
		isOn = false;
		ampEnd = Properties.AMPLITUDE;
		fEnd = p.frameCount;
		sustain = Math.abs(ampStart-ampEnd)/(fEnd-fStart);
	}
	
	// Update attack and sustain values
	public void update() {
		if (isOn) {
			attack = Properties.AMPLITUDE;
		} else {
			if (!Properties.USE_SUSTAIN) {
				if(attack > 0) { attack-=0.1f; 
				} else { attack = 0.0f; }
			} else {
				fadeOut(attack);
			}
			// System.out.println("*** "+attack); 
		}
	}
	
	// Use fade out instead of calculated sustain
	private void fadeOut(float start) {
		attack = 0 - (start*sustain);
		if (attack <= 0) attack = 0;
	}
	
}