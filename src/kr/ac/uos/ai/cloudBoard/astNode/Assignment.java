package kr.ac.uos.ai.cloudBoard.astNode;

import lombok.Data;

/*
 * This is git test
 */
public @Data class Assignment extends StatementCode{
	private String 				left;
	private ExpressionCode		right;
}
