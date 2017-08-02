package com.example.maopao.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.example.maopao.net.NetConnection.FailCallback;
import com.example.maopao.net.NetConnection.SuccessCallback;

import android.os.AsyncTask;
import android.util.Log;

public class UploadUtil {
	private static UploadUtil uploadUtil;
	private static final String BOUNDARY = UUID.randomUUID().toString();

	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data";

	public UploadUtil()
	{
	}


	private static final String TAG = "UploadUtil";
	private int readTimeOut = 10 * 1000;
	private int connectTimeout = 10 * 1000;

	private static int requestTime = 0;

	private static final String CHARSET = "utf-8";

	public static final int UPLOAD_SUCCESS_CODE = 1;

	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;

	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	protected static final int WHAT_TO_UPLOAD = 1;
	protected static final int WHAT_UPLOAD_DONE = 2;

	
	

	public void uploadFile(String filePath, final String fileKey, final String RequestURL, final Map<String, String> param,
			final SuccessCallback successCallback,final FailCallback failCallback) {
		if (filePath == null) {
			return ;
		}
		try {
			final File file = new File(filePath);
			if (file == null || (!file.exists())) {
				return;
			}
			new AsyncTask<Void, Void, String>() {
				private int requestTime;

				@Override
				public String doInBackground(Void... arg0) {
					String result = null;
					requestTime = 0;

					long requestTime = System.currentTimeMillis();
					long responseTime = 0;

					try {
						URL url = new URL(RequestURL);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setReadTimeout(readTimeOut);
						conn.setConnectTimeout(connectTimeout);
						conn.setDoInput(true); 
						conn.setDoOutput(true); 
						conn.setUseCaches(false); 
						conn.setRequestMethod("POST"); 
						conn.setRequestProperty("Charset", CHARSET); 
						conn.setRequestProperty("connection", "keep-alive");
						conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
						conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

						/**
						 * 当文件不为空，把文件包装并且上传
						 */
						DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
						StringBuffer sb = null;
						String params = "";

						/***
						 * 以下是用于上传参�?
						 */
						if (param != null && param.size() > 0) {
							Iterator<String> it = param.keySet().iterator();
							while (it.hasNext()) {
								sb = null;
								sb = new StringBuffer();
								String key = it.next();
								String value = param.get(key);
								sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
								sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END)
										.append(LINE_END);
								sb.append(value).append(LINE_END);
								params = sb.toString();
								Log.i(TAG, key + "=" + params + "##");
								dos.write(params.getBytes());
								// dos.flush();
							}
						}

						sb = null;
						params = null;
						sb = new StringBuffer();
						sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
						sb.append("Content-Disposition:form-data; name=\"" + fileKey + "\"; filename=\"" + file.getName() + "\""
								+ LINE_END);
						sb.append("Content-Type:image/pjpeg" + LINE_END); 
																			
						sb.append(LINE_END);
						params = sb.toString();
						sb = null;

						Log.i(TAG, file.getName() + "=" + params + "##");
						dos.write(params.getBytes());
						/** 上传文件 */
						InputStream is = new FileInputStream(file);
						byte[] bytes = new byte[1024];
						int len = 0;
						int curLen = 0;
						while ((len = is.read(bytes)) != -1) {
							curLen = len;
							dos.write(bytes, 0, len);
						}
						is.close();

						dos.write(LINE_END.getBytes());
						byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
						dos.write(end_data);
						dos.flush();
						//
						// dos.write(tempOutputStream.toByteArray());
						/**
						 * 获取响应�? 200=成功 当响应成功，获取响应的流
						 */
						int res = conn.getResponseCode();
						responseTime = System.currentTimeMillis();
						this.requestTime = (int) ((responseTime - requestTime) / 1000);
						if (res == 200) {
							InputStream input = conn.getInputStream();
							StringBuffer sb1 = new StringBuffer();
							int ss;
							while ((ss = input.read()) != -1) {
								sb1.append((char) ss);
							}
							result = sb1.toString();
							return result;
						} else {
							return null;
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
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
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}

}
