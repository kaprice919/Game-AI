import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

import processing.core.PApplet;

public class ID3 {
	
	public LinkedList<Example> examples;
	public LinkedList<String> attributes;
	Map map;

	File file;
	PApplet parent;
	
	public ID3( File file, Map map, PApplet parent ){
		
		this.file = file;	
		attributes = new LinkedList<String>();
		attributes.add("nikesOn");
		attributes.add("seeNikes");
		attributes.add("seeDoor");
		attributes.add("nearDoor");
		attributes.add("doorLocked");
		attributes.add("doorOpen");
		this.map = map;
		this.parent = parent;
		
	}
	
	void printExamples(){
		for(Example e : examples){
			Iterator<Entry<String, Boolean>> i = e.attributes.entrySet().iterator();
			while(i.hasNext()){
				System.out.print(i.next().getValue());
				System.out.print(" ");
			}
			System.out.println(e.action);
		}
			
	}
	
	void makeTree( LinkedList<Example> examples, LinkedList<String> attributes, MultiDecision decisionNode  ){
		
		float initialEntropy = entropy(examples);
		
		
		if(initialEntropy <= 0 )
			return;
		
		int exampleCount = examples.size();
		
		float bestInformationGain = 0;
		String bestSplitAttribute = "";
		Hashtable<Boolean, LinkedList<Example>> bestSets = null;
		
		for(String attribute: attributes){
			Hashtable<Boolean, LinkedList<Example>> sets = splitByAttribute(examples, attribute);
			float overallEntropy = entropyOfSets(sets, exampleCount);
			float informationGain = initialEntropy - overallEntropy;
			
			if (informationGain > bestInformationGain){
				bestInformationGain = informationGain;
				bestSplitAttribute  = attribute;
				bestSets = sets;
			}
			
			decisionNode.decision = bestSplitAttribute;
			
			LinkedList<String> newAttributes = new LinkedList<String>();
			for(String a : attributes){
				if(!a.equals(bestSplitAttribute))
					newAttributes.add(a);
			}
			
			Iterator<Entry<Boolean, LinkedList<Example>>> i = bestSets.entrySet().iterator();
			while(i.hasNext()){
				Entry<Boolean, LinkedList<Example>> next = i.next();

				LinkedList<Example> set = next.getValue();
				if(set.size() == 0)
					continue;
				boolean attributeValue = set.get(0).getValue(bestSplitAttribute);
				
				MultiDecision daughter = null;
				daughter = null;
				daughter = null;
				daughter = null;
				daughter = null;
				daughter = null;
				daughter = null;
				daughter = null;
				
				//TODO
				if(bestSplitAttribute.equals("seeDoor")){
					daughter = new seeDoorMultiDecisionNode();
					decisionNode.daughterNodes.put(false, new searchAction(map, parent));
					decisionNode.daughterNodes.put(true, new moveToDoorAction(map));
				} if(bestSplitAttribute.equals("nikesOn")){	
					daughter = new nikesOnMultiDecisionNode();
					decisionNode.daughterNodes.put(attributeValue, daughter);	

				} if(bestSplitAttribute.equals("seeNikes")){
					 daughter = new seeNikesMultiDecisionNode();
					decisionNode.daughterNodes.put(false, daughter);
					decisionNode.daughterNodes.put(true, new moveToNikesAction(map));
				} if(bestSplitAttribute.equals("nearDoor")){
					 daughter = new nearDoorMultiDecisionNode();
					decisionNode.daughterNodes.put(attributeValue, daughter);

				} if(bestSplitAttribute.equals("doorLocked")){
					 daughter = new doorLockedMultiDecisionNode();
					decisionNode.daughterNodes.put(false, daughter);
					decisionNode.daughterNodes.put(true, new bargeInAction(map));
				} if(bestSplitAttribute.equals("doorOpen")){
					 daughter = new doorOpenMultiDecisionNode();
					decisionNode.daughterNodes.put(false, daughter);
					decisionNode.daughterNodes.put(true, new grabPlayerAction(map));
				}
				
				
				
								
				
				makeTree(set, newAttributes, daughter);
			}
			
		}
		
			
	}
	
