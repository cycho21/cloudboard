package test.model;

import java.util.HashMap;
import java.util.Map;

public class Data {
	
	private HashMap<String, String> arguments;

	public Data() {
		this.arguments = new HashMap<String, String>();
	}
	
	public Map<String, String> getArguments() {
		return arguments;
	}
	
	public String getValue(String key){
		return arguments.get(key);
	}
	
	public void appendArgument(String key, String value){
		this.arguments.put(key, value);
	}
}
