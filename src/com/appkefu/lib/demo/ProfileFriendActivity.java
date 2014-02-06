package com.appkefu.lib.demo;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.ui.entity.KFVCardEntity;
import com.appkefu.lib.utils.KFImageUtils;

import android.os.Bundle;
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

/**
 * 
 * @author Administrator
 *
 */
public class ProfileFriendActivity extends Activity implements OnClickListener{
	
	private KFVCardEntity vcardEntity;
	
	private TextView user_profile_title;
	
	private TextView personal_company_detail;
	private TextView personal_nickname_detail;
	private TextView personal_job_detail;
	private TextView personal_signature_detail;
	
	private ImageView personal_head_imageview;
	
	private Button add_friends_reback_btn;
	private Button make_it_black;
	private Button make_a_friend;
	
	private String chatName;
	
	private boolean isSubscribed;
	private boolean isBlocked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_friend);
		
		chatName = getIntent().getStringExtra("chatName");
		vcardEntity = KFIMInterfaces.getVCardOf(chatName);
		
		user_profile_title = (TextView)findViewById(R.id.user_profile_title);
		user_profile_title.setText(chatName);
		
		personal_nickname_detail = (TextView)findViewById(R.id.personal_nickname_detail);	
		personal_nickname_detail.setText(vcardEntity.getNickname());
		
		personal_company_detail = (TextView)findViewById(R.id.personal_company_detail);
		personal_company_detail.setText(vcardEntity.getCompany());
		
		personal_job_detail = (TextView)findViewById(R.id.personal_job_detail);	
		personal_job_detail.setText(vcardEntity.getJob());
		
		personal_signature_detail = (TextView)findViewById(R.id.personal_signature_detail);
		personal_signature_detail.setText(vcardEntity.getSignature());
		
		personal_head_imageview = (ImageView)findViewById(R.id.personal_head_imageview);
		
		add_friends_reback_btn = (Button)findViewById(R.id.add_friends_reback_btn);
		add_friends_reback_btn.setOnClickListener(this);
		
		make_it_black = (Button)findViewById(R.id.make_it_black);	
		make_it_black.setOnClickListener(this);
		
		make_a_friend = (Button)findViewById(R.id.make_a_friend);
		make_a_friend.setOnClickListener(this);
		
		isSubscribed = false;
		isBlocked = false;
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		IntentFilter intentFilter = new IntentFilter();
		//获取好友个人资料结果
        //intentFilter.addAction(KFMainService.ACTION_IM_GET_FRIEND_VCARD_FIELD_RESULT);
        //获取好友头像结果
        intentFilter.addAction(KFMainService.ACTION_IM_GET_FRIEND_AVATAR_RESULT);
        //获取添加好友结果
        intentFilter.addAction(KFMainService.ACTION_IM_IS_FOLLOWED_RESULT);
        //获取拉黑结果
        intentFilter.addAction(KFMainService.ACTION_IM_IS_BLOCKED_RESULT);
        //
        registerReceiver(mXmppreceiver, intentFilter);        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//获取好友公司名，其中"COMPANY"可自行定义，只需要与mXmppreceiver中的key相匹配即可
		//KFIMInterfaces.getFriendVCardField(chatName, "COMPANY", this);
		//获取好友工作职位，同上
		//KFIMInterfaces.getFriendVCardField(chatName, "JOB", this);
		//获取好友个性签名
		//KFIMInterfaces.getFriendVCardField(chatName, "SIGNATURE", this);
		//获取昵称
		//KFIMInterfaces.getFriendVCardField(chatName, "NICKNAME", this);
		//判断好友是否已经存在黑名单中
		KFIMInterfaces.isBlocked(chatName, this);
		//判断好友是否已经为好友，或者已经发送好友请求
		KFIMInterfaces.isFollowed(chatName, this);
		//获取好友头像本地地址
		KFIMInterfaces.getFriendHeadImage(chatName, this);

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
            /*
            if (action.equals(KFMainService.ACTION_IM_GET_FRIEND_VCARD_FIELD_RESULT)) 
            {
            	String key = intent.getStringExtra("key");
            	String value = intent.getStringExtra("value");
            	
            	if("COMPANY".equals(key))
            	{
            		personal_company_detail.setText(value);
            	}
            	else if("JOB".equals(key))
            	{
            		personal_job_detail.setText(value);
            	}
            	else if("SIGNATURE".equals(key))
            	{
            		personal_signature_detail.setText(value);
            	}
            	else if("NICKNAME".equals(key))
            	{
            		personal_nickname_detail.setText(value);
            	}
            	
            }
            else */
            if(action.equals(KFMainService.ACTION_IM_GET_FRIEND_AVATAR_RESULT))
            {
            	String avatarPath = intent.getStringExtra("path");
        		
        		Bitmap bitmap = KFImageUtils.loadImgThumbnail(avatarPath, 100, 100);
        		if(bitmap != null)
        			personal_head_imageview.setImageBitmap(bitmap);
            }
            else if(action.equals(KFMainService.ACTION_IM_IS_FOLLOWED_RESULT))
            {
            	isSubscribed = intent.getBooleanExtra("result", false);
            	
        		if(isSubscribed)
        		{
        			make_a_friend.setText("取消好友");
        		}
        		else
        			make_a_friend.setText("添加好友");
            }
            else if(action.equals(KFMainService.ACTION_IM_IS_BLOCKED_RESULT))
            {
            	isBlocked = intent.getBooleanExtra("result", false);
            	
        		if(isBlocked)
        		{
        			make_it_black.setText("取消黑名单");
        		}
        		else
        			make_it_black.setText("拉黑");
        		
            }
        }
    };

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch(viewId)
		{
		case R.id.add_friends_reback_btn:
			finish();
			break;
		case R.id.make_it_black:
			make_black();
			break;
		case R.id.make_a_friend:
			add_friend();
			break;
		default:
			break;
		}
	}
	
	public void make_black()
	{
		String blackTitle = "";
		if(isBlocked)
		{
			blackTitle = "确定要解除黑名单?";
		}
		else
		{
			blackTitle = "确认要将其加入黑名单?";
		}
		
		new AlertDialog.Builder(this)
		.setMessage(blackTitle)
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {						
						
						if(isBlocked)
						{
							KFIMInterfaces.unblockUser(chatName, ProfileFriendActivity.this);
						}
						else
						{
							KFIMInterfaces.blockUser(chatName, ProfileFriendActivity.this);
						}
						
						refresh();
					}
				})
				.setNegativeButton("取消", null).create()
					.show();
	}
	
	public void add_friend()
	{
		String addTitle = "";
		if(isSubscribed)
		{
			addTitle = "确定要解除好友关系?";
		}
		else
		{
			addTitle = "确认要添加其为好友?";
		}
		
		new AlertDialog.Builder(this)
		.setMessage(addTitle)
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {						
						
						if(isSubscribed)
						{
							KFIMInterfaces.removeFriend(chatName, ProfileFriendActivity.this);
						}
						else
						{
							KFIMInterfaces.addFriend(chatName, "设置昵称", ProfileFriendActivity.this);
						}
						
						refresh();
					}
				})
				.setNegativeButton("取消", null).create()
		.show();
		
		 
	}
	
	public void refresh ()
	{
	    Intent intent = getIntent();
	    intent.putExtra("chatName", chatName);
	    finish();
	    startActivity(intent);
	}
	
}


















