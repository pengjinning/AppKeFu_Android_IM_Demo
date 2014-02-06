package com.appkefu.lib.demo;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.demo.adapter.RosterListViewAdapter;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.ui.entity.KFRosterEntity;
import com.appkefu.lib.utils.KFSLog;
import com.appkefu.lib.xmpp.XmppFriend;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 
 * @author Administrator
 *
 */
public class RosterListActivity extends Activity implements OnClickListener{

	private Button mBackBtn;
	
	private ListView mListView;
	private List<KFRosterEntity> mRosterList = new ArrayList<KFRosterEntity>();
	private RosterListViewAdapter mRosterAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster_list);
		
		mBackBtn = (Button) findViewById(R.id.friends_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.roster_listView);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.friends_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		//监听好友状态变化情况
		IntentFilter intentFilter = new IntentFilter(KFMainService.ACTION_XMPP_PRESENCE_CHANGED);
        registerReceiver(mXmppreceiver, intentFilter); 

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//发送获取全部好友请求
		KFIMInterfaces.getFriends(this);
		
		//类似于个性签名
		//String status = KFIMInterfaces.getStatus(username);
		//在线:0; 离开: 1; 忙: 3; 离线: 5;
		//int state = KFIMInterfaces.getPresenceState(username);
	}
	
	@Override
	protected void onStop() {
		super.onStop();

        unregisterReceiver(mXmppreceiver);
	}
	
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            if (action.equals(KFMainService.ACTION_XMPP_PRESENCE_CHANGED)) 
            {
            	
            	//在线状态对应关系：0:在线; 1:离开; 2:离开; 3:忙; 4:欢迎聊天; 5:离线;
                int stateInt = intent.getIntExtra("state", XmppFriend.OFFLINE);//在线状态，默认为离线
                
                String userId = intent.getStringExtra("userid");//用户的jid
                String name = intent.getStringExtra("name");//昵称
                String status = intent.getStringExtra("status");//个性签名

                KFSLog.d("userId:"+userId
                		+" state:"+stateInt
                		+" name:"+name 
                		+" status:"+status);
                
                if(StringUtils.parseName(userId).length() > 0)
                {
            		KFRosterEntity entity = new KFRosterEntity();
            		entity.setJid(userId);
            		entity.setNick(name);
            		
            		if(!mRosterList.contains(entity))
            		{
                        mRosterList.add(entity);               		
            		} 
            		else
            		{
            			mRosterList.remove(entity);
            			mRosterList.add(entity);
            		}
            		
            		mRosterAdapter = new RosterListViewAdapter(RosterListActivity.this, mRosterList);
            		mListView.setAdapter(mRosterAdapter);
            		mRosterAdapter.notifyDataSetChanged();
                }
            }            
        }
    };
	
}





















