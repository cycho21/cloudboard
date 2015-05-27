package kr.ac.uos.ai.cloudBoard.control;

import java.util.ArrayList;

import kr.ac.uos.ai.cloudBoard.dao.DataBase;
import kr.ac.uos.ai.cloudBoard.model.bean.CloudBoardMessage;
import kr.ac.uos.ai.cloudBoard.model.bean.Fact;
import kr.ac.uos.ai.cloudBoard.model.bean.NotificationRule;
import kr.ac.uos.ai.cloudBoard.model.bean.QueryRule;
import kr.ac.uos.ai.cloudBoard.model.bean.SubscriptionRule;
import test.model.Operation;
import test.view.ViewManager;


public class BoardDataManager {
	private DataBase						database;
	
	public BoardDataManager(ViewManager view) {
		database = new DataBase(view);
	}
	
	public void insertBoardData(QueryRule query, Fact data){
		database.insertBoardData(query, data);
		database.dumpBoardData();
	}

	public Fact[] dumpBoardData(){
		return null;
	}

	public void deleteBoardData(Fact data) {
		database.deleteBoardData(data);
	}
	
	public Fact[] queryBoardData(Fact data){
		return database.queryBoardData(data);
	}

	public void modifyBoardData(QueryRule query, Fact data) {
		database.modifyBoardData(query, data);
	}

	public void queryBoardData(QueryRule rule) {
		database.queryBoardData(rule);
	}

	public void postBoardData(Fact data) {
		database.postBoardData(data);
	}

	public void addSubscribe(SubscriptionRule subscriptionRule, Fact data, NotificationRule notificationRule) {
		database.subBoardData(subscriptionRule, data, notificationRule);
	}

	public void removeSubscribe(SubscriptionRule subscriptionRule, Fact data) {
		database.removeSubscribe(subscriptionRule, data);
	}

	public void addCon(ArrayList<String> connectionList) {
		database.addCon(connectionList);
	}

	public void getBoardData(CloudBoardMessage message) {
		database.getBoardData(message);
	}

	
}
