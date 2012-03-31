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
import android.view.inputmethod.EditorInfo;
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
    
    public ChannelFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.chat_fragment, container, false);
        mChatContents = (TextView) base.findViewById(R.id.chat);
        mChatInput = (EditText) base.findViewById(R.id.edit);
        scroller = (ScrollView) base.findViewById(R.id.scroller);
        nick = "TestingIRCApp";
        channel = "##RAD-IRC";
		
		configureMessageHandler();
		
		ircClient = new IRCClient(nick,handler);
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
						ircClient.changeNick(args[1]);
						if (nick.equals(ircClient.getNick())) {
							showError("Nick already in use.");
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
		
        try {
			connect("irc.freenode.net");
		} catch (NickAlreadyInUseException e) {
			showError("Nick is already in use");
		} catch (IOException e) {
			showError("Unknown IOException");
		} catch (IrcException e) {
			showError("e.printStackTrace()");
		}
        ircClient.joinChannel(channel);
        scrollToBottom();
        return base;
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
					 showNotification(notificationMessage.notificationMsg);
				 }
			 }
		};
	}

	private void connect(String server) throws NickAlreadyInUseException, IOException, IrcException {
        ircClient.setVerbose(true);
		ircClient.connect(server);
	}
 
    private void scrollToBottom() {
    	scroller.smoothScrollTo(1, mChatContents.getBottom());
    }
    private void sendMessage(CharSequence msg) {
    	Calendar currentDate = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    	ircClient.sendMessage(channel, msg.toString());
		mChatContents.append(Html.fromHtml("<br>("+ formatter.format(currentDate.getTime()) +") <font color=red><b>"+ircClient.getNick()+"</b></font>: " + msg));
		scrollToBottom();
    }
    private void getNewMessage(String channel, String sender, String login,
			String hostname, String message) {
    	Calendar currentDate = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    	mChatContents.append(Html.fromHtml("<br>(" + formatter.format(currentDate.getTime()) + ") <font color=red>" + sender + "</font>: " + message));
		scrollToBottom();
    }

	private void showError(String errorMsg) {
		mChatContents.append(Html.fromHtml("<br><i><font color=red>ERROR: " + errorMsg +"</font></i>"));
		scrollToBottom();
	}
	private void showNotification(String notificationMsg) {
		mChatContents.append(Html.fromHtml("<br><i><font color=green>* " + notificationMsg +"</font></i>"));
		scrollToBottom();
	}
}
