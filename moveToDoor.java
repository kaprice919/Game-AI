import java.util.LinkedList;

public class moveToDoor extends Task {
	
	Map map;
	Arrive arrive;
	LinkedList<Graph.Edge> path = new LinkedList<Graph.Edge>();
	Graph.Node closest = null;
	Graph.Node end  = null;
	String name = "moveToDoor";
	
	
	public moveToDoor(Map map){
		this.map = map;
		arrive = new Arrive( new Character(200, 285, 0 ,0, null ) ,map.characters.getLast() );
	}
	

	@Override
	public boolean run(float time, Map map ) {
		
			if(this.map.firstRun) {
				//search through all nodes in graph for start
				//goal is just bottom middle

				int smallestDist = Integer.MAX_VALUE;
				//find the closest node to monster
				for ( Graph.Node node : map.graph.nodeList ) {
					int distanceToPlayer =  (int)Math.sqrt( Math.pow(map.characters.getLast().position.x - node.x, 2) + Math.pow(map.characters.getLast().position.y - node.y, 2) );
					//update the smallest
					if ( distanceToPlayer < smallestDist ) {
						smallestDist = distanceToPlayer;
						closest = node;
					}
				}
				end = map.graph.search("bottom middle");
				//get the path from the closest node to the front of the door
				path = map.graph.dijkstra(closest, end);
				this.map.firstRun = false;
			}
			
			//if we get the door node as the closest node, we don't need to pathfollow, just arrive at door
			if( closest.equals(end) ){
				map.characters.getLast().update(arrive.getAction(), time);
			} else 
				PathFollow.pathFollow(map.characters.getLast(), path, time);
			this.map.lastAction = this;
			return true;
	}
	
	public String toString(){
		return name;
	}

}
