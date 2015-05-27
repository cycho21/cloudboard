package test.controller;

import java.util.ArrayList;

import test.view.ViewManager;
import kr.ac.uos.ai.cloudBoard.model.bean.CloudBoardMessage;
import kr.ac.uos.ai.cloudBoard.control.BoardDataManager;
import kr.ac.uos.ai.cloudBoard.control.BoardRequestHandler;

public class RequestHandler {
	
	private BoardRequestHandler 	boardRequestHandler;
	private BoardDataManager    	data;
	
	public RequestHandler(ViewManager view) {
		data 				= new BoardDataManager(view);
		boardRequestHandler = new BoardRequestHandler(data);
	}
	
	public void go(CloudBoardMessage cloudBoardMessage) {
		boardRequestHandler.messageRecieved(cloudBoardMessage);
	}

	public void addCon(ArrayList<String> connectionList) {
		boardRequestHandler.addCon(connectionList);
	}

}