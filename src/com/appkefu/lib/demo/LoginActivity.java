package com.appkefu.lib.demo;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFSLog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
public class LoginActivity extends Activity implements OnClickListener{

	private EditText mUsername; // 帐号编辑框
	private EditText mPassword; // 密码编辑框

	private Button mLoginBtn;
	private Button mBackBtn;
	private Button mFindPswBtn;
	
	private String username;
	private String password;
	
	private KFSettingsManager mSettingsMgr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mSettingsMgr = KFSettingsManager.getSettingsManager(this);

		mUsername = (EditText) findViewById(R.id.login_user_edit);
		mUsername.setText(mSettingsMgr.getUsername());
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		mPassword.setText(mSettingsMgr.getPassword());
		
		mLoginBtn = (Button) findViewById(R.id.login_login_btn);
		mLoginBtn.setOnClickListener(this);
		mBackBtn = (Button) findViewById(R.id.login_reback_btn);
		mBackBtn.setOnClickListener(this);
		mFindPswBtn = (Button) findViewById(R.id.forget_passwd);
		mFindPswBtn.setOnClickListener(this);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		KFSLog.d("onStart");
		
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
        registerReceiver(mXmppreceiver, intentFilter);        

	}
	
	@Override
	protected void onStop() {
		super.onStop();

		KFSLog.d("onStop");
        unregisterReceiver(mXmppreceiver);
	}
	
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED)) 
            {
                updateStatus(intent.getIntExtra("new_state", 0));        
            }
           
        }
    };
    
    private void updateStatus(int status) {
        switch (status) {
            case KFXmppManager.CONNECTED:
            	KFSLog.d("登录成功");
            	finish();
                break;
            case KFXmppManager.DISCONNECTED:
            	KFSLog.d("未登录");  
            	mLoginBtn.setEnabled(true);
                break;
            case KFXmppManager.CONNECTING:
            	KFSLog.d("登录中");
            	break;
            case KFXmppManager.DISCONNECTING:
            	KFSLog.d("登出中");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	KFSLog.d("waiting to connect");
                break;
            default:
                throw new IllegalStateException();
        }      
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.login_login_btn:
			login();
			break;
		case R.id.login_reback_btn:
			finish();
			break;
		case R.id.forget_passwd:
			forget_password();
			break;
		default:
			break;
		}
	}
	
	public void login() 
	{
		username = mUsername.getText().toString();
		password = mPassword.getText().toString();
		
		if ("".equals(username)
				|| "".equals(password))// 判断 帐号和密码
		{
			new AlertDialog.Builder(LoginActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("登录错误").setMessage("帐号或者密码不能为空，\n请输入后再登录！")
					.create().show();
		}
		else
		{
			mLoginBtn.setEnabled(false);
			
			Toast.makeText(this, "正在登录...", Toast.LENGTH_LONG).show();
			//登录接口
			KFIMInterfaces.login(username, password, this);
		}  
	}

	//
	public void forget_password() {// 忘记密码按钮
		
		new AlertDialog.Builder(LoginActivity.this)
		.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
		.setMessage("此功能稍后放出")
		.create().show();
	}

}
