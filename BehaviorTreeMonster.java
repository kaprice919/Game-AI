import java.io.*;
import java.util.LinkedList;

import processing.core.PApplet;

public class BehaviorTreeMonster {

	 PApplet parent;
	 
	 Selector s1, s2;
	 Sequence q1, q2, q3, q4, q5;
	 nearDoor nearDoor;
	 Search search;
	 doorLocked doorLocked;
	 bargeIn bargeIn;
	 doorOpen doorOpen;
	 grabPlayer grab;
	 moveToDoor moveToDoor;
	 seeDoor seeDoor;
	 nikesOff nikesOff;
	 nikesOn nikesOn;
	 moveToNikes moveToNikes;
	 seeNikes seeNikes;
	 attemptBarge attemptBarge;
	 BufferedWriter writer;
	 
	 LinkedList<Example> examples;
	
	public BehaviorTreeMonster( PApplet parent, Map map, BufferedWriter writer ){


		this.parent = parent;
		this.writer = writer;
		examples = new LinkedList<Example>();

		 s1 = new Selector(); s2 = new Selector();
		 q1 = new Sequence(); q2 = new Sequence(); q3 = new Sequence(); q4 = new Sequence(); q5 = new Sequence(); 

		 nearDoor = new nearDoor( map );
		 search = new Search( map, parent );
		 doorLocked = new doorLocked( map );
		 bargeIn = new bargeIn( map );
		 doorOpen = new doorOpen( map );
		 grab = new grabPlayer( map );
		 moveToDoor = new moveToDoor( map );
		 seeDoor = new seeDoor( map );
		 nikesOff = new nikesOff( map );
		 nikesOn = new nikesOn( map );
		 moveToNikes = new moveToNikes( map );
		 seeNikes = new seeNikes( map );
		 attemptBarge = new attemptBarge( map, bargeIn );
		
		s1.children.add(q1); /* q5 here*/ s1.children.add(q5); s1.children.add(q2); s1.children.add(s2);
		q1.children.add(nearDoor); q1.children.add(doorOpen); q1.children.add(grab);
		q2.children.add(nearDoor); q2.children.add(doorLocked); q2.children.add(attemptBarge);
		s2.children.add(q3);/* q4 here*/ s2.children.add(q4); s2.children.add(search);
		q3.children.add(seeDoor); q3.children.add(moveToDoor);
		
		

		
		q4.children.add(nikesOff);
		q4.children.add(seeNikes);
		q4.children.add(moveToNikes);
		
		q5.children.add(nearDoor); q5.children.add(doorLocked); q5.children.add(nikesOn); q5.children.add(bargeIn);
		
		
		
	}
	
	public void execute( float time, Map map ){
		
		s1.run( time, map );
		
		try {
			writer.write(map.characters.getLast().nikesOn + " " + seeNikes.run(time, map) + " " + seeDoor.run(time, map) + " " + this.nearDoor.run(time, map) + " " + !map.doorOpen + " " +  map.doorOpen + " " + 
		    map.lastAction );
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		


		 		 
	}
}
