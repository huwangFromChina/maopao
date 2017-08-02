package com.example.maopao.aty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import com.example.maopao.R;
import com.example.maopao.ada.ChatAdapter;
import com.example.maopao.cla.BundleCommitte;
import com.example.maopao.cla.Friend;
import com.example.maopao.cla.MyMessage;
import com.example.maopao.cla.Url;
import com.example.maopao.data.SQL;

public class Chat extends Activity {

	private ListView chat_list;
	private ImageView send;
	private ImageView back;
	private ImageView friend;
	private EditText edit;
	private ChatAdapter adapter;
	private SQL chatSQL;
	private static int Port=9999;
	private static String Host=Url.chat;
	private Socket socket = null;  
    String buffer = "";  
	private ArrayList<MyMessage> chat = new ArrayList<MyMessage>();
	

	private BundleCommitte application;

	 @SuppressLint("HandlerLeak")
	public Handler myHandler = new Handler() {  
	        @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == 0x11) {  
	                Bundle bundle = msg.getData();  
	                chat.add(new MyMessage("server:"+bundle.getString("msg"),0));  
	                adapter.notifyDataSetChanged();
	            }  
	        }  
	  
	    };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat);
		application = (BundleCommitte)this.getApplicationContext();
		Toast.makeText(this, application.getId(), Toast.LENGTH_SHORT).show();
		init();
	}

	// 初始化聊天信息
	private void presetList() {
		// TODO Auto-generated method stub
		chatSQL = new SQL(this, "maopao", null, 1);
		SQLiteDatabase db = chatSQL.getWritableDatabase();
		String limit = "mark=" + application.getPhone();
		Cursor cursor = db.query("chat", null, limit, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				MyMessage message = new MyMessage(cursor.getString(cursor.getColumnIndex("content")),
						cursor.getInt(cursor.getColumnIndex("tag")));
				message.setMark(cursor.getInt(cursor.getColumnIndex("mark")));
				chat.add(message);
			} while (cursor.moveToNext());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		/*SQLiteDatabase db = chatSQL.getWritableDatabase();
		String limit = application.getId();
		db.delete("chat", "mark=", new String[] { limit });
		ContentValues value = new ContentValues();
		for (int i = 0; i < chat.size(); i++) {
			value.put("content", chat.get(i).getMessage());
			value.put("tag", chat.get(i).getTag());
			value.put("mark", chat.get(i).getMark());
			db.insert("chat", null, value);
		}*/
	}

	private void init() {
		// TODO Auto-generated method stub
		//presetList();
		chat_list = (ListView) findViewById(R.id.chat_list);
		send = (ImageView) findViewById(R.id.chat_submit);
		back = (ImageView) findViewById(R.id.chat_back);
		friend = (ImageView) findViewById(R.id.chat_friend);
		edit = (EditText) findViewById(R.id.chat_content);
		adapter = new ChatAdapter(Chat.this, R.layout.chat_row, chat);
		chat_list.setAdapter(adapter);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!edit.getText().toString().equals("")) {
					MyMessage message = new MyMessage(edit.getText().toString(), 1);
					message.setMark(Integer.parseInt(application.getId()));
					chat.add(message);
					adapter.notifyDataSetChanged();
					chat_list.setSelection(chat.size());
					
					new MyThread(edit.getText().toString()).start();
     				edit.setText("");
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		friend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Chat.this, FriendDetail.class);
				startActivity(intent);
			}
		});
	}
	
	 class MyThread extends Thread {  
		  
	        public String txt1;  
	  
	        public MyThread(String str) {  
	            txt1 = str;  
	        }  
	  
	        @Override  
	        public void run() {  
	            //定义消息  
	            Message msg = new Message();  
	            msg.what = 0x11;  
	            Bundle bundle = new Bundle();  
	            bundle.clear();  
	            try {  
	                //连接服务器 并设置连接超时为5秒  
	                socket = new Socket();  
	                socket.connect(new InetSocketAddress(Host, Port), 5000);  
	                //获取输入输出流  
	                OutputStream ou = socket.getOutputStream();  
	                BufferedReader bff = new BufferedReader(new InputStreamReader(  
	                        socket.getInputStream()));  
	                //读取发来服务器信息  
	                String line = null;  
	                buffer="";  
	                while ((line = bff.readLine()) != null) {  
	                    buffer = line + buffer;  
	                }  
	                  
	                //向服务器发送信息  
	                ou.write(txt1.getBytes("UTF-8"));  
	                ou.flush();  
	                bundle.putString("msg", buffer.toString());  
	                msg.setData(bundle);  
	                //发送消息 修改UI线程中的组件  
	                myHandler.sendMessage(msg);  
	                //关闭各种输入输出流  
	                bff.close();  
	                ou.close();  
	                socket.close();  
	            } catch (SocketTimeoutException aa) {  
	                //连接超时 在UI界面显示消息  
	                bundle.putString("msg", "服务器连接失败！请检查网络是否打开");  
	                msg.setData(bundle);  
	                //发送消息 修改UI线程中的组件  
	                myHandler.sendMessage(msg);  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	  
}
