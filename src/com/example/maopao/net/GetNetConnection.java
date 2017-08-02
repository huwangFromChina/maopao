package com.example.maopao.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class GetNetConnection {

	public GetNetConnection(final String path,  final SuccessCallback successCallback,
			final FailCallback failCallback) {
		new AsyncTask<Void, Void, String>() {
			@Override
			public String doInBackground(Void... arg0) {
				HttpURLConnection connection=null;
				try{
					URL url=new URL(path);
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(3000);
					connection.setReadTimeout(3000);
					connection.setDoInput(true);
					connection.setDoOutput(true);
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null)
					{
						response.append(line);
					}
					return String.valueOf((response));
				}
				catch(Exception e)
				{
				}
				finally
				{
					if(connection!=null)
						connection.disconnect();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {// 运行在UI线程
				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFail();
					}
				}
				super.onPostExecute(result);
			}

		}.execute();
	}


	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}
}
