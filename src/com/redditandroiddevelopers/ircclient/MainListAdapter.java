package com.redditandroiddevelopers.ircclient;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
			
			LinearLayout status_line = (LinearLayout) rowView.findViewById(R.id.status_line);

			rowView.setTag(viewHolder);
			
			ViewHolder holder = (ViewHolder) rowView.getTag();
			String serverNames = names[position];
			holder.text.setText(serverNames);
			
			
			int colorPosition = position % bgColors.length; //get a number according to row position, determine if number is odd/even
			//rowView.setBackgroundColor(bgColors[colorPosition]); //make a multi- colored list
			if (serverNames.equals("DalNet")){
						
							// Connected Status color
				status_line.setBackgroundColor(Color.parseColor("#32CD32"));
				
			}else if(serverNames.equals("EfNet")){
				
							// Disconnected Status color
				status_line.setBackgroundColor(Color.parseColor("#CD143C"));

			}else {
							// Default Color
				status_line.setBackgroundColor(Color.parseColor("#DCDCDC"));
			}
		}

		

		return rowView;
	}
}