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
	
	//��ʾ������Ѿ����й��ɰ��Demo���������ֻ���ɾ��ԭ�ȵ�App���������д˹���
	
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
		//���ÿ����ߵ���ģʽ��Ĭ��Ϊtrue����Ҫ�رտ�����ģʽ��������Ϊfalse
		mSettingsMgr.setDebugMode(true);
		//��Դ��ʼ��
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
		case R.id.register_button://ע�����˺�
			intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_button://��¼
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.chat_button:
			intent = new Intent(this, KFChatActivity.class);
			//��admin�Ự,ʵ��Ӧ������Ҫ��"admin"�滻Ϊ�Է����û���
			intent.putExtra("username", "admin");	
			//��ѡ���������������ʾ�Է����ǳƣ�����Է�û�������ǳƣ�����ʾ���û���
			intent.putExtra("title", "΢�ͷ��ͷ�");
			startActivity(intent);
			break;
		case R.id.self_chat_button:			
			//�Զ��壺�����ı���Ϣ�ӿ�
			//KFIMInterfaces.sendTextMessage(String msgContent, String toUsername, String type, Context context)
			//���У�msgContent: ������Ϣ���ݣ�toUsername: �Է��û�����type:һ��һ����̶�Ϊ"chat",�����޸�
			//���磺���û�admin�����ı���Ϣ���Զ�����Ϣ���ݡ�Ϊ��
			
			//�жϵ�ǰ�û��Ƿ��Ѿ���¼��true���Ѿ���¼��false��δ��¼
			if(!KFIMInterfaces.isLogin())
			{
				Toast.makeText(this, "δ��¼,���ܷ�����Ϣ", Toast.LENGTH_SHORT).show();
				return;
			}
			
			new AlertDialog.Builder(this)
			.setMessage("ȷ��Ҫ�����Զ�����Ϣ���ݣ�")
			.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {						
							
							KFIMInterfaces.sendTextMessage("�Զ�����Ϣ����_android", "admin", "chat", MainActivity.this);	
							Toast.makeText(MainActivity.this, "�Զ�����Ϣ�ѷ���", Toast.LENGTH_SHORT).show();
						}
					})
					.setNegativeButton("ȡ��", null)
					.create()
					.show();
			
			
			break;	
		case R.id.add_friend_button://��Ӻ���
			intent = new Intent(this, AddFriendActivity.class);
			startActivity(intent);
			break;
		case R.id.roster_list_button://�����б�
			intent = new Intent(this, RosterListActivity.class);
			startActivity(intent);
			break;
		case R.id.history_list_button://��ʷ�����¼
			intent = new Intent(this, HistoryActivity.class);
			startActivity(intent);
			break;
		case R.id.history_personal_button://�Ự��Ϣ��¼
			intent = new Intent(this, MessageActivity.class);
			startActivity(intent);
			break;
		case R.id.profile_button://����������Ϣ
			intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
			break;
		case R.id.friend_profile_button:
			//���ˣ�"admin"������������Ϣ��ʵ�ʲ���ʱ��Ҫ��admin�滻Ϊ�Է���ʵ���û���
			intent = new Intent(this, ProfileFriendActivity.class);
			intent.putExtra("chatName", "admin");
			startActivity(intent);
			break;
		case R.id.change_password_button:
			intent = new Intent(this, ChangePasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.group_chat_button://Ⱥ�ģ������У�
			intent = new Intent(this, GroupChatActivity.class);
			startActivity(intent);
			break;
		case R.id.msg_notif_button:
			intent = new Intent(this, MsgNotificationActivity.class);
			startActivity(intent);
			break;
		case R.id.logout_button://�˳���¼
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
		//�����������ӱ仯���
        intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
        //������Ϣ
        intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
        //������Ӻ���/ɾ��������Ϣ
        intentFilter.addAction(KFMainService.ACTION_XMPP_PRESENCE_SUBSCRIBE);
        //
        registerReceiver(mXmppreceiver, intentFilter);        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(KFIMInterfaces.isLogin())
		{
			mTitle.setText("΢�ͷ�IM Demo("+mSettingsMgr.getUsername()+")");
        	mLoginBtn.setText("��¼("+mSettingsMgr.getUsername()+"�ѵ�¼)");
		}
		else
		{
			mTitle.setText("΢�ͷ�IM Demo(δ����)");
        	mLoginBtn.setText("��¼(δ��¼)");
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
            	//����: bodyΪ��Ϣ���ݣ�fromΪ����Ϣ�ߵ��û���
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
            		KFSLog.d(from+" ���������Ϊ����");
            	}
            	else if(type.equals("subscribed"))
            	{
            		KFSLog.d(from+" ͬ�����ĺ�������");
            	}
            	else if(type.equals("unsubscribed"))
            	{
            		KFSLog.d(from +" �ܾ����ĺ�������");
            	}         	
            }
            
        }
    };
    
    private void login()
    {
		//��� �û���/���� �Ƿ��Ѿ�����,����Ѿ����ã����¼
		if(!"".equals(mSettingsMgr.getUsername()) 
				&& !"".equals(mSettingsMgr.getPassword()))
			KFIMInterfaces.login(mSettingsMgr.getUsername(), mSettingsMgr.getPassword(), this);
    }
    
    //���ݼ����������ӱ仯������½�����ʾ
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	KFSLog.d("connected");
            	mTitle.setText("΢�ͷ�IM Demo("+mSettingsMgr.getUsername()+")");
            	mLoginBtn.setText("��¼("+mSettingsMgr.getUsername()+"�ѵ�¼)");
                break;
            case KFXmppManager.DISCONNECTED:
            	KFSLog.d("disconnected");
            	mTitle.setText("΢�ͷ�IM Demo(δ����)");
            	mLoginBtn.setText("��¼(δ��¼)");
                break;
            case KFXmppManager.CONNECTING:
            	KFSLog.d("connecting");
            	mTitle.setText("΢�ͷ�IM Demo(��¼��...)");
            	mLoginBtn.setText("��¼(δ��¼)");
            	break;
            case KFXmppManager.DISCONNECTING:
            	KFSLog.d("connecting");
            	mTitle.setText("΢�ͷ�IM Demo(�ǳ���...)");
            	mLoginBtn.setText("��¼(δ��¼)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	KFSLog.d("waiting to connect");
            	mTitle.setText("΢�ͷ�IM Demo(�ȴ���)");
            	mLoginBtn.setText("��¼(δ��¼)");
                break;
            default:
                throw new IllegalStateException();
        }
    }

    //�˳���¼
	private void logout() {
		new AlertDialog.Builder(this)
		.setMessage("ȷ���˳���¼?")
		.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{			
						//�˳���¼
						KFIMInterfaces.Logout(MainActivity.this);
					}
				}).setNegativeButton("ȡ��", null).create()
		.show();
	}

}




















