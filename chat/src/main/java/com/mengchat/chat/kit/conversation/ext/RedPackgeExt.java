package com.mengchat.chat.kit.conversation.ext;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mengchat.chat.R;
import com.mengchat.chat.app.red_envelope.RedEnvelopeActivity;
import com.mengchat.chat.kit.annotation.ExtContextMenuItem;
import com.mengchat.chat.kit.conversation.ext.core.ConversationExt;

import cn.wildfirechat.RedEnvelopeVo;
import cn.wildfirechat.message.TypingMessageContent;
import cn.wildfirechat.model.Conversation;

import static android.app.Activity.RESULT_OK;


public class RedPackgeExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "[红包]")
    public void pickLocation(View containerView, Conversation conversation) {

        Intent intent = new Intent(context, RedEnvelopeActivity.class);
        intent.putExtra("type",conversation);
        startActivityForResult(intent, 100);
        TypingMessageContent content = new TypingMessageContent(TypingMessageContent.TYPING_RED);
        conversationViewModel.sendMessage(content);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            RedEnvelopeVo locationData =  data.getParcelableExtra("redEnvelopeVo");
            conversationViewModel.sendRedPackge(locationData);
        }
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_red_packge;
    }

    @Override
    public String title(Context context) {
        return "红包";
    }
}

