package com.example.maopao.frag;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.maopao.R;
import com.example.maopao.ada.BehaviorAdapter;
import com.example.maopao.aty.PublicBehavior;
import com.example.maopao.cla.Behavior;
import com.example.maopao.cla.Friend;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.GetNetConnection;

import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BehaviorFragment extends Fragment {
	private ListView listview;
	private ArrayList<Behavior> behaviorList = new ArrayList<Behavior>();
	private ImageView add;
	private ImageView menu;
	private BehaviorAdapter adapter;
	private View view;
	private Button test;

	@Override
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		init_behavior();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.behavior, container, false);
		add = (ImageView) view.findViewById(R.id.public_behavior);
		menu = (ImageView) view.findViewById(R.id.menu);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PublicBehavior.class);
				startActivityForResult(intent, 0);
			}
		});
		menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.example.maopao.call1");
				LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
			}
		});
		listview = (ListView) view.findViewById(R.id.behavior_list);
		adapter = new BehaviorAdapter(this.getActivity(), R.layout.behavior_row, behaviorList);
		listview.setAdapter(adapter);
		test=(Button)view.findViewById(R.id.update);
		test.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				init_behavior();
			}
			
		});
		return view;
	}

	public void init_behavior() {
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "提交数据中", "wait...");
		pd.show();
		String path=Url.getBehavior+User.id;
		new GetNetConnection(path,new GetNetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stubstub
				pd.dismiss();
				TextView test=(TextView)view.findViewById(R.id.test);
				//test.setText(result);
				behaviorList.clear();
				ArrayList<Behavior> behaviorlist=JsonAnalysis.getBehaviors(result);
				if(behaviorlist==null)
				{
					Toast.makeText(getActivity(), "未获取到动态", Toast.LENGTH_SHORT).show();
					return;
				}
				else
					Toast.makeText(getActivity(), "获取到动态", Toast.LENGTH_SHORT).show();
				for(int i=0;i<behaviorlist.size();i++)
				{
					ArrayList<String> comment_name=new ArrayList<String>();
					ArrayList<String> comment_content=new ArrayList<String>();
					Friend friend=new Friend(behaviorlist.get(i).getf().getId(),behaviorlist.get(i).getName(), behaviorlist.get(i).getf().getDescription(), behaviorlist.get(i).getPhone(),behaviorlist.get(i).getf().getSex(),behaviorlist.get(i).getf().getCreateDate(), behaviorlist.get(i).getPortrait());
					comment_name=behaviorlist.get(i).getCommentName();
					comment_content=behaviorlist.get(i).getCommentContent();
					Behavior fr=new Behavior(behaviorlist.get(i).getId(),behaviorlist.get(i).getContent(),behaviorlist.get(i).getf().getPhone(),behaviorlist.get(i).getName(),behaviorlist.get(i).getPortrait(),friend,comment_name, comment_content);
					behaviorList.add(fr);
				}
				adapter.setResource(behaviorList);
				adapter.notifyDataSetChanged();
			
			}
		}, new GetNetConnection.FailCallback() {
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				pd.dismiss();
				Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
			String tag = data.getExtras().getString("tags");
			if (tag.equals("1")) {
				String content = data.getExtras().getString("content");
				//Toast.makeText(getContext(), tag+content, Toast.LENGTH_SHORT).show();
				ArrayList<String> comment_name=new ArrayList<String>();
				ArrayList<String> comment_content=new ArrayList<String>();
				Friend friend=new Friend(Integer.parseInt((User.id)), User.name, User.description, User.phone, 1, User.createDate, User.path);
				Behavior behaviora = new Behavior(Integer.parseInt(User.id),content,User.phone, User.name, User.path,  friend,comment_name, comment_content);
				behaviorList.add(behaviora);
				adapter.notifyDataSetChanged();
		}

	}

}
