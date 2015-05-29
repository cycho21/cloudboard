package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

import lombok.Data;

public @Data class WhileStatement extends StatementCode {
	
	private ExpressionCode					condition;				
	private LinkedList<StatementCode>		codeBlock;
	
	public WhileStatement() {
		codeBlock = new LinkedList<StatementCode>();
	}
}
