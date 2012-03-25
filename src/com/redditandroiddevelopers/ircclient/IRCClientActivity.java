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
	static String[] serverList = new String[]{
		"DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet","DalNet","FreeNode","EfNet"
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		setContentView(R.layout.main);
		setListAdapter(new ArrayAdapter<String>(this,R.layout.single_chat_list_item,serverList));
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