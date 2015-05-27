package kr.ac.uos.ai.cloudBoard.model.bean;

public class Rule {
	private String					argName;
	private OperationLiteral		operation;
	private String					operand;
	
	public Rule(String argumentName, OperationLiteral operation, String operand) {
		this.argName = argumentName;
		this.operation = operation;
		this.operand = operand;
	}
	
	public String getArgName() {
		return argName;
	}
	public void setArgName(String argName) {
		this.argName = argName;
	}
	public OperationLiteral getOperation() {
		return operation;
	}
	public void setOperation(OperationLiteral operation) {
		this.operation = operation;
	}
	public String getOperand() {
		return operand;
	}
	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	
}
