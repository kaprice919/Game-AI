
public class bargeIn extends Task {

	
	Map map;
	ArriveBarge arrive;
	String name = "bargeIn";

	
	public bargeIn(Map map){
		this.map = map;
		arrive = new ArriveBarge( new Character(200, 400, 0 ,0, null ) ,map.characters.getLast() );
		
	}
	

	@Override
	public boolean run(float time, Map map ) {
		//TODO run the barge animation here

		map.characters.getLast().update(arrive.getAction(), time);

		
		if(  (Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  200   , 2)       +  Math.pow( map.characters.getLast().position.y   -  400  , 2)      )   <= 5 ) ){
			arrive = new ArriveBarge( new Character(200, 285, 0 ,0, null ) ,map.characters.getLast() );
			map.backedUp = true;	
		}
		if(  (Math.sqrt(    Math.pow(   map.characters.getLast().position.x    -  200   , 2)       +  Math.pow( map.characters.getLast().position.y   -  290  , 2)      )   <= 5 ) && map.backedUp == true ){
			arrive = new ArriveBarge( new Character(200, 400, 0 ,0, null ) ,map.characters.getLast() );
			map.forth = true;
		}
		
		
		if(map.backedUp == true && map.forth == true){

			map.runSoFar++;
		if(!map.characters.getLast().nikesOn){
			map.playerwins = true;

		}
		else{
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.doorOpen = true;
		}
		}
		this.map.lastAction = this;
		return true;

	}
	
	public String toString(){
		return name;
	}
}
