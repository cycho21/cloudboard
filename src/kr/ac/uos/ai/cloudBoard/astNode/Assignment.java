package kr.ac.uos.ai.cloudBoard.astNode;

public class Assignment extends StatementCode{
	
	private String 				left;
	private ExpressionCode		right;
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public ExpressionCode getRight() {
		return right;
	}
	public void setRight(ExpressionCode right) {
		this.right = right;
	}
	
	
}
