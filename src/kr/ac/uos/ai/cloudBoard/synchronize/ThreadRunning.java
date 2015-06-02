package kr.ac.uos.ai.cloudBoard.synchronize;

import static kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration.ArgumentList;

import java.io.Serializable;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import kr.ac.uos.ai.cloudBoard.model.bean.StatementObj;
import lombok.Data;

public @Data class ThreadRunning implements Runnable {

	private StatementObj obj;
	private DBCollection boardDataCollection;
	private DBCollection subscribeCollection;
	private DBCollection connectionCollection;
	private boolean go = true;
	private String conditionName;
	
	public ThreadRunning() {
		MongoClient mongoC = null;
		mongoC = new MongoClient();
		@SuppressWarnings("deprecation")
		DB db = mongoC.getDB("CloudBoardDataBase");
		boardDataCollection = db.getCollection("CloudBoardData");
		subscribeCollection = db.getCollection("CloudBoardDataSub");
		connectionCollection = db.getCollection("CloudBoardConnector");
	}

	public String parse(String queryString) {
		JSONObject object = (JSONObject) JSONValue.parse(queryString);
		JSONArray argumentsArray = (JSONArray) object.get(ArgumentList);
			String sensorValue = null; 
		for (Object o : argumentsArray){
			JSONObject valueEntry = (JSONObject) o;
			String name = (String) valueEntry.keySet().iterator().next();
			String value = (String) valueEntry.get(name);
			sensorValue = value;
		}
		return sensorValue;
	}
	
	public String query(){
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("author", obj.getRobotName());
		dbo.put("name", obj.getSensorName());
		System.out.println(dbo);
		DBCursor cursor = boardDataCollection.find(dbo);

		return cursor.next().toString();
	}
	
	public void run() {
		
			while(go){
				try {
					String queryString = query();
					System.out.println("parseString : " + parse(queryString));
					System.out.println("rhs			: " + obj.getRhs());
					if(obj.getOperator().equals("<")){
						if(Integer.parseInt(parse(queryString)) < Integer.parseInt(obj.getRhs())){
							System.out.println("WHILE DONE!!!!");
							makeDBO();
							go = false;
						}
					}
					if(obj.getOperator().equals(">")){
						if(Integer.parseInt(parse(queryString)) > Integer.parseInt(obj.getRhs())){
							System.out.println("WHILE DONE!!!!");
							makeDBO();
							go = false;
						}
					}
					
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}

	private void makeDBO() {
		BasicDBObject dbo = new BasicDBObject();
		HashMap<String, Serializable> hm = new HashMap<String, Serializable>();
		HashMap<String, String> hm2 = new HashMap<String, String>();
		dbo.put("sender", "cloudboard");
		dbo.put("messageType", "NOTIFY");
		
		hm2.put(conditionName, obj.getWhatReturn());
		
		hm.put("arguments", hm2);
		hm.put("name", "CONDITION");
		dbo.put("data", hm);
		System.out.println("?!?!" + dbo);
	}

	public void setConditionName(String string) {
		this.conditionName = string;
	}
	
}
