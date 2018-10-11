import processing.core.PApplet;

public class Search extends Task {
	
	Map map;
	Wander wander;
	String name = "Search";
	
	public Search(Map map, PApplet parent ){
		this.map = map;
		wander = new Wander( map.characters.getLast(), parent );
		
	}
	

	@Override
	public boolean run(float time, Map map ) {
		
		    map.characters.getLast().update(wander.getAction(), time);
		    this.map.lastAction = this;
			return true;
	}
	

	public String toString(){
		return name;
	}
}
