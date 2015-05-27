package test.model;

import kr.ac.uos.ai.cloudBoard.model.bean.Fact;


public class Operation {

	private Fact 	facts;
	private String  messageType;
	private long 	operationTime; // Operation이 요청되었을 때의 시간

	public Operation() {
		this.operationTime = System.currentTimeMillis();
	}

	public long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(long operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperation() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Fact getFacts() {
		return facts;
	}

	public void setFacts(String author, String dataName, String operand, String operation, String messageType, long pTime, long eTime) {
		Fact data = new Fact(author, dataName, operand, operation, messageType, pTime, eTime);
		this.facts = data;
	}

}
