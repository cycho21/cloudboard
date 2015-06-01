package kr.ac.uos.ai.cloudBoard.astNode;

import lombok.Data;

public @Data class ExpressionCode {

	private String value;
	private String lhs;
	private String rhs;
	private String operator;
	private String robotName;
	private String sensorName;
}
