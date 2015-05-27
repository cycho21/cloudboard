package test.controller;

import java.util.ArrayList;

import test.view.ViewManager;
import kr.ac.uos.ai.cloudBoard.model.bean.CloudBoardMessage;
import kr.ac.uos.ai.cloudBoard.factory.BoardDataFactory;
import kr.ac.uos.ai.cloudBoard.model.bean.NotificationRule;
import kr.ac.uos.ai.cloudBoard.model.bean.QueryRule;
import kr.ac.uos.ai.cloudBoard.model.bean.SubscriptionRule;

public class Dispatcher {

	private RequestHandler 	 requestHandler;
	private ArrayList<String> connectionList;
	
	public Dispatcher(ViewManager view) {
		requestHandler = new RequestHandler(view);
	}

	public void go(String message, ArrayList<String> connectionList) {
		
		BoardDataFactory factory = BoardDataFactory.getInstance();
		CloudBoardMessage cloudBoardMessage = factory.parseMessageFromString(message);
		
		String author = cloudBoardMessage.getOperation().getFacts().getAuthor();			  // subscribe 시에 subscribe 하기를 원하는 author
		String name   = cloudBoardMessage.getOperation().getFacts().getName();				  
		String sender = cloudBoardMessage.getWriter();
		String operand = cloudBoardMessage.getOperation().getFacts().getOperand();
		String requestOperation = cloudBoardMessage.getOperation().getFacts().getOperation(); // subscribe 시에  subscribe 하기를 원하는 messageType
		
		NotificationRule notificationRule = cloudBoardMessage.getNotificationRule();
		QueryRule queryRule = new QueryRule(author, name);
		SubscriptionRule subRule = new SubscriptionRule(sender, name, operand, requestOperation, author);
		
		setRule(cloudBoardMessage, queryRule, subRule, notificationRule);
		requestHandler.go(cloudBoardMessage);
	}

	private void setRule(CloudBoardMessage cloudBoardMessage,
			QueryRule queryRule, SubscriptionRule subRule, NotificationRule notificationRule) {
		cloudBoardMessage.setRule(queryRule);
		cloudBoardMessage.setSubRule(subRule);
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public void addCon(ArrayList<String> connectionList) {
		this.connectionList = connectionList;
		requestHandler.addCon(connectionList);
	}
}
