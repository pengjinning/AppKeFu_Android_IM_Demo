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
		//��ȡ���Ѹ������Ͻ��
        //intentFilter.addAction(KFMainService.ACTION_IM_GET_FRIEND_VCARD_FIELD_RESULT);
        //��ȡ����ͷ����
        intentFilter.addAction(KFMainService.ACTION_IM_GET_FRIEND_AVATAR_RESULT);
        //��ȡ��Ӻ��ѽ��
        intentFilter.addAction(KFMainService.ACTION_IM_IS_FOLLOWED_RESULT);
        //��ȡ���ڽ��
        intentFilter.addAction(KFMainService.ACTION_IM_IS_BLOCKED_RESULT);
        //
        registerReceiver(mXmppreceiver, intentFilter);        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//��ȡ���ѹ�˾��������"COMPANY"�����ж��壬ֻ��Ҫ��mXmppreceiver�е�key��ƥ�伴��
		//KFIMInterfaces.getFriendVCardField(chatName, "COMPANY", this);
		//��ȡ���ѹ���ְλ��ͬ��
		//KFIMInterfaces.getFriendVCardField(chatName, "JOB", this);
		//��ȡ���Ѹ���ǩ��
		//KFIMInterfaces.getFriendVCardField(chatName, "SIGNATURE", this);
		//��ȡ�ǳ�
		//KFIMInterfaces.getFriendVCardField(chatName, "NICKNAME", this);
		//�жϺ����Ƿ��Ѿ����ں�������
		KFIMInterfaces.isBlocked(chatName, this);
		//�жϺ����Ƿ��Ѿ�Ϊ���ѣ������Ѿ����ͺ�������
		KFIMInterfaces.isFollowed(chatName, this);
		//��ȡ����ͷ�񱾵ص�ַ
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
        			make_a_friend.setText("ȡ������");
        		}
        		else
        			make_a_friend.setText("��Ӻ���");
            }
            else if(action.equals(KFMainService.ACTION_IM_IS_BLOCKED_RESULT))
            {
            	isBlocked = intent.getBooleanExtra("result", false);
            	
        		if(isBlocked)
        		{
        			make_it_black.setText("ȡ��������");
        		}
        		else
        			make_it_black.setText("����");
        		
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
			blackTitle = "ȷ��Ҫ���������?";
		}
		else
		{
			blackTitle = "ȷ��Ҫ������������?";
		}
		
		new AlertDialog.Builder(this)
		.setMessage(blackTitle)
		.setPositiveButton("ȷ��",
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
				.setNegativeButton("ȡ��", null).create()
					.show();
	}
	
	public void add_friend()
	{
		String addTitle = "";
		if(isSubscribed)
		{
			addTitle = "ȷ��Ҫ������ѹ�ϵ?";
		}
		else
		{
			addTitle = "ȷ��Ҫ�����Ϊ����?";
		}
		
		new AlertDialog.Builder(this)
		.setMessage(addTitle)
		.setPositiveButton("ȷ��",
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
							KFIMInterfaces.addFriend(chatName, "�����ǳ�", ProfileFriendActivity.this);
						}
						
						refresh();
					}
				})
				.setNegativeButton("ȡ��", null).create()
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


















