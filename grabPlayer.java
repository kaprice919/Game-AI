
public class grabPlayer extends Task {
	
	Map map;
	Arrive arrive;
	String name = "grabPlayer";
	
	public grabPlayer(Map map){
		this.map = map;
		arrive = new Arrive( map.characters.getFirst() ,map.characters.getLast() );
	}
	

	@Override
	public boolean run(float time, Map map ) {
		
		if(   Math.sqrt( Math.pow(  map.characters.getFirst().position.x    -   map.characters.getLast().position.x , 2)    +    Math.pow(   map.characters.getFirst().position.y - map.characters.getLast().position.y  , 2)   ) <= 5  ){
			map.playerEaten = true;
		}
		
		map.characters.getLast().update(arrive.getAction(), time);
		this.map.lastAction = this;
		return true;
	}
	
	public String toString(){
		return name;
	}

}
