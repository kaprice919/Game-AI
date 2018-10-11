


public abstract class ActionNode implements DecisionTreeNode {
	Steering getAction() {
		return null; 
	}

	public DecisionTreeNode makeDecision() {
		return this;
	}

}
