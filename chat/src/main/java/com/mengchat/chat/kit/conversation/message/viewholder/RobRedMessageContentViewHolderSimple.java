package com.mengchat.chat.kit.conversation.message.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;

import butterknife.Bind;
import cn.wildfirechat.message.StickerMessageContent;
import cn.wildfirechat.message.notification.RobRedMessageContent;

@MessageContentType(RobRedMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_notification)
public class RobRedMessageContentViewHolderSimple extends SimpleNotificationMessageContentViewHolder {
    @Bind(R.id.notificationTextView)
    TextView notificationTextView;

    public RobRedMessageContentViewHolderSimple(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
    }

    @Override
    protected void onBind(UiMessage message) {
        RobRedMessageContent stickerMessage = (RobRedMessageContent) message.message.content;
        notificationTextView.setText(message.message.digest());
    }
}
