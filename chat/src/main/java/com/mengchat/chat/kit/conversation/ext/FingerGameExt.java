package com.mengchat.chat.kit.conversation.ext;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mengchat.chat.R;
import com.mengchat.chat.app.red_envelope.RedEnvelopeActivity;
import com.mengchat.chat.kit.annotation.ExtContextMenuItem;
import com.mengchat.chat.kit.conversation.ext.core.ConversationExt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.wildfirechat.RedEnvelopeVo;
import cn.wildfirechat.message.TypingMessageContent;
import cn.wildfirechat.model.Conversation;

import static android.app.Activity.RESULT_OK;


public class FingerGameExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "[猜拳]")
    public void pickLocation(View containerView, Conversation conversation) {
        List<String> list = new ArrayList<>();
        list.add("finger1");
        list.add("finger2");
        list.add("finger3");
        Random rand = new Random();
        String fingerName = list.get(rand.nextInt(list.size()));

        conversationViewModel.sendFingerMessage(fingerName);

    }


    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_fun_finger;
    }

    @Override
    public String title(Context context) {
        return "猜拳";
    }
}

