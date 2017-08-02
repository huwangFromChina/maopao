package com.example.maopao.cla;

public class MenuOption {
	private String option;
	private int imageId;

	public MenuOption(String option, int imageId) {
		this.option = option;
		this.imageId = imageId;
	}

	public String getOption() {
		return option;
	}

	public int getImageId() {
		return imageId;
	}
}
