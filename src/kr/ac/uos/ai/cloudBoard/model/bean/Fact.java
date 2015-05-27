package kr.ac.uos.ai.cloudBoard.model.bean;

import java.util.Map;

import test.model.Data;

public class Fact {
	private final String						author;
	private final long							productionTime;
	private final long							expirationTime;
	private final String						dataName;
	private final Data							data;
	private final String						operand;
	private 	  String 						operation;
	private final String 						messageType;

	public String getMessageType() {
		return messageType;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
	public String getOperation() {
		return operation;
	}

	public String getOperand() {
		return operand;
	}

	public Fact(String author, String name, String operand, String operation, String messageType, long pTime, long eTime) {
		this.author = author;
		this.dataName = name;
		this.operand = operand;
		this.messageType = messageType;
		this.operation = operation;
		this.productionTime = pTime;
		this.expirationTime = eTime;
		this.data	   = new Data();
	}
	
	public Data getData() {
		return data;
	}

	public String getName() {
		return dataName;
	}

	public Map<String, String> getArguments() {
		return data.getArguments();
	}
	
	public String getValue(String key){
		return data.getArguments().get(key);
	}
	
	public void appendArgument(String key, String value){
		data.getArguments().put(key, value);
	}
	
	public String getAuthor() {
		return author;
	}

	public long getProductionTime() {
		return productionTime;
	}

	public long getExpirationTime() {
		return expirationTime;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(dataName);
		sb.append(' ');
		for (String string : data.getArguments().keySet()) {
			System.out.println(string);
			sb.append(string).append('=').append(data.getArguments().get(string)).append(' ');
		}
		return sb.toString();
	}
}
