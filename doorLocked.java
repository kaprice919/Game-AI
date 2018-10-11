
public class doorLocked extends Task {
	
	Map map;
	
	public doorLocked(Map map){
		this.map = map;
	}
	

	@Override
	public boolean run(float time, Map map ) {
		
			return !map.doorOpen;
	}

}
