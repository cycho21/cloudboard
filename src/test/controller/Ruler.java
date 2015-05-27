package test.controller;

import static kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration.ArgumentList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Ruler {
	
	private String returnString;
	
	public Ruler() {
	}

	public String eventExec(String jSonString, String decision) {
		
		returnString = parseToRequest(jSonString, decision);
		return returnString;
	}

	private String parseToRequest(String jSonString, String decision) {
		
		JSONObject object = (JSONObject) JSONValue.parse(jSonString);
		JSONArray argumentsArray = (JSONArray) object.get(ArgumentList);
		
		for (Object o : argumentsArray) {
			JSONObject valueEntry = (JSONObject) o;
			String name = (String) valueEntry.keySet().iterator().next();
			String value = (String) valueEntry.get(name);
			
			if(decision == "key"){
				returnString = name;
			} 
			if(decision == "value"){
				returnString = value;
			}
		}
		return returnString;
	}
}
