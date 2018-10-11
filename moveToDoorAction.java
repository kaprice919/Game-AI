
public class moveToDoorAction extends MultiDecision {
	Map map;
	Arrive arrive;
	Steering getAction() {
		arrive = new Arrive( new Character(200, 285, 0 ,0, null ) ,map.characters.getLast() );		
		return arrive.steering;
	}
	
	moveToDoorAction(Map map){
		this.map = map;
	}

	public DecisionTreeNode makeDecision() {
		return this;
	}
}
