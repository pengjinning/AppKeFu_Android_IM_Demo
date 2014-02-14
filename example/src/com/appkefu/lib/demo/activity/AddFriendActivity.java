package com.appkefu.lib.demo.activity;

import com.appkefu.lib.demo.R;
import com.appkefu.lib.interfaces.KFIMInterfaces;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 *
 */
public class AddFriendActivity extends Activity implements OnClickListener{
	
	private EditText mFriendUsername; // 帐号编辑框

	private Button mAddFriendBtn;
	private Button mBackBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
		mFriendUsername = (EditText) findViewById(R.id.add_user_edit);
		
		mAddFriendBtn = (Button) findViewById(R.id.add_btn);
		mAddFriendBtn.setOnClickListener(this);
		
		mBackBtn = (Button) findViewById(R.id.add_reback_btn);
		mBackBtn.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.add_btn:
			add_friend();
			break;
		case R.id.add_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	public void add_friend() 
	{
		String friendUsername = mFriendUsername.getText().toString();
		if ("".equals(friendUsername))// 判断 帐号和密码
		{
			new AlertDialog.Builder(AddFriendActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("添加好友错误").setMessage("对方帐号不能为空，\n请输入后再登录！")
					.create().show();
		}
		else if(friendUsername.contains("@"))
		{
			Toast.makeText(this, "含有非法字符", Toast.LENGTH_SHORT).show();
			return;
		}
		else 
		{
			//判断用户是否存在
			if(KFIMInterfaces.isUserExist(friendUsername))
			{
				//添加好友
				KFIMInterfaces.addFriend(this, friendUsername, "我是昵称");
			}	
			else
			{
				Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
			}
		}  
	}

}




















