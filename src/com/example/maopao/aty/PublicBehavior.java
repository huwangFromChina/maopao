package com.example.maopao.aty;

import java.util.HashMap;
import java.util.Map;

import com.example.maopao.R;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.NetConnection;
import com.example.maopao.net.SubmitMessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PublicBehavior extends Activity {

	private ImageView send;
	private ImageView back;
	private TextView content;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.public_behavior);
		send = (ImageView) findViewById(R.id.send);
		back = (ImageView) findViewById(R.id.back);
		content = (TextView) findViewById(R.id.add_content);
		send.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (content.getText().toString().equals("") == true) {
					Toast.makeText(getBaseContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}

				Map<String, String> behavior = new HashMap<String, String>();
				behavior.put("activeMessage", content.getText().toString());
				final ProgressDialog pd = ProgressDialog.show(PublicBehavior.this, "提交数据中", "wait...");
				pd.show();
				new NetConnection(Url.publicBehavior+User.id, behavior, new NetConnection.SuccessCallback() {

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						pd.dismiss();
						if(JsonAnalysis.getInt(result)!=1)
						{
							Toast.makeText(PublicBehavior.this, "fabu失败", Toast.LENGTH_SHORT).show();
							return ;
						}
						Intent in = new Intent();
						in.putExtra("tags", "1");
						in.putExtra("content", content.getText().toString());
						setResult(RESULT_OK, in);
						Toast.makeText(getBaseContext(), "发布成功", Toast.LENGTH_SHORT).show();
						finish();
					}
				}, new NetConnection.FailCallback() {
					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(getBaseContext(), "发布失败", Toast.LENGTH_SHORT).show();
					}

				});

				/*Intent in = new Intent();
				in.putExtra("tags", "1");
				in.putExtra("content", content.getText().toString());
				setResult(RESULT_OK, in);
				finish();*/
			}
			
		});
		back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.putExtra("tags", "0");
				in.putExtra("content", content.getText().toString());
				setResult(RESULT_OK, in);
				finish();
			}
			
		});
	}



		@Override
	    public void onBackPressed() {
	        // super.onBackPressed();
			
			Intent in = new Intent();
			in.putExtra("tags", "0");
			in.putExtra("content", content.getText().toString());
			setResult(RESULT_OK, in);
			finish();
	    }
}
