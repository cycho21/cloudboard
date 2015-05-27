package kr.ac.uos.ai.cloudBoard.model.bean;

import java.util.Map;


public class ReceiveRule {
	
	private String requester;
	private String author;
	private String dataName;
	private String operation;
	private String operand;
	private String type;
	private NotificationRule notificationRule;

	public NotificationRule getNotificationRule() {
		return notificationRule;
	}

	public void setNotificationRule(NotificationRule notificationRule) {
		this.notificationRule = notificationRule;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	private Map<String, String> arguments;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	public ReceiveRule() { 
	}

	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public Map<String, String> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, String> arguments) {
		this.arguments = arguments;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
