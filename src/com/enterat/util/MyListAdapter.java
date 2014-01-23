package com.enterat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.enterat.R;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<HashMap<String, Object>> {

	Context context;
	ArrayList<HashMap<String, Object>> data;

	public MyListAdapter(Context context, int resource, ArrayList<HashMap<String, Object>> data) {
		
		super(context, resource, data);

		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		
		if (view == null) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			
			if (position % 2 == 0) {
				view = inflater.inflate(R.layout.list_view_item, null);
			}
			else {
				view = inflater.inflate(R.layout.list_view_item, null);
			}
		}
		
		Map<String, Object> element = data.get(position); 
		
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleListViewItem);
		tvTitle.setText((String) element.get("Title"));
		
		TextView tvDate = (TextView) view.findViewById(R.id.tvDateListViewItem);
		tvDate.setText((String) element.get("Date"));
		
		TextView tvDescription = (TextView) view.findViewById(R.id.tvDescriptionListViewItem);
		tvDescription.setText((String) element.get("Description"));
		
		ImageView ivIcon = (ImageView) view.findViewById(R.id.ivListViewItem);
		ivIcon.setImageResource((Integer) element.get("Icon"));
		
		return view;		
	}


}