package com.mengchat.chat.kit.annotation;

import com.mengchat.chat.kit.conversation.message.viewholder.MessageContentViewHolder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.wildfirechat.message.MessageContent;

/**
 * 用于设置消息UI({@link MessageContentViewHolder})和消息体({@link MessageContent})对应关系
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MessageContentType {
    Class<? extends MessageContent>[] value();
}
