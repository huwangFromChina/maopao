package com.example.maopao.cla;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public  class JsonAnalysis {

	public static ArrayList<Friend> getFriends(String result)
	{
		
		try {
			JSONArray jsa=new JSONArray(result);
			ArrayList<Friend> seek_result=new ArrayList<Friend>();
			for(int i=0;i<jsa.length();i++)
			{
				JSONObject json=jsa.getJSONObject(i);
				Friend frind=new Friend(
						Integer.parseInt(json.getString("id")),
						json.getString("name"),
						json.getString("description"),
						null,//json.getString("phone"),
						Integer.parseInt(json.getString("sex")),
						json.getString("createDate"),
						json.getString("imagePath"));
				seek_result.add(frind);
			}
			return seek_result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Behavior> getBehaviors(String result)
	{
		ArrayList<Behavior> behavior=new ArrayList<Behavior>();
		try {
			JSONArray jsa=new JSONArray(result);
			for(int i=0;i<jsa.length();i++)
			{
				ArrayList<String> comment1=new ArrayList<String>();
				ArrayList<String> comment2=new ArrayList<String>();
				JSONObject json=(JSONObject)jsa.get(i);
				JSONObject userinfo=json.getJSONObject("user");
				if(!json.getString("comments").equals("null"))
				{
				JSONArray comment=json.getJSONArray("comments");
				for(int j=0;j<comment.length();j++)
				{
				JSONObject commen=(JSONObject)comment.get(j);
				JSONObject jsonName=commen.getJSONObject("user");
				comment1.add(commen.getString("content"));
				comment2.add(jsonName.getString("name"));
				}
				}
				Friend friend=new Friend(
						Integer.parseInt(userinfo.getString("id")),
						userinfo.getString("name"), 
						userinfo.getString("description"), 
						userinfo.getString("phone"), 
						Integer.parseInt(userinfo.getString("sex")), 
						userinfo.getString("createDate"),
						userinfo.getString("imagePath"));
				Behavior beha=new Behavior(
						Integer.parseInt(json.getString("id")),
						json.getString("activeMessage"),
						userinfo.getString("phone"),
						userinfo.getString("name"),
						userinfo.getString("imagePath"),
						friend,
						comment2,comment1);
						
				behavior.add(beha);
			}
			return behavior;
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}


	public static int getInt(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject json=new JSONObject(result);
			String tag=json.getString("result");
			return Integer.parseInt(tag);
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static ArrayList<Friend> getLocationInfor(String result) {
		// TODO Auto-generated method stub
		
		try {
			JSONArray jsa=new JSONArray(result);
			ArrayList<Friend> seek_result=new ArrayList<Friend>();
			for(int i=0;i<jsa.length();i++)
			{
				JSONObject json=jsa.getJSONObject(i);
				Friend frind=new Friend(
						Integer.parseInt(json.getString("id")),
						json.getString("name"),
						json.getString("description"),
						null,//json.getString("phone"),
						Integer.parseInt(json.getString("sex")),
						json.getString("distance"),    //createdata¿é´æ¾àÀë 
						json.getString("imagePath"));
				seek_result.add(frind);
			}
			return seek_result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getRegister(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject json=new JSONObject(result);
			String tag=json.getString("result");
			if(tag.equals("0"))
			{
				return json.getString("errorMessage");
			}
			JSONObject object=json.getJSONObject("attachObj");
			User.name=object.getString("name");
			User.id=object.getString("id");
			User.phone=object.getString("phone");
			User.password=object.getString("password");
			User.sex=Integer.parseInt(object.getString("sex"));
			User.description=object.getString("description");
			User.netPath=object.getString("imagePath");
			User.createDate=object.getString("createDate");
			return "1";
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0";
		}
		
	}

	public static int getLogin(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject object=new JSONObject(result);
			User.id=object.getString("id");
			User.phone=object.getString("phone");
			User.password=object.getString("password");
			User.sex=Integer.parseInt(object.getString("sex"));
			User.description=object.getString("description");
			User.name=object.getString("name");
			User.netPath=object.getString("imagePath");
			User.createDate=object.getString("createDate");
			return 1;
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static String getConsummate(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject json=new JSONObject(result);
			String tag=json.getString("result");
			if(tag.equals("0"))
			{
				return json.getString("errorMessage");
			}
			return "1";
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0";
		}
	}

	public static ArrayList<Friend> getNewFriends(String result) {
		// TODO Auto-generated method stub
		ArrayList<Friend> behavior=new ArrayList<Friend>();
		try {
			JSONArray jsa=new JSONArray(result);
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
						
				behavior.add(friend);
			}
			return behavior;
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	

}
