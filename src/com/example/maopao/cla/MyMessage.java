package com.example.maopao.cla;

public class MyMessage {

	private String message;
	private int tag;
	private int mark;

	public MyMessage(String message, int tag) {
		this.message = message;
		this.tag = tag;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getMark() {
		return mark;
	}

	public String getMessage() {
		return message;
	}

	public int getTag() {
		return tag;
	}
}
