
public class moveToNikes extends Task {
	Map map;
	Arrive arrive;
	String name = "moveToNikes";
	
	
	public moveToNikes(Map map){
		this.map = map;
		arrive = new Arrive( new Character(600, 600, 0 ,0, null ) ,map.characters.getLast() );
	}
	

	@Override
	public boolean run(float time, Map map ) {
		
			
		map.characters.getLast().update(arrive.getAction(), time);
		
		if( ( Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  600   , 2)       +  Math.pow( map.characters.getLast().position.y   -  600  , 2)      )   <= 15 ) ) {
			map.characters.getLast().nikesOn = true;
			map.characters.getLast().maxSpeed = (float) .13;
		}
		this.map.lastAction = this;
		return true;
			
	}
	
	public String toString(){
		return name;
	}
}
