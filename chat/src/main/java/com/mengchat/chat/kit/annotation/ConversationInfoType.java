package com.mengchat.chat.kit.annotation;

import com.mengchat.chat.kit.conversationlist.viewholder.ConversationViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.wildfirechat.model.Conversation;

/**
 * 用户设置会话UI({@link ConversationViewHolder})和会话({@link Conversation.ConversationType} + 会话线路)之间的对应关系
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConversationInfoType {
    Conversation.ConversationType type();

    int line();
}
