package kr.ac.uos.ai.cloudBoard.model.bean;


public class Subscription {
	private String					subscripter;
	private CloudBoardMessage		notification;
	
	public Subscription(String subscripter, CloudBoardMessage notification) {
		this.subscripter = subscripter;
		this.notification = notification;
	}

	public String getSubscripter() {
		return subscripter;
	}

	public void setSubscripter(String subscripter) {
		this.subscripter = subscripter;
	}

	public CloudBoardMessage getNotification() {
		return notification;
	}

	public void setNotification(CloudBoardMessage notification) {
		this.notification = notification;
	}
}
