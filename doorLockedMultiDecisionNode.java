import java.util.HashMap;

public class doorLockedMultiDecisionNode extends MultiDecision {
	
	Map map;
	String state = "Unvisited";
	
	public doorLockedMultiDecisionNode(){
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	

	@Override
	public DecisionTreeNode makeDecision(){
		
		return daughterNodes.get(!map.doorOpen);
	}

}
