
public class attemptBarge extends Task {
	
	Map map;
	int runLimit = 1;
	Task child;
	
	
	public attemptBarge(Map map, Task child){
		this.map = map;
		this.child = child;
		
	}
	

	@Override
	public boolean run(float time, Map map ) {
	
		if ( map.runSoFar >= runLimit){
			return false;
		}

	    return child.run( time, map );

	}

}
