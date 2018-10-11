
public class nikesOff extends Task {
	Map world;

	public nikesOff( Map world ){
		this.world = world;
	}
	

	@Override
	public boolean run(float time, Map map ) {

			return !map.characters.getLast().nikesOn;
		
	}
}
