package com.example.maopao.ada;

import java.util.List;

import com.example.maopao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<String> {

	private int resourceId;

	public MenuAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		String menu = getItem(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else {
			view = convertView;
		}
		ImageView image = (ImageView) view.findViewById(R.id.menu_image);
		TextView option = (TextView) view.findViewById(R.id.menu_name);
		option.setText(menu);
		image.setImageResource(R.drawable.poke);
		return view;
	}

}
