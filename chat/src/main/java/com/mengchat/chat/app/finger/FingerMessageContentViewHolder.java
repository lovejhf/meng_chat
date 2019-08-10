package com.mengchat.chat.app.finger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.EnableContextMenu;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;
import com.mengchat.chat.kit.conversation.message.viewholder.NormalMessageContentViewHolder;


import butterknife.Bind;
import cn.wildfirechat.message.FingerMessageContent;

@MessageContentType(FingerMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_finger)
@EnableContextMenu
public class FingerMessageContentViewHolder extends NormalMessageContentViewHolder {

    @Bind(R.id.finger)
    ImageView finger;

    public FingerMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
        this.context = context;
    }

    @Override
    public void onBind(UiMessage message) {
        FingerMessageContent locationMessage = (FingerMessageContent) message.message.content;
        switch (locationMessage.getImageName()){
            case "finger1":
                Glide.with(context).load(R.mipmap.ic_finger_1).into(finger);
                break;
            case "finger2":
                Glide.with(context).load(R.mipmap.ic_finger_2).into(finger);
                break;
            case "finger3":
                Glide.with(context).load(R.mipmap.ic_finger_3).into(finger);
                break;
        }


    }
}
