
public class nearDoor extends Task {

	Map world;
	int RANGETODOOR = 20;
	public nearDoor( Map world ){
		this.world = world;
	}
	

	@Override
	public boolean run(float time, Map map ) {
			if(  (Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  200   , 2)       +  Math.pow( map.characters.getLast().position.y   -  285  , 2)      )   <= RANGETODOOR ) || map.characters.getLast().inHouse ){
				map.characters.getLast().inHouse = true;
				return true;
				
			}
			return false;
		
	}
}
