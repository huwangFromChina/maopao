package com.example.maopao.aty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maopao.R;
import com.example.maopao.cla.JsonAnalysis;
import com.example.maopao.cla.Url;
import com.example.maopao.cla.User;
import com.example.maopao.data.SQL;
import com.example.maopao.net.NetConnection;
import com.example.maopao.net.UploadUtil;

public class Consummate extends Activity {
	private EditText email;
	private EditText signature;
	private RadioGroup sex;
	private EditText age;
	private Button certain_bt;
	private ImageView portrait;
	private boolean flag = true;
	private File photoFile;
	private String path;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.consummate);
		signature = (EditText) findViewById(R.id.signature);
		sex = (RadioGroup) findViewById(R.id.radioGroup);
		certain_bt = (Button) findViewById(R.id.certain_bt);
		certain_bt.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag=true;
				if (judge() == true) {
					// 提交信息
					final ProgressDialog pd = ProgressDialog.show(Consummate.this, "提交数据中", "wait...");
					pd.show();
					User.path=path;
					Map<String, String> param = new HashMap<String, String>();
					param.put("id", User.id);				
					new UploadUtil().uploadFile(path, "huwang", Url.sendFile, param,new UploadUtil.SuccessCallback() {

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							//Toast.makeText(Consummate.this, result, Toast.LENGTH_SHORT).show();
							
							
							Map<String, String> user = new HashMap<String, String>();
							user.put("name", User.name);
							user.put("sex", "1");
							user.put("id", User.id);
							user.put("description", signature.getText().toString());
							User.description=signature.getText().toString();
							User.sex=1;
							new NetConnection(Url.consummate, user, new NetConnection.SuccessCallback() {

								@Override
								public void onSuccess(String result) {
									// TODO Auto-generated method stub
									pd.dismiss();
									//TextView test=(TextView)findViewById(R.id.sex_text);
									//test.setText(result);
									String res=JsonAnalysis.getConsummate(result);
								    if(res.equals("0"))
								    {
										Toast.makeText(Consummate.this, "修改失败", Toast.LENGTH_SHORT).show();
								    	return;
								    }
									
									Intent intent = new Intent("com.example.maopao.call2");
									LocalBroadcastManager.getInstance(Consummate.this).sendBroadcast(intent);
									Toast.makeText(getBaseContext(), "完善成功", Toast.LENGTH_SHORT).show();
									finish();
								}
							}, new NetConnection.FailCallback() {
								@Override
								public void onFail() {
									// TODO Auto-generated method stub

									pd.dismiss();
									Toast.makeText(getBaseContext(), "完善失败", Toast.LENGTH_SHORT).show();
									return;
								}
							});
						}
					}, new UploadUtil.FailCallback() {
						@Override
						public void onFail() {
							// TODO Auto-generated method stu
							pd.dismiss();
							Toast.makeText(getBaseContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
						}

					});
				}
			}
			
		});
		portrait = (ImageView) findViewById(R.id.consummate_portrait);
		portrait.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Consummate.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("选择一个选项");
				// 指定下拉列表的显示数据
				final String[] cities = { "本地", "拍照" };
				// 设置一个下拉的列表选择项
				builder.setItems(cities, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Intent intent = new Intent();
							/* 开启Pictures画面Type设定为image */
							intent.setType("image/*");
							/* 使用Intent.ACTION_GET_CONTENT这个Action */
							intent.setAction(Intent.ACTION_GET_CONTENT);
							/* 取得相片后返回本画面 */
							startActivityForResult(intent, 1);
						} else {
							photoFile = new File(Environment.getExternalStorageDirectory() + "/my_camera/me.jpg");
							path = Environment.getExternalStorageDirectory() + "/my_camera/me.jpg";
							if (!photoFile.getParentFile().exists()) {
								photoFile.getParentFile().mkdirs();
							}
							Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
							startActivityForResult(intent2, 2);
						}
					}
				});
				builder.show();
			}
			
		});

	}

	private boolean judge() {
		 if (signature.getText().toString().equals("")) {
			Toast.makeText(Consummate.this, "个性签名不能为空", Toast.LENGTH_SHORT).show();
			flag = false;
		} else if (signature.getText().toString().equals("")) {
			Toast.makeText(Consummate.this, "年龄不能为空", Toast.LENGTH_SHORT).show();
			flag = false;
		}
		return flag;

	}

	public void postInformation() {
		// 提交信息
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Bitmap bitmap = null;
			if (requestCode == 1) {
				Uri uri = data.getData();
				Log.d("home", "000" + uri.toString());
				ContentResolver cr = this.getContentResolver();
				try {
					bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] proj = { MediaStore.Images.Media.DATA };

				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(uri, proj, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				path = cursor.getString(column_index);
			} else {
				bitmap = BitmapFactory.decodeFile(photoFile.getPath());
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] b = baos.toByteArray();
			// 将字节换成KB
			double mid = b.length / 1024;
			// 判断bitmap占用空间是否大于允许最大空间
			if (mid > 200) {
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				Matrix matrix = new Matrix();
				matrix.postScale((float) (1 / Math.sqrt(mid / 200)), (float) (1 / Math.sqrt(mid / 200)));
				Bitmap new_bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				portrait.setImageBitmap(new_bitmap);
				Log.d("home", "222210s");
			} else {
				portrait.setImageBitmap(bitmap);
				Log.d("home", "22221");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
