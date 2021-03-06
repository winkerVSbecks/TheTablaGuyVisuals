import processing.core.PApplet;

public class Key {
	PApplet parent;
	float ampStart, ampEnd, ampNow;
	float attack, sustain;
	boolean isNoteOn;
	int index, freq;
	float x, h, low, hi, res;
	
	public Key(PApplet _parent, int _index, int _freq) {
        parent = _parent;
        index = _index;
        freq = _freq;
        res = parent.width/14.0f; //14.0f;
        x = (index+1)*res;
        h = 5*res;
        low = h+150;
        hi = h+300;
    }
	
	public void update(float attack, boolean isNoteOn) {
		if (isNoteOn) {
			h = PApplet.map(attack, 0.0f, 1.0f, low, hi);
		} else {
			h = Math.round(h);
			if(h>5*res) { h-=5; }
		}
		parent.fill(220, 50, 47);
        parent.rect(x-res/2, h/2, res, h);
	}
}