import processing.core.PApplet;

public class Note {
	PApplet parent;
	float ampStart, ampEnd, ampNow;
	float attack, sustain;
	boolean isNoteOn;
	int index;
	float x, y, b;
	int fStart, fEnd;
	
	public Note(PApplet _parent, int _index) {
        parent = _parent;
        index = _index;
        int ii = index+1;
        float res = parent.width/12.0f;
        x = ii*res % (parent.width-res);
        y = ( (ii*res - x) / (float)(parent.width-res) ) * res;
        y+=res/2;
        b = 10;
    }
	
	public float getAttack() {
		return attack;
	}
	
	public float getSustain() {
		return sustain;
	}
	
	public void on(float ampIn) {
		// handle note on
		isNoteOn = true;
		ampStart = ampIn;
		fStart = parent.frameCount;
	}
	
	public void off(float ampIn) {
		// handle note off
		isNoteOn = false;
		ampEnd = ampIn;
		fEnd = parent.frameCount;
		sustain = Math.abs(ampStart-ampEnd)/(fEnd-fStart);
	}
	
	public void update(float amplitude, boolean doRenderGrid, boolean useSustain) {
		// update midi, amp, attack and sustain
		ampNow = amplitude;
		if (isNoteOn) {
			b = PApplet.map(ampNow, 0.0f, 1.0f, 20.0f, 50.0f);
		} else {
			if (!useSustain) {
				b = Math.round(b);
				if(b>10) { b--; }
			} else {
				fadeOut(b, 10);
			}
		}
		if (doRenderGrid) render();		
	}
	
	private void fadeOut(float start, float target) {
		b = target - (start*sustain);
		if (b <= target) b = target;
	}
 
    private void render() {
    	parent.fill(220, 50, 47);
        parent.rect(x, y, b, b);
    }
}