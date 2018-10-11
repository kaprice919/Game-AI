import java.util.HashMap;

public class seeDoorMultiDecisionNode extends MultiDecision {
	private Map map;
	private float RANGETODOOR = 150;
	String state = "Unvisited";
	
	public seeDoorMultiDecisionNode() {
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	
	
	@Override
	public DecisionTreeNode makeDecision(){
		//check if in x-axes or y-axes
		for( Graph.Node node : map.graph.nodeList )
			if( ( Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  node.x   , 2)       +  Math.pow( map.characters.getLast().position.y   -  node.y  , 2)      )   <= RANGETODOOR ) || map.characters.getLast().seenDoor ){
				map.characters.getLast().seenDoor = true;
				return daughterNodes.get(true);
				
			}
		return daughterNodes.get(false);
	}
}
