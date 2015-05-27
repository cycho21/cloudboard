package kr.ac.uos.ai.cloudBoard.control;

import java.util.ArrayList;

import kr.ac.uos.ai.cloudBoard.model.bean.CloudBoardMessage;
import kr.ac.uos.ai.cloudBoard.model.bean.MessageType;
import kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration;

public class BoardRequestHandler {
	private final BoardDataManager					board;
	
	public BoardRequestHandler(BoardDataManager board) {
		this.board = board;
	}
	
	public CloudBoardMessage messageRecieved(CloudBoardMessage message){
		switch (MessageType.valueOf(message.getOperation().getOperation().toUpperCase())) {
		case ASSERT:
			board.insertBoardData(message.getRule(), message.getOperation().getFacts());
			break;
		case MATCH:
			board.queryBoardData(message.getRule());
			break;
		case POST:
			board.postBoardData(message.getOperation().getFacts());
			break;
		case RETRACT:
			board.deleteBoardData(message.getOperation().getFacts());
			break;
		case SUBSCRIBE:
			board.addSubscribe(message.getSubRule(), message.getOperation().getFacts(), message.getNotificationRule());
			break;
		case UNSUBSCRIBE:
			board.removeSubscribe(message.getSubRule(), message.getOperation().getFacts());
			break;
		case UPDATE:
			board.modifyBoardData(message.getRule(), message.getOperation().getFacts());
			break;
		case GETS:
			board.getBoardData(message);
			break;
		default:
			break;
		}
		
		CloudBoardMessage responseMessage = new CloudBoardMessage();
		responseMessage.setWriter(NameConfiguration.CloudBoardURI);
		return responseMessage;
	}

	public void addCon(ArrayList<String> connectionList) {
		board.addCon(connectionList);
	}

}
