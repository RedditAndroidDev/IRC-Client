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

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class IRCClientActivity extends ListActivity implements OnItemClickListener {
    /** Called when the activity is first created. */
	TextView chatText;
	MainListAdapter mainListAdapter;
	static String[] serverList = new String[]{
		"DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet"
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		setContentView(R.layout.main);
		// create custom list adapter: MainListAdapter just to handle rows
		mainListAdapter = new MainListAdapter(this, serverList);
		setListAdapter(mainListAdapter);
		
		/*
		setListAdapter(new ArrayAdapter<String>(this,R.layout.single_chat_list_item,serverList));
		ListView serverListView = getListView();
		setListAdapter(mainListAdapter);
		*/
		
		ListView serverListView = getListView();
		serverListView.setTextFilterEnabled(true);
		serverListView.setOnItemClickListener(this);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		startActivity(new Intent().setClass(IRCClientActivity.this, ChannelActivity.class));
		//Toast.makeText(getApplicationContext(), ((TextView) arg1).getText(), Toast.LENGTH_LONG).show(); 
	}
}