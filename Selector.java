import java.util.LinkedList;

public class Selector extends Task {
	
	
	public Selector(){
		children = new LinkedList<Task>();
	}
	

	@Override
	public boolean run(float time, Map map ) {

		for( Task c : children )
			if ( c.run( time, map ) )
				return true;
		return false;
	}

}
