package kr.ac.uos.ai.cloudBoard.synchronize;

import java.io.StringReader;

import org.junit.Test;

import kr.ac.uos.ai.cloudBoard.astNode.*;
import kr.ac.uos.ai.cloudBoard.javacc.DynamicComp;
import kr.ac.uos.ai.cloudBoard.javacc.ParseException;

public class Sinker {

	private WhileStatement  whileStatement;
	private IfStatement 	ifStatement;
	private Assignment 		assignment;
	String str = "gildong.leftSensor=gL; if(gL<10) {return wallEncoutered;};#";

	public Sinker() {
	}

	@Test
	public void Test() throws ParseException {
		ifStatement(str);
		assignStatment(str);
//		whileStatment(str);
	}

	public DynamicComp makeReaderAndComp(String string) {
		StringReader reader = new StringReader(str);
		DynamicComp dynamic = makeComp(reader);
		return dynamic; 
	}
	
	public DynamicComp makeComp(StringReader reader) {
		DynamicComp dynamic = new DynamicComp(reader);
		return dynamic;
	}

	public void ifStatement(String string) throws ParseException {
		DynamicComp dynamic = makeReaderAndComp(string);
		IfStatement a = (IfStatement) dynamic.getCodeBlock().getStatements().get(1);
		
		System.out.println(a.getOper());
		System.out.println(a.getExp().getLhs());
		System.out.println(a.getExp().getOperator());
		System.out.println(a.getExp().getRhs());
		System.out.println(a.getCondition().getWhatReturn());
	}

	
	
	public void whileStatment(String string) throws ParseException {
		DynamicComp dynamic = makeReaderAndComp(string);
		WhileStatement a = (WhileStatement) dynamic.getCodeBlock().getStatements().get(1);

		System.out.println(a.getExp().getLhs());
		System.out.println(a.getExp().getOperator());
		System.out.println(a.getExp().getRhs());
		System.out.println(a.getCondition().getWhatReturn());
	}

	public void ifStatment(String string) {

	}

	public void assignStatment(String string) throws ParseException {
		DynamicComp dynamic = makeReaderAndComp(string);
		Assignment b = (Assignment) dynamic.getCodeBlock().getStatements().get(0);

		System.out.println(b.getRobotName());
		System.out.println(b.getSensorName());
		System.out.println(b.getRight().getValue());
	}

	public void go(String string) {
	}

}
