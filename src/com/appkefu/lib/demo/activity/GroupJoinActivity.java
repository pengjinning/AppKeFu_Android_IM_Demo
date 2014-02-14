package com.appkefu.lib.demo.activity;

import com.appkefu.lib.demo.R;
import com.appkefu.lib.interfaces.KFIMInterfaces;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class GroupJoinActivity extends Activity implements OnClickListener {

	private Button mBackBtn;
	private EditText mGroupNameEdit;
	private Button mJoinBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_join);
		
		mBackBtn = (Button)findViewById(R.id.add_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mGroupNameEdit = (EditText)findViewById(R.id.muc_name);
		mGroupNameEdit.setOnClickListener(this);
		
		mJoinBtn = (Button)findViewById(R.id.join_btn);
		mJoinBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch(viewId) {
		case R.id.add_reback_btn:
			finish();
			break;
		case R.id.join_btn:
			joinGroup();
			break;
		default:
			break;
		}
	}

	private void joinGroup()
	{
		new AlertDialog.Builder(this)
		.setMessage("确认要加入群?")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{			
						String groupName = mGroupNameEdit.getText().toString();
						KFIMInterfaces.joinMUCRoom(GroupJoinActivity.this, groupName);
					}
				}).setNegativeButton("取消", null).create()
		.show();
	}

}













