package com.mengchat.chat.kit.conversation.ext;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mengchat.chat.R;
import com.mengchat.chat.app.BaseApp;
import com.mengchat.chat.app.red_envelope.RedEnvelopeActivity;
import com.mengchat.chat.kit.ChatManagerHolder;
import com.mengchat.chat.kit.annotation.ExtContextMenuItem;
import com.mengchat.chat.kit.conversation.ext.core.ConversationExt;

import cn.wildfirechat.RedEnvelopeVo;
import cn.wildfirechat.message.RedPackgeMessageContent;
import cn.wildfirechat.message.TypingMessageContent;
import cn.wildfirechat.message.notification.RobRedMessageContent;
import cn.wildfirechat.message.notification.ShakeMessageContent;
import cn.wildfirechat.model.Conversation;

import static android.app.Activity.RESULT_OK;


public class ShakeExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "[戳一戳]")
    public void pickLocation(View containerView, Conversation conversation) {
            //发送抖动戳一戳
        conversationViewModel.sendshakeMessage(conversation.target,ChatManagerHolder.gChatManager.getUserId());

    }


    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_fun_shake;
    }

    @Override
    public String title(Context context) {
        return "戳一戳";
    }
}

