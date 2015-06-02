package kr.ac.uos.ai.cloudBoard.factory;

import kr.ac.uos.ai.cloudBoard.model.bean.CloudBoardMessage;
import kr.ac.uos.ai.cloudBoard.model.bean.Fact;
import kr.ac.uos.ai.cloudBoard.model.bean.NotificationRule;
import kr.ac.uos.ai.cloudBoard.model.bean.QueryRule;
import kr.ac.uos.ai.cloudBoard.model.bean.Rule;
import kr.ac.uos.ai.cloudBoard.model.bean.SubscriptionRule;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;

import test.model.Operation;
import static kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration.*;

public class BoardDataFactory {
	private static BoardDataFactory instance;

	private BoardDataFactory() {
	}

	public static synchronized BoardDataFactory getInstance() {

		if (instance == null) {
			instance = new BoardDataFactory();
		}
		return instance;
	}

	public CloudBoardMessage parseMessageFromString(String requestString) {

		CloudBoardMessage message = new CloudBoardMessage();
		JSONObject object = (JSONObject) JSONValue.parse(requestString);

		String messageType = (String) object.get(MessageType);
		String requester = (String) object.get(MessageSender);
		JSONObject dataObject = (JSONObject) object.get(MessageContent);

		Operation operationModel = parseBoardDataFromJSON(object, dataObject, messageType);

		operationModel.setMessageType(messageType);
		message.setWriter(requester);
		message.setOperation(operationModel);

		if (dataObject.containsKey("notificationRule")) {
			JSONArray argumentsArr = (JSONArray) dataObject.get("notificationRule");
			for (Object o : argumentsArr) {
				JSONObject valueEntry = (JSONObject) o;
				String name = (String) valueEntry.keySet().iterator().next();
				String value = (String) valueEntry.get(name);
				message.getNotificationRule().getArguments().put(name, value);
			}
		}

		return message;
	}

	public Operation parseBoardDataFromJSON(JSONObject object, JSONObject dataObject,
			String messageType) {

		String dataName = (String) dataObject.get(DataName);
		String author = (String) dataObject.get(DataAuthor);
		String operand = (String) dataObject.get("operand");
		String operationType = (String) dataObject.get("operation");

		long pTime = Long.valueOf(dataObject.get(ProductionTime).toString());
		long eTime = Long.valueOf(dataObject.get(ExpriationTime).toString());

		Operation operation = new Operation();
		operation.setFacts(author, dataName, operand, operationType,
				messageType, pTime, eTime);

		if (object.containsValue("gets")) {
		} else {
			JSONArray argumentsArray = (JSONArray) dataObject.get(ArgumentList);
			for (Object o : argumentsArray) {
				JSONObject valueEntry = (JSONObject) o;
				String name = (String) valueEntry.keySet().iterator().next();
				String value = (String) valueEntry.get(name);
				operation.getFacts().appendArgument(name, value);
			}
		}
		return operation;
	}

	@SuppressWarnings("unchecked")
	public String parseMessageToJSON(CloudBoardMessage message) {
		JSONObject object = new JSONObject();
		object.put(MessageSender, message.getWriter());
		object.put(MessageType, message.getOperation().getOperation()
				.toString());

		JSONObject dataObject = parseBoardDataToJSON(message.getOperation()
				.getFacts());

		object.put(MessageContent, dataObject);

		return object.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public JSONObject parseSubDataToJSON(SubscriptionRule subscriptionRule,
			Fact data, NotificationRule notificationRule) {

		JSONObject dataObject = new JSONObject();
		dataObject.put("sender", subscriptionRule.getSender());
		dataObject.put("author", data.getAuthor());
		dataObject.put("operand", data.getOperand());
		dataObject.put(DataName, data.getName());

		JSONArray argumentArray = new JSONArray();

		for (String name : data.getArguments().keySet()) {
			JSONObject argument = new JSONObject();
			argument.put(name, data.getValue(name));
			argumentArray.add(argument);
		}
		dataObject.put(ArgumentList, argumentArray);
		return dataObject;
	}

	@SuppressWarnings("unchecked")
	public JSONObject parseBoardDataToJSON(Fact data) {
		JSONObject dataObject = new JSONObject();

		
		dataObject.put("messageType", data.getMessageType());
		dataObject.put(Operation, data.getMessageType());
		dataObject.put(DataName, data.getName());
		dataObject.put(DataAuthor, data.getAuthor());
//		dataObject.put(ProductionTime, data.getProductionTime());
//		dataObject.put(ExpriationTime, data.getExpirationTime());

		JSONArray argumentArray = new JSONArray();

		for (String name : data.getArguments().keySet()) {
			JSONObject argument = new JSONObject();
			argument.put(name, data.getValue(name));
			argumentArray.add(argument);
		}
		dataObject.put(ArgumentList, argumentArray);
		return dataObject;
	}

	@SuppressWarnings("unchecked")
	public JSONObject parseInsertBoardDataToJSON(Fact data) {
		JSONObject dataObject = new JSONObject();
		
		dataObject.put("sender", data.getAuthor());
		dataObject.put("messageType", data.getMessageType());
		
		dataObject.put(Operation, data.getMessageType());

		dataObject.put(DataName, data.getName());

		dataObject.put(DataAuthor, data.getAuthor());

		JSONArray argumentArray = new JSONArray();
		for (String name : data.getArguments().keySet()) {
			JSONObject argument = new JSONObject();
			argument.put(name, data.getValue(name));
			argumentArray.add(argument);
		}
		
		dataObject.put(ArgumentList, argumentArray);
		return dataObject;
	}

	@SuppressWarnings("unchecked")
	public JSONObject parseQueryRuleToJSON(QueryRule data) {
		JSONObject dataObject = new JSONObject();
		if (data.getName() != null)
			dataObject.put(DataName, data.getName());
		if (data.getAuthor() != null)
			dataObject.put(DataAuthor, data.getAuthor());
		
		JSONArray argumentArray = new JSONArray();

		for (Rule rule : data.getRules()) {
			JSONObject argument = new JSONObject();
			argument.put(ArgumentName, rule.getArgName());
			argument.put(ArgumentOperator, rule.getOperation());
			argument.put(ArgumentValue, rule.getOperand());
			argumentArray.add(argument);
		}
		dataObject.put(ArgumentRule, argumentArray);
		return dataObject;
	}

	@SuppressWarnings("unchecked")
	public JSONObject parseUnSubDataToJSON(SubscriptionRule subscriptionRule,
			Fact data) {

		JSONObject dataObject = new JSONObject();
		dataObject.put(MessageSender, subscriptionRule.getSender());
		dataObject.put(DataAuthor, data.getAuthor());
		dataObject.put(DataName, data.getName());

		JSONArray argumentArray = new JSONArray();

		for (String name : data.getArguments().keySet()) {
			JSONObject argument = new JSONObject();
			argument.put(name, data.getValue(name));
			argumentArray.add(argument);
		}
		dataObject.put(ArgumentList, argumentArray);
		return dataObject;
	}

}
