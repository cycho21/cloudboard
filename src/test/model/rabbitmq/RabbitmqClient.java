package test.model.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitmqClient {
	public static String 				HOST = "172.16.165.197";
	public static String 				USER_NAME = "ai";
	public static String 				PASSWORD = "dlsrhdwlsmd";
	public static int					PORT = 6938;
	
	private Connection 					connection = null;
	private Channel						channel = null;
	
	public Channel getChannel() throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitmqClient.HOST);
		factory.setUsername(RabbitmqClient.USER_NAME);
		factory.setPassword(RabbitmqClient.PASSWORD);
		factory.setPort(RabbitmqClient.PORT);
		
		this.connection = factory.newConnection();
		this.channel = connection.createChannel();
		
		return this.channel;		
	}

	public void close() throws IOException{
		this.channel.close();
		this.connection.close();
	}
	
}
