package com.appkefu.lib.demo.activity;

import java.util.ArrayList;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.demo.R;
import com.appkefu.lib.demo.adapter.MainActivityAdapter;
import com.appkefu.lib.demo.entity.ApiEntity;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFSLog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
 * ΢�ͷ�(AppKeFu.com)
 * 
 * ΢�ͷ������ɵ���App������߿ͷ�
 * �����׿�App������߿ͷ���֧�����֡����顢ͼƬ���������졣 ��־Ϊ�ƶ��������ṩ��õ����߿ͷ�
 * 
 * ��������QQȺ:48661516
 * 
 * @author jack ning, http://github.com/pengjinning
 *
 */
public class MainActivity extends Activity{
		
	/**
	 * �����ĵ�����
	 * http://appkefu.com/AppKeFu/android_im_demo.html
	 * 
	 */
	
	private KFSettingsManager mSettingsMgr;
	
	private TextView mTitle;
	private ListView mApiListView;
	private ArrayList<ApiEntity> mApiArray;
	private MainActivityAdapter mAdapter;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//
		mSettingsMgr = KFSettingsManager.getSettingsManager(this);
		
		//���ÿ����ߵ���ģʽ��Ĭ��Ϊtrue����Ҫ�رտ�����ģʽ��������Ϊfalse
		KFIMInterfaces.enableDebugMode(this, true);
		//��Դ��ʼ��
		initView();
		
