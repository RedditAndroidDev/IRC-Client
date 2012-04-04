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

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelActivity extends SherlockFragmentActivity {
	private static ChannelFragment mFragment;
	
	// variables so we can easily tell which itemID belongs to which subMenuItem 
			// just some actions I think are most important
		private static final int BASICGROUP = 0;
		private static final int mnuITEM_DISCONNECT = Menu.FIRST;
		private static final int mnuITEM_SERVER_DETAILS = mnuITEM_DISCONNECT + 1;
		private static final int mnuITEM_USERS = mnuITEM_SERVER_DETAILS + 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        String placeholder_names[] = new String[] { "##RAD-IRC", "##reddit-android-developers", "##hello-world", "##aaaa", "##bbbbb" };
        for (int i = 0; i < 5; i++) {
            Tab tab = actionBar.newTab()
                    .setText(placeholder_names[i])
                    .setTabListener(new TabListener<ChannelFragment>());
            actionBar.addTab(tab);
        }
    }  
    
    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = new ChannelFragment();
                ft.add(android.R.id.content, mFragment, "channel");
            } else {
                ft.attach(mFragment);
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
        
    }
    
    @Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
	    // prepare a menu on the ActionBar
		 
		
		
		//add the submenu items, and set itemID
		SubMenu RADsubmenu = menu.addSubMenu("").setIcon(R.drawable.ic_menu_moreoverflow_normal_holo_light);
		
		 RADsubmenu.add(BASICGROUP, mnuITEM_DISCONNECT, 1,"Disconnet").setIcon(R.drawable.radlogo);
		 RADsubmenu.add(BASICGROUP, mnuITEM_SERVER_DETAILS, 2,"Server Details").setIcon(R.drawable.logo2);
		 RADsubmenu.add(BASICGROUP, mnuITEM_USERS, 3,"Users").setIcon(R.drawable.rad_logo);
		
		
	        MenuItem RADsubmenuItem = RADsubmenu.getItem();
	        RADsubmenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);


	        return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle menuItem events
	    switch (item.getItemId()) {
	        case 1:
	        	// Disconnect was clicked
	           Toast.makeText(this, "Disconnect", Toast.LENGTH_SHORT).show();
	        	return true;
	        case 2:
	        	// Server Details was clicked
		           Toast.makeText(this, "Server Details", Toast.LENGTH_SHORT).show();
		        	return true;
	        case 3:
	        	// Users was clicked
		           Toast.makeText(this, "Users", Toast.LENGTH_SHORT).show();
		        	return true;
	      
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
	
}
