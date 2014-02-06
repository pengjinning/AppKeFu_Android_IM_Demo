package com.appkefu.lib.demo;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
//import com.appkefu.lib.service.KFMainService.LocalBinder;
import com.appkefu.lib.ui.activity.KFChatActivity;
import com.appkefu.lib.utils.KFSLog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 
 * @author Administrator
 *
 */
public class MainActivity extends Activity implements OnClickListener{
	
	//提示：如果已经运行过旧版的Demo，请先在手机上删除原先的App再重新运行此工程
	
	private KFSettingsManager mSettingsMgr;
	
	private TextView mTitle;
	
	private Button mRegisterBtn;
	private Button mLoginBtn;
	private Button mChatBtn;
	private Button mSelfChatBtn;
	private Button mAddFriendBtn;
	private Button mShowRosterBtn;
	private Button mHistoryBtn;
	private Button mMessageBtn;
	private Button mProfileBtn;
	private Button mProfileFriendBtn;
	private Button mChangePasswordBtn;
	private Button mGroupChatBtn;
	private Button mMsgNotifBtn;
	private Button mLogoutBtn;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSettingsMgr = KFSettingsManager.getSettingsManager(this);
		//设置开发者调试模式，默认为true，如要关闭开发者模式，请设置为false
		mSettingsMgr.setDebugMode(true);
		//资源初始化
		initView();
		
