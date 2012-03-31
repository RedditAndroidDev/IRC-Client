package com.redditandroiddevelopers.ircclient;
import org.jibble.pircbot.*;

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

	@Override
	protected void onConnect() {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage("Connected to " + this.getServer());
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onDisconnect() {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage("Disconnected from " + this.getServer());
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onJoin(String channel, String sender, String login,
			String hostname) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(sender + " joined " + channel);
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onPart(String channel, String sender, String login,
			String hostname) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(sender + " left " + channel);
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(oldNick + " is now known as " + newNick);
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		Message msg;
		msg = mHandler.obtainMessage();
		NotificationMessage notificationMessage = new NotificationMessage(sourceNick + " Quit (" + reason + ")");
		msg.obj = notificationMessage;
		mHandler.sendMessage(msg);
	}
}
