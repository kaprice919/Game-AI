
import java.util.Random;

import processing.core.PApplet;
import processing.core.PVector;

final class Wander {
	private Character monster;
	Steering steering;
	PApplet parent;
	int timer = 0;
	int wanderRate = 150;
	Character target;
	
	public Wander(Character monster, PApplet parent){
		this.monster = monster;
		this.parent = parent;
		steering = new Steering();
		target = new Character( 400, 400, 0, (float) .1, null);
	}
	

	protected Steering getAction() {
		Steering steering = new Steering();
		
		target = wander(target);
		
		steering.angularAcceleration += this.face(target).angularAcceleration;
		steering.linearAcceleration.add(this.arrive(target).linearAcceleration);
		
		return steering;
	}
	
	
	Character wander( Character target ) {
		
		float wanderOffset = 300;


	     //Edge clipping
	      if(monster.position.x > parent.width)
	    	  monster.position.x = 0;
	      if(monster.position.y > parent.height)
	    	  monster.position.y = 0;
	      if(monster.position.x < 0)
	    	  monster.position.x = parent.width;
	      if(monster.position.y < 0)
	    	  monster.position.y = parent.height;
	     
	      //Space out the rate at which we wander
	      timer++;

	      if( timer < wanderRate ) 
	        return target;
	      else 
	        timer = 0;
	        
	       Random rand = new Random();
	        
	      //Get a random orientation
	      float wanderOrientation = (float) (rand.nextFloat() - rand.nextFloat() * 2*Math.PI);    
	      
	      //Make a new target for the character 
	      target = new Character( 0, 0, 0 , (float) 1, null );
	      
	      target.orientation = monster.orientation + wanderOrientation;
	      
	      if(target.orientation > 2*Math.PI)
	       target.orientation -= 2*Math.PI;
	      if(target.orientation <= 0)
	      target.orientation += 2*Math.PI;
	      
	      target.position.x = (float) (monster.position.x + Math.sin( target.orientation ) * wanderOffset);
	      target.position.y = (float) (monster.position.y + Math.cos( target.orientation ) * wanderOffset);
	            
	      return target;

	   }

	
	float mapToRange( float rot ) {
	      float r = (float) (rot % (2* Math.PI));
	      if(Math.abs(r) <= Math.PI ) return r;
	      else if (r>Math.PI) return (float) (r-2*Math.PI);
	      else return (float) (r + 2*Math.PI);
	  }
	
	Steering align( Character target ) {
	      float goalRotation = 0;
	      float rotation = 0;
	      float rotationSize = 0;
	      float rotationRadiusOfSatisfaction = (float) (Math.PI / 20);
	      float rotationRadiusOfDeceleration = (float) (Math.PI / 4);
	      float maxRotation = (float) .00005;
	      float timeToTargetRotation = 75;
	      float maxAngularAcceleration = (float) .00005;
	      
	      Steering steering = new Steering();
	      
	      rotation = target.orientation - monster.orientation;
	      rotation = mapToRange( rotation );
	      
	      rotationSize = Math.abs( rotation );
	        
	      if ( rotationSize < rotationRadiusOfSatisfaction ) {
	    	  monster.rotation = 0;
	        return steering;
	        
	      } if ( rotationSize > rotationRadiusOfDeceleration ) {
	        goalRotation = maxRotation;
	      }else{
	        goalRotation = maxRotation * ( rotationSize / rotationRadiusOfDeceleration );
	      }
	        
	      goalRotation *= (rotation / Math.abs(rotation)); 
	      
	      steering.angularAcceleration = goalRotation - monster.rotation;
	      steering.angularAcceleration /= timeToTargetRotation;
	      
	      //Clip to max accel
	      float angularAcceleration = Math.abs(steering.angularAcceleration);
	      if( steering.angularAcceleration > maxAngularAcceleration ) {
	        steering.angularAcceleration /= angularAcceleration; 
	        steering.angularAcceleration *= maxAngularAcceleration;
	      }
	      return steering;
	   }
	
	
	Steering lookWhereYoureGoing(Character target) {
		
		Steering steering = new Steering();
		
	     if ( target.velocity.mag() == 0 ) {
	    	 target.rotation = 0;
	       return steering;
	     }
	     Character newTarget = new Character( target.position.x, target.position.y, target.orientation, 1, null );
	     newTarget.orientation = processing.core.PApplet.atan2( target.velocity.y, target.velocity.x );
	     return align( newTarget );
	   }
	
	
	Steering face( Character target ) {
	    PVector direction = new PVector( 0, 0 ); 
	    Steering steering = new Steering();
	    direction = PVector.sub( target.position, monster.position ); 
	    if (direction.mag() == 0.0)
	      return steering;
	    Character newTarget = new Character( target.position.x, target.position.y, target.orientation, (float).1, null );
	    newTarget.orientation = processing.core.PApplet.atan2( direction.y, direction.x );
	    return align(newTarget);
	   }
	
	Steering arrive( Character target ) {
		
		   float maxVelocity = (float) .5;
		   float maxAcceleration = (float) .5;
		   float raidusOfSatisfaction = 30;
		   float radiusOfDeceleration = 60;
		   float timeToTargetVelocity = 70;
		   Steering steering = new Steering();
		
		
	      PVector direction = new PVector( 0, 0 );
	      PVector.sub( target.position, monster.position, direction );
	      float distance = direction.mag();
	      float goalSpeed = 0;
	            
	      if( distance < raidusOfSatisfaction ){
	    	  //Throw new caught the player exception because the monster got him
	        monster.velocity.mult(0);
	        return steering;
	      }
	      
	      if ( distance > radiusOfDeceleration ) {
	        goalSpeed = maxVelocity;
	      }else{
	        goalSpeed = maxVelocity * ( distance / radiusOfDeceleration );
	      }
	      
	      PVector goalVelocity = direction;
	      goalVelocity.normalize();
	      goalVelocity.mult( goalSpeed );
	      PVector.sub( goalVelocity , monster.velocity , steering.linearAcceleration );
	      steering.linearAcceleration.div( timeToTargetVelocity );
	      
	      //Clip
	      if ( steering.linearAcceleration.mag() > maxAcceleration ) {
	        steering.linearAcceleration.normalize();
	        steering.linearAcceleration.mult( maxAcceleration );
	      }
	      return steering;
	   }
}
