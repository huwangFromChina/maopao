package com.example.maopao.ada;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.maopao.R;
import com.example.maopao.cla.Behavior;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.NetConnection;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BehaviorAdapter extends ArrayAdapter<Behavior> {

	private int resourceId;
	private LinearLayout comment;
	public ArrayList<Behavior> behaviorlist=null;
	private Context context;

	public BehaviorAdapter(Context context, int resource, ArrayList<Behavior> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resourceId = resource;
		this.context=context;
		behaviorlist=objects;
	}

	public void setResource(ArrayList<Behavior> objects)
	{
		behaviorlist=objects;
	}
	
	
	
	public View getView(int position, View convertView, ViewGroup parent) {

		final TextView my_comment1;
		final TextView my_comment2;
		final TextView my_comment3;
		final EditText edit;
		final Behavior behavior = behaviorlist.get(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else
			view = convertView;
		comment = (LinearLayout) view.findViewById(R.id.comment);
		edit = (EditText) view.findViewById(R.id.input_comment);
		TextView name = (TextView) view.findViewById(R.id.name);
		ImageView portrait = (ImageView) view.findViewById(R.id.portrait);
		TextView content = (TextView) view.findViewById(R.id.content);
		name.setText(behavior.getName());
		if(behavior.getPortrait()!=null)
		{
		    Bitmap bitmap = getHttpBitmap(behavior.getPortrait());
	     //   portrait.setImageBitmap(bitmap);
		}
		content.setText(behavior.getContent());
		comment.removeAllViews();
		for (int i = 0; i < behavior.getCommentName().size(); i++) {
			LinearLayout lay = new LinearLayout(context);
			TextView comment_name = new TextView(context);
			TextView comment_content = new TextView(context);
			comment_name.setText(behavior.getCommentName().get(i).toString() + ":");
			comment_content.setText(behavior.getCommentContent().get(i).toString());
			lay.addView(comment_name);
			lay.addView(comment_content);
			comment.addView(lay);
		}
		Button submit = (Button) view.findViewById(R.id.submit_comment);
	    my_comment1=(TextView)view.findViewById(R.id.my_comment1);
        my_comment2=(TextView)view.findViewById(R.id.my_comment2);
		my_comment3=(TextView)view.findViewById(R.id.my_comment3);
		submit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!edit.getText().toString().equals("")) {
					if(!my_comment1.getText().toString().equals(""))
					{
						if(!my_comment2.getText().toString().equals(""))
						{
							if(!my_comment3.getText().toString().equals(""))
							{
								Toast.makeText(context, "最多添加3条评论", Toast.LENGTH_SHORT).show();
							}
							else
							{
								my_comment3.setText(User.name+":"+edit.getText().toString());
							}
						}
						else
						{
							my_comment2.setText(User.name+":"+edit.getText().toString());
						}
					}
					else
					{
						my_comment1.setText(User.name+":"+edit.getText().toString());
					}
					Map<String, String> comment = new HashMap<String, String>();
					comment.put("content", edit.getText().toString());
					new NetConnection(Url.submitComment+User.id+"/"+behavior.getId(), comment, new NetConnection.SuccessCallback() {

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							if(JsonAnalysis.getInt(result)!=1)
								Toast.makeText(context, "评论失败a", Toast.LENGTH_SHORT).show();
						}
					}, new NetConnection.FailCallback() {
						@Override
						public void onFail() {
							// TODO Auto-generated method stub
							Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
						}

					});
					edit.setText("");
				}
			}
			
		});
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
