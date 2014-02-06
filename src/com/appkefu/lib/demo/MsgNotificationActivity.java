package com.appkefu.lib.demo;

import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.utils.KFSettings;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @author Administrator
 *
 */
public class MsgNotificationActivity extends Activity implements OnClickListener{

	private CheckBox new_message_notification;
	private CheckBox new_message_voice;
	private CheckBox new_message_vibrate;
	
	private Button mBackBtn;
	
	private KFSettingsManager mSettingsMgr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_notification);
		
		mSettingsMgr = KFSettingsManager.getSettingsManager(this);
		
		mBackBtn = (Button) findViewById(R.id.add_friends_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		new_message_notification = (CheckBox) findViewById(R.id.new_message_notification);
		new_message_voice = (CheckBox) findViewById(R.id.new_message_voice);
		new_message_vibrate = (CheckBox) findViewById(R.id.new_message_vibrate);
		
		new_message_notification.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_NOTIFICATION, true));
		new_message_voice.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_VOICE, true));
		new_message_vibrate.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_VIBRATE, true));
		
		new_message_notification.setOnCheckedChangeListener(new OnCheckedChangeListener() {           
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
            	mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_NOTIFICATION, isChecked);
            	            	
            	if(!isChecked)
            	{
            		new_message_voice.setChecked(false);
            		new_message_vibrate.setChecked(false);
            		
            		new_message_voice.setVisibility(View.GONE);
            		new_message_vibrate.setVisibility(View.GONE);
            	}
            	else
            	{
            		new_message_voice.setChecked(true);
            		new_message_vibrate.setChecked(true);
            		
            		new_message_voice.setVisibility(View.VISIBLE);
            		new_message_vibrate.setVisibility(View.VISIBLE);
            	}
            }
        });
		
		new_message_voice.setOnCheckedChangeListener(new OnCheckedChangeListener() {           
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
            	mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VOICE, isChecked);
            }
        });
		
		new_message_vibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {           
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
            	mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VIBRATE, isChecked);
            }
        });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.add_friends_reback_btn:
			finish();
			break;

		default:
			break;
		}
	}

}