	void readFile(){
		BufferedReader reader = null;
		LinkedList<Example> examples = new LinkedList<Example>();

        try { 
           FileInputStream f = new FileInputStream(file); 
           reader = new BufferedReader(new InputStreamReader(f));;
           
           // read the first record of the file
           String line;
           Hashtable<String, Boolean> attributes;
           
           
           while ((line = reader.readLine()) != null) {
              StringTokenizer st = new StringTokenizer(line);
              attributes = new Hashtable<String, Boolean>();
              	
			  String nikesOn = st.nextToken();
			  String seeNikes = st.nextToken();
			  String seeDoor = st.nextToken();
			  String nearDoor = st.nextToken();
			  String doorLocked = st.nextToken();
			  String doorOpen = st.nextToken();
			  String action = st.nextToken();
			  
			  if(nikesOn.equals("true")){
				  attributes.put("nikesOn", true);
			  }else{
				  attributes.put("nikesOn", false);
			  }
			  
			  if(seeNikes.equals("true")){
				  attributes.put("seeNikes", true);
			  }else{
				  attributes.put("seeNikes", false);
			  }
			  
			  if(seeDoor.equals("true")){
				  attributes.put("seeDoor", true);
			  }else{
				  attributes.put("seeDoor", false);
			  }
			  
			  if(nearDoor.equals("true")){
				  attributes.put("nearDoor", true);
			  }else{
				  attributes.put("nearDoor", false);
			  }
			  
			  if(doorLocked.equals("true")){
				  attributes.put("doorLocked", true);
			  }else{
				  attributes.put("doorLocked", false);
			  }
			  
			  if(doorOpen.equals("true")){
				  attributes.put("doorOpen", true);
			  }else{
				  attributes.put("doorOpen", false);
			  }
			  
			  //TODO: CODE FOR ADDING ACTIONNODE TO EXAMPLE AND REPLACE NULL BELOW
			  
			  examples.add(new Example(attributes, action));
           }
           this.examples = examples;

        } 
        catch (IOException e) { 
           System.out.println("Uh oh, got an IOException error: " + e.getMessage()); 
        } 
        catch (Exception e) {
            System.out.println("Uh oh, got an Exception error: " + e.getMessage()); 
        }
	}
	
	
	//Returns the entropy of a given set of lists of examples
	float entropyOfSets(Hashtable<Boolean, LinkedList<Example>> sets, int exampleCount ){
		float entropy = 0;
		
		Iterator<Entry<Boolean, LinkedList<Example>>> iterator = sets.entrySet().iterator();
		while(iterator.hasNext()){
			LinkedList<Example> examples = iterator.next().getValue();
			float proportion = (float) examples.size() / exampleCount;
			entropy -= proportion * entropy(examples);
		}
		return entropy;
	}
	
	// Puts the examples into lists based on true or false (per attribute), then adds both lists to a Hashtable indexed by true/false
	Hashtable<Boolean, LinkedList<Example>> splitByAttribute( LinkedList<Example> examples, String attribute ) {
		Hashtable<Boolean, LinkedList<Example>> sets = new Hashtable<Boolean, LinkedList<Example>>();
		
		LinkedList<Example> setTrue = new LinkedList<Example>();
		LinkedList<Example> setFalse = new LinkedList<Example>();
		
		sets.put(true, setTrue);
		sets.put(false, setFalse);
		
		for( Example e : examples ){
			if(e.getValue(attribute) == true ){
				setTrue.add(e);
			}
			else{
				setFalse.add(e);
			}
		}
		
		return sets;
		
	}
	
	//Get the entropy of the list of examples
	float entropy( LinkedList<Example> examples ){
		
		Hashtable<String, AtomicInteger> actionTallies = new Hashtable<String, AtomicInteger>();
		
		actionTallies.put("grabPlayer", new AtomicInteger(0));
		actionTallies.put("bargeIn", new AtomicInteger(0));
		actionTallies.put("moveToDoor", new AtomicInteger(0));
		actionTallies.put("moveToNikes", new AtomicInteger(0));
		actionTallies.put("Search", new AtomicInteger(0));

		
		int exampleCount = examples.size();

		if (exampleCount == 0)
			return 0;
				

		
		for( Example e : examples ){
			actionTallies.get(e.action).incrementAndGet();
			
		}
		
		
		int actionCount = 0;
		
		Iterator<Entry<String, AtomicInteger>> i = actionTallies.entrySet().iterator();
		
		while(i.hasNext()){
			if(i.next().getValue().intValue() != 0)
				actionCount++;
		}
		
		
				
		if(actionCount == 0)
			return 0;
		
		float entropy = 0;
		
		Iterator<Entry<String, AtomicInteger>> iterator = actionTallies.entrySet().iterator();
		
		while(iterator.hasNext()){
			int nextValue =  iterator.next().getValue().intValue();
			if(nextValue == 0)
				continue;
			
			float proportion = (float) nextValue / exampleCount;
			
			entropy -= (float) proportion * log2(proportion);
			
		}
		
		return entropy;
		
	}
	
	//Helper function for entropy
	int log2(float x){
		return (int) (Math.log(x) / Math.log(2));
	}
	
	//The Example data structure
	class Example{
		String action;
		Hashtable<String, Boolean> attributes;
		
		public Example(Hashtable<String, Boolean> attributes, String action){
			this.attributes = attributes;
			this.action = action;
		}
		
		Boolean getValue(String attribute){
			return attributes.get(attribute);
		}
	}

	
}
