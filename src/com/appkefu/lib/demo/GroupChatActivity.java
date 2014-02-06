package com.appkefu.lib.demo;


import com.appkefu.lib.ui.activity.KFMUCChatActivity;
import com.appkefu.lib.xmpp.XmppMuc;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Administrator
 *
 */
public class GroupChatActivity extends Activity implements OnClickListener {

	private Button mBackBtn;
	
	//private Button mCreateGroupBtn;
	private Button mJoinGroupBtn;
	//private Button mExitGroupBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_chat);
		
		mBackBtn = (Button) findViewById(R.id.groupchat_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		//mCreateGroupBtn = (Button) findViewById(R.id.create_room);
		//mCreateGroupBtn.setOnClickListener(this);
		
		mJoinGroupBtn = (Button) findViewById(R.id.join_room);
		mJoinGroupBtn.setOnClickListener(this);
		
		//mExitGroupBtn = (Button) findViewById(R.id.exit_room);
		//mExitGroupBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.groupchat_reback_btn:
			finish();
			break;
		//case R.id.create_room:
		//	createRoom();
		//	break;
		case R.id.join_room:
			joinRoom();
			break;
		//case R.id.exit_room:
		//	exitRoom();
		//	break;
		default:
			break;
		}
	}
	
	//private void createRoom() {
	//}
	
	private void joinRoom() {
		
		XmppMuc.joinRoom("appkefu_product");
		
		Intent intent = new Intent(this, KFMUCChatActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("groupjid", "appkefu_product@conference.appkefu.com");
		startActivity(intent);
	}
	
	//private void exitRoom() {
	//}

}
























