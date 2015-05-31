package kr.ac.uos.ai.cloudBoard.synchronize;

import java.io.StringReader;

import org.junit.Test;


import kr.ac.uos.ai.cloudBoard.javacc.DynamicComp;
import kr.ac.uos.ai.cloudBoard.javacc.ParseException;

public class Tester {

	public Tester() {
		
	}
	
	
	@Test
	public void go() throws ParseException {
		String str = "while(xyz){abc;return abc;}; x.y=t;#";
		StringReader reader = new StringReader(str);
		DynamicComp dynamic = new DynamicComp(reader);
		dynamic.makeParseAndCondition(str);
		System.out.println(dynamic.getCodeBlock());
	}
}
