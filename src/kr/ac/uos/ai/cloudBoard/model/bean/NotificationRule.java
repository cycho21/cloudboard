package kr.ac.uos.ai.cloudBoard.model.bean;

import java.util.HashMap;

public class NotificationRule {


	private HashMap<String, String> arguments;
	
	public NotificationRule(){
	}

	public HashMap<String, String> getArguments() {
		return arguments;
	}
	
	public void setArguments(HashMap<String, String> arguments) {
		this.arguments = arguments;
	}

}
