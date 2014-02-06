package com.appkefu.lib.demo;

import com.appkefu.lib.interfaces.KFIMInterfaces;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;

import android.util.Log;
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
public class RegisterActivity extends Activity implements OnClickListener{

	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private EditText mRePassword;

	private Button mRegisterBtn;
	private Button mBackBtn;
	
	private String username;
	private String password;
	private String repassword;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
				
		mUser = (EditText) findViewById(R.id.register_user_edit);
		mPassword = (EditText) findViewById(R.id.register_passwd_edit);
		mRePassword = (EditText) findViewById(R.id.re_register_passwd_edit);
		
		mRegisterBtn = (Button) findViewById(R.id.register_register_btn);
		mRegisterBtn.setOnClickListener(this);
		mBackBtn = (Button) findViewById(R.id.register_reback_btn);
		mBackBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.register_register_btn:
			reigster();
			break;
		case R.id.register_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	

	public void reigster() 
	{
		username = mUser.getText().toString();
		password = mPassword.getText().toString();
		repassword = mRePassword.getText().toString();
		
		if ("".equals(username)
				|| "".equals(password)
						|| "".equals(repassword))// 判断 帐号和密码
		{
			new AlertDialog.Builder(RegisterActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("注册错误").setMessage("帐号或者密码不能为空，\n请输入后再登录！")
					.create().show();
		}
		else if(!password.equals(repassword)) 
		{
			new AlertDialog.Builder(RegisterActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("注册错误").setMessage("两次输入的密码不一致!")
			.create().show();
		}
		else
		{		
			mRegisterBtn.setEnabled(false);
			
			//注册
			registerThread();
		}  
	}
	
	
	public void registerThread() {
  		
  		final Handler handler = new Handler()
      	{
      		public void handleMessage(Message msg) 
  			{
  				Integer result = (Integer)msg.obj;
  				
  				if(result == 1)
            	{
  					Log.d("注册成功", "注册成功");
  					Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400001)
            	{
            		Log.d("注册失败", "用户名长度最少为6(错误码:-400001)");
            		Toast.makeText(RegisterActivity.this, "注册失败:用户名长度最少为6(错误码:-400001)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400002)
            	{
            		Log.d("注册失败", "密码长度最少为6(错误码:-400002)");
            		Toast.makeText(RegisterActivity.this, "注册失败:密码长度最少为6(错误码:-400002)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400003)
            	{
            		Log.d("注册失败", "此用户名已经被注册(错误码:-400003)");
            		Toast.makeText(RegisterActivity.this, "注册失败:此用户名已经被注册(错误码:-400003)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400004)
            	{
            		Log.d("注册失败", "用户名含有非法字符(错误码:-400004)");
            		Toast.makeText(RegisterActivity.this, "注册失败:用户名含有非法字符(错误码:-400004)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == 0)
            	{
            		Log.d("注册失败", "其他原因");
            		Toast.makeText(RegisterActivity.this, "注册失败:其他原因", Toast.LENGTH_LONG);
            	}
  				
  				mRegisterBtn.setEnabled(true);
  			}
      	};
      	
      	new Thread() 
  		{
  			public void run() 
  			{
  				Message msg = new Message();
  				
  				//目前用户名为整个微客服唯一，建议开发者在程序内部将appkey做为用户名的后缀，
  				//这样可以保证用户名的唯一性
  						  //注册接口，返回结果为int
  				msg.obj = KFIMInterfaces.register(username, password);
  				
  				handler.sendMessage(msg);
  			}
  		}.start();
  		
  	}
	
}