		login();
	}
	
	private void initView() {
		
		mTitle = (TextView) findViewById(R.id.demo_title);
		
		mRegisterBtn = (Button) findViewById(R.id.register_button);
		mRegisterBtn.setOnClickListener(this);
		
		mLoginBtn = (Button) findViewById(R.id.login_button);
		mLoginBtn.setOnClickListener(this);
		
		mChatBtn = (Button) findViewById(R.id.chat_button);
		mChatBtn.setOnClickListener(this);
		
		mSelfChatBtn = (Button) findViewById(R.id.self_chat_button);
		mSelfChatBtn.setOnClickListener(this);
		
		mAddFriendBtn = (Button) findViewById(R.id.add_friend_button);
		mAddFriendBtn.setOnClickListener(this);
		
		mShowRosterBtn = (Button) findViewById(R.id.roster_list_button);
		mShowRosterBtn.setOnClickListener(this);
		
		mHistoryBtn = (Button) findViewById(R.id.history_list_button);
		mHistoryBtn.setOnClickListener(this);
		
		mMessageBtn = (Button) findViewById(R.id.history_personal_button);
		mMessageBtn.setOnClickListener(this);
		
		mProfileBtn = (Button) findViewById(R.id.profile_button);
		mProfileBtn.setOnClickListener(this);
		
		mProfileFriendBtn = (Button) findViewById(R.id.friend_profile_button);
		mProfileFriendBtn.setOnClickListener(this);
		
		mChangePasswordBtn = (Button) findViewById(R.id.change_password_button);
		mChangePasswordBtn.setOnClickListener(this);
		
		mGroupChatBtn = (Button) findViewById(R.id.group_chat_button);
		mGroupChatBtn.setOnClickListener(this);
		
		mMsgNotifBtn = (Button) findViewById(R.id.msg_notif_button);
		mMsgNotifBtn.setOnClickListener(this);
		
		mLogoutBtn = (Button) findViewById(R.id.logout_button);
		mLogoutBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(view.getId()) {
		case R.id.register_button://注册新账号
			intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_button://登录
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.chat_button:
			intent = new Intent(this, KFChatActivity.class);
			//与admin会话,实际应用中需要将"admin"替换为对方的用户名
			intent.putExtra("username", "admin");	
			//可选，如果不设置则显示对方的昵称，如果对方没有设置昵称，则显示其用户名
			intent.putExtra("title", "微客服客服");
			startActivity(intent);
			break;
		case R.id.self_chat_button:			
			//自定义：发送文本消息接口
			//KFIMInterfaces.sendTextMessage(String msgContent, String toUsername, String type, Context context)
			//其中：msgContent: 聊天消息内容，toUsername: 对方用户名，type:一对一聊天固定为"chat",不能修改
			//例如：给用户admin发送文本消息“自定义消息内容”为：
			
			//判断当前用户是否已经登录，true：已经登录，false：未登录
			if(!KFIMInterfaces.isLogin())
			{
				Toast.makeText(this, "未登录,不能发送消息", Toast.LENGTH_SHORT).show();
				return;
			}
			
			new AlertDialog.Builder(this)
			.setMessage("确定要发送自定义消息内容？")
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {						
							
							KFIMInterfaces.sendTextMessage("自定义消息内容_android", "admin", "chat", MainActivity.this);	
							Toast.makeText(MainActivity.this, "自定义消息已发送", Toast.LENGTH_SHORT).show();
						}
					})
					.setNegativeButton("取消", null)
					.create()
					.show();
			
			
			break;	
		case R.id.add_friend_button://添加好友
			intent = new Intent(this, AddFriendActivity.class);
			startActivity(intent);
			break;
		case R.id.roster_list_button://好友列表
			intent = new Intent(this, RosterListActivity.class);
			startActivity(intent);
			break;
		case R.id.history_list_button://历史聊天记录
			intent = new Intent(this, HistoryActivity.class);
			startActivity(intent);
			break;
		case R.id.history_personal_button://会话消息记录
			intent = new Intent(this, MessageActivity.class);
			startActivity(intent);
			break;
		case R.id.profile_button://个人资料信息
			intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
			break;
		case R.id.friend_profile_button:
			//他人（"admin"）个人资料信息，实际操作时需要将admin替换为对方真实的用户名
			intent = new Intent(this, ProfileFriendActivity.class);
			intent.putExtra("chatName", "admin");
			startActivity(intent);
			break;
		case R.id.change_password_button:
			intent = new Intent(this, ChangePasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.group_chat_button://群聊（开发中）
			intent = new Intent(this, GroupChatActivity.class);
			startActivity(intent);
			break;
		case R.id.msg_notif_button:
			intent = new Intent(this, MsgNotificationActivity.class);
			startActivity(intent);
			break;
		case R.id.logout_button://退出登录
			logout();			
			break;
		default:			
			break;		
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		IntentFilter intentFilter = new IntentFilter();
		//监听网络连接变化情况
        intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
        //监听消息
        intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
        //监听添加好友/删除好友消息
        intentFilter.addAction(KFMainService.ACTION_XMPP_PRESENCE_SUBSCRIBE);
        //
        registerReceiver(mXmppreceiver, intentFilter);        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(KFIMInterfaces.isLogin())
		{
			mTitle.setText("微客服IM Demo("+mSettingsMgr.getUsername()+")");
        	mLoginBtn.setText("登录("+mSettingsMgr.getUsername()+"已登录)");
		}
		else
		{
			mTitle.setText("微客服IM Demo(未连接)");
        	mLoginBtn.setText("登录(未登录)");
		}
		
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
            
            if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED)) 
            {
                updateStatus(intent.getIntExtra("new_state", 0));        
            }
            else if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))
            {
            	//其中: body为消息内容，from为发消息者的用户名
            	String body = intent.getStringExtra("body");
            	String from = StringUtils.parseName(intent.getStringExtra("from"));
            	
            	KFSLog.d("body:"+body+" from:"+from);
            }
            else if(action.equals(KFMainService.ACTION_XMPP_PRESENCE_SUBSCRIBE))
            {
            	String type = intent.getStringExtra("type");
            	String from = StringUtils.parseName(intent.getStringExtra("from"));
            	
            	KFSLog.d("type:"+type+" from:"+from);
            	
            	if(type.equals("subscribe"))
            	{
            		KFSLog.d(from+" 请求添加您为好友");
            	}
            	else if(type.equals("subscribed"))
            	{
            		KFSLog.d(from+" 同意您的好友请求");
            	}
            	else if(type.equals("unsubscribed"))
            	{
            		KFSLog.d(from +" 拒绝您的好友请求");
            	}         	
            }
            
        }
    };
    
    private void login()
    {
		//检查 用户名/密码 是否已经设置,如果已经设置，则登录
		if(!"".equals(mSettingsMgr.getUsername()) 
				&& !"".equals(mSettingsMgr.getPassword()))
			KFIMInterfaces.login(mSettingsMgr.getUsername(), mSettingsMgr.getPassword(), this);
    }
    
    //根据监听到的连接变化情况更新界面显示
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	KFSLog.d("connected");
            	mTitle.setText("微客服IM Demo("+mSettingsMgr.getUsername()+")");
            	mLoginBtn.setText("登录("+mSettingsMgr.getUsername()+"已登录)");
                break;
            case KFXmppManager.DISCONNECTED:
            	KFSLog.d("disconnected");
            	mTitle.setText("微客服IM Demo(未连接)");
            	mLoginBtn.setText("登录(未登录)");
                break;
            case KFXmppManager.CONNECTING:
            	KFSLog.d("connecting");
            	mTitle.setText("微客服IM Demo(登录中...)");
            	mLoginBtn.setText("登录(未登录)");
            	break;
            case KFXmppManager.DISCONNECTING:
            	KFSLog.d("connecting");
            	mTitle.setText("微客服IM Demo(登出中...)");
            	mLoginBtn.setText("登录(未登录)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	KFSLog.d("waiting to connect");
            	mTitle.setText("微客服IM Demo(等待中)");
            	mLoginBtn.setText("登录(未登录)");
                break;
            default:
                throw new IllegalStateException();
        }
    }

    //退出登录
	private void logout() {
		new AlertDialog.Builder(this)
		.setMessage("确认退出登录?")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{			
						//退出登录
						KFIMInterfaces.Logout(MainActivity.this);
					}
				}).setNegativeButton("取消", null).create()
		.show();
	}

}




















