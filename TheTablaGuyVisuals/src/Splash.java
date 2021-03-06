// Based on "Rain Test" by Anastasis Chasandras

import processing.core.*;

public class Splash {
	PVector position,speed;
	PApplet p;
	boolean isUmbrella;

	public Splash(PApplet _p, float x, float y, boolean _isUmbrella) {
		p = _p;
		float angle = p.random(PConstants.PI, PConstants.TWO_PI);
		float distance = p.random(1,5);
		float xx = PApplet.cos(angle)*distance;
		float yy = PApplet.sin(angle)*distance;
		position = new PVector(x, y);
		speed = new PVector(xx, yy);
		isUmbrella = _isUmbrella;
	}

	public void draw(float d) {
		update();
		p.strokeWeight(1);
		
		float c = isUmbrella ? 255 : PApplet.map(d, 0.5f, 0.8f, 0, 255);
		p.stroke(c, 127.5f);
		p.fill(c, 255);
		p.ellipse(position.x, position.y, 2, 2);
	}

	void update() {
		gravity();
		speed.x*=0.98;
		speed.y*=0.98;
		position.add(speed);
	}

	void gravity() {
		speed.y+=.2;
	}

}