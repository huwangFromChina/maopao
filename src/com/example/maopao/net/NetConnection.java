package com.example.maopao.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.os.AsyncTask;

public class NetConnection {

	public NetConnection(final String path, final Map<String, String> params, final SuccessCallback successCallback,
			final FailCallback failCallback) {
		new AsyncTask<Void, Void, String>() {
			@Override
			public String doInBackground(Void... arg0) {
				URL url = null;
				try {
					url = new URL(path);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				StringBuffer stringBuffer = new StringBuffer();
				try {
					for (Map.Entry<String, String> entry : params.entrySet()) {
						stringBuffer.append(entry.getKey()).append("=")
								.append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
					}
					stringBuffer.deleteCharAt(stringBuffer.length() - 1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				try {
					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setConnectTimeout(3000);
					urlConnection.setRequestMethod("POST"); // 以post请求方式提交
					urlConnection.setDoInput(true); // 读取数据
					urlConnection.setDoOutput(true); // 向服务器写数据
					// 获取上传信息的大小和长度
					byte[] myData = stringBuffer.toString().getBytes();
					// 设置请求体的类型是文本类型,表示当前提交的是文本数据
					urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					urlConnection.setRequestProperty("Content-Length", String.valueOf(myData.length));
					urlConnection.setRequestProperty("Charset", "UTF-8");
					// 获得输出流，向服务器输出内容
					OutputStream outputStream = urlConnection.getOutputStream();
					// 写入数据
					outputStream.write(myData, 0, myData.length);
					outputStream.flush();
					outputStream.close();
					// 获得服务器响应结果和状态码
					// 取回响应的结果
					return changeInputStream(urlConnection.getInputStream(), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
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

	private static String changeInputStream(InputStream inputStream, String encode) {

		// 内存流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = null;
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					byteArrayOutputStream.write(data, 0, len);
				}
				result = new String(byteArrayOutputStream.toByteArray(), encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}
}
