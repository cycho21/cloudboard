package kr.ac.uos.ai.cloudBoard;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObjectBuilder;

public class Log {
	
	private String sender;
	private String receiver;
	private String messageType;
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Log() {
	}
	
	public void parseFromStringToLog(String logString) {
	JSONObject object = (JSONObject) JSONValue.parse(logString);
	return ;
	}
}
