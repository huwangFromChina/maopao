package com.example.maopao.net;

import java.util.ArrayList;
import java.util.Map;

import com.example.maopao.cla.Friend;

public class GetFriends {
	private ArrayList<Friend> friends = new ArrayList<Friend>();

	public GetFriends(String path, Map<String, String> params) {
		new NetConnection(path, params, new NetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub

			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				// TODO Auto-generated method stub

			}

		});
	}

	public ArrayList<Friend> getList() {
		// TODO Auto-generated method stub
		return friends;
	}

}
