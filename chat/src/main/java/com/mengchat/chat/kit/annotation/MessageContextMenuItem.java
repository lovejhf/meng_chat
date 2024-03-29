package com.mengchat.chat.kit.annotation;

import com.mengchat.chat.kit.conversation.message.model.UiMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解消息的长按菜单项
 * <p>
 * 所注解的方法，必须是public，且接受两个参数，第一个为{@link android.view.View}， 第二个为{@link UiMessage}
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageContextMenuItem {
    String title();

    /**
     * 用来唯一表示菜单项
     *
     * @return
     */
    String tag();

    int priority() default 0;

    /**
     * 是否需要二次确认
     *
     * @return
     */
    boolean confirm() default false;

    /**
     * 二次确认的提示，只有当{@link #confirm()}为true时有用
     *
     * @return
     */
    String confirmPrompt() default "";
}
