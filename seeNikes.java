
public class seeNikes extends Task {
	Map world;
	int RANGETONIKES = 150;
	
	
	public seeNikes( Map world ){
		this.world = world;
	}
	@Override
	public boolean run(float time, Map map ) {
		

			if( ( Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  600   , 2)       +  Math.pow( map.characters.getLast().position.y   -  600  , 2)      )   <= RANGETONIKES ) ){

				return true;
				
			}
			return false;
		
	}

}
