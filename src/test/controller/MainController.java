package test.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import test.view.ViewManager;

public class MainController {
	private ViewManager view;
	private Receiver receiver;
	private Dispatcher dispatcher;
	private ArrayList<String> connectionList;

	public MainController() throws IOException, InterruptedException {
		connectionList = new ArrayList<String>();
		view = new ViewManager();
		receiver = new Receiver(this);
		dispatcher = new Dispatcher(view);
		view.printLogMessage("Ready to receive.");
		receiver.start();
	}

	public void receiveMessage() throws IOException{
		
		String 	   message = receiver.getMessage();		// message 가 client에서 JSON 형식으로 받은 메세지이다.
		JSONObject object  = (JSONObject) JSONValue.parse(message);
		
			if (object.containsKey("connect")) {
			String connector = (String) object.get("connect");
				if(connectionList.contains(connector)){
				} else {
					connectionList.add(connector);
					dispatcher.addCon(connectionList);
				}
			}
			
				if(object.containsKey("disconnect")) {
					String disconnector = (String) object.get("disconnect");
					connectionList.remove(disconnector);
					dispatcher.addCon(connectionList);
				}
			
			if(((object.containsKey("disconnect") ==false) && (object.containsKey("connect") == false))){
				dispatcher.go(message, connectionList);
			}
	}
}
