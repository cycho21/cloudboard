package test.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test.model.Message;
import test.model.rabbitmq.RabbitmqClient;

import com.rabbitmq.client.Channel;

public class Sender {
	private String								queueName;
	private RabbitmqClient						client;
	private Channel								channel;
	
	public Sender() throws IOException {
		client = new RabbitmqClient();
		channel = client.getChannel();
	}
	public void createQueue(String clientName) throws IOException{
		queueName = clientName;
		channel.queueDeclare(queueName, false, false, false, null);		
	}

	public void sendMessage(String message) throws IOException {
		List<Message> messages = new ArrayList<Message>();
		messages.add(new Message(queueName, message));
		System.out.println("Send Message to " + queueName + " ==> "+ message +"\n");
		for (Message m : messages) {
			channel.basicPublish(m.exchange, m.routingKey, null,m.body.getBytes());
		}
	}

	public void senderClose() throws IOException{
		this.client.close();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
