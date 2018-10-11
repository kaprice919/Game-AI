import java.util.LinkedList;

public abstract class Task {
	
	LinkedList<Task> children;
	public String name;
	
	//Always results in success or failure
	public boolean run(){
		return false;
	}

	public boolean run( float time, Map map ) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString(){
		return name;
	}

}
