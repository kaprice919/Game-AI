import java.util.HashMap;

public class nearDoorMultiDecisionNode extends MultiDecision {
	
	Map map;
	int RANGETODOOR = 20;
	String state = "Unvisited";
	public nearDoorMultiDecisionNode( ){
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	

	@Override
	public DecisionTreeNode makeDecision(){
			if(  (Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  200   , 2)       +  Math.pow( map.characters.getLast().position.y   -  285  , 2)      )   <= RANGETODOOR ) || map.characters.getLast().inHouse ){
				map.characters.getLast().inHouse = true;
				return daughterNodes.get(true);
				
			}
			return daughterNodes.get(false);
		
	}

}
