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
					urlConnection.setRequestMethod("POST"); // ��post����ʽ�ύ
					urlConnection.setDoInput(true); // ��ȡ����
					urlConnection.setDoOutput(true); // �������д����
					// ��ȡ�ϴ���Ϣ�Ĵ�С�ͳ���
					byte[] myData = stringBuffer.toString().getBytes();
					// ������������������ı�����,��ʾ��ǰ�ύ�����ı�����
					urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					urlConnection.setRequestProperty("Content-Length", String.valueOf(myData.length));
					urlConnection.setRequestProperty("Charset", "UTF-8");
					// ������������������������
					OutputStream outputStream = urlConnection.getOutputStream();
					// д������
					outputStream.write(myData, 0, myData.length);
					outputStream.flush();
					outputStream.close();
					// ��÷�������Ӧ�����״̬��
					// ȡ����Ӧ�Ľ��
					return changeInputStream(urlConnection.getInputStream(), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {// ������UI�߳�
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

		// �ڴ���
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
