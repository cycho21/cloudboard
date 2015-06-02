package kr.ac.uos.ai.cloudBoard.dao;

import static kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.ac.uos.ai.cloudBoard.javacc.ParseException;
import kr.ac.uos.ai.cloudBoard.model.bean.ClientMonitorData;
import kr.ac.uos.ai.cloudBoard.model.bean.CloudBoardMessage;
import kr.ac.uos.ai.cloudBoard.model.bean.Fact;
import kr.ac.uos.ai.cloudBoard.model.bean.JSONParsingBean;
import kr.ac.uos.ai.cloudBoard.model.bean.NotificationRule;
import kr.ac.uos.ai.cloudBoard.model.bean.QueryRule;
import kr.ac.uos.ai.cloudBoard.model.bean.ReceiveRule;
import kr.ac.uos.ai.cloudBoard.model.bean.Rule;
import kr.ac.uos.ai.cloudBoard.model.bean.StatementObj;
import kr.ac.uos.ai.cloudBoard.model.bean.SubscriptionRule;
import kr.ac.uos.ai.cloudBoard.synchronize.Sinker;
import kr.ac.uos.ai.cloudBoard.synchronize.ThreadRunning;
import kr.ac.uos.ai.cloudBoard.factory.BoardDataFactory;
import kr.ac.uos.ai.cloudBoard.factory.JsonParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import test.controller.Ruler;
import test.controller.Sender;
import test.view.ViewManager;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataBase {

	private DBCollection boardDataCollection;
	private DBCollection subscribeCollection;
	private DBCollection connectionCollection;
	private ArrayList<ReceiveRule> subList;
	private ArrayList<String> searchedDB;
	private String author;
	private String operand;
	private String dataName;
	private String requester;
	private String operation;
	private Ruler ruler;
	private Map<String, String> arguments;
	private boolean check;
	private ViewManager view;
	private JsonParser jParser;
	private String value;
	private String key;

	@SuppressWarnings("resource")
	public DataBase(ViewManager view) {
		searchedDB = new ArrayList<String>();
		subList = new ArrayList<ReceiveRule>();
		ruler = new Ruler();
		this.view = view;
		MongoClient mongoC = null;
		mongoC = new MongoClient();

		@SuppressWarnings("deprecation")
		DB db = mongoC.getDB("CloudBoardDataBase");
		boardDataCollection = db.getCollection("CloudBoardData");
		subscribeCollection = db.getCollection("CloudBoardDataSub");
		connectionCollection = db.getCollection("CloudBoardConnector");
		jParser = new JsonParser();
	}

	public void dumpBoardData() {
		DBCursor cursor = boardDataCollection.find();
		while (cursor.hasNext()) {
			view.printMongoDBMessage(cursor.next().toString());
		}
	}

	public void dumpSubscribeData() {
		DBCursor cursor = subscribeCollection.find();
		while (cursor.hasNext()) {
			view.printSubscribe(cursor.next().toString());
		}
	}

	public void insertBoardData(QueryRule query, Fact data) {

		BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
		JSONObject object = BoardDataFactory.getInstance()
				.parseInsertBoardDataToJSON(data);

		@SuppressWarnings("unchecked")
		Set<String> keys = object.keySet();
		for (String key : keys) {
			builder.append(key, object.get(key));
		}

		DBObject dbo = builder.get();

		DBCursor cursor = boardDataCollection.find(dbo);
		if (cursor.hasNext()) {
			view.printLogMessage("\n"
					+ data.getAuthor()
					+ " requests Insert process"
					+ "\n Insert process denied. Because there is repetitive data");
		} else {
			JsonParser jParser = new JsonParser();
			JSONParsingBean jBean = jParser.parse(dbo.toString());
			boardDataCollection.insert(dbo);
			view.printLogMessage("\n" + data.getAuthor() + " INSERT");
			view.printLogMessage("Name      : " + data.getName());
			view.printLogMessage("Key         : " + jBean.getKey());
			view.printLogMessage("Value       : " + jBean.getValue());
		}

		insertSub(data);
	}

	@SuppressWarnings({ "unchecked" })
	public void getBoardData(CloudBoardMessage message) {
		ClientMonitorData cmd = new ClientMonitorData();

		Fact fact = message.getOperation().getFacts();
		requester = message.getWriter();

		JSONArray arr = new JSONArray();
		JSONArray arr2 = new JSONArray();
		HashMap<String, String> map = new HashMap<String, String>();

		if (fact.getName().equals("robotList")) {

			BasicDBObject sendD = new BasicDBObject();
			DBCursor cursor = connectionCollection.find();

			while (cursor.hasNext()) {

				DBObject dboTemp = cursor.next();
				for (int i = 1; i < dboTemp.keySet().size(); i++) {
					map.put(dboTemp.get(Integer.toString(i)).toString(),
							Integer.toString(i));
				}
			}

			arr.add(new BasicDBObject("name", fact.getName()).append(
					"arguments", arr2));
			arr2.add(map);
			sendD.put("messageType", "gets");
			sendD.put("data", arr);

			sendMessage(sendD.toString());

		} else {

			BasicDBObject dbo = cmd.go(message);
			BasicDBObject sendD = new BasicDBObject();
			DBCursor cursor = boardDataCollection.find(dbo);

			while (cursor.hasNext()) {
				String string = cursor.next().toString();
				JSONObject object = (JSONObject) JSONValue.parse(string);
				JSONArray argumentsArray = (JSONArray) object.get(ArgumentList);
				for (Object o : argumentsArray) {
					JSONObject valueEntry = (JSONObject) o;
					String name = (String) valueEntry.keySet().iterator()
							.next();
					String value = (String) valueEntry.get(name);
					map.put(name, value);
				}
			}

			arr.add(new BasicDBObject("name", fact.getName()).append(
					"arguments", arr2));
			arr2.add(map);
			sendD.put("messageType", "gets");
			sendD.put("data", arr);

			sendMessage(sendD.toString());
		}
	}

	public void insertSub(Fact data) {
		for (int i = 0; i <= subList.size() - 1; i++) {
			for (String key : subList.get(i).getArguments().keySet()) {
				if (data.getArguments().containsKey(key)) {
					doSub(i);
				}
			}
		}
	}

	private DBObject converteDBOFromObject(Fact data) {
		BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
		JSONObject object = BoardDataFactory.getInstance()
				.parseBoardDataToJSON(data);

		@SuppressWarnings("unchecked")
		Set<String> keys = object.keySet();
		for (String key : keys) {
			builder.append(key, object.get(key));
		}
		DBObject dbo = builder.get();
		return dbo;
	}

	public Fact[] getDataByCursor(DBCursor cursor) {

		Fact[] boardData = new Fact[cursor.count()];
		List<DBObject> dboArray = cursor.toArray();

		for (int i = 0; i < dboArray.size(); i++) {
			DBObject data = dboArray.get(i);
			Fact iData = new Fact(data.get(DataAuthor).toString(), data.get(
					DataName).toString(), data.get("operand").toString(), data
					.get("operation").toString(), data.get(MessageType)
					.toString(), Long.valueOf(data.get(ProductionTime)
					.toString()), Long.valueOf(data.get(ExpriationTime)
					.toString()));
			BasicDBList dbo = (BasicDBList) data.get(ArgumentList);
			for (Object object : dbo) {
				DBObject value = (DBObject) object;
				iData.appendArgument((String) value.get(ArgumentName),
						(String) value.get(ArgumentValue));
			}
			boardData[i] = iData;
		}
		cursor.close();
		return boardData;
	}

	public void deleteBoardData(QueryRule data) {
		DBObject query = generateQueryOperation(data);
		DBCursor cursor = boardDataCollection.find(query);
		List<DBObject> deleteData = cursor.toArray();
		for (DBObject dbObject : deleteData) {
			boardDataCollection.remove(dbObject);
		}
	}

	private DBObject generateQueryOperation(QueryRule data) {

		BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
		if (data.getAuthor() != null) {
			builder.append(DataAuthor, data.getAuthor());
		}

		if (data.getName() != null) {
			builder.append(DataName, data.getName());
		}

		if (!data.getRules().isEmpty()) {
			BasicDBObjectBuilder argArrayBuilder = new BasicDBObjectBuilder();
			for (Rule rule : data.getRules()) {
				BasicDBObject dbo;
				switch (rule.getOperation()) {
				case Equals:
					argArrayBuilder
							.append(rule.getArgName(), rule.getOperand());
					break;
				case GreaterOrEqual:
					dbo = new BasicDBObject("$gte", rule.getOperand());
					argArrayBuilder.append(rule.getArgName(), dbo);
					break;
				case GreaterThan:
					dbo = new BasicDBObject("$gt", rule.getOperand());
					argArrayBuilder.append(rule.getArgName(), dbo);
					break;
				case LessOrEqual:
					dbo = new BasicDBObject("$lte", rule.getOperand());
					argArrayBuilder.append(rule.getArgName(), dbo);
					break;
				case LessThan:
					dbo = new BasicDBObject("$le", rule.getOperand());
					argArrayBuilder.append(rule.getArgName(), dbo);
					break;
				case NotEquals:
					dbo = new BasicDBObject("$ne", rule.getOperand());
					argArrayBuilder.append(rule.getArgName(), dbo);
					break;
				default:
					break;
				}
			}
			builder.append(ArgumentList, argArrayBuilder.get());
		}
		return builder.get();
	}

	public void modifyBoardData(QueryRule query, Fact data) {
		DBCursor cursor = boardDataCollection
				.find(generateQueryOperation(query));
		while (cursor.hasNext())
			boardDataCollection.update(cursor.next(),
					converteDBOFromObject(data));

		arguments = data.getArguments();
		key = null;
		value = null;
		for (String s : arguments.keySet()) {
			key = s;
			value = arguments.get(s);
		}

		view.printLogMessage("\n" + data.getAuthor() + " UPDATE ");
		view.printLogMessage("Name      : " + data.getName());
		view.printLogMessage("Key         : " + key);
		view.printLogMessage("value       : " + value);
		subListCheck(data);
	}

	public void subListCheck(Fact data) {
		for (int i = 0; i < subList.size(); i++) {
			for (String key : subList.get(i).getArguments().keySet()) {
				if (data.getArguments().containsKey(key)) {
					doSub(i);
				}
			}
		}
	}

	public Fact[] queryBoardData(QueryRule rule) {
		DBCursor cursor = boardDataCollection
				.find(generateQueryOperation(rule));
		Fact[] queriedData = getDataByCursor(cursor);
		return queriedData;
	}

	public void deleteBoardData(Fact data) {

	}

	public Fact[] queryBoardData(Fact data) {
		return null;
	}

	private void queryAndThread(StatementObj obj) {
		DBCursor cursor = boardDataCollection.find(generateThreadQuery(
				obj.getRobotName(), obj.getSensorName(), obj.getRhs(),
				obj.getOperator()));
		while (cursor.hasNext()) {
			searchedDB.add(cursor.next().toString());
		}
	}

	private BasicDBObject generateThreadQuery(String robotName,
			String sensorName, String rhs, String oper) {
		BasicDBObject dbo = new BasicDBObject();

		switch (oper) {
		case ">":
			dbo.put("author", robotName);
			dbo.put("arguments." + sensorName, new BasicDBObject("#gt", rhs));
			break;
		case "<":
			dbo.put("author", robotName);
			dbo.put("arguments." + sensorName, new BasicDBObject("#lt", rhs));
			break;
		}
		System.out.println(dbo);
		return dbo;
	}

	public BasicDBObject findQuery(String conditionName, String author) {
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("author", author);
		dbo.put("name", "condition");
		return dbo;
	}

	public String parseCode(String string) {

		JSONObject object = (JSONObject) JSONValue.parse(string);
		JSONArray argumentsArray = (JSONArray) object.get(ArgumentList);
		String code = null;
		for (Object o : argumentsArray) {
			JSONObject valueEntry = (JSONObject) o;
			String name = (String) valueEntry.keySet().iterator().next();
			String value = (String) valueEntry.get(name);
			code = value;
		}
		return code;
	}

	public void subBoardData(SubscriptionRule subscriptionRule, Fact data,
			NotificationRule notificationRule) {
		check = false;

		if (data.getName().toUpperCase().equals("CONDITION")) {
			Sinker sinker = new Sinker();

			Set<String> key = data.getArguments().keySet();
			
			String conditionName = null;
			StatementObj obj = null;
System.out.println("1111111111111");
			ThreadRunning tRun = new ThreadRunning();
			tRun.setObj(obj);
			for (String s : key) {
				conditionName = s;
				tRun.setConditionName(data.getArguments().get(s));
			}

			DBCursor dbcursor = boardDataCollection.find(findQuery(
					conditionName, data.getAuthor()));

			String requestString = dbcursor.next().toString();

			try {
				obj = sinker.ifStatement(parseCode(requestString));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Thread tRunThread = new Thread(tRun);
			tRunThread.start();

		} else {
			if (subCheck(subscriptionRule, data)) {
				for (String s : data.getArguments().keySet()) {
					view.printLogMessage("\n" + subscriptionRule.getSender()
							+ " subscribes " + subscriptionRule.getAuthor()
							+ " \'s " + subscriptionRule.getOperation()
							+ " data " + "(" + subscriptionRule.getOperand()
							+ " (" + s + " : " + data.getArguments().get(s)
							+ "))" + "  (dataName : " + data.getName() + ")");
				}

				DBObject dbo = subConvert(subscriptionRule, data,
						notificationRule);
				subscribeCollection.insert(dbo);

				ReceiveRule receiveRule = new ReceiveRule();
				receiveRule.setDataName(data.getName());
				receiveRule.setRequester(subscriptionRule.getSender());
				receiveRule.setAuthor(subscriptionRule.getAuthor());
				receiveRule.setOperand(subscriptionRule.getOperand());
				receiveRule.setOperation(subscriptionRule.getOperation());
				receiveRule.setArguments(data.getArguments());
				receiveRule.setNotificationRule(notificationRule);
				subList.add(receiveRule);

				String key = null;
				String value = null;
				for (String s : data.getArguments().keySet()) {
					key = s;
					value = data.getArguments().get(s);
				}

				view.printLogMessage("\n" + subscriptionRule.getSender()
						+ " Subscribe /* Subscribe list added");
				view.printLogMessage("Name      : " + data.getName());
				view.printLogMessage("Sender    : "
						+ subscriptionRule.getAuthor());
				view.printLogMessage("Type        : " + operation);
				view.printLogMessage("Key         : " + key);
				view.printLogMessage("value       : " + value);
				subscribeDo();
			} else {
			}
		}

	}

	private boolean subCheck(SubscriptionRule subscriptionRule, Fact data) {
		check = false;
		operand = subscriptionRule.getOperand();
		dataName = subscriptionRule.getName();
		author = subscriptionRule.getAuthor();
		arguments = data.getArguments();
		operation = subscriptionRule.getOperation();

		if (subList.size() == 0) {
			check = true;
		} else {
			check = true;
			for (int i = 0; i < subList.size(); i++) {
				if (author.equals(subList.get(i).getAuthor())) {
					if (dataName.equals(subList.get(i).getDataName())) {
						for (String s : arguments.keySet()) {
							if (subList.get(i).getArguments().containsKey(s)) {
								if (subList.get(i).getArguments().get(s)
										.equals(arguments.get(s))) {
									check = false;
								}
							}
						}
					}
				}
			}
		}
		return check;
	}

	private void subQuery(String key, String value, String operand,
			String dataName, String author, String messageType) {
		DBCursor cursor = boardDataCollection.find(generateQuery(key, value,
				operand, dataName, author, messageType));
		while (cursor.hasNext()) {
			searchedDB.add(cursor.next().toString());
		}
	}

	private void makeSubData(String string) {

		BasicDBObject sendD = new BasicDBObject();
		JSONObject object = (JSONObject) JSONValue.parse(string);

		String messageType = (String) object.get(MessageType);
		String requester = (String) object.get(MessageSender);
		JSONObject dataObject = (JSONObject) object.get(MessageContent);

		JSONArray arr = new JSONArray();

		sendD.put("messageType", messageType);
	}

	private BasicDBObject generateQuery(String key, String value,
			String operand, String dataName, String author, String messageType) {
		BasicDBObject dbo = new BasicDBObject();

		if (key.contains("#")) {
			if (operand.equals("$equal")) {
				dbo.put(Operation, messageType);
				dbo.put("name", dataName);
				dbo.put("author", author);
				// dbo.put("arguments", new BasicDBObject(key, value));
			} else {
				dbo.put(Operation, messageType);
				dbo.put("name", dataName);
				dbo.put("author", author);
				// dbo.put("arguments." + key, new BasicDBObject(operand,
				// value));
			}

		} else if (value.contains("#")) {
			if (operand.equals("$equal")) {
				dbo.put(Operation, messageType);
				dbo.put("name", dataName);
				dbo.put("author", author);
				// dbo.put("arguments", new BasicDBObject(key, value));
			} else {
				dbo.put(Operation, messageType);
				dbo.put("name", dataName);
				dbo.put("author", author);
				// dbo.put("arguments." + key, new BasicDBObject(operand,
				// value));
			}
		} else {
			if (operand.equals("$equal")) {
				dbo.put(Operation, messageType);
				dbo.put("name", dataName);
				dbo.put("author", author);
				dbo.put("arguments", new BasicDBObject(key, value));

			} else {
				dbo.put(Operation, messageType);
				dbo.put("name", dataName);
				dbo.put("author", author);
				dbo.put("arguments." + key, new BasicDBObject(operand, value));
			}
		}
		return dbo;
	}

	private DBObject subConvert(SubscriptionRule subscriptionRule, Fact data,
			NotificationRule notificationRule) {
		BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
		JSONObject object = BoardDataFactory.getInstance().parseSubDataToJSON(
				subscriptionRule, data, notificationRule);
		@SuppressWarnings("unchecked")
		Set<String> keys = object.keySet();
		for (String key : keys) {
			builder.append(key, object.get(key));
		}
		DBObject dbo = builder.get();
		return dbo;
	}

	public void subscribeDo() {

		int i = subList.size() - 1;

		requester = subList.get(i).getRequester();
		author = subList.get(i).getAuthor();
		dataName = subList.get(i).getDataName();
		operand = subList.get(i).getOperand();
		operation = subList.get(i).getOperation();
		arguments = subList.get(i).getArguments();
		String sendString = null;
		Set<String> keys = arguments.keySet();
		for (String key : keys) {
			subQuery(key, arguments.get(key), operand, dataName, author,
					operation);
			if (searchedDB.size() != 0) {
				for (int j = 0; j < searchedDB.size(); j++) {
					String decision = null;

					if (key.contains("#")) {
						decision = "key";
					}
					if (arguments.get(key).contains("#")) {
						decision = "value";
					}

					if (decision == null) {
						sendMessage(searchedDB.get(j));
					}

					if (decision != null) {
						sendString = ruler.eventExec(searchedDB.get(j)
								.toString(), decision);
						sendMessage(sendString);
					}
				}
				JSONParsingBean jBean = jParser.parse(sendString);
				view.printLogMessage("\n" + "Cloudboard ===> " + requester
						+ " /* Subscribe Do");
				view.printLogMessage("Data      : " + dataName);
				view.printLogMessage("Type        : " + operation);
				view.printLogMessage("Key         : " + jBean.getKey());
				view.printLogMessage("Value       : " + jBean.getValue());
			}
		}
		searchedDB.clear();
	}

	private void sendMessage(String string) {

		Sender sender;

		try {
			sender = new Sender();
			sender.createQueue(requester);
			sender.sendMessage(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeSubscribe(SubscriptionRule subscriptionRule, Fact data) {
		DBObject dbo = unsubConvert(subscriptionRule, data);
		subscribeCollection.remove(dbo);

		for (int i = 0; i < subList.size(); i++) {
			if (author.equals(subList.get(i).getAuthor())) {
				if (dataName.equals(subList.get(i).getDataName())) {
					for (String s : arguments.keySet()) {
						if (subList.get(i).getArguments().containsKey(s)) {
							if (subList.get(i).getArguments().get(s)
									.equals(arguments.get(s))) {
								subList.remove(i);
							}
						}
					}
				}
			}
		}
	}

	private DBObject unsubConvert(SubscriptionRule subscriptionRule, Fact data) {
		BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
		JSONObject object = BoardDataFactory.getInstance()
				.parseUnSubDataToJSON(subscriptionRule, data);

		@SuppressWarnings("unchecked")
		Set<String> keys = object.keySet();
		for (String key : keys) {
			builder.append(key, object.get(key));
		}
		DBObject dbo = builder.get();
		return dbo;
	}

	public void postBoardData(Fact data) {

		for (int i = 0; i <= subList.size() - 1; i++) {

			dataName = subList.get(i).getDataName();
			author = subList.get(i).getAuthor();
			requester = subList.get(i).getRequester();
			arguments = subList.get(i).getArguments();
			operand = subList.get(i).getOperand();
			operation = "post";
			String key1 = null;
			String value1 = null;

			if (data.getAuthor().equals(author)) {
				if (operation.equals(subList.get(i).getOperation())) {
					Set<String> keys = arguments.keySet();
					for (String key : keys) {
						if (data.getArguments().containsKey(key))
							if (arguments.get(key).equals(
									data.getArguments().get(key))) {
								data.setOperation(operation);
								key1 = key;
								value1 = data.getArguments().get(key);
								sendMessage(converteDBOFromObject(data).toString());
							}
					}
					view.printLogMessage("\n " + author + " ===> " + requester);
					view.printLogMessage("Name      : " + dataName);
					view.printLogMessage("Type        : " + operation);
					view.printLogMessage("Key         : " + key1);
					view.printLogMessage("Value       : " + value1);
				}
			}
		}
	}

	public void doSub(int i) {

		dataName = subList.get(i).getDataName();
		requester = subList.get(i).getRequester();
		arguments = subList.get(i).getArguments();
		operation = subList.get(i).getOperation();
		operand = subList.get(i).getOperand();
		author = subList.get(i).getAuthor();
		Set<String> keys = arguments.keySet();

		for (String key : keys) {
			subQuery(key, arguments.get(key), operand, dataName, author,
					operation);

			for (int j = 0; j < searchedDB.size(); j++) {
				view.printLogMessage("\n" + requester
						+ " Subscribe /* Subscribe this");
				view.printLogMessage("Name      : " + dataName);
				view.printLogMessage("Type        : " + operation);
				view.printLogMessage("Key         : " + key);
				view.printLogMessage("value       : " + value);
				sendMessage(searchedDB.get(j));
			}
		}
		searchedDB.clear();
	}

	public void addCon(ArrayList<String> connectionList) {
		BasicDBObjectBuilder builder = new BasicDBObjectBuilder();

		for (int i = 0; i < connectionList.size(); i++) {
			String temp = Integer.toString(i + 1);
			builder.append(temp, connectionList.get(i));
		}

		DBObject dbo = builder.get();

		connectionCollection.drop();
		connectionCollection.insert(dbo);
	}
}
