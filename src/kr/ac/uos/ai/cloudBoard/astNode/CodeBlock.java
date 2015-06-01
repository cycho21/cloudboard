package kr.ac.uos.ai.cloudBoard.astNode;

import java.util.LinkedList;

import lombok.Data;

public @Data class CodeBlock {
	
	private LinkedList<StatementCode>			statements;

	public CodeBlock() {
		statements = new LinkedList<StatementCode>();
	}

}
