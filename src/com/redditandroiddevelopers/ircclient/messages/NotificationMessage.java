package com.redditandroiddevelopers.ircclient.messages;

public class NotificationMessage {
	public String notificationMsg;
	public String type;
	public NotificationMessage(String notificationMsg, String type) {
		this.notificationMsg = notificationMsg;
		this.type = type;
	}
	
}