		login();
	}
	
	private void initView() {
		
		mTitle = (TextView) findViewById(R.id.demo_title);
		mApiListView = (ListView)findViewById(R.id.api_list_view);
		mApiArray = new ArrayList<ApiEntity>();
		
		mAdapter = new MainActivityAdapter(this,  mApiArray);
		mApiListView.setAdapter(mAdapter);
		
		ApiEntity entity = new ApiEntity(1, "ע��");
			mApiArray.add(entity);
		entity = new ApiEntity(2, "��¼");
			mApiArray.add(entity);
		entity = new ApiEntity(3, "����");
			mApiArray.add(entity);
		entity = new ApiEntity(4, "�����ı���Ϣ�ӿ�");
			mApiArray.add(entity);
		entity = new ApiEntity(5, "��Ӻ���");
			mApiArray.add(entity);
		entity = new ApiEntity(6, "�����б�");
			mApiArray.add(entity);
		entity = new ApiEntity(7, "��ʷ�����¼");
			mApiArray.add(entity);
		entity = new ApiEntity(8, "�Ự��Ϣ��¼");
			mApiArray.add(entity);
		entity = new ApiEntity(9, "����������Ϣ");
			mApiArray.add(entity);
		entity = new ApiEntity(10, "�����û���Ϣ");
			mApiArray.add(entity);
		entity = new ApiEntity(11, "�޸�����");
			mApiArray.add(entity);
		entity = new ApiEntity(12, "Ⱥ��");
			mApiArray.add(entity);
		entity = new ApiEntity(13, "��Ϣ֪ͨ����");
			mApiArray.add(entity);
		entity = new ApiEntity(14, "�˳���¼");
			mApiArray.add(entity);

		mAdapter.notifyDataSetChanged();
		
		mApiListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				// TODO Auto-generated method stub
				
				ApiEntity entity = mApiArray.get(index);
				Log.d("MainActivity", "OnItemClickListener:"+entity.getApiName());
				
				Intent intent;
				switch(entity.getId()) {
				case 1:
					intent = new Intent(MainActivity.this, RegisterActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					break;
				case 3:

					KFIMInterfaces.startChatWithUser(
							MainActivity.this,//������Context
							"admin",//�Է��û���
							"�Զ��崰�ڱ���");//�Զ���Ự���ڱ���
					
					break;
				case 4:
					//�Զ��壺�����ı���Ϣ�ӿ�
					//KFIMInterfaces.sendTextMessage(String msgContent, String toUsername, String type, Context context)
					//���У�msgContent: ������Ϣ���ݣ�toUsername: �Է��û�����type:һ��һ����̶�Ϊ"chat",�����޸�
					//���磺���û�admin�����ı���Ϣ���Զ�����Ϣ���ݡ�Ϊ��
					
					//�жϵ�ǰ�û��Ƿ��Ѿ���¼��true���Ѿ���¼��false��δ��¼
					if(!KFIMInterfaces.isLogin())
					{
						Toast.makeText(MainActivity.this, "δ��¼,���ܷ�����Ϣ", Toast.LENGTH_SHORT).show();
						return;
					}
					
					new AlertDialog.Builder(MainActivity.this)
					.setMessage("ȷ��Ҫ�����Զ�����Ϣ���ݣ�")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {						
									
									KFIMInterfaces.sendTextMessage(MainActivity.this, "�Զ�����Ϣ����_android", "admin", "chat");	
									Toast.makeText(MainActivity.this, "�Զ�����Ϣ�ѷ���", Toast.LENGTH_SHORT).show();
								}
							})
							.setNegativeButton("ȡ��", null)
							.create()
							.show();
					break;
				case 5:
					intent = new Intent(MainActivity.this, AddFriendActivity.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent(MainActivity.this, RosterListActivity.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(MainActivity.this, HistoryActivity.class);
					startActivity(intent);
					break;
				case 8:
					intent = new Intent(MainActivity.this, MessageActivity.class);
					startActivity(intent);
					break;
				case 9:
					intent = new Intent(MainActivity.this, ProfileActivity.class);
					startActivity(intent);
					break;
				case 10:
					//���ˣ�"admin"������������Ϣ��ʵ�ʲ���ʱ��Ҫ��admin�滻Ϊ�Է���ʵ���û���
					intent = new Intent(MainActivity.this, ProfileFriendActivity.class);
					intent.putExtra("chatName", "admin");
					startActivity(intent);
					break;
				case 11:
					intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
					startActivity(intent);
					break;
				case 12:
					intent = new Intent(MainActivity.this, GroupChatActivity.class);
					startActivity(intent);
					break;
				case 13:
					intent = new Intent(MainActivity.this, MsgNotificationActivity.class);
					startActivity(intent);
					break;
				case 14:
					logout();	
					break;
				default:
					break;
				}
				
			}
		});
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
			mApiArray.get(1).setApiName("��¼("+mSettingsMgr.getUsername()+"�ѵ�¼)");
		}
		else
		{
			mTitle.setText("΢�ͷ�IM Demo(δ����)");
			mApiArray.get(1).setApiName("��¼(δ��¼)");
		}
		
		mAdapter.notifyDataSetChanged();
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
			KFIMInterfaces.login(this, mSettingsMgr.getUsername(), mSettingsMgr.getPassword());
    }
    
    //���ݼ����������ӱ仯������½�����ʾ
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	mTitle.setText("΢�ͷ�IM Demo("+mSettingsMgr.getUsername()+")");
            	mApiArray.get(1).setApiName("��¼("+mSettingsMgr.getUsername()+"�ѵ�¼)");
                break;
            case KFXmppManager.DISCONNECTED:
            	mTitle.setText("΢�ͷ�IM Demo(δ����)");
            	mApiArray.get(1).setApiName("��¼(δ��¼)");
                break;
            case KFXmppManager.CONNECTING:
            	mTitle.setText("΢�ͷ�IM Demo(��¼��...)");
            	mApiArray.get(1).setApiName("��¼(δ��¼)");
            	break;
            case KFXmppManager.DISCONNECTING:
            	mTitle.setText("΢�ͷ�IM Demo(�ǳ���...)");
            	mApiArray.get(1).setApiName("��¼(δ��¼)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	mTitle.setText("΢�ͷ�IM Demo(�ȴ���)");
            	mApiArray.get(1).setApiName("��¼(δ��¼)");
                break;
            default:
                throw new IllegalStateException();
        }
    	
    	mAdapter.notifyDataSetChanged();
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




















