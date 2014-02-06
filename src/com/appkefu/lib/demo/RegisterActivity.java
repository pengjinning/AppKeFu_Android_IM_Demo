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

	private EditText mUser; // �ʺű༭��
	private EditText mPassword; // ����༭��
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
						|| "".equals(repassword))// �ж� �ʺź�����
		{
			new AlertDialog.Builder(RegisterActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("ע�����").setMessage("�ʺŻ������벻��Ϊ�գ�\n��������ٵ�¼��")
					.create().show();
		}
		else if(!password.equals(repassword)) 
		{
			new AlertDialog.Builder(RegisterActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("ע�����").setMessage("������������벻һ��!")
			.create().show();
		}
		else
		{		
			mRegisterBtn.setEnabled(false);
			
			//ע��
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
  					Log.d("ע��ɹ�", "ע��ɹ�");
  					Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400001)
            	{
            		Log.d("ע��ʧ��", "�û�����������Ϊ6(������:-400001)");
            		Toast.makeText(RegisterActivity.this, "ע��ʧ��:�û�����������Ϊ6(������:-400001)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400002)
            	{
            		Log.d("ע��ʧ��", "���볤������Ϊ6(������:-400002)");
            		Toast.makeText(RegisterActivity.this, "ע��ʧ��:���볤������Ϊ6(������:-400002)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400003)
            	{
            		Log.d("ע��ʧ��", "���û����Ѿ���ע��(������:-400003)");
            		Toast.makeText(RegisterActivity.this, "ע��ʧ��:���û����Ѿ���ע��(������:-400003)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == -400004)
            	{
            		Log.d("ע��ʧ��", "�û������зǷ��ַ�(������:-400004)");
            		Toast.makeText(RegisterActivity.this, "ע��ʧ��:�û������зǷ��ַ�(������:-400004)", Toast.LENGTH_LONG).show();
            	}
            	else if(result == 0)
            	{
            		Log.d("ע��ʧ��", "����ԭ��");
            		Toast.makeText(RegisterActivity.this, "ע��ʧ��:����ԭ��", Toast.LENGTH_LONG);
            	}
  				
  				mRegisterBtn.setEnabled(true);
  			}
      	};
      	
      	new Thread() 
  		{
  			public void run() 
  			{
  				Message msg = new Message();
  				
  				//Ŀǰ�û���Ϊ����΢�ͷ�Ψһ�����鿪�����ڳ����ڲ���appkey��Ϊ�û����ĺ�׺��
  				//�������Ա�֤�û�����Ψһ��
  						  //ע��ӿڣ����ؽ��Ϊint
  				msg.obj = KFIMInterfaces.register(username, password);
  				
  				handler.sendMessage(msg);
  			}
  		}.start();
  		
  	}
	
}























