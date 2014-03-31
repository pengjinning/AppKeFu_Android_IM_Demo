package com.appkefu.lib.demo.activity;


import java.util.ArrayList;

import com.appkefu.lib.demo.R;
import com.appkefu.lib.demo.adapter.GroupChatAdapter;
import com.appkefu.lib.demo.entity.ApiEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Administrator
 *
 */
public class GroupChatActivity extends Activity implements OnClickListener {

	private Button mBackBtn;
	
	private ListView mApiListView;
	private ArrayList<ApiEntity> mApiArray;
	private GroupChatAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_chat);
		
		mBackBtn = (Button) findViewById(R.id.groupchat_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		initView();
	}
	
	private void initView() {
		
		mApiListView = (ListView)findViewById(R.id.group_api_list_view);
		mApiArray = new ArrayList<ApiEntity>();
		
		mAdapter = new GroupChatAdapter(this,  mApiArray);
		mApiListView.setAdapter(mAdapter);
		
		ApiEntity entity = new ApiEntity(10001, "创建群");
			mApiArray.add(entity);
		entity = new ApiEntity(10002, "加入群");
			mApiArray.add(entity);
			/*
		entity = new ApiEntity(10003, "邀请加入群");
			mApiArray.add(entity);
		entity = new ApiEntity(10004, "群验证消息");
			mApiArray.add(entity);
		entity = new ApiEntity(10005, "群成员");
			mApiArray.add(entity);
		entity = new ApiEntity(10006, "加入的群");
			mApiArray.add(entity);
		entity = new ApiEntity(10007, "退出群");
		 	mApiArray.add(entity);
		 	*/
			
		mAdapter.notifyDataSetChanged();
			
		mApiListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				// TODO Auto-generated method stub
					
				ApiEntity entity = mApiArray.get(index);
				Log.d("GroupChatActivity", "OnItemClickListener:"+entity.getApiName());
					
				Intent intent;
				switch(entity.getId()) {
				case 10001:
					intent = new Intent(GroupChatActivity.this, GroupCreateActivity.class);
					startActivity(intent);
					break;
				case 10002:
					intent = new Intent(GroupChatActivity.this, GroupJoinActivity.class);
					startActivity(intent);
					break;
					/*
				case 10003:
					intent = new Intent(GroupChatActivity.this, GroupInviteActivity.class);
					startActivity(intent);
					break;
				case 10004:
					intent = new Intent(GroupChatActivity.this, GroupNotificationActivity.class);
					startActivity(intent);
					break;
				case 10005:
					intent = new Intent(GroupChatActivity.this, GroupMembersActivity.class);
					startActivity(intent);
					break;
				case 10006:
					intent = new Intent(GroupChatActivity.this, GroupJoinedActivity.class);
					startActivity(intent);
					break;
				case 10007:
					intent = new Intent(GroupChatActivity.this, GroupExitActivity.class);
					startActivity(intent);
					break;
					*/
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.groupchat_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}

	
}
























