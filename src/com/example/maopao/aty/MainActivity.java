package com.example.maopao.aty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.maopao.R;
import com.example.maopao.ada.ViewPagerAdapter;
import com.example.maopao.cla.ActivityCollector;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.data.SQL;
import com.example.maopao.frag.BehaviorFragment;
import com.example.maopao.frag.FriendsFragment;
import com.example.maopao.frag.MenuFragment;
import com.example.maopao.frag.NeibouringFragment;
import com.example.maopao.net.NetConnection;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity {

	private ViewPager viewpager;
	private ViewPagerAdapter viewadapter;
	private ArrayList<Fragment> fragmentlist;
	private double latitude = 0.0;
	private double longitude = 0.0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init_sliding_menu();
		init_view_pager();
		initBroadcastListener();
		init_widget();
		submitLocation();
		ActivityCollector.addActivity(this);
	}


	public void submitLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
		} else {
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude(); // 经度
				longitude = location.getLongitude(); // 纬度
			}
		}
		Map<String, String> location = new HashMap<String, String>();
		location.put("longitude", String.valueOf(longitude));
		location.put("latitude", String.valueOf(latitude));
		  new NetConnection(Url.sendLocation+User.id,location, 
			new NetConnection.SuccessCallback() {
		  
		      @Override 
		      public void onSuccess(String result) { 
		 
		      } 
		  }, new NetConnection.FailCallback() {
		      @Override
		      public void onFail() { 
			      // TODO Auto-generated method stub
		          Toast.makeText(getBaseContext(), "上传地址失败", Toast.LENGTH_SHORT).show();
		      }
		  });
	}

	private void init_widget() {
		// TODO Auto-generated method stub
		ImageView button1 = (ImageView) findViewById(R.id.button1);
		ImageView button2 = (ImageView) findViewById(R.id.button2);
		ImageView button3 = (ImageView) findViewById(R.id.button3);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewpager.setCurrentItem(0);
			}

		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewpager.setCurrentItem(1);
			}

		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewpager.setCurrentItem(2);
			}

		});
	}

	// 初始化viewpager
	@SuppressWarnings("deprecation")
	private void init_view_pager() {
		Log.d("home", "init_viewpager1");
		// TODO Auto-generated method stub
		Fragment behavior = new BehaviorFragment();
		Log.d("home", "init_viewpager0");
		Fragment friends = new FriendsFragment();
		Fragment neibouring = new NeibouringFragment();
		viewpager = (ViewPager) findViewById(R.id.view_pager);
		fragmentlist = new ArrayList<Fragment>();
		fragmentlist.add(behavior);
		fragmentlist.add(friends);
		fragmentlist.add(neibouring);
		viewadapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentlist);
		viewpager.setAdapter(viewadapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				SlidingMenu menu = getSlidingMenu();
				if (position == 0) {
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else {
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				}
			}

		});
		Log.d("home", "init_Viepager");
	}

	// 初始化侧边栏
	private void init_sliding_menu() {
		// TODO Auto-generated method stub
		setBehindContentView(R.layout.leftmenufragment);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu, new MenuFragment()).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.width);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.3f);
		Log.d("home", "init_SLindingmenu");
	}

	private void initBroadcastListener() {
		LocalBroadcastManager mBroadcastManager = LocalBroadcastManager.getInstance(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.maopao.call1");
		mBroadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
	}
	

	private BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals("com.example.maopao.call1"))  
            {    
				   
				SlidingMenu slidingmenu = getSlidingMenu();
				slidingmenu.showMenu();
            } 
		}
	};
	
	

	//按下返回键跳转到主界面
	@Override
    public void onBackPressed() {
        // super.onBackPressed();
		
		Intent i= new Intent(Intent.ACTION_MAIN); 

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 

        i.addCategory(Intent.CATEGORY_HOME); 

        startActivity(i);
    }

}
