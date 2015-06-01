package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

import lombok.Data;

public @Data class IfStatement extends StatementCode {
	
	private LinkedList<StatementCode>		codeBlock;
	private Condition						condition;
	private ExpressionCode					exp;
	private String							oper;
	
	public IfStatement() {
		codeBlock = new LinkedList<StatementCode>();
	}
}
