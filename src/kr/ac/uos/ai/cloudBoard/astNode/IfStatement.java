package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

import lombok.Data;

public @Data class IfStatement extends StatementCode {
	
	private ExpressionCode					condition;				
	private LinkedList<StatementCode>		codeBlock;
	
	public IfStatement() {
		codeBlock = new LinkedList<StatementCode>();
	}
}
