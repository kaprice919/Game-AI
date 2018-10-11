import java.util.LinkedList;

public class Sequence extends Task {
	
	public Sequence(){
		children = new LinkedList<Task>();
	}
	

	@Override
	public boolean run(float time, Map map ) {

		for( Task c : children )
			if ( !c.run(time, map) )
				return false;
		return true;
	}
}
