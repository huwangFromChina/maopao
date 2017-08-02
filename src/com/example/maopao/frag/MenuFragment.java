package com.example.maopao.frag;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.maopao.R;
import com.example.maopao.ada.MenuAdapter;
import com.example.maopao.aty.About;
import com.example.maopao.aty.Consummate;
import com.example.maopao.aty.Guider;
import com.example.maopao.aty.Login;
import com.example.maopao.cla.ActivityCollector;
import com.example.maopao.cla.MenuOption;
import com.example.maopao.cla.User;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends Fragment {
	private ArrayList<MenuOption> menu_items = new ArrayList<MenuOption>();
	private ListView menu_list;
	private View mView;
	private TextView exit;
	private TextView help;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.leftmenu, null);
			exit = (TextView) mView.findViewById(R.id.exit);
			help = (TextView) mView.findViewById(R.id.help);
			init();
			initBroadcastListener();
		}
		return mView;
	}

	
	
	public void init() {
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ActivityCollector.finishAll();
			}


		});
		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),Guider.class);
				startActivity(intent);
			}

		});
		for (int i = 0; i > 8; i++) {
			MenuOption item = new MenuOption(Integer.toString(i), R.drawable.poke);
			menu_items.add(item);
		}
		ArrayList<String> string = new ArrayList<String>();
		string.add("完善个人信息");
		string.add("冒泡");
		string.add("感觉自己萌萌哒");
		string.add("退出登录");
		string.add("了解我们");
		menu_list = (ListView) mView.findViewById(R.id.menu_list);
		MenuAdapter menu_adapter = new MenuAdapter(this.getActivity(), R.layout.menu_row, string);
		menu_list.setAdapter(menu_adapter);
		menu_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent intent = new Intent(getActivity(), Consummate.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent2 = new Intent(getActivity(), About.class);
					startActivity(intent2);
					break;
				case 3:
					getActivity().finish();
					break;
				case 4:
					// Intent intent4=new
					// Intent(getActivity(),Information.class);
					// startActivity(intent4);
					break;
				default:
					break;
				}
			}

		});
		/*if(User.netPath!=null)
		{
		    ImageView portrait=(ImageView)mView.findViewById(R.id.user_portrait);
		    Bitmap bitmap=getHttpBitmap(User.netPath);
		    portrait.setImageBitmap(bitmap);
		}*/
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
	
	private void initBroadcastListener() {
		LocalBroadcastManager mBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.maopao.call2");
		mBroadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
	}
	

	private BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) { 
            if(intent.getAction().equals("com.example.maopao.call2"))  
            {    
    			//updateUser
            	ImageView portrait=(ImageView)mView.findViewById(R.id.user_portrait);
            	TextView name=(TextView)mView.findViewById(R.id.user_name);
            	TextView description=(TextView)mView.findViewById(R.id.user_description);
            	Bitmap bitmap=getLoacalBitmap(User.path);
            	portrait.setImageBitmap(bitmap);
            	name.setText(User.name);
            	description.setText(User.description);
            } 
		}
		public Bitmap getLoacalBitmap(String url) {
			try {
				FileInputStream fis = new FileInputStream(url);
				return BitmapFactory.decodeStream(fis); /// 把流转化为Bitmap图片

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
	};
}
