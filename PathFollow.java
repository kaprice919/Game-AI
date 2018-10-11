import java.util.LinkedList;

public class PathFollow {
	public static void pathFollow(Character agent, LinkedList<Graph.Edge> path, float time) {

		Steering steering = new Steering();
		Character target = new Character(path.getFirst().toNode.x, path.getFirst().toNode.y, 0, 0, null);
		target.orientation = 1;

		if (path.size() > 1) {
			steering.linearAcceleration = Seek.PathFollowSeek(agent, path).linearAcceleration;

			steering.angularAcceleration = Face.getSteering(agent, target).angularAcceleration;
			agent.update(steering, time);
			if ((int) Math.sqrt(Math.pow(agent.position.x - path.getFirst().toNode.x, 2)
					+ Math.pow(agent.position.y - path.getFirst().toNode.y, 2)) <= 10)
				path.removeFirst();
		}

		steering.linearAcceleration = Arrive2.arrive(agent, target).linearAcceleration;
		steering.angularAcceleration = Face.getSteering(agent, target).angularAcceleration;

		agent.update(steering, time);
	}
}
