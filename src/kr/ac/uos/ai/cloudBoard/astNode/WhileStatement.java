package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

public class WhileStatement extends StatementCode {
	private ExpressionCode					condition;				// Condition
	private LinkedList<StatementCode>		codeBlock;				// 
	
	public WhileStatement() {
		codeBlock = new LinkedList<StatementCode>();
	}
	
	public ExpressionCode getCondition() {
		return condition;
	}
	
	public void setCondition(ExpressionCode condition) {
		this.condition = condition;
	}
	
	public LinkedList<StatementCode> getCodeBlock() {
		return codeBlock;
	}
	
	public void setCodeBlock(LinkedList<StatementCode> codeBlock) {
		this.codeBlock = codeBlock;
	}
}
