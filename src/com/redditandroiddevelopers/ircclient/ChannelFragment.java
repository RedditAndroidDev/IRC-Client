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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChannelFragment extends Fragment {

    private TextView mChatContents;
    
    public ChannelFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.chat_fragment, container, false);
        
        mChatContents = (TextView) base.findViewById(R.id.chat);
        // TODO: Placeholder
        mChatContents.setText(Html.fromHtml("(10:32:50) <font color=red>@member68</font>: that's because they don't have a local copy for redmine. I don't really see why we should keep the repository tab anyway since we have github for browsing the code" +
                "<br>(10:33:04) <font color=red>@veeti</font>: it's so that issues can be closed by commits automatically" +
                "<br>(10:33:09) <font color=red>Hogofwar</font>: make make a link in the project to the github" +
                "<br>(10:33:14) <font color=red>Hogofwar</font>: and that" +
                "<br>(10:33:53) <font color=red>@member68</font>: can redmine do that? haven't tried it" +
                "<br>(10:34:37) <font color=red>@member68</font>: how does it detect which commit belongs wo which issue?" +
                "<br>(10:34:48) <font color=red>@veeti</font>: you put something like" +
                "<br>(10:34:53) <font color=red>@veeti</font>: \"fixes #23142345\" in the commit message" +
                "<br>(10:34:57) <font color=red>@veeti</font>: and it closes the issue automatically" +
                "<br>(10:35:12) <font color=red>@member68</font>: that's useful." +
                "<br>(10:35:33) <font color=red>@member68</font>: guess that's a good reason to add the repos to redmine" +
                "<br>(10:35:58) <font color=red>Hogofwar</font>: woo" +
                "<br>(10:36:11) <font color=red>@member68</font>: wjoe would be the one to add them though. he's the only one who can do that" +
                "<br>(10:40:46) <font color=red>Hiver</font>: So, what's the difference between an idea and a suggestion?" +
                "<br>(10:41:37) <font color=red>@member68</font>: I removed Ideas as an issue tracker a few minutes ago because hogofwar pointed out here wasn't one" +
                "<br>(10:44:08) <font color=red>Hiver</font>: Ahh"));
        mChatContents.append(Html.fromHtml("<br>(10:44:08) <font color=red>Raionic</font>: Hi"));
        
        return base;
    }
 
    
    
}
