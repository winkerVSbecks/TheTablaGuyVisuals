public class Note {
	float ampStart, ampEnd;
	int fStart, fEnd;
	float attack, sustain;
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
	
	public void on() {
		// handle note on
		isOn = true;
		ampStart = p.amplitude;
		fStart = p.frameCount;
	}
	
	public void off() {
		// handle note off
		isOn = false;
		ampEnd = p.amplitude;
		fEnd = p.frameCount;
		sustain = Math.abs(ampStart-ampEnd)/(fEnd-fStart);
	}
	
	public void update() {
		// update midi, amp, attack and sustain
		if (isOn) {
			attack = p.amplitude;
		} else {
			if (!p.useSustain) {
				if(attack > 0) { attack-=0.1f; 
				} else { attack = 0.0f; }
			} else {
				fadeOut(attack);
			}
			// System.out.println("*** "+attack); 
		}
	}
	
	private void fadeOut(float start) {
		attack = 0 - (start*sustain);
		if (attack <= 0) attack = 0;
	}
}