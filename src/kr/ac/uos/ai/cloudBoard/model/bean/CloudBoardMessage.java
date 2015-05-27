package kr.ac.uos.ai.cloudBoard.model.bean;

import java.util.ArrayList;

import test.model.Operation;

public class CloudBoardMessage {
	
	private String 					sender;
	private String					receiver;
	private long					receiveTime;
	private QueryRule				queryRule;
	private SubscriptionRule		subcriptionRule;
	private Operation				operation;
	private NotificationRule	    notificationRule;
	private ArrayList<String>       connectionList;
	
	
	public ArrayList<String> getConnectionList() {
		return connectionList;
	}
	public void setConnectionList(ArrayList<String> connectionList) {
		this.connectionList = connectionList;
	}
	public NotificationRule getNotificationRule() {
		return notificationRule;
	}
	public void setNotificationRule(NotificationRule notificationRule) {
		this.notificationRule = notificationRule;
	}
	public long getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(long receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public SubscriptionRule getSubRule() {
		return subcriptionRule;
	}
	public void setSubRule(SubscriptionRule subRule) {
		this.subcriptionRule = subRule;
	}
	
	public String getWriter() {
		return sender;
	}
	public void setWriter(String writer) {
		this.sender = writer;
	}

	public QueryRule getRule() {
		return queryRule;
	}
	
	public void setRule(QueryRule rule) {
		this.queryRule = rule;
	}
	
}