package com.example.maopao.frag;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maopao.R;
import com.example.maopao.ada.FriendAdapter;
import com.example.maopao.aty.Chat;
import com.example.maopao.aty.NewFriend;
import com.example.maopao.aty.SeekFriend;
import com.example.maopao.cla.BundleCommitte;
import com.example.maopao.cla.Friend;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.GetNetConnection;

public class FriendsFragment extends Fragment {
	private ListView listview;
	private ImageView add_friend;
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private FriendAdapter adapter;
	private View view;
	private int i;
	private boolean markAdd=false;
	private String res="";
	private  ArrayList<Friend> new_friend;
	private Button update;
	private BundleCommitte application;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if(!msg.obj.equals("[]")&&!msg.obj.equals(""))
				{
					//Toast.makeText(getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
					new_friend=new ArrayList<Friend>();

					try {
						JSONArray jsa=new JSONArray(msg.obj.toString());
						for(int i=0;i<jsa.length();i++)
						{
							JSONObject json=(JSONObject)jsa.get(i);
							JSONObject userinfo=json.getJSONObject("user");
							Friend friend=new Friend(
									Integer.parseInt(userinfo.getString("id")),
									userinfo.getString("name"), 
									userinfo.getString("description"), 
									userinfo.getString("phone"), 
									Integer.parseInt(userinfo.getString("sex")), 
									userinfo.getString("createDate"),
									userinfo.getString("imagePath"));
									
							new_friend.add(friend);
						}
						} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(i=0;i<1;i++)
					{
		            LinearLayout notify=(LinearLayout)view.findViewById(R.id.notify);
		            notify.setVisibility(View.VISIBLE);
		            notify.setOnClickListener(new OnClickListener()
		            {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							application.set(new_friend.get(0).getId(),"", "", "",1, "", "");
							Intent intent=new Intent(getActivity(),NewFriend.class);
							startActivityForResult(intent, 0);
						}
		            	
		            });
					}
				}
			}
			super.handleMessage(msg);
		};
	};
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			message.obj="";


			new GetNetConnection(Url.friendingRequest+User.id,new GetNetConnection.SuccessCallback() {

				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					res=result;
					
				}
			}, new GetNetConnection.FailCallback() {
				@Override
				public void onFail() {
					// TODO Auto-generated method stub

				}

			});
			message.obj=res;
			handler.sendMessage(message);
		}
	};

	@Override
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		application = (BundleCommitte)getActivity().getApplicationContext();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.friends, container, false);
		init_friend();
		add_friend = (ImageView) view.findViewById(R.id.add_friend);
		add_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SeekFriend.class);
				startActivity(intent);
			}

		});
		update=(Button)view.findViewById(R.id.updateFriend);
		update.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				init_friend();
				adapter.notifyDataSetChanged();
			}
			
		});
		listview = (ListView) view.findViewById(R.id.test_list2);
		adapter = new FriendAdapter(getActivity(), R.layout.friend_row, friends);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Friend friend = friends.get(position);
				Intent intent = new Intent(getActivity(), Chat.class);
				application.set(friend.getId(),friend.getName(), friend.getDescription(), friend.getPhone(), friend.getSex(), friend.getCreateDate(), friend.getPortrait());
				startActivity(intent);
			}

		});
		timer.schedule(task, 1000, 20000);
	
		
		return view;
	}

	
	public void init_friend() {
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "提交数据中", "wait...");
		pd.show();
		String path=Url.getFriends+User.id;
		
		
		new GetNetConnection(path,new GetNetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stubstub
				pd.dismiss();
				if(result.equals("[]"))
				{
					Toast.makeText(getContext(), "未获取到好友1", Toast.LENGTH_SHORT).show();
					return;
			    }
				ArrayList<Friend> friend=JsonAnalysis.getFriends(result);
				if(friend==null)
				{
					Toast.makeText(getContext(), "未获取到好友", Toast.LENGTH_SHORT).show();
					return;
				}
				friends.clear();
				for(int i=0;i<friend.size();i++)
				{
					Friend fr=new Friend(friend.get(i).getId(), null, null,null,0,null, null);
					friends.add(fr);
				}
				for (int i = 0; i < friends.size(); i++) {
					friends.get(i).setFriend();
				}
				adapter.notifyDataSetChanged();
			}
		}, new GetNetConnection.FailCallback() {
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				pd.dismiss();
				Toast.makeText(getContext(), "获取好友失败", Toast.LENGTH_SHORT).show();
			}
		});
	}


	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
			String tag = data.getExtras().getString("tag");
			if (tag.equals("1")) {
				String phone = data.getExtras().getString("phone");
				for(int i=0;i<friends.size();i++)
				{
					if(phone.equals(friends.get(i).getPhone()))
					{
						friends.remove(i);
						adapter.notifyDataSetChanged();
					}
				}
		    }
			else if(tag.equals("2"))
			{
				LinearLayout notify=(LinearLayout)view.findViewById(R.id.notify);
				notify.setVisibility(View.GONE);
				friends.add(new_friend.get(0));
				adapter.notifyDataSetChanged();
			}

	}
}
