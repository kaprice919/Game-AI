
public class bargeInAction extends MultiDecision {
	Map map;
	ArriveBarge arrive;
	Steering getAction() {
		arrive = new ArriveBarge( new Character(200, 400, 0 ,0, null ) ,map.characters.getLast() );
		return arrive.getAction();
	}
	
	bargeInAction(Map map){
		this.map = map;
	}

	public DecisionTreeNode makeDecision() {
		return this;
	}
}
