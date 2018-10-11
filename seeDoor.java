
public class seeDoor extends Task {
	Map world;
	int RANGETODOOR = 150;
	
	
	public seeDoor( Map world ){
		this.world = world;
	}
	

	@Override
	public boolean run(float time, Map map ) {
		for( Graph.Node node : map.graph.nodeList )
			if( ( Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  node.x   , 2)       +  Math.pow( map.characters.getLast().position.y   -  node.y  , 2)      )   <= RANGETODOOR ) || map.characters.getLast().seenDoor ){
				map.characters.getLast().seenDoor = true;
				return true;
				
			}
			return false;
		
	}
}
