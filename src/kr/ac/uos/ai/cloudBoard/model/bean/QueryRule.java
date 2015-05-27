package kr.ac.uos.ai.cloudBoard.model.bean;

import java.util.LinkedList;
import java.util.Map;

public class QueryRule {
	private String 						author;
	private String						name;
	private LinkedList<Rule>			rules;
	
	public QueryRule(String author, String name) {
		this.author = author;
		this.name = name;
		this.rules = new LinkedList<Rule>();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Rule> getRules() {
		return rules;
	}

	public void setRules(LinkedList<Rule> rules) {
		this.rules = rules;
	}
	
	public void appendRule(Rule rule){
		this.rules.add(rule);
	}
	
	public boolean isMatched(Fact data){
		if(!data.getAuthor().equals(author)){
			return false;
		}
		if(!data.getName().equals(name)){
			return false;
		}
		
		Map<String, String> args = data.getArguments();

		for (Rule rule : rules) {
			String value = args.get(rule.getArgName());
			if(value==null) return false;
			switch(rule.getOperation()){
			case Equals:
				if(!rule.getOperand().equals(value))
					return false;
				break;
			case GreaterOrEqual:
				if(value.contains(".")){
					long longValue = Long.valueOf(value);
					long longOperand = Long.valueOf(rule.getOperand());
					if(longValue < longOperand){
						return false;
					}
				}else{
					int intValue = Integer.valueOf(value);
					int intOperand = Integer.valueOf(rule.getOperand());
					if(intValue < intOperand){
						return false;
					}
				}
				break;
			case GreaterThan:
				if(value.contains(".")){
					long longValue = Long.valueOf(value);
					long longOperand = Long.valueOf(rule.getOperand());
					if(longValue <= longOperand){
						return false;
					}
				}else{
					int intValue = Integer.valueOf(value);
					int intOperand = Integer.valueOf(rule.getOperand());
					if(intValue <= intOperand){
						return false;
					}
				}
				break;
			case LessOrEqual:
				if(value.contains(".")){
					long longValue = Long.valueOf(value);
					long longOperand = Long.valueOf(rule.getOperand());
					if(longValue > longOperand){
						return false;
					}
				}else{
					int intValue = Integer.valueOf(value);
					int intOperand = Integer.valueOf(rule.getOperand());
					if(intValue > intOperand){
						return false;
					}
				}
				break;
			case LessThan:
				if(value.contains(".")){
					long longValue = Long.valueOf(value);
					long longOperand = Long.valueOf(rule.getOperand());
					if(longValue >= longOperand){
						return false;
					}
				}else{
					int intValue = Integer.valueOf(value);
					int intOperand = Integer.valueOf(rule.getOperand());
					if(intValue >= intOperand){
						return false;
					}
				}
				break;
			case NotEquals:
				if(rule.getOperand().equals(value))
					return false;
				break;
			default:
				return false;
			}
		}
		return true;
	}
}
