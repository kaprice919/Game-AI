
import java.util.HashMap;

public class seeNikesMultiDecisionNode extends MultiDecision {
	private Map map;
	private float RANGETONIKES = 150;
	String state = "Unvisited";
	
	public seeNikesMultiDecisionNode() {
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	
	
	@Override
	public DecisionTreeNode makeDecision(){
		

		if( ( Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  600   , 2)       +  Math.pow( map.characters.getLast().position.y   -  600  , 2)      )   <= RANGETONIKES ) ){

			return daughterNodes.get(true);
			
		}
		return daughterNodes.get(false);
	
	}
}