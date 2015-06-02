package kr.ac.uos.ai.cloudBoard.synchronize;

import java.io.StringReader;

import org.junit.Test;

import kr.ac.uos.ai.cloudBoard.astNode.*;
import kr.ac.uos.ai.cloudBoard.javacc.DynamicComp;
import kr.ac.uos.ai.cloudBoard.javacc.ParseException;
import kr.ac.uos.ai.cloudBoard.model.bean.StatementObj;

public class Sinker {

	private WhileStatement  whileStatement;
	private IfStatement 	ifStatement;
	private Assignment 		assignment;
	String str = "if(gildong.leftSensor < 10){};#";

	public Sinker() {
	}

	public void Test() throws ParseException {
		
	}

	public DynamicComp makeReaderAndComp(String string) {
		StringReader reader = new StringReader(string);
		DynamicComp dynamic = makeComp(reader);
		return dynamic; 
	}
	
	public DynamicComp makeComp(StringReader reader) {
		DynamicComp dynamic = new DynamicComp(reader);
		return dynamic;
	}
	
	public StatementObj ifStatement(String string) throws ParseException {
		DynamicComp dynamic = makeReaderAndComp(string);
		IfStatement a = (IfStatement) dynamic.getCodeBlock().getStatements().get(0);
		StatementObj statementObj = new StatementObj();
		statementObj.setOperator(a.getExp().getOperator());
		statementObj.setRobotName(a.getExp().getRobotName());
		statementObj.setSensorName(a.getExp().getSensorName());
		statementObj.setRhs(a.getExp().getRhs());
		return statementObj;
	}

	
	@Test
	public void ifStatement() throws ParseException {
		DynamicComp dynamic = makeReaderAndComp(str);
		IfStatement a = (IfStatement) dynamic.getCodeBlock().getStatements().get(0);
		StatementObj statementObj = new StatementObj();
		statementObj.setOperator(a.getExp().getOperator());
		statementObj.setRobotName(a.getExp().getRobotName());
		statementObj.setSensorName(a.getExp().getSensorName());
		statementObj.setRhs(a.getExp().getRhs());
	}
}
