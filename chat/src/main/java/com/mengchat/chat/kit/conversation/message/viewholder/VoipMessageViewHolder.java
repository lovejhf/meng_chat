package com.mengchat.chat.kit.conversation.message.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.WfcUIKit;
import com.mengchat.chat.kit.annotation.EnableContextMenu;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.annotation.ReceiveLayoutRes;
import com.mengchat.chat.kit.annotation.SendLayoutRes;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfirechat.message.CallStartMessageContent;

@MessageContentType(CallStartMessageContent.class)
@ReceiveLayoutRes(resId = R.layout.conversation_item_voip_receive)
@SendLayoutRes(resId = R.layout.conversation_item_voip_send)
@EnableContextMenu
public class VoipMessageViewHolder extends NormalMessageContentViewHolder {
    @Bind(R.id.contentTextView)
    TextView textView;

    public VoipMessageViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        CallStartMessageContent content = (CallStartMessageContent) message.message.content;
        if (content.getStatus() == 0) {
            textView.setText("对方未接听");
        } else if (content.getStatus() == 1) {
            textView.setText("通话中");
        } else {
            String text;
            if (content.getConnectTime() > 0) {
                long duration = (content.getEndTime() - content.getConnectTime()) / 1000;
                if (duration > 3600) {
                    text = String.format("通话时长 %d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
                } else {
                    text = String.format("通话时长 %02d:%02d", duration / 60, (duration % 60));
                }
            } else {
                text = "对方未接听";
            }
            textView.setText(text);
        }
    }

    @OnClick(R.id.contentTextView)
    public void call(View view) {
        if (((CallStartMessageContent) message.message.content).getStatus() == 1) {
            return;
        }
        WfcUIKit.onCall(context, message.message.conversation.target, true, false);
    }
}
