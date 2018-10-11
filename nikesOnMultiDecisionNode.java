import java.util.HashMap;

public class nikesOnMultiDecisionNode extends MultiDecision {
	Map map;
	String state = "Unvisited";
	
	public nikesOnMultiDecisionNode() {
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	
	@Override
	public DecisionTreeNode makeDecision(){
	    return daughterNodes.get(map.characters.getLast().nikesOn);
	}

}
