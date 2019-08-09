package com.mengchat.chat.kit.conversationlist.notification.viewholder;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.StatusNotificationType;
import com.mengchat.chat.kit.conversationlist.notification.PCOnlineNotification;
import com.mengchat.chat.kit.conversationlist.notification.StatusNotification;


@LayoutRes(resId = R.layout.conversationlist_item_notification_pc_online)
@StatusNotificationType(PCOnlineNotification.class)
public class PCOlineNotificationViewHolder extends StatusNotificationViewHolder {
    public PCOlineNotificationViewHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void onBind(View view, StatusNotification notification) {

    }
}
