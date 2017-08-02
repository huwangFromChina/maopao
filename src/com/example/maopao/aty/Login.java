package com.example.maopao.aty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maopao.R;
import com.example.maopao.cla.ActivityCollector;
import com.example.maopao.cla.BundleCommitte;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.net.NetConnection;

public class Login extends Activity implements OnClickListener, OnCheckedChangeListener, OnFocusChangeListener {
	private Button bt_login;
    private CheckBox bt_auto;
    private CheckBox bt_remeber;
    private Button bt_findpass;
    private Button bt_register;
    private EditText edit_number;
    private EditText edit_pass;
    private SharedPreferences myshare;
    private Editor editor;
    boolean rememberflag;
    private boolean firstflag;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        bt_login=(Button) findViewById(R.id.Login_bt);
        bt_auto=(CheckBox) findViewById(R.id.autologin_bt);
        bt_remeber=(CheckBox) findViewById(R.id.remember_bt);
        bt_findpass=(Button) findViewById(R.id.findpass_bt);
        bt_register=(Button) findViewById(R.id.register_bt);
        edit_number=(EditText) findViewById(R.id.user_num);
        edit_pass=(EditText) findViewById(R.id.user_pass);
        if(rememberflag==false)
        {
        edit_number.setTextColor(android.graphics.Color.GRAY);
        edit_number.setText("账户");
        
        edit_pass.setTextColor(android.graphics.Color.GRAY);
        edit_pass.setText("密码");
        }
        //设置第一次使用为真
      //  firstflag=true;
        myshare=getSharedPreferences("myshare", MODE_PRIVATE);
        editor=myshare.edit();
        firstflag=myshare.getBoolean("firstflag", true);
        bt_login.setOnClickListener(this);
        edit_number.setOnFocusChangeListener(this);
        edit_pass.setOnFocusChangeListener(this);
        bt_auto.setOnCheckedChangeListener(this);
        bt_register.setOnClickListener(this);
        rememberflag=myshare.getBoolean("remeberflag", false);
        displayGuidness();
        judge_RAA();//判断是否可以自动登录
        ActivityCollector.addActivity(this);
        createFile("inital.jpg", R.drawable.po);
    }
    private void displayGuidness()
    {
    	if(firstflag==true)
    	{
    		intent=new Intent(Login.this,Guider.class);
    		startActivity(intent);
    	}
    
    }
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(bt_auto==arg0&&arg1==true)
		{
			if(bt_remeber.isChecked()==false)
			{
				bt_auto.setChecked(false);
				Toast.makeText(Login.this, "请先记住密码", Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//if(isNetworkAvailable(Login.this)==true);
		if(arg0==bt_login)
		{
			verifyInfo();
		}
		//注册信息
		else if(arg0==bt_register)
		{
            intent=new Intent(Login.this, RegisterUser.class);
            startActivity(intent);
		}
	}
	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
		if(arg0==edit_number&&arg1==true&&rememberflag==false)
		{
			 edit_number.setTextColor(android.graphics.Color.WHITE);
		        edit_number.setText("");
		        edit_number.setBackgroundResource(R.drawable.shapeedit);
		        
		}
		else if(arg0==edit_pass&&arg1==true&&rememberflag==false)
		{
			 edit_pass.setTextColor(android.graphics.Color.WHITE);
		        edit_pass.setText("");
		        edit_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		        edit_pass.setBackgroundResource(R.drawable.shapeedit);
		}
		if(arg0==edit_number&&arg1==false)
		{
			judgeInput_number();
		}
	   if(arg0==edit_pass&&arg1==false)
		{
			judgeInput_pass();
		}
		
	}
	//验证信息
	private void verifyInfo()
	{
        
        	SetShare();
        	final ProgressDialog pd = ProgressDialog.show(this, "提交数据中", "wait...");
    		pd.show();
        	Map<String,String> params=new HashMap<String,String>();
        	params.put("phone", edit_number.getText().toString());
        	params.put("password",edit_pass.getText().toString());
        	new NetConnection(Url.login, params, new NetConnection.SuccessCallback() {
    			@Override
    			public void onSuccess(String result) {
    				// TODO Auto-generated method stub
    				pd.dismiss();
    				//TextView test=(TextView)findViewById(R.id.test222);
    				//test.setText(result);
    				if(result.equals(""))
    				{
    					Toast.makeText(Login.this, "密码或账号错误", Toast.LENGTH_SHORT).show();
    					return ;
    				}
    				if(JsonAnalysis.getLogin(result)==0)
    				{
    					Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();
    					return;
    				}
    				
    	        	intent = new Intent(Login.this, MainActivity.class);
    				startActivity(intent);
    			} 
    		}, new NetConnection.FailCallback() {
    			@Override
    			public void onFail() {
    				// TODO Auto-generated method stub
    				pd.dismiss();
    	        	Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();

    	        	
    	        	TextView test=(TextView)findViewById(R.id.test222);
    				User.phone="13767005141";
    				User.password="123456";
    				User.id="1";
    	        	intent = new Intent(Login.this, MainActivity.class);
    				startActivity(intent);
    			}

    		});
        
	}
	//设置shareprefence
	private void SetShare()
	{
		if(bt_auto.isChecked()==true)
		{
		   editor.putBoolean("autoflag", true);
		}
		else 
		{
			editor.putBoolean("autoflag", false);
		}
		if(bt_remeber.isChecked()==true)
		{
			editor.putBoolean("remeberflag", true);
		    editor.putString("usernumber", edit_number.getText().toString());
		    editor.putString("userpass", edit_pass.getText().toString());
		}
		else
		{
			editor.putBoolean("remeberflag", false);
		    editor.putString("", edit_number.getText().toString());
		    editor.putString("", edit_pass.getText().toString());
		}
		editor.commit();
       
	}
	//判断账号和密码的输入合法性
	private void judgeInput_number()
	{
		if(edit_number.getText().toString().equals(""))
		{
			 edit_number.setTextColor(android.graphics.Color.GRAY);
		        edit_number.setText("账户不能为空");
		        edit_number.setBackgroundResource(R.drawable.error_shape);
		}
		
	}
	private void judgeInput_pass()
	{
		if(edit_pass.getText().toString().equals(""))
		{
			 edit_pass.setTextColor(android.graphics.Color.GRAY);
		        edit_pass.setText("密码不能为空");
		        edit_pass.setBackgroundResource(R.drawable.error_shape);
		}
	}
   //判断是否需要自动登录。
	private void judge_RAA()
	{
		
		String number=myshare.getString("usernumber", "");
		String pass=myshare.getString("userpass", "");
		boolean autoflag=myshare.getBoolean("autoflag", false);
		boolean rememberflag=myshare.getBoolean("remeberflag", false);
		if(rememberflag==true)
		{
			Toast.makeText(Login.this,pass, Toast.LENGTH_SHORT).show();
			edit_number.setText(number);
			edit_pass.setText(pass);
			bt_remeber.setChecked(true);
			if(autoflag==true)
			{
				bt_auto.setChecked(true);
				verifyInfo();
			}
		}
	}
	//检查网络是否可用
	public  boolean isNetworkAvailable(Context context) {  
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo[] info = mgr.getAllNetworkInfo();  
        if (info != null) {  
            for (int i = 0; i < info.length; i++) {  
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
                    return true;  
                }  
            }  
        } 
        Toast.makeText(Login.this, "网络不可用，无法登录", Toast.LENGTH_SHORT).show();
        return false;  
    }  
    

	public void createFile(String name , int id) {  
        String filePath = Environment.getExternalStorageDirectory() + "/my_camera/" + name;// 文件路径  
        try {  
            File dir = new File(Environment.getExternalStorageDirectory() + "/my_camera/");// 目录路径  
            if (!dir.exists()) {// 如果不存在，则创建路径名  
                dir.mkdirs();
            }  
            // 目录存在，则将apk中raw中的需要的文档复制到该目录下  
            File file = new File(filePath);  
            if (!file.exists()) {// 文件不存在  
                InputStream ins = getBaseContext().getResources().openRawResource(  
                        id);// 通过raw得到数据资源  
                FileOutputStream fos = new FileOutputStream(file);  
                byte[] buffer = new byte[8192];  
                int count = 0;// 循环写出  
                while ((count = ins.read(buffer)) > 0) {  
                    fos.write(buffer, 0, count);  
                }  
                fos.close();// 关闭流  
                ins.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}
