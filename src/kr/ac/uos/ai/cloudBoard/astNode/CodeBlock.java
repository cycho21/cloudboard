package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

public class CodeBlock {
	
	private LinkedList<StatementCode>			statements;

	public CodeBlock() {
		statements = new LinkedList<StatementCode>();
	}
	
	public LinkedList<StatementCode> getStatements() {
		return statements;
	}

	public void setStatements(LinkedList<StatementCode> statements) {
		this.statements = statements;
	}
	
}
