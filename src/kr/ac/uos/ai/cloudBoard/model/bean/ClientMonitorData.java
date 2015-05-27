package kr.ac.uos.ai.cloudBoard.model.bean;

import com.mongodb.BasicDBObject;

public class ClientMonitorData {

	public ClientMonitorData() {
	}

	public BasicDBObject go(CloudBoardMessage message) {
		Fact fact = message.getOperation().getFacts();
	
		BasicDBObject dbo = new BasicDBObject();
		switch (fact.getName()) {
		case "conditionList":
				dbo.put("name", "condition");
			break;
		case "eventList":
				dbo.put("name", "event");
			break;
		case "robotSensorList":
				dbo.put("name", "robotSensor");
			break;
		default :
			break;
		}
		return dbo;
	}
}

//{"sender":"user5","messageType":"get","data":{"name":"robotList","author":"user5","pTime":1428237917188,"eTime":1428237917188}}
//{"sender":"user5","messageType":"get","data":{"name":"conditionList","author":"user5","pTime":1428237921954,"eTime":1428237921954}}