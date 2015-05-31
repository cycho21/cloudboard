package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

import lombok.Data;

public @Data class Condition {

	@SuppressWarnings("rawtypes")
	private LinkedList statements;
	private String whenReturn;
	
}
