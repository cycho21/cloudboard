package kr.ac.uos.ai.cloudBoard.model.bean;

public class EventRule {
	
	private String messageType;
	private String eventName;
	private String mainRef;
	private String subRef;
	
	public EventRule() {
	
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getMainRef() {
		return mainRef;
	}
	
	public void setMainRef(String mainRef) {
		this.mainRef = mainRef;
	}
	
	public String getSubRef() {
		return subRef;
	}
	
	public void setSubRef(String subRef) {
		this.subRef = subRef;
	}

}
