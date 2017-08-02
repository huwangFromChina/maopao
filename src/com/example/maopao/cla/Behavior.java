package com.example.maopao.cla;

import java.util.ArrayList;

public class Behavior{
	private String name;
	private String content;
	private String portrait;
	private int id;
	private String phone;
	private ArrayList<String> comment_name;
	private ArrayList<String> comment_content;
	private Friend friend;

	public Behavior(int id, String content, String phone, String name, String portrait,Friend friend,
			ArrayList<String> comment_name,
			ArrayList<String> comment_content) {
		this.id=id;
		this.phone = phone;
		this.name = name;
		this.portrait = portrait;
		this.content = content;
		this.comment_content = comment_content;
		this.comment_name = comment_name;
		this.friend=friend;
	}

	public int getId()
	{
		return id;
	}

	public Friend getf()
	{
		return friend;
	}
	public String getPhone() {
		return phone;
	}

	public String getName() {
		return name;
	}

	public String getPortrait() {
		return portrait;
	}

	public String getContent() {
		return content;
	}


	public ArrayList<String> getCommentName() {
		return comment_name;
	}

	public ArrayList<String> getCommentContent() {
		return comment_content;
	}
}
