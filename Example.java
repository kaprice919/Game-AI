import java.util.Hashtable;

public class Example {
			Task action;
			Hashtable<String, Boolean> attributes;
			
			public Example( Hashtable<String, Boolean> attributes, Task action ){
				this.attributes = attributes;
				this.action = action;
			}
			
			boolean getValue( String attribute ){
				return attributes.get(attribute);
			}
	}
