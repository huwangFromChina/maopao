package com.example.maopao.aty;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.maopao.R;
import com.example.maopao.cla.BundleCommitte;
import com.example.maopao.cla.Friend;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.net.NetConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SeekFriend extends Activity {

	private EditText input;
	private ImageView seek;
	private ArrayList<Friend> seek_result=new ArrayList<Friend>();
	private BundleCommitte application;
	private LinearLayout seek_layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seek_friend);
		application = (BundleCommitte) this.getApplicationContext();
		init();
	}

	private void seek() {
		// TODO Auto-generated method stub
		Map<String, String> message = new HashMap<String, String>();
		message.put("condition", input.getText().toString());
		final ProgressDialog pd = ProgressDialog.show(SeekFriend.this, "提交数据中", "wait...");
		pd.show();
		new NetConnection(Url.seekFriend, message, new NetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				pd.dismiss();
				seek_result.clear();
				TextView description=(TextView)findViewById(R.id.seek_result_name);
				//description.setText(result);
				if(result.equals("[]"))
				{
					Toast.makeText(SeekFriend.this, "未查找到好友", Toast.LENGTH_SHORT).show();
					return;
				}
				ArrayList<Friend> seek_res=JsonAnalysis.getFriends(result);
				for(int i=0;i<seek_res.size();i++)
				{
					Friend fr=new Friend(seek_res.get(i).getId(), seek_res.get(i).getName(),null, null, 0, null, null);
					seek_result.add(fr);
				}
				seek_layout.setVisibility(View.VISIBLE); 
				init_widget(seek_result);
			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				pd.dismiss();
				Toast.makeText(SeekFriend.this, "查找失败", Toast.LENGTH_SHORT).show();
			}

		});
	}

	
	public void init_widget(final ArrayList<Friend> seek_resul)
	{
		if(seek_resul.size()>=0)
		{
		TextView name = (TextView) findViewById(R.id.seek_result_name);
		ImageView portrait = (ImageView) findViewById(R.id.seek_result_portrait);
		TextView description = (TextView) findViewById(R.id.seek_result_description);
		name.setText(seek_result.get(0).getName());
		if(seek_resul.get(0).getPortrait()!=null)
		{
			Bitmap bitmap=getHttpBitmap(seek_resul.get(0).getPortrait());
			portrait.setImageBitmap(bitmap);
		}
		description.setText(seek_resul.get(0).getName());
		LinearLayout result = (LinearLayout) findViewById(R.id.seek_result);
		result.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				application.set(seek_resul.get(0).getId(),seek_resul.get(0).getName(),"","",1,"","");
					Intent intent = new Intent(SeekFriend.this, FriendDetail.class);
					startActivity(intent);
			}
		});
		}
		
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

	private void init() {
		// TODO Auto-generated method stub
		input = (EditText) findViewById(R.id.input);
		seek = (ImageView) findViewById(R.id.seek);
		seek_layout=(LinearLayout)findViewById(R.id.seek_result_layout);
		seek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!input.getText().toString().equals("")) {
					seek();
				}
			}

		});
	}
}
