package com.example.maopao.aty;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.maopao.R;
import com.example.maopao.cla.BundleCommitte;
import com.example.maopao.cla.Friend;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.GetNetConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendDetail extends Activity {
	private TextView content1;
	private TextView content2;
	private TextView content3;
	private TextView content4;
	private TextView content5;
	private TextView name;
	private ImageView portrait;
	private Button add;
	private Button remove;
	private ImageView back;
	
	private BundleCommitte application;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail);
		application = (BundleCommitte) this.getApplicationContext();
		init_widget();
	}

	private void init_widget() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.detail_back);
		add = (Button) findViewById(R.id.detail_add);
		remove = (Button) findViewById(R.id.detail_remove);
		if(application.getBoolean())
		{
			remove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new GetNetConnection(Url.deleteFriend+User.id+"/"+String.valueOf(application.getId()), new GetNetConnection.SuccessCallback() {

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							content5.setText(result);
							if(JsonAnalysis.getInt(result)!=1)
							{
								Toast.makeText(FriendDetail.this, "删除好友失败", Toast.LENGTH_SHORT).show();
								return ;
							}
							Intent in = new Intent();
							in.putExtra("tag", "1");
							in.putExtra("phone", application.getPhone());
							setResult(RESULT_OK, in);
							finish();
						}
					}, new GetNetConnection.FailCallback() {
						@Override
						public void onFail() {
							// TODO Auto-generated method stub
							Toast.makeText(FriendDetail.this, "操作失败", Toast.LENGTH_SHORT).show();
						}

					});
				}

			});
		}
		else
		{
			add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new GetNetConnection(Url.addFriend+User.id+"/"+String.valueOf(application.getId()),new GetNetConnection.SuccessCallback() {

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							if(JsonAnalysis.getInt(result)!=1)
							{
								Toast.makeText(FriendDetail.this, "shibai", Toast.LENGTH_SHORT).show();
								return ;
							}
							Toast.makeText(FriendDetail.this,"消息发送成功，等待好友确认", Toast.LENGTH_SHORT).show();
						}
					}, new GetNetConnection.FailCallback() {
						@Override
						public void onFail() {
							// TODO Auto-generated method stub
							Toast.makeText(FriendDetail.this, "添加好友失败", Toast.LENGTH_SHORT).show();
						}

					});
				}

			});
		}
		content1 = (TextView) findViewById(R.id.detail_content1);
		content2 = (TextView) findViewById(R.id.detail_content2);
		content3 = (TextView) findViewById(R.id.detail_content3);
		content4 = (TextView) findViewById(R.id.detail_content4);
		content5 = (TextView) findViewById(R.id.detail_content5);
		name = (TextView) findViewById(R.id.detail_name);
		portrait = (ImageView) findViewById(R.id.detail_portrait);

		
		content2.setText(application.geDescription());
		content5.setText(application.getPhone());
		content1.setText(application.getName());
		content3.setText(application.getCreateDate());
		content4.setText(application.getSex());
		/*if(!application.getPortrait().equals(""))
		{
		Bitmap bitmap = getHttpBitmap(application.getPortrait());
		portrait.setImageBitmap(bitmap);
		}*/
		name.setText(application.getName());
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.putExtra("tag", "0");
				in.putExtra("phone", application.getPhone());
				setResult(RESULT_OK, in);
				finish();
			}

		});

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
	
	@Override
    public void onBackPressed() {
        // super.onBackPressed();
		
		Intent in = new Intent();
		in.putExtra("tag", "0");
		in.putExtra("phone","");
		setResult(RESULT_OK, in);
		finish();
    }
}
