package com.appkefu.lib.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.db.KFMessageHelper;
import com.appkefu.lib.demo.adapter.MessageAdapter;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.ui.entity.KFChatEntity;
import com.appkefu.lib.utils.KFSLog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * 
 * @author Administrator
 *
 */
public class MessageActivity extends Activity implements OnClickListener{

	//所有本地消息记录处理接口在此
	private Button mBackBtn;
	private ImageButton mClearBtn;
	
	private ListView mListView;
	private List<KFChatEntity> mMessageList = new ArrayList<KFChatEntity>();
	private MessageAdapter mMessageListAdapter;
	
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		//admin为示例，需要替换为相应的用户名
		username = "admin";
		
		mBackBtn = (Button) findViewById(R.id.history_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mClearBtn = (ImageButton) findViewById(R.id.right_btn);
		mClearBtn.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.history_listView);	
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
                
                final int index = position;
                
                final KFChatEntity entity = (KFChatEntity)mMessageList.get(index);
                
                KFSLog.d("index:"+index+" id:"+entity.getId() +" content:"+entity.getText()+" date:"+entity.getDate());
                
                if(!entity.getText().startsWith("<img")
						&& !entity.getText().startsWith("appkefu_location:")
						&& !entity.getText().endsWith(".amr")
						&& !entity.getText().endsWith(".jpg")
						&& !entity.getText().endsWith(".jpeg")
						&& !entity.getText().endsWith(".png")
						&& !entity.getText().endsWith(".bmp")
						&& !entity.getText().endsWith(".gif"))
				{
                	new AlertDialog.Builder(MessageActivity.this)
    				.setMessage("请选择操作")
    				.setPositiveButton("复制内容",
    						new DialogInterface.OnClickListener() {
    							public void onClick(DialogInterface dialog,int which) {		
    													
    								ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    								cmb.setText(entity.getText());    								
    							}
    				})
    				.setNeutralButton("删除内容", new DialogInterface.OnClickListener()
    				{
    					@Override
    					public void onClick(DialogInterface arg0, int arg1) {
    						// TODO Auto-generated method stub
    						
    						KFMessageHelper.getMessageHelper(getApplicationContext()).deleteMessageById(entity.getId());
    						
    						mMessageList.remove(index);
    						mMessageListAdapter.notifyDataSetChanged();
    					}	
    				})
    				.setNegativeButton("取消", null)
    				.create()
    				.show();
				}
                else
                {
                	new AlertDialog.Builder(MessageActivity.this)
    				.setMessage("请选择操作")
    				.setPositiveButton("删除内容",
    						new DialogInterface.OnClickListener() {
    							public void onClick(DialogInterface dialog,int which) {		
    													
    								KFMessageHelper.getMessageHelper(getApplicationContext()).deleteMessageById(entity.getId());
    	    						
    								mMessageList.remove(index);
    								mMessageListAdapter.notifyDataSetChanged();
    	    						
    	    						//应当删除关联文件
    	    						if(entity.getText().endsWith(".amr"))
    	    						{
    	    							File file = new File(entity.getText());

    	    							if (file.exists()) {
    	    								file.delete();
    	    							}
    	    						}   								
    						}
    				})   				
    				.setNegativeButton("取消", null)
    				.create()
    				.show();
                	
                }
 	
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
			
		case R.id.right_btn:
			clearMessages();
			break;
			
		default:
			break;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();		
		
		IntentFilter intentFilter = new IntentFilter();
        //监听消息
        intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
        registerReceiver(mXmppreceiver, intentFilter); 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		invalidateMessage();
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
            	
            	invalidateMessage();
            }
        }
    };
	
	@SuppressWarnings("unchecked")
	private void invalidateMessage() 
	{
		mMessageList = KFMessageHelper.getMessageHelper(this).getAllMessages(username);
		mMessageListAdapter = new MessageAdapter(this,mMessageList);
		mListView.setAdapter(mMessageListAdapter);
		mMessageListAdapter.notifyDataSetChanged();
	}
	
	public void clearMessages()
	{
		new AlertDialog.Builder(this)
		.setMessage("确认清空聊天记录?")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						
						KFMessageHelper.getMessageHelper(
								getApplicationContext())
								.deleteMessages(username);

						mMessageList.clear();
						mMessageListAdapter.notifyDataSetChanged();
					}
				}).setNegativeButton("取消", null).create()
		.show();
	}

}





