package com.example.maopao.net;

import java.util.Map;


public class SubmitMessage {

	public SubmitMessage(String path, Map<String, String> params) {
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

}
