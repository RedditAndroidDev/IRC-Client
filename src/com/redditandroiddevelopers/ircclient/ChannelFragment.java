/*
 * RAD IRC Client: an IRC client for Android
 * Copyright (C) 2012 Reddit Android Developers and contributors

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.redditandroiddevelopers.ircclient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import com.redditandroiddevelopers.ircclient.messages.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChannelFragment extends Fragment {

	private TextView mChatContents;
    private EditText mChatInput;
    private IRCClient ircClient;
    private ScrollView scroller;
    private String channel;
    private String nick;
    private Handler handler;
    
    private Thread thread;
    
    public ChannelFragment() { }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.chat_fragment, container, false);
        mChatContents = (TextView) base.findViewById(R.id.chat);
        mChatInput = (EditText) base.findViewById(R.id.edit);
        scroller = (ScrollView) base.findViewById(R.id.scroller);
        
		configureMessageHandler();
		
    	nick = "TestingIRCApp";
        channel = "##RAD-IRC";
        
    	if(ircClient==null) {
			ircClient = new IRCClient(nick,handler);
		}
		
		if(!ircClient.isConnected()) {
			connect();
		}
		
		return base;
    }
    
    private void connect() {
    	mChatInput.setOnKeyListener(new OnKeyListener() {
    		@Override
	    	public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					String inputText = mChatInput.getText().toString();
					if (inputText.equals("/disconnect") || inputText == "/disconnect") {
						ircClient.quitServer("I work when i want. You're not the boss of me.");
					} else if (inputText.matches("^/nick .*")) {
						nick=ircClient.getNick();
						String [] args = inputText.split(" ");
						String newNick = args[1];
						ircClient.changeNick(newNick);
						boolean changeNickSuccessful = !(nick.equals(newNick)); // TODO: Not functioning properly
						if (!changeNickSuccessful) {
							showError(newNick +" already in use.");
						}
						nick=ircClient.getNick();
					} else {
						if (inputText == "" || (inputText.equals("")) || inputText == null) {
							showError("Invalid input");
						} else {
							sendMessage(inputText);
						}
					}
					mChatInput.setText("");
					return true;
				}
				return false;
			}
		});
		
		showNotification("Connecting to server...", "connect");
		
		if(thread == null) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
			        try {
						connect("irc.freenode.net");
					} catch (NickAlreadyInUseException e) {
						showError("Nick is already in use");
					} catch (IOException e) {
						showError("Unknown IOException");
					} catch (IrcException e) {
						showError("IRC Exception");
					} catch (Exception e) {
						showError(e.toString());
					}
					ircClient.joinChannel(channel);
				}
			});
			thread.start();
		}
		scrollToBottom();
		
    }

	private void configureMessageHandler() {
		handler = new Handler() {
			 public void handleMessage(Message msg) {
				 if (msg.obj instanceof ChatMessage) {
					ChatMessage chatMessage = (ChatMessage)msg.obj;
					getNewMessage(chatMessage.channel,chatMessage.sender,chatMessage.login,chatMessage.hostname,chatMessage.message);
				 }
				 else if (msg.obj instanceof NotificationMessage) {
					 NotificationMessage notificationMessage = (NotificationMessage)msg.obj;
					 showNotification(notificationMessage.notificationMsg, notificationMessage.type);
				 }
			 }
		};
	}

	private void connect(String server) throws NickAlreadyInUseException, IOException, IrcException {
        ircClient.setVerbose(true);
		ircClient.connect(server);
	}
 
    private void scrollToBottom() {
    	scroller.smoothScrollTo(0, mChatContents.getBottom());
    }
    private void sendMessage(CharSequence msg) {
    	Calendar currentDate = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    	ircClient.sendMessage(channel, msg.toString());
		mChatContents.append(Html.fromHtml("<br>("+ formatter.format(currentDate.getTime()) +") <font color=red><b>"+ircClient.getNick()+"</b></font>: " + msg));
		scrollToBottom();
    }
    private void getNewMessage(String channel, String sender, String login,
			String hostname, String message) {
    	Calendar currentDate = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    	mChatContents.append(Html.fromHtml("<br>(" + formatter.format(currentDate.getTime()) + ") <font color=red>" + sender + "</font>: " + message));
		scrollToBottom();
    }

	private void showError(String errorMsg) {
		mChatContents.append(Html.fromHtml("<br><i><font color=red>ERROR: " + errorMsg +"</font></i>"));
		scrollToBottom();
	}
	private void showNotification(String notificationMsg, String type) {
		if(type.equalsIgnoreCase("join"))
			mChatContents.append(Html.fromHtml("<br><i><font color=green>* " + notificationMsg +"</font></i>"));
		else if(type.equalsIgnoreCase("connect"))
			mChatContents.append(Html.fromHtml("<br><i><font color=green>* " + notificationMsg +"</font></i>"));
		else if(type.equalsIgnoreCase("topic"))
			mChatContents.append(Html.fromHtml("<br><i><font color=green>* " + notificationMsg +"</font></i>"));
		else if(type.equalsIgnoreCase("disconnect"))
			mChatContents.append(Html.fromHtml("<br><i><font color=red>* " + notificationMsg +"</font></i>"));
		else if(type.equalsIgnoreCase("quit"))
			mChatContents.append(Html.fromHtml("<br><i><font color=#700000>* " + notificationMsg +"</font></i>"));
		else if(type.equalsIgnoreCase("parted"))
			mChatContents.append(Html.fromHtml("<br><i><font color=#700000>* " + notificationMsg +"</font></i>"));
		else if(type.equalsIgnoreCase("nickchange"))
			mChatContents.append(Html.fromHtml("<br><i><font color=#0066CC>* " + notificationMsg +"</font></i>"));
		else
			mChatContents.append(Html.fromHtml("<br><i><font color=green>* " + notificationMsg +"</font></i>"));
		scrollToBottom();
	}
}
