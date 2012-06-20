package com.redditandroiddevelopers.ircclient;
import org.jibble.pircbot.*;

import com.redditandroiddevelopers.ircclient.messages.*;

import android.os.Handler;
import android.os.Message;


public class IRCClient extends PircBot {
	Handler mHandler;
	public IRCClient(String nick, Handler h) {
		this.setName(nick);
		this.setAutoNickChange(true);
		this.setLogin(nick);
		this.mHandler = h;
	}

	@Override
	protected void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		Message msg;
		msg = mHandler.obtainMessage();
		ChatMessage chatMessage = new ChatMessage(channel, sender, login, hostname, message);
		msg.obj = chatMessage;
		mHandler.sendMessage(msg);
	}
	
	public void setLoginPublic(String userName)
	{
		super.setLogin(userName);
	}

	@Override
	protected void onConnect() {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage("Connected to " + this.getServer(), "connect");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onDisconnect() {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage("Disconnected from " + this.getServer(), "disconnect");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onTopic(String channel, String topic, String setBy,
			long date, boolean changed) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage("Topic is '" + topic + "' <br>Set by  " + setBy + " on " + date, "topic");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onJoin(String channel, String sender, String login,
			String hostname) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(sender + " joined " + channel, "join");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onPart(String channel, String sender, String login,
			String hostname) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(sender + " left " + channel, "parted");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(oldNick + " is now known as " + newNick, "nickchange");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(sourceNick + " Quit (" + reason + ")", "quit");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}
}
