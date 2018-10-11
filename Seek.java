
import java.util.LinkedList;

import processing.core.PVector;

public class Seek {

	static float maxAcceleration = (float) .005;

	public static Steering seek(Character agent, Character target) {

		Steering steering = new Steering();
		steering.linearAcceleration = PVector.sub(target.position, agent.position);
		steering.linearAcceleration.normalize();
		steering.linearAcceleration.mult(maxAcceleration);
		steering.angularAcceleration = 0;
		return steering;
	}

	public static Steering PathFollowSeek(Character agent, LinkedList<Graph.Edge> path) {

		Steering steering = new Steering();
		Character target = new Character(0, 0, 0, 0, null);
		target.position.x = path.getFirst().toNode.x;
		target.position.y = path.getFirst().toNode.y;

		steering.linearAcceleration = PVector.sub(target.position, agent.position);
		steering.linearAcceleration.normalize();
		steering.linearAcceleration.mult(maxAcceleration);

		steering.angularAcceleration = 0;

		return steering;

	}
}
