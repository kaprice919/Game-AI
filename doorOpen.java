
public class doorOpen extends Task {

	Map map;
	
	public doorOpen(Map map){
		this.map = map;
	}
	

	@Override
	public boolean run(float time, Map map ) {
		
			return map.doorOpen;
	}
}
