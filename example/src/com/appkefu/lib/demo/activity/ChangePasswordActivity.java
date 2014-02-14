package com.appkefu.lib.demo.activity;

import com.appkefu.lib.demo.R;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFSettingsManager;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;

/**
 * 
 * @author Administrator
 *
 */
public class ChangePasswordActivity extends Activity implements OnClickListener{

	private EditText mOldPassword; // ’ ∫≈±‡º≠øÚ
	private EditText mPassword; // √‹¬Î±‡º≠øÚ
	private EditText mRePassword;

	private Button mChangePswBtn;
	private Button mBackBtn;
	
	private String oldpassword;
	private String password;
	private String repassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		mOldPassword = (EditText) findViewById(R.id.change_password_edit);
		mPassword = (EditText) findViewById(R.id.change_new_passwd_edit);
		mRePassword = (EditText) findViewById(R.id.re_change_new_passwd_edit);
		
		mChangePswBtn = (Button) findViewById(R.id.change_change_btn);
		mChangePswBtn.setOnClickListener(this);
		mBackBtn = (Button) findViewById(R.id.change_reback_btn);
		mBackBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.change_change_btn:
			changePassword();
			break;
		case R.id.change_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	

	public void changePassword() 
	{
		oldpassword = mOldPassword.getText().toString();
		password = mPassword.getText().toString();
		repassword = mRePassword.getText().toString();
		
		
		if ("".equals(oldpassword)
				|| "".equals(password)
						|| "".equals(repassword))// ≈–∂œ ’ ∫≈∫Õ√‹¬Î
		{
			new AlertDialog.Builder(ChangePasswordActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("¥ÌŒÛ").setMessage("≤ªƒ‹Œ™ø’")
					.create().show();
		}
		else if(!oldpassword.equals(KFSettingsManager.getSettingsManager(this).getPassword()))
		{
			Toast.makeText(this, "‘≠√‹¬Î≤ª’˝»∑", Toast.LENGTH_SHORT).show();
		}
		else if(!password.equals(repassword)) 
		{
			new AlertDialog.Builder(ChangePasswordActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("¥ÌŒÛ").setMessage("¡Ω¥Œ ‰»Îµƒ√‹¬Î≤ª“ª÷¬!")
			.create().show();
		}
		else
		{		
			mChangePswBtn.setEnabled(false);
			
			//–ﬁ∏ƒ√‹¬Î
			//changeThread();
			KFIMInterfaces.changePassword(password);
			
			mChangePswBtn.setEnabled(true);
		}  
	}
	
	

}











