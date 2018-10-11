import processing.core.PApplet;

public class searchAction extends MultiDecision {
	Map map;
	Wander wander;
	PApplet parent;
	Steering getAction() {
		wander = new Wander( map.characters.getLast(), parent );	
		return wander.steering;
	}
	
	searchAction(Map map, PApplet parent){
		this.map = map;
		this.parent = parent;
	}

	public DecisionTreeNode makeDecision() {
		return this;
	}
}
