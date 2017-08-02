package com.example.maopao.cla;


public class Friend {

	private String name;
	private String description;
	private String phone; // ��¼�ֻ���
	private int sex; // �Ա�
	private String createDate;// ����ʱ��
	private String portrait;
	private boolean isFriend=false;
	private int id;

	public Friend(int id,String name, String description, String phone, int sex, String createDate, String portrait) {
		this.id=id;
		this.name = name;
		this.description = description;
		this.phone = phone;
		this.sex = sex;
		this.createDate = createDate;
		this.portrait = portrait;
	}

	public void setFriend()
	{
		this.isFriend=true;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPhone() {
		return phone;
	}

	public int getSex() {
		return sex;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getPortrait() {
		return portrait;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return isFriend;
	}
}
