package kr.ac.uos.ai.cloudBoard.model.bean;

import lombok.Data;

public @Data class StatementObj {

	private String robotName;
	private String sensorName;
	private String operator;
	private String rhs;
	private String whatReturn;
}
