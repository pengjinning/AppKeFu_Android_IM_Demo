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

public class GroupCreateActivity extends Activity implements OnClickListener{

	private Button mBackBtn;
	private EditText mMUCNameEdit;
	
	private EditText mMUCDescriptionEdit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_muc);
		
		mBackBtn = (Button)findViewById(R.id.add_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mMUCNameEdit = (EditText)findViewById(R.id.new_muc_name);
		mMUCNameEdit.setOnClickListener(this);
		
		mMUCDescriptionEdit = (EditText)findViewById(R.id.new_muc_description);
		mMUCDescriptionEdit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch(viewId) {
		case R.id.add_reback_btn:
			finish();
			break;
		case R.id.create_btn:
			createGroup();
			break;
		default:
			break;
		}
	}

	private void createGroup()
	{
		new AlertDialog.Builder(this)
		.setMessage("确认要创建群?")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{			
						String roomName = mMUCNameEdit.getText().toString();
						String roomDescription = mMUCDescriptionEdit.getText().toString();
						
						KFIMInterfaces.createMUCRoom(GroupCreateActivity.this, roomName, roomDescription);
					}
				})
				.setNegativeButton("取消", null)
				.create()
				.show();
	}
	
}
















