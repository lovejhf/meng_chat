package com.mengchat.chat.kit.conversationlist.notification.viewholder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.StatusNotificationType;
import com.mengchat.chat.kit.conversationlist.notification.ConnectionStatusNotification;
import com.mengchat.chat.kit.conversationlist.notification.StatusNotification;

import butterknife.Bind;
import butterknife.OnClick;

@LayoutRes(resId = R.layout.conversationlist_item_notification_connection_status)
@StatusNotificationType(ConnectionStatusNotification.class)
public class ConnectionNotificationViewHolder extends StatusNotificationViewHolder {
    public ConnectionNotificationViewHolder(Fragment fragment) {
        super(fragment);
    }

    @Bind(R.id.statusTextView)
    TextView statusTextView;

    @Override
    public void onBind(View view, StatusNotification notification) {
        String status = ((ConnectionStatusNotification) notification).getValue();
        statusTextView.setText(status);
    }

    @OnClick(R.id.statusTextView)
    public void onClick() {
        Toast.makeText(fragment.getContext(), "status on Click", Toast.LENGTH_SHORT).show();
    }
}