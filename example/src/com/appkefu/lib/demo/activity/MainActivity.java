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
 * 微客服(AppKeFu.com)
 * 
 * 微客服，集成到您App里的在线客服
 * 国内首款App里的在线客服，支持文字、表情、图片、语音聊天。 立志为移动开发者提供最好的在线客服
 * 
 * 技术交流QQ群:48661516
 * 
 * @author jack ning, http://github.com/pengjinning
 *
 */
public class MainActivity extends Activity{
		
	/**
	 * 开发文档见：
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
		
		//设置开发者调试模式，默认为true，如要关闭开发者模式，请设置为false
		KFIMInterfaces.enableDebugMode(this, true);
		//资源初始化
		initView();
		
		login();
	}
	
	private void initView() {
		
		mTitle = (TextView) findViewById(R.id.demo_title);
		mApiListView = (ListView)findViewById(R.id.api_list_view);
		mApiArray = new ArrayList<ApiEntity>();
		
		mAdapter = new MainActivityAdapter(this,  mApiArray);
		mApiListView.setAdapter(mAdapter);
		
		ApiEntity entity = new ApiEntity(1, "注册");
			mApiArray.add(entity);
		entity = new ApiEntity(2, "登录");
			mApiArray.add(entity);
		entity = new ApiEntity(3, "聊天");
			mApiArray.add(entity);
		entity = new ApiEntity(4, "发送文本消息接口");
			mApiArray.add(entity);
		entity = new ApiEntity(5, "添加好友");
			mApiArray.add(entity);
		entity = new ApiEntity(6, "好友列表");
			mApiArray.add(entity);
		entity = new ApiEntity(7, "历史聊天记录");
			mApiArray.add(entity);
		entity = new ApiEntity(8, "会话消息记录");
			mApiArray.add(entity);
		entity = new ApiEntity(9, "个人资料信息");
			mApiArray.add(entity);
		entity = new ApiEntity(10, "其他用户信息");
			mApiArray.add(entity);
		entity = new ApiEntity(11, "修改密码");
			mApiArray.add(entity);
		entity = new ApiEntity(12, "群聊");
			mApiArray.add(entity);
		entity = new ApiEntity(13, "消息通知设置");
			mApiArray.add(entity);
		entity = new ApiEntity(14, "退出登录");
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
							MainActivity.this,//上下文Context
							"admin",//对方用户名
							"自定义窗口标题");//自定义会话窗口标题
					
					break;
				case 4:
					//自定义：发送文本消息接口
					//KFIMInterfaces.sendTextMessage(String msgContent, String toUsername, String type, Context context)
					//其中：msgContent: 聊天消息内容，toUsername: 对方用户名，type:一对一聊天固定为"chat",不能修改
					//例如：给用户admin发送文本消息“自定义消息内容”为：
					
					//判断当前用户是否已经登录，true：已经登录，false：未登录
					if(!KFIMInterfaces.isLogin())
					{
						Toast.makeText(MainActivity.this, "未登录,不能发送消息", Toast.LENGTH_SHORT).show();
						return;
					}
					
					new AlertDialog.Builder(MainActivity.this)
					.setMessage("确定要发送自定义消息内容？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {						
									
									KFIMInterfaces.sendTextMessage(MainActivity.this, "自定义消息内容_android", "admin", "chat");	
									Toast.makeText(MainActivity.this, "自定义消息已发送", Toast.LENGTH_SHORT).show();
								}
							})
							.setNegativeButton("取消", null)
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
					//他人（"admin"）个人资料信息，实际操作时需要将admin替换为对方真实的用户名
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
			mApiArray.get(1).setApiName("登录("+mSettingsMgr.getUsername()+"已登录)");
		}
		else
		{
			mTitle.setText("微客服IM Demo(未连接)");
			mApiArray.get(1).setApiName("登录(未登录)");
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
			KFIMInterfaces.login(this, mSettingsMgr.getUsername(), mSettingsMgr.getPassword());
    }
    
    //根据监听到的连接变化情况更新界面显示
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	mTitle.setText("微客服IM Demo("+mSettingsMgr.getUsername()+")");
            	mApiArray.get(1).setApiName("登录("+mSettingsMgr.getUsername()+"已登录)");
                break;
            case KFXmppManager.DISCONNECTED:
            	mTitle.setText("微客服IM Demo(未连接)");
            	mApiArray.get(1).setApiName("登录(未登录)");
                break;
            case KFXmppManager.CONNECTING:
            	mTitle.setText("微客服IM Demo(登录中...)");
            	mApiArray.get(1).setApiName("登录(未登录)");
            	break;
            case KFXmppManager.DISCONNECTING:
            	mTitle.setText("微客服IM Demo(登出中...)");
            	mApiArray.get(1).setApiName("登录(未登录)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	mTitle.setText("微客服IM Demo(等待中)");
            	mApiArray.get(1).setApiName("登录(未登录)");
                break;
            default:
                throw new IllegalStateException();
        }
    	
    	mAdapter.notifyDataSetChanged();
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




















