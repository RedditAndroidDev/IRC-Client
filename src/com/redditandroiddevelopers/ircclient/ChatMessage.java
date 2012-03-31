package com.redditandroiddevelopers.ircclient;

public class ChatMessage {
	public String channel;
	public String sender;
	public String login;
	public String hostname;
	public String message;
	public ChatMessage(String channel, String sender, String login,
			String hostname, String message) {
		super();
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.message = message;
	}
	
}
