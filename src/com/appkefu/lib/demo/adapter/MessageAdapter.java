package com.appkefu.lib.demo.adapter;

import java.util.List;

import com.appkefu.lib.demo.R;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.ui.entity.KFChatEntity;
import com.appkefu.lib.ui.entity.KFConversationEntity;
import com.appkefu.lib.utils.KFImageUtils;
import com.appkefu.lib.utils.KFSLog;
import com.appkefu.lib.xmpp.XmppVCard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Administrator
 *
 */
public class MessageAdapter extends BaseAdapter {

	private static final String TAG = MessageAdapter.class.getSimpleName();
	
	private Context context;
	private List<KFChatEntity> list;
	private LayoutInflater inflater;

	public MessageAdapter(Context context, List<KFChatEntity> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// ͨ�������Ƴ�
	public void remove(KFConversationEntity entity) {
		list.remove(entity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.message_list_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.recent_userhead);
			holder.name = (TextView) convertView.findViewById(R.id.recent_name);
			holder.date = (TextView) convertView.findViewById(R.id.recent_time);
			holder.msg = (TextView) convertView.findViewById(R.id.recent_msg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final KFChatEntity entity = list.get(position);

		KFSLog.d("conv.id "+entity.getId()+ " conv.name:"+entity.getName());

		//���Ȼ�ȡ�ǳƣ�����ǳ�δ���ã�����ʾ�Է��û���
		if(entity.isSend() == 1)//�Լ����͵���Ϣ
		{
			holder.name.setText("�Լ����͵���Ϣ");
			
			//�����Լ���ͷ��
			updateVCardThread(KFSettingsManager.getSettingsManager(context).getUsername(), holder.icon);
		}
		else
		{//�յ��Է�����Ϣ
			String nickname = KFIMInterfaces.getOtherNickname(entity.getName());
			if(nickname == null)
			{
				nickname = entity.getName();
			}
			holder.name.setText("�յ�����:"+nickname+"����Ϣ");
			
			//���öԷ���ͷ��
			updateVCardThread(entity.getName(), holder.icon);
		}

		holder.name.setTextColor(Color.BLACK);
		holder.date.setText(entity.getDate());
		holder.msg.setText("��ϢID:"+entity.getId()+" ��Ϣ����:"+entity.getText());

		
		convertView.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				Log.d(TAG, "setOnCreateContextMenuListener");								
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		public ImageView icon;
		public TextView name;
		public TextView date;
		public TextView msg;
	}
	
	@SuppressLint("HandlerLeak")
	private void updateVCardThread(final String username, final ImageView avatar) {
		
		final Handler handler = new Handler() 
		{
			public void handleMessage(Message msg) 
			{
				if(msg.what == 1)
				{
					String avatarPath = (String)msg.obj;
					Bitmap bitmap = KFImageUtils.getBitmapByPath(avatarPath);
					if(bitmap != null)
						avatar.setImageBitmap(bitmap);
					else
						avatar.setImageResource(R.drawable.app_panel_friendcard_icon);
				}
				
			}
		};
		
		new Thread() 
		{
			public void run() 
			{
				Message msg = new Message();			
				
				//��ȡͷ���ַ
				String avatarPath = XmppVCard.getOthersAvatarPath(username);
		
				msg.what = 1; 
				msg.obj = avatarPath; 
								
				handler.sendMessage(msg);
			}
		}.start();
		
	}

}
