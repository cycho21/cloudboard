package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

import lombok.Data;

public @Data class WhileStatement extends StatementCode {
	
	private LinkedList<StatementCode>		codeBlock;
	private Condition						condition;
	private ExpressionCode					exp;
	private String							oper;
	
	public WhileStatement() {
		codeBlock = new LinkedList<StatementCode>();
	}
}
