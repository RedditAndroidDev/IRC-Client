package com.redditandroiddevelopers.ircclient;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] names;
	// first and second color for rows
			// right now it is: grey'ish --- white
    private final int[] bgColors = new int[] { Color.parseColor("#F0F0F0"), Color.parseColor("#FFFFFF") };


	static class ViewHolder {
		public TextView text;
	}

	public MainListAdapter(Activity context, String[] names) {
		super(context, R.layout.main_row_layout, names);
		this.context = context;
		this.names = names;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/* this will handle specific rows */
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.main_row_layout, null);  // created custom layout in order to manipulate 
																		 // 	certain parts of row if needed
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.tv_ServerName);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		String s = names[position];
		holder.text.setText(s);
		
		
		int colorPosition = position % bgColors.length; //get a number according to row position, determine if number is odd/even
		rowView.setBackgroundColor(bgColors[colorPosition]); 
		if (s.startsWith("DalNet")){
			// when a TextView == DalNet, you will get in this statement
		} else {
			//TextView != DalNet

		}

		return rowView;
	}
}