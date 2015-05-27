package kr.ac.uos.ai.cloudBoard.model.bean;

import java.util.LinkedList;

public class SubscriptionRule {

	private String sender;
	private String name;
	private String operation;
	private String operand;
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	private LinkedList<ReceiveRule> rules;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public SubscriptionRule(String sender, String name, String operand,
			String operation, String author) {
		this.author = author;
		this.name = name;
		this.sender = sender;
		this.operation = operation;
		this.operand = operand;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public LinkedList<ReceiveRule> getRules() {
		return rules;
	}

	public void setRules(LinkedList<ReceiveRule> rules) {
		this.rules = rules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
