package com.appkefu.lib.demo;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.db.KFConversationHelper;
import com.appkefu.lib.demo.adapter.ConversationAdapter;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.ui.entity.KFConversationEntity;
import com.appkefu.lib.utils.KFSLog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * 
 * @author Administrator
 *
 */
public class HistoryActivity extends Activity implements OnClickListener{
	
	private Button mBackBtn;
	
	private ListView mListView;
	private List<KFConversationEntity> mConversationList = new ArrayList<KFConversationEntity>();
	private ConversationAdapter mConversationListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		mBackBtn = (Button) findViewById(R.id.history_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.history_listView);		
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long id) {
                // TODO Auto-generated method stub
                
                final int location = position;
				new AlertDialog.Builder(HistoryActivity.this)
				.setMessage("确定要删除此会话？")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {		
								//是否应该一起将消息记录删除，而不仅仅是删除会话conversation
								KFConversationEntity entity = (KFConversationEntity) mConversationListAdapter.getItem(location);
								KFSLog.d("name:"+entity.getName());
								KFConversationHelper.getConversationHelper(getApplicationContext()).deleteConversation(entity.getName());

								mConversationList.remove(location);
								mConversationListAdapter.notifyDataSetChanged();
							}
				}).setNegativeButton("取消", null).create().show();
                return false;
            }
        }); 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.history_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		KFSLog.d("onStart");
		
		IntentFilter intentFilter = new IntentFilter();
        //监听消息
        intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
        registerReceiver(mXmppreceiver, intentFilter); 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		invalidateConversation();
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
            if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))
            {
            	String body = intent.getStringExtra("body");
            	String from = StringUtils.parseName(intent.getStringExtra("from"));
            	
            	KFSLog.d("body:"+body+" from:"+from);
            	
            	invalidateConversation();
            }
        }
    };
	
	@SuppressWarnings("unchecked")
	private void invalidateConversation() 
	{
		mConversationList = KFConversationHelper.getConversationHelper(this).getAllConversation();
		mConversationListAdapter = new ConversationAdapter(this,mConversationList);
		mListView.setAdapter(mConversationListAdapter);
		mConversationListAdapter.notifyDataSetChanged();
	}
	
}



























