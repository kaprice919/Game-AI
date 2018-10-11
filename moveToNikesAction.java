
public class moveToNikesAction extends MultiDecision {
	Map map;
	Arrive arrive;
	Steering getAction() {
		arrive = new Arrive( new Character(600, 600, 0 ,0, null ) ,map.characters.getLast() );
		return arrive.steering;
	}
	
	moveToNikesAction(Map map){
		this.map = map;
	}

	public DecisionTreeNode makeDecision() {
		return this;
	}
}
