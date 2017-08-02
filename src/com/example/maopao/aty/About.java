package com.example.maopao.aty;

import com.example.maopao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class About extends Activity implements OnClickListener {
	private LinearLayout update;
	private LinearLayout introduction;
	private LinearLayout about;
	private Intent intent;
	private ImageView help_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_main);
		update = (LinearLayout) findViewById(R.id.update_bar);
		introduction = (LinearLayout) findViewById(R.id.introduction_bar);
		about = (LinearLayout) findViewById(R.id.about_bar);
		help_back = (ImageView) findViewById(R.id.help_back);
		update.setOnClickListener(this);
		about.setOnClickListener(this);
		introduction.setOnClickListener(this);
		help_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == update) {
			Toast.makeText(About.this, "已是最新版本", Toast.LENGTH_SHORT).show();
		} else if (arg0 == about) {
			intent = new Intent(About.this, AboutActivity.class);
			startActivity(intent);

		} else if (arg0 == introduction) {
			// 进入帮助页面
		} else if (arg0 == help_back) {
			// 返回主界面
		}

	}
}
