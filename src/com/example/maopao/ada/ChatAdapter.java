package com.example.maopao.ada;

import java.util.List;

import com.example.maopao.R;
import com.example.maopao.cla.MyMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<MyMessage> {

	int resourceId;

	public ChatAdapter(Context context, int resource, List<MyMessage> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		MyMessage message = getItem(position);
		View view;
		if (convertView == null)
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		else
			view = convertView;

		if (message.getTag() == 1) {
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.chat_left);
			LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.chat_right);
			layout.setVisibility(View.GONE);
			layout2.setVisibility(View.VISIBLE);
			TextView mes = (TextView) view.findViewById(R.id.chat_row2);
			mes.setText(message.getMessage());
		} else {
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.chat_left);
			LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.chat_right);
			layout.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.GONE);
			TextView mes = (TextView) view.findViewById(R.id.chat_row1);
			mes.setText(message.getMessage());
		}
		return view;
	}

}
