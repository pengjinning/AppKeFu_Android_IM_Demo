package com.appkefu.lib.demo;

import com.appkefu.lib.interfaces.KFIMInterfaces;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 *
 */
public class ProfileChangeActivity extends Activity implements OnClickListener{

	private Button   changeBtn;
	private TextView profileFieldTextView;
	
	private String  profileField;
	private String 	profileValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_change);
		
		changeBtn = (Button) findViewById(R.id.change_profile_btn);
		changeBtn.setOnClickListener(this);
		
		profileFieldTextView = (TextView) findViewById(R.id.change_profile_user_edit);
		
		profileField = getIntent().getStringExtra("profileField");
		profileValue = getIntent().getStringExtra("value");
		
		profileFieldTextView.setText(profileValue);
	}
	
	public void changeProfile()
	{		
		profileValue = profileFieldTextView.getText().toString();
		if(profileValue.length() <= 0)
		{
			Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
			return;
		}	
		
		changeBtn.setEnabled(false);

		//设置个人资料
		KFIMInterfaces.setVCardField(profileField, profileValue, this);
		
		finish();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()) {
		case R.id.change_profile_reback_btn:
			finish();
			break;
		case R.id.change_profile_btn:
			changeProfile();
			break;
		default:
			break;
		}	
	}

}
