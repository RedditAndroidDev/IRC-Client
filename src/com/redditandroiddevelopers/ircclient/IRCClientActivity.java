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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

public class IRCClientActivity extends SherlockListActivity implements OnItemClickListener {
    /** Called when the activity is first created. */

	// just some variables so we can easily tell which itemID belongs to which subMenuItem 
	private static final int BASICGROUP = 0;
	private static final int mnuITEM_ADD_SERVER = Menu.FIRST;
	private static final int mnuITEM_SETTINGS = mnuITEM_ADD_SERVER + 1;
	private static final int mnuITEM_ABOUT = mnuITEM_SETTINGS + 1;
	
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
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
	    // prepare a menu on the ActionBar
		 
		
		
		//add the submenu items, and set itemID
		SubMenu RADsubmenu = menu.addSubMenu("").setIcon(R.drawable.ic_menu_moreoverflow_normal_holo_light);
		
		 RADsubmenu.add(BASICGROUP, mnuITEM_ADD_SERVER, 1,"Add Server").setIcon(R.drawable.radlogo);
		 RADsubmenu.add(BASICGROUP, mnuITEM_SETTINGS, 2,"Settings").setIcon(R.drawable.logo2);
		 RADsubmenu.add(BASICGROUP, mnuITEM_ABOUT, 3,"About").setIcon(R.drawable.rad_logo);
		
	        MenuItem RADsubmenuItem = RADsubmenu.getItem();
	        RADsubmenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);


	        return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle menuItem events
	    switch (item.getItemId()) {
	        case 1:
	        	// Add Server was clicked
	           Toast.makeText(this, "Add Server", Toast.LENGTH_SHORT).show();
	        	return true;
	        case 2:
	        	// Settings was clicked
				Intent settingsIntent = new Intent(IRCClientActivity.this, Preferences.class);
				startActivity(settingsIntent);
		           Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
		        	return true;
	        case 3:
	        	// About was clicked
		           Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
		        	return true;
	      
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	   
}
