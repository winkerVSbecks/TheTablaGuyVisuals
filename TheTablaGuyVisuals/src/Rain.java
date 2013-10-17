// Based on "Rain Test" by Anastasis Chasandras

import processing.core.*;

public class Rain {
	PVector position, pposition, speed;
	float col;
	PApplet p;
	int xDir;

	public Rain(PApplet _p) {
		p = _p;
		position = new PVector(p.random(p.width), 0);
		pposition = position;
		speed = new PVector(0,0);
		col = p.random(76, 255);
		xDir = (int) p.random(-2, 2);
	}

	void draw(float d) {
		update();
//		p.stroke(255, col);
		p.stroke(PApplet.map(d, 0.5f, 0.8f, 0, 255), col);
		p.strokeWeight(2);
		p.line(position.x,position.y,pposition.x,pposition.y);
		//ellipse(position.x,position.y,5,5);
	}

	void update() {
		pposition = new PVector(position.x,position.y);
		gravity();
	}

	void gravity() {
		speed.y += .4;
		speed.x += .01*xDir;
		position.add(speed);
	}
}
