package com.mengchat.chat.kit.conversation.message.viewholder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.GlideApp;
import com.mengchat.chat.kit.annotation.EnableContextMenu;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.annotation.MessageContextMenuItem;
import com.mengchat.chat.kit.conversation.forward.ForwardActivity;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;
import com.mengchat.chat.kit.third.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.wildfirechat.message.StickerMessageContent;

@MessageContentType(StickerMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_sticker_send)
@EnableContextMenu
public class ExampleRichNotificationMessageContentViewHolder extends NotificationMessageContentViewHolder {
    private String path;
    @Bind(R.id.stickerImageView)
    ImageView imageView;

    public ExampleRichNotificationMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message, int position) {
        StickerMessageContent stickerMessage = (StickerMessageContent) message.message.content;
        imageView.getLayoutParams().width = UIUtils.dip2Px(stickerMessage.width > 150 ? 150 : stickerMessage.width);
        imageView.getLayoutParams().height = UIUtils.dip2Px(stickerMessage.height > 150 ? 150 : stickerMessage.height);

        if (!TextUtils.isEmpty(stickerMessage.localPath)) {
            if (stickerMessage.localPath.equals(path)) {
                return;
            }
            GlideApp.with(context).load(stickerMessage.localPath)
                    .into(imageView);
            path = stickerMessage.localPath;
        } else {
            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
            progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);
            progressDrawable.start();
            GlideApp.with(context)
                    .load(stickerMessage.remoteUrl)
                    .placeholder(progressDrawable)
                    .into(imageView);
        }
    }

    @OnClick(R.id.stickerImageView)
    public void onClick(View view) {
        Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String itemTitle) {
        return false;
    }

    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_FORWARD, title = "转发", priority = 11)
    public void forwardMessage(View itemView, UiMessage message) {
        Intent intent = new Intent(context, ForwardActivity.class);
        intent.putExtra("message", message.message);
        context.startActivity(intent);
    }
}
