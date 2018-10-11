
//Decorator
public class UntilFail extends Task {
	
	int limit = 1000;
	int soFar = 0;
	Task child;
	
	public UntilFail( Task child ){
		this.child = child;
	}
	
	@Override
	public boolean run(float time, Map map ) {

		
		if( soFar >= limit ) {
			 return false;
		}
			
		soFar++;
		return child.run(time, map);
	}
}
