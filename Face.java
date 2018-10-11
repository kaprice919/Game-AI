import processing.core.PVector;

public class Face {

	public static Steering getSteering(Character agent, Character target) {
		Steering steering = new Steering();
		PVector direction = PVector.sub(target.position, agent.position);
		if (direction.mag() == 0)
			return steering;
		target.orientation = processing.core.PApplet.atan2(direction.y, direction.x);
		return Align.align(agent, target);
	}
}