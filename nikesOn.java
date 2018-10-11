
public class nikesOn extends Task {
	Map world;

	public nikesOn( Map world ){
		this.world = world;
	}
	

	@Override
	public boolean run(float time, Map map ) {
			return map.characters.getLast().nikesOn;
		
	}
}
