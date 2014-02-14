package com.appkefu.lib.demo.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.appkefu.lib.demo.R;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.ui.entity.KFVCardEntity;
import com.appkefu.lib.utils.KFFileUtils;
import com.appkefu.lib.utils.KFImageUtils;
import com.appkefu.lib.utils.KFMediaUtils;
import com.appkefu.lib.utils.KFSLog;
import com.appkefu.lib.utils.KFUtils;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 *
 */
public class ProfileActivity extends Activity implements OnClickListener{

	private KFVCardEntity vcardEntity;
	
	private TextView personal_company_detail;
	private TextView personal_username_detail;
	private TextView personal_nickname_detail;
	private TextView personal_job_detail;
	private TextView personal_signature_detail;
	
	private ImageView personal_head_imageview;
	
	private String imagePath;
	
	private Button mBackBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		vcardEntity = KFIMInterfaces.getVCard();
		personal_username_detail = (TextView)findViewById(R.id.personal_username_detail);	
		personal_username_detail.setText(KFSettingsManager.getSettingsManager(this).getUsername());

		personal_nickname_detail = (TextView)findViewById(R.id.personal_nickname_detail);
		personal_nickname_detail.setText(vcardEntity.getNickname());
		
		personal_company_detail = (TextView)findViewById(R.id.personal_company_detail);	
		personal_company_detail.setText(vcardEntity.getCompany());
		
		personal_job_detail = (TextView)findViewById(R.id.personal_job_detail);	
		personal_job_detail.setText(vcardEntity.getJob());
		
		personal_signature_detail = (TextView)findViewById(R.id.personal_signature_detail);
		personal_signature_detail.setText(vcardEntity.getSignature());
		
		personal_head_imageview = (ImageView)findViewById(R.id.personal_head_imageview);
		
		mBackBtn = (Button) findViewById(R.id.profile_reback_btn);
		mBackBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.profile_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}

	public void change_avatar(View v) 
	{
		CharSequence[] items = { "手机相册", "手机拍照" };
		imageChooseItem(items);
	}
	
	public void change_nickname(View v)
	{
		Intent intent = new Intent(this, ProfileChangeActivity.class);
		intent.putExtra("profileField", "NICKNAME");
		intent.putExtra("value", vcardEntity.getNickname());
		startActivity(intent);
	}
	
	public void change_company(View v)
	{
		Intent intent = new Intent(this, ProfileChangeActivity.class);
		intent.putExtra("profileField", "COMPANY");
		intent.putExtra("value", vcardEntity.getCompany());
		startActivity(intent);
	}
	
	public void change_job(View v)
	{
		Intent intent = new Intent(this, ProfileChangeActivity.class);
		intent.putExtra("profileField", "JOB");
		intent.putExtra("value", vcardEntity.getJob());
		startActivity(intent);
	}
	
	public void change_signature(View v)
	{
		Intent intent = new Intent(this, ProfileChangeActivity.class);
		intent.putExtra("profileField", "SIGNATURE");
		intent.putExtra("value", vcardEntity.getSignature());
		startActivity(intent);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KFMainService.ACTION_IM_GET_AVATAR_RESULT);
        registerReceiver(mXmppreceiver, intentFilter);        
	}
	
	@Override
	protected void onStop() {
		super.onStop();

        unregisterReceiver(mXmppreceiver);
	}
	
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();

            if(action.equals(KFMainService.ACTION_IM_GET_AVATAR_RESULT))
            {
            	String path = intent.getStringExtra("path");
            	
            	Bitmap bitmap = KFImageUtils.loadImgThumbnail(path, 100, 100);
        		if(bitmap != null)
        			personal_head_imageview.setImageBitmap(bitmap);
        		
            }
        }
    };
    
	@Override
	protected void onResume() {
		super.onResume();	
		KFIMInterfaces.getHeadImage(this);
	}
	
	public void imageChooseItem(CharSequence[] items) {
		AlertDialog imageDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.appkefu_set_headview)
				.setIcon(android.R.drawable.btn_star)
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 手机选图
						if (item == 0) {
							Intent intent = new Intent(
									Intent.ACTION_GET_CONTENT);
							intent.addCategory(Intent.CATEGORY_OPENABLE);
							intent.setType("image/*");
							startActivityForResult(
									Intent.createChooser(intent, "选择图片"),
									KFImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
						}
						// 拍照
						else if (item == 1) {
							String savePath = "";
							// 判断是否挂载了SD卡
							String storageState = Environment
									.getExternalStorageState();
							if (storageState.equals(Environment.MEDIA_MOUNTED)) {
								savePath = Environment
										.getExternalStorageDirectory()
										.getAbsolutePath()
										+ "/AppKeFu/Camera/";// 存放照片的文件夹
								File savedir = new File(savePath);
								if (!savedir.exists()) {
									savedir.mkdirs();
								}
							}

							// 没有挂载SD卡，无法保存文件
							if (KFUtils.isEmpty(savePath)) {
								Toast.makeText(ProfileActivity.this,
										"无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT)
										.show();
								return;
							}

							String timeStamp = new SimpleDateFormat(
									"yyyyMMddHHmmss").format(new Date());
							String fileName = "appkefu_" + timeStamp + ".jpg";// 照片命名
							File out = new File(savePath, fileName);
							Uri uri = Uri.fromFile(out);

							imagePath = savePath + fileName;// 该照片的绝对路径

							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
							startActivityForResult(intent,
									KFImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
						}
					}
				}).create();

		imageDialog.show();
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {

		if (requestCode == 1) {
			
		} else {
			KFSLog.d("uploadPicture result returned");

			if (resultCode != RESULT_OK)
				return;
			
			if (requestCode == KFImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD) {
				KFSLog.d("pick picture");
				if (data == null)
					return;

				Uri thisUri = data.getData();
				String filePath = KFImageUtils
						.getAbsolutePathFromNoStandardUri(thisUri);

				// 如果是标准Uri
				if (KFUtils.isEmpty(filePath)) {
					imagePath = KFImageUtils.getAbsoluteImagePath(
							ProfileActivity.this, thisUri);
				} else {
					imagePath = filePath;
				}

				String attFormat = KFFileUtils.getFileFormat(imagePath);
				if (!"photo".equals(KFMediaUtils
						.getContentType(attFormat))) {
					Toast.makeText(ProfileActivity.this,
							"图片格式错误",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			// 拍摄图片
			else if (requestCode == KFImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {
				KFSLog.d("take picture");
			}
			
			//调用设置头像接口
			KFIMInterfaces.setHeadImage(this, imagePath);	
			
			//
			Bitmap bitmap = KFImageUtils.loadImgThumbnail(imagePath, 100, 100);
			if(bitmap != null)
				personal_head_imageview.setImageBitmap(bitmap);
		}
	}
	
}








