package com.example.maopao.aty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.maopao.R;
import com.example.maopao.cla.ActivityCollector;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.MyDialog;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.data.SQL;
import com.example.maopao.net.NetConnection;
import com.example.maopao.net.SubmitMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUser extends Activity implements OnClickListener, OnFocusChangeListener {
	private Intent intent;
	private Button bt_register;
	private EditText user_phone;
	private EditText user_name;
	private EditText user_pass1;// ��һ����������
	private EditText user_pass2;// �ٴ�ȷ������
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_user);
		bt_register = (Button) findViewById(R.id.register_bt);
		user_phone = (EditText) findViewById(R.id.user_num);
		user_name = (EditText) findViewById(R.id.user_name);
		user_pass1 = (EditText) findViewById(R.id.user_pass1);
		user_pass2 = (EditText) findViewById(R.id.user_pass2);
		bt_register.setOnClickListener(this);
		user_phone.setOnFocusChangeListener(this);
		user_name.setOnFocusChangeListener(this);
		user_pass1.setOnFocusChangeListener(this);
		user_pass2.setOnFocusChangeListener(this);
		user_pass1.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if (user_pass1.getText().toString().equals("") == false) {
					user_pass2.setEnabled(true);
				} else {
					user_pass2.setEnabled(false);
				}

			}

		});
		ActivityCollector.addActivity(this);

	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if (arg0 == user_phone) {
			if (arg1 == true) {
				if (user_phone.getText().toString().equals("�ֻ�����")) {
					user_phone.setText("");
				}
			} else if (arg1 == false) {
				if (user_phone.getText().toString().equals("")) {
					user_phone.setText("�ֻ�����");
				}
			}
		}

		else if (arg0 == user_name) {
			if (arg1 == true) {
				if (user_name.getText().toString().equals("����")) {
					user_name.setText("");
				}
			} else if (arg1 == false) {
				if (user_name.getText().toString().equals("")) {
					user_name.setText("����");
				}
			}
		} else if (arg0 == user_pass1) {
			if (arg1 == true) {
				if (user_pass1.getText().toString().equals("����")) {
					user_pass1.setText("");
				}
			} else if (arg1 == false) {
				if (user_pass1.getText().toString().equals("")) {
					user_pass1.setText("����");
					user_pass2.setEnabled(false);
				} else {
					user_pass2.setEnabled(true);
				}
			}
		} else if (arg0 == user_pass2) {
			if (arg1 == true) {
				if (user_pass2.getText().toString().equals("ȷ������")) {
					user_pass2.setText("");
				}
			} else if (arg1 == false) {
				if (user_pass2.getText().toString().equals("")) {
					user_pass2.setText("ȷ������");
				}
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == bt_register && judgeInfo() == true) {
			postUser();// �ύע����Ϣ
		}
	}

	void postUser() {
		final ProgressDialog pd = ProgressDialog.show(this, "�ύ������", "wait...");
		pd.show();

		
		Map<String, String> account = new HashMap<String, String>();
		account.put("phone", user_phone.getText().toString());
		account.put("password", user_pass1.getText().toString());
		new NetConnection(Url.register, account, new NetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				pd.dismiss();
				//TextView test=(TextView)findViewById(R.id.test111);
				//test.setText(result);
				String res=JsonAnalysis.getRegister(result);
			    if(res.equals("0")||!res.equals("1"))
			    {
					Toast.makeText(RegisterUser.this, "ע��ʧ��:"+JsonAnalysis.getRegister(result), Toast.LENGTH_SHORT).show();
			    	return;
			    }
				Toast.makeText(RegisterUser.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();// ע��ɹ���ʾ
				User.name=user_name.getText().toString();
				intent = new Intent(RegisterUser.this, MainActivity.class);
				startActivity(intent);
			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				//pd.dismiss();
				// TODO Auto-generated method stub
				Toast.makeText(RegisterUser.this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
				
				
				pd.dismiss();
				/*initUser();
				ContentValues value = new ContentValues();
				value.put("phone", user_phone.getText().toString());
				value.put("password", user_pass1.getText().toString());
				value.put("sex", "1");
				value.put("createDate", new Date().toString());
				value.put("name", user_name.getText().toString());
				value.put("path", Environment.getExternalStorageDirectory() + "/my_camera/inital.jpg");
				value.put("userId", "1");
				db.insert("user", null, value);
				Toast.makeText(RegisterUser.this, "inser SQL", Toast.LENGTH_SHORT).show();
				createFile("inital.jpg",R.drawable.test);

				intent = new Intent(RegisterUser.this, MainActivity.class);
				startActivity(intent);*/
			}

		});
		// �ύע���û���Ϣ
	}


	boolean judgeInfo()// �ж��û������Ƿ�Ϸ�
	{
		if (user_phone.getText().toString().equals("�ֻ�����")) {
			Toast.makeText(RegisterUser.this, "��������ȷ�ֻ�����", Toast.LENGTH_SHORT).show();
			return false;
		} else if (user_phone.getText().toString().length() != 11) {
			Toast.makeText(RegisterUser.this, "��������ȷ�ֻ�����", Toast.LENGTH_SHORT).show();
			return false;
		} else if (user_name.getText().toString().equals("����")) {
			Toast.makeText(RegisterUser.this, "����������", Toast.LENGTH_SHORT).show();
			return false;
		} else if (user_pass1.getText().toString().equals("����")) {
			Toast.makeText(RegisterUser.this, "����������", Toast.LENGTH_SHORT).show();
			return false;
		} else if (user_pass1.getText().toString().length() < 6 || user_pass1.getText().toString().length() > 16) {
			Toast.makeText(RegisterUser.this, "����������6λС��16λ", Toast.LENGTH_SHORT).show();
			return false;
		} else if (user_pass2.getText().toString().equals("ȷ������")) {
			Toast.makeText(RegisterUser.this, "��ȷ������", Toast.LENGTH_SHORT).show();
			return false;
		} else if (user_pass2.getText().toString().equals(user_pass1.getText().toString()) == false) {
			Toast.makeText(RegisterUser.this, "�������벻��ȷ������������", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;

	}
	


}
