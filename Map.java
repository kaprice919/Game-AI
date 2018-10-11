import java.util.LinkedList;
import processing.core.*;

public class Map {
	
	PApplet parent;
	Graph graph;
	boolean doorOpen = false;
	boolean playerEaten = false;
	boolean firstRun = true;
	int i = 0;
	int runSoFar = 0;
	boolean playerwins = false;
	boolean forth = false;
	boolean backedUp = false;
	Task lastAction;

	
	
	LinkedList<Character> characters;
	
	public Map( PApplet parent, LinkedList<Character> characters ) {
		this.parent = parent;
		this.characters = characters;
		graph = new Graph();
		graph.generateSimpleGraph();
	}
	
	public void drawMap() {
		//left wall
		parent.rect(100, 100, 20, 150);
		//right wall
		parent.rect(275, 100, 20, 150);
		//bottom wall
		parent.rect(100, 250, 195, 20);
		//top wall
		parent.rect(100, 100, 195, 20);
		
		//door
		parent.fill(0,0,0);
		if(doorOpen == false)
			parent.rect(185, 250, 35, 20);
		else{
			parent.fill(25,25,75);
			parent.rect(185, 250, 35, 20);
		}

	}
	
	
	public void reset() {
		 doorOpen = false;
		 playerEaten = false;
		 firstRun = true;
		 this.characters.getLast().maxSpeed = (float) .08;
		 this.characters.getLast().inHouse = false;
		 this.characters.getLast().seenDoor = false;
		 characters.getLast().position.x = 600;
		 characters.getLast().position.y = 100;
		 characters.getLast().nikesOn = false;
		 runSoFar = 0;
		 playerwins = false;
		 i = 0;
		 backedUp = false;
		 forth = false;
		 
	}

}
