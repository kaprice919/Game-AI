import processing.core.PVector;

final class Arrive  {

	Character agent, monster;
	Steering steering;
	
	
	public Arrive( Character agent, Character monster ) {
		this.agent = agent;
		this.monster = monster;
		steering = new Steering();

	}
	
	public void resetMonster(){
		monster.position.x = 400;
		monster.position.y = 400;
	}
	

	protected Steering getAction() {

		Steering steering = new Steering();
		steering.angularAcceleration += this.face(agent).angularAcceleration;
		steering.linearAcceleration.add(this.arrive(agent).linearAcceleration);
		return steering;
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
	
	
	Steering lookWhereYoureGoing() {
		
		Steering steering = new Steering();
		
	     if ( agent.velocity.mag() == 0 ) {
	       agent.rotation = 0;
	       return steering;
	     }
	     Character newTarget = new Character( agent.position.x, agent.position.y, agent.orientation, 1, null );
	     newTarget.orientation = processing.core.PApplet.atan2( agent.velocity.y, agent.velocity.x );
	     return align( newTarget );
	   }
	
	
	Steering face( Character target ) {
	    PVector direction = new PVector( 0, 0 ); 
	    Steering steering = new Steering();
	    direction = PVector.sub( agent.position, monster.position ); 
	    if (direction.mag() == 0.0)
	      return steering;
	    Character newTarget = new Character( agent.position.x, agent.position.y, agent.orientation, (float).1, null );
	    newTarget.orientation = processing.core.PApplet.atan2( direction.y, direction.x );
	    return align(newTarget);
	   }
	
	Steering arrive( Character target ) {
		
		   float maxVelocity = (float) .5;
		   float maxAcceleration = (float) .5;
		   float raidusOfSatisfaction = 5;
		   float radiusOfDeceleration = 60;
		   float timeToTargetVelocity = 70;
		   Steering steering = new Steering();
		
		
	      PVector direction = new PVector( 0, 0 );
	      PVector.sub( target.position, monster.position, direction );
	      float distance = direction.mag();
	      float goalSpeed = 0;
	            
	      if( distance < raidusOfSatisfaction ){

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
