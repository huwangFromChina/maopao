package com.example.maopao.cla;

import android.app.Application;

public class BundleCommitte extends Application{

	public String name="huwang";
	public int id=0;
	public  String portrait="mao";
	public  String createDate="";
	public String description="";
	public String phone="";
	public  int sex=1;
	public boolean isFriend=false;
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public String getId() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}
	public String geDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getPhone() {
		// TODO Auto-generated method stub
		return phone;
	}
	
	public String getCreateDate() {
		// TODO Auto-generated method stub
		return createDate;
	}
	
	public void set(int id,String name, String description, String phone, int sex, String createDate, String portrait) {
		this.id=id;
		this.name = name;
		this.description = description;
		this.phone = phone;
		this.sex = sex;
		this.createDate = createDate;
		this.portrait = portrait;
	}
	public String getPortrait() {
		// TODO Auto-generated method stub
		return portrait;
	}
	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return isFriend;
	}
	public String  getSex() {
		// TODO Auto-generated method stub
		return String.valueOf(sex);
	}
}
