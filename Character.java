
import processing.core.*;

public class Character {

	PApplet parent;
	PVector position = new PVector(0, 0);
	PVector velocity = new PVector(0, 0);
	float orientation = 0;
	float rotation = 0;
	float maxSpeed = 0;
	boolean inHouse = false;
	boolean seenDoor = false;
	boolean nikesOn = false;


	public Character(float x, float y, float orientation, float maxSpeed, PApplet p) {
		this.position.x = x;
		this.position.y = y;
		this.orientation = orientation;
		this.maxSpeed = maxSpeed;
		parent = p;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void update(Steering s, float time) {

		position.add(PVector.mult(velocity, time));
		orientation += rotation * time;

		velocity.add(PVector.mult(s.linearAcceleration, time));
		rotation += s.angularAcceleration * time;

		if (velocity.mag() > maxSpeed) {
			velocity.normalize();
			velocity.mult(maxSpeed);
		}
	}

	public void changeMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void drawCharacter() {
		parent.noStroke();
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		parent.rotate(orientation);
		parent.ellipse(0, 0, 16, 16);
		parent.triangle(5, -6, 20, 0, 5, 6);
		parent.popMatrix();
	}
	
}
