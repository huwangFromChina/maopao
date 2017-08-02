package com.example.maopao.ada;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maopao.R;
import com.example.maopao.cla.Friend;

public class FriendAdapter extends ArrayAdapter<Friend> {

	private int resourceId;

	public FriendAdapter(Context context, int resource, List<Friend> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
		Log.d("home", "init_friendAdapter");
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Friend friend = getItem(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else
			view = convertView;
		TextView name = (TextView) view.findViewById(R.id.friend_name);
		TextView description = (TextView) view.findViewById(R.id.friend_description);
		ImageView portrait = (ImageView) view.findViewById(R.id.friend_portrait);
		name.setText(friend.getName());
			//Bitmap bitmap = getHttpBitmap(friend.getPortrait());
			//portrait.setImageBitmap(bitmap);
		description.setText(friend.getDescription());
		description.setText("I am maopao");
		name.setText("maopao");
		return view;
	}

	public Bitmap getHttpBitmap(String url) {
		Bitmap bitmap = null;
		try {
			URL pictureUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) pictureUrl.openConnection();
			InputStream in = con.getInputStream();
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	

}
