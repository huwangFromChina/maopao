package com.example.maopao.frag;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maopao.R;
import com.example.maopao.aty.FriendDetail;
import com.example.maopao.cla.BundleCommitte;
import com.example.maopao.cla.Friend;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.GetNetConnection;
import com.example.maopao.net.NetConnection;

public class NeibouringFragment extends Fragment{
	

	private ImageView swit;
	private double latitude = 0.0;
	private double longitude = 0.0;
	private ArrayList<Friend> addition=new ArrayList<Friend>();
	private boolean tag=false;
	private String url=Url.getNeibouring;
	private View view;
	
	private BundleCommitte application;
	@Override
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		application = (BundleCommitte) getActivity().getApplicationContext();
		submitLocation();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.neibouring, container, false);
		swit=(ImageView)view.findViewById(R.id.neigbouring_switch);
		swit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tag==false)
				{
				   swit.setImageResource(R.drawable.swit2);
				   url=Url.getNeibouring;
				   tag=true;
				   submitLocation();
				}
				else
				{
					swit.setImageResource(R.drawable.swit1);
					url=Url.recommend;
					tag=false;
					getCommend(url+User.id);
				}
			}
		
		});
		return view;
	}

	protected void getCommend(String string) {
		// TODO Auto-generated method stub
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "提交数据中", "wait...");
		pd.show();
		new GetNetConnection(string,new GetNetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				pd.dismiss();
				addition.clear();
				ArrayList<Friend> friend=JsonAnalysis.getFriends(result);
				if(friend==null)
				{
					Toast.makeText(getContext(), "未获取到资源", Toast.LENGTH_SHORT).show();
					return;
				}
				for(int i=0;i<friend.size();i++)
				{
					Friend fr=new Friend(friend.get(i).getId(),friend.get(i).getName(),friend.get(i).getDescription(),friend.get(i).getPhone(),0,friend.get(i).getCreateDate(), friend.get(i).getPortrait());
					addition.add(fr);
					Toast.makeText(getContext(), i, Toast.LENGTH_SHORT).show();
				}

				TextView name=(TextView)view.findViewById(R.id.neigbouring_name);
				TextView name2=(TextView)view.findViewById(R.id.neigbouring_name2);
				TextView name3=(TextView)view.findViewById(R.id.neigbouring_name3);
				TextView name4=(TextView)view.findViewById(R.id.neigbouring_name4);
				TextView name5=(TextView)view.findViewById(R.id.neigbouring_name5);
				
				TextView description=(TextView)view.findViewById(R.id.neigbouring_description);
				TextView description2=(TextView)view.findViewById(R.id.neigbouring_description2);
				TextView description3=(TextView)view.findViewById(R.id.neigbouring_description3);
				TextView description4=(TextView)view.findViewById(R.id.neigbouring_description4);
				TextView description5=(TextView)view.findViewById(R.id.neigbouring_description5);
				
				ImageView portrait=(ImageView)view.findViewById(R.id.neigbouring_portrait);
				ImageView portrait2=(ImageView)view.findViewById(R.id.neigbouring_portrait2);
				ImageView portrait3=(ImageView)view.findViewById(R.id.neigbouring_portrait3);
				ImageView portrait4=(ImageView)view.findViewById(R.id.neigbouring_portrait4);
				ImageView portrait5=(ImageView)view.findViewById(R.id.neigbouring_portrait5);
				
				TextView distance=(TextView)view.findViewById(R.id.neigbouring_distance);
				TextView distance2=(TextView)view.findViewById(R.id.neigbouring_distance2);
				TextView distance3=(TextView)view.findViewById(R.id.neigbouring_distance3);
				TextView distance4=(TextView)view.findViewById(R.id.neigbouring_distance4);
				TextView distance5=(TextView)view.findViewById(R.id.neigbouring_distance5);
				
				
				
				for(int i=0;i<addition.size();i++)
				{
					if(i==0)
					{
						if(addition.get(i).getName()!=null)
							name.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
								description.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						//Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait.setImageBitmap(bitmap);
						}
						distance.setText(addition.get(i).getCreateDate());
						LinearLayout get1=(LinearLayout)view.findViewById(R.id.neibouring1);
						get1.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								setApplication(0);
								startActivity(intent);
							}
							
						});
					}
					if(i==1)
					{
						if(addition.get(i).getName()!=null)
						name2.setText(addition.get(i).getName());
						if(addition.get(i).getDescription()!=null)
						description2.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait2.setImageBitmap(bitmap);
						}
							
						distance2.setText(addition.get(i).getCreateDate());
						LinearLayout get2=(LinearLayout)view.findViewById(R.id.neibouring2);
						get2.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {

								setApplication(1);
								// TODO Auto-generated method stub
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}
					if(i==2)
					{
						if(addition.get(i).getName()!=null)
							name3.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description3.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						portrait3.setImageBitmap(bitmap);
						}
						distance3.setText(addition.get(i).getCreateDate());
						LinearLayout get3=(LinearLayout)view.findViewById(R.id.neibouring3);
						get3.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								setApplication(2);
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}
					if(i==3)
					{
						if(addition.get(i).getName()!=null)
							name4.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description4.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait4.setImageBitmap(bitmap);
						}
						distance4.setText(addition.get(i).getCreateDate());
						LinearLayout get4=(LinearLayout)view.findViewById(R.id.neibouring4);
						get4.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								setApplication(3);
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}

					if(i==4)
					{
						if(addition.get(i).getName()!=null)
							name5.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description5.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait5.setImageBitmap(bitmap);
						}
						distance5.setText(addition.get(i).getCreateDate());
						LinearLayout get5=(LinearLayout)view.findViewById(R.id.neibouring5);
						get5.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								setApplication(4);
								Intent intent = new Intent(getActivity(),FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}
				}
			}
		}, new GetNetConnection.FailCallback() {
			@Override
			public void onFail() {
				// TODO Auto-generated method stub

			}

		});
	}

	public void submitLocation() {
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
		 Map<String, String> params = new HashMap<String, String>();
		   params.put("longitude",String.valueOf(longitude));
		   params.put("latitude", String.valueOf(latitude));
		   getNeibouring(url+User.id,params);
	}


	private void getNeibouring(String path,Map<String,String> params) {
		// TODO Auto-generated method stub
    	final ProgressDialog pd = ProgressDialog.show(getActivity(), "提交数据中", "wait...");
		pd.show();
		new NetConnection(path, params, new NetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				pd.dismiss();
				addition.clear();
				ArrayList<Friend> friend=JsonAnalysis.getLocationInfor(result);
				if(friend==null)
				{
					Toast.makeText(getContext(), "未获取到资源", Toast.LENGTH_SHORT).show();
					return;
				}
				for(int i=0;i<friend.size();i++)
				{
					Friend fr=new Friend(friend.get(i).getId(),friend.get(i).getName(),friend.get(i).getDescription(),friend.get(i).getPhone(),0,friend.get(i).getCreateDate(), friend.get(i).getPortrait());
					addition.add(fr);
				}
				

				TextView name=(TextView)view.findViewById(R.id.neigbouring_name);
				TextView name2=(TextView)view.findViewById(R.id.neigbouring_name2);
				TextView name3=(TextView)view.findViewById(R.id.neigbouring_name3);
				TextView name4=(TextView)view.findViewById(R.id.neigbouring_name4);
				TextView name5=(TextView)view.findViewById(R.id.neigbouring_name5);
				
				TextView description=(TextView)view.findViewById(R.id.neigbouring_description);
				TextView description2=(TextView)view.findViewById(R.id.neigbouring_description2);
				TextView description3=(TextView)view.findViewById(R.id.neigbouring_description3);
				TextView description4=(TextView)view.findViewById(R.id.neigbouring_description4);
				TextView description5=(TextView)view.findViewById(R.id.neigbouring_description5);
				
				ImageView portrait=(ImageView)view.findViewById(R.id.neigbouring_portrait);
				ImageView portrait2=(ImageView)view.findViewById(R.id.neigbouring_portrait2);
				ImageView portrait3=(ImageView)view.findViewById(R.id.neigbouring_portrait3);
				ImageView portrait4=(ImageView)view.findViewById(R.id.neigbouring_portrait4);
				ImageView portrait5=(ImageView)view.findViewById(R.id.neigbouring_portrait5);
				
				TextView distance=(TextView)view.findViewById(R.id.neigbouring_distance);
				TextView distance2=(TextView)view.findViewById(R.id.neigbouring_distance2);
				TextView distance3=(TextView)view.findViewById(R.id.neigbouring_distance3);
				TextView distance4=(TextView)view.findViewById(R.id.neigbouring_distance4);
				TextView distance5=(TextView)view.findViewById(R.id.neigbouring_distance5);
				
				
				
				for(int i=0;i<addition.size();i++)
				{
					if(i==0)
					{
						if(addition.get(i).getName()!=null)
							name.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait.setImageBitmap(bitmap);
						}
						Toast.makeText(getContext(), addition.get(i).getCreateDate(), Toast.LENGTH_SHORT).show();
						distance.setText(addition.get(i).getCreateDate());
						LinearLayout get1=(LinearLayout)view.findViewById(R.id.neibouring1);
						get1.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								Intent intent = new Intent(getActivity(), FriendDetail.class);
								setApplication(0);
								startActivity(intent);
							}
							
						});
					}
					if(i==1)
					{
						if(addition.get(i).getName()!=null)
							name2.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description2.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait2.setImageBitmap(bitmap);
						}
						distance2.setText(addition.get(i).getCreateDate());
						LinearLayout get2=(LinearLayout)view.findViewById(R.id.neibouring2);
						get2.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								setApplication(1);
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}
					if(i==2)
					{
						if(addition.get(i).getName()!=null)
							name3.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description3.setText(addition.get(i).getDescription());
						if(addition.get(i).getPortrait()!=null)
						{
						Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait3.setImageBitmap(bitmap);
						}
						distance3.setText(addition.get(i).getCreateDate());
						LinearLayout get3=(LinearLayout)view.findViewById(R.id.neibouring3);
						get3.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								setApplication(2);
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}
					if(i==3)
					{
						if(addition.get(i).getName()!=null)
							name4.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description4.setText(addition.get(i).getDescription());
							if(addition.get(i).getPortrait()!=null)
							{
							Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait4.setImageBitmap(bitmap);
							}
						distance4.setText(addition.get(i).getCreateDate());
						LinearLayout get4=(LinearLayout)view.findViewById(R.id.neibouring4);
						get4.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								setApplication(3);
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}

					if(i==4)
					{
						if(addition.get(i).getName()!=null)
							name5.setText(addition.get(i).getName());
							if(addition.get(i).getDescription()!=null)
						description5.setText(addition.get(i).getDescription());
							if(addition.get(i).getPortrait()!=null)
							{
							Bitmap bitmap=getHttpBitmap(addition.get(i).getPortrait());
						//portrait5.setImageBitmap(bitmap);
							}
						distance5.setText(addition.get(i).getCreateDate());
						LinearLayout get5=(LinearLayout)view.findViewById(R.id.neibouring5);
						get5.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								setApplication(4);
								Intent intent = new Intent(getActivity(), FriendDetail.class);
								startActivity(intent);
							}
							
						});
					}
				}
			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				pd.dismiss();
				Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
			}

		});
	}

	

	private void setApplication(int i) {
		// TODO Auto-generated method stub
		String name = addition.get(i).getName();
		String description = addition.get(i).getName();
		String phone=addition.get(i).getName();
		String portrait=addition.get(i).getPortrait();
		String createDate=addition.get(i).getCreateDate();
		int sex=addition.get(i).getSex();
		if(addition.get(i).getName()==null)
			 name="";
		if(addition.get(i).getName()==null)
			phone="";
		if(addition.get(i).getPortrait()==null)
			portrait="";
		if(addition.get(i).getCreateDate()==null)
			createDate="";
		if(addition.get(i).getName()==null)
			description="";
		 application.set(addition.get(i).getId(),
				 name,
				 description,
				 phone,
				 sex,
				 createDate,
				 portrait);
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
