
public class grabPlayerAction extends MultiDecision {
	Map map;
	Arrive arrive;
	Steering getAction() {
		arrive = new Arrive( map.characters.getFirst() ,map.characters.getLast() );
		return arrive.steering;
	}
	
	grabPlayerAction(Map map){
		this.map = map;
	}

	public DecisionTreeNode makeDecision() {
		return this;
	}

}
