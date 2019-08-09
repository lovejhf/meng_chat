package com.mengchat.chat.kit.conversation.message.viewholder;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;

import butterknife.Bind;
import cn.wildfirechat.message.core.MessageStatus;
import cn.wildfirechat.message.notification.AddGroupMemberNotificationContent;
import cn.wildfirechat.message.notification.ChangeGroupNameNotificationContent;
import cn.wildfirechat.message.notification.ChangeGroupPortraitNotificationContent;
import cn.wildfirechat.message.notification.CreateGroupNotificationContent;
import cn.wildfirechat.message.notification.DismissGroupNotificationContent;
import cn.wildfirechat.message.notification.GroupJoinTypeNotificationContent;
import cn.wildfirechat.message.notification.GroupMuteNotificationContent;
import cn.wildfirechat.message.notification.GroupPrivateChatNotificationContent;
import cn.wildfirechat.message.notification.GroupSetManagerChatNotificationContent;
import cn.wildfirechat.message.notification.KickoffGroupMemberNotificationContent;
import cn.wildfirechat.message.notification.ModifyGroupAliasNotificationContent;
import cn.wildfirechat.message.notification.NotificationMessageContent;
import cn.wildfirechat.message.notification.QuitGroupNotificationContent;
import cn.wildfirechat.message.notification.RecallMessageContent;
import cn.wildfirechat.message.notification.RobRedMessageContent;
import cn.wildfirechat.message.notification.ShakeMessageContent;
import cn.wildfirechat.message.notification.TipNotificationContent;
import cn.wildfirechat.message.notification.TransferGroupOwnerNotificationContent;

@MessageContentType(value = {
        AddGroupMemberNotificationContent.class,
        ChangeGroupNameNotificationContent.class,
        ChangeGroupPortraitNotificationContent.class,
        CreateGroupNotificationContent.class,
        DismissGroupNotificationContent.class,
        DismissGroupNotificationContent.class,
        KickoffGroupMemberNotificationContent.class,
        ModifyGroupAliasNotificationContent.class,
        QuitGroupNotificationContent.class,
        TransferGroupOwnerNotificationContent.class,
        TipNotificationContent.class,
        RecallMessageContent.class,
        GroupMuteNotificationContent.class,
        GroupPrivateChatNotificationContent.class,
        GroupJoinTypeNotificationContent.class,
        GroupSetManagerChatNotificationContent.class,
        RobRedMessageContent.class,
        ShakeMessageContent.class,

})
@LayoutRes(resId = R.layout.conversation_item_notification)
/**
 * 小灰条消息, 居中显示，且不显示发送者，用于简单通知，如果需要扩展成复杂通知，可以参考 {@link ExampleRichNotificationMessageContentViewHolder}
 *
 */
public class SimpleNotificationMessageContentViewHolder extends MessageContentViewHolder {

    @Bind(R.id.notificationTextView)
    TextView notificationTextView;

    public SimpleNotificationMessageContentViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message, int position) {
        super.onBind(message, position);
        onBind(message);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        return true;
    }

    protected void onBind(UiMessage message) {
        String notification;
        try {
            notification = ((NotificationMessageContent) message.message.content).formatNotification(message.message);
        } catch (Exception e) {
            e.printStackTrace();
            notification = "message is invalid";
        }
        if (notification.contains("戳")){
            Log.e("------",message.message.status+notification);
            if (message.message.status!=MessageStatus.Readed&&message.message.status!=MessageStatus.Sent){

                Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
                vibrator.vibrate(pattern,-1);
            }


        }
        notificationTextView.setText(notification);
    }
}
