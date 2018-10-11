import java.util.HashMap;

public class doorOpenMultiDecisionNode extends MultiDecision {
	
	Map map;
	String state = "Unvisited";
	
	public doorOpenMultiDecisionNode(){
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	

	@Override
	public DecisionTreeNode makeDecision(){
		
			return daughterNodes.get(map.doorOpen);
	}

}