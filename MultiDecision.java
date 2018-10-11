import java.util.HashMap;

public  class MultiDecision implements DecisionTreeNode {
	
	HashMap<Boolean, MultiDecision> daughterNodes;
	String state = "Unvisited";
	Boolean testValue;
	String decision;
	
	public MultiDecision(){
		daughterNodes =  new HashMap<Boolean, MultiDecision>();
	}
	
	MultiDecision getBranch( ){
		return daughterNodes.get(testValue);
	}
	
	public DecisionTreeNode makeDecision(){
		MultiDecision branch = getBranch();
		return branch.makeDecision();
	}

}
