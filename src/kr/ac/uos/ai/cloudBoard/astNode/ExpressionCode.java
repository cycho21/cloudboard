package kr.ac.uos.ai.cloudBoard.astNode;

import lombok.Data;

public @Data class ExpressionCode {
	
	private String					value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
