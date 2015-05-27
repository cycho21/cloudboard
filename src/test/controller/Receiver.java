package test.controller;

import java.io.IOException;

import test.model.rabbitmq.RabbitmqClient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Receiver extends Thread {
	public final static String 					BASIC_QUEUE_NAME = "test.server.cloudboard";
	private RabbitmqClient						client;
	private Channel								channel;
	private String								message;
	private String								queueName;
	private QueueingConsumer					consumer;
	private MainController						mc;

	public Receiver(MainController mc)  throws java.io.IOException,
			java.lang.InterruptedException {
		this.mc = mc;
		
		client = new RabbitmqClient();
		channel = client.getChannel();
		
		channel.queueDeclare(BASIC_QUEUE_NAME, false, false, false, null);
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(BASIC_QUEUE_NAME, true, consumer);

	}
	
	public void createQueue(String clientName) throws IOException{
		queueName = "server." + clientName;
		channel.queueDeclare(queueName, false, false, false, null);
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
	}
	
	@Override
	public void run() {
		try {
			this.receiveMessage();
		} catch (ShutdownSignalException | ConsumerCancelledException
				| InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage() throws ShutdownSignalException,
			ConsumerCancelledException, InterruptedException, IOException {
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			setMessage(message);
			System.out.println("Receive Message " + message + "\n");
			mc.receiveMessage();
		}
	}
	
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void receiverClose() throws IOException {
		this.client.close();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
}
