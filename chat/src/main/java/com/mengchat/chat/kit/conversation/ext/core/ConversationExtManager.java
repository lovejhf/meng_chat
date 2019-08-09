package com.mengchat.chat.kit.conversation.ext.core;

import com.mengchat.chat.kit.conversation.ext.ExampleAudioInputExt;
import com.mengchat.chat.kit.conversation.ext.FileExt;
import com.mengchat.chat.kit.conversation.ext.FingerGameExt;
import com.mengchat.chat.kit.conversation.ext.ImageExt;
import com.mengchat.chat.kit.conversation.ext.LocationExt;
import com.mengchat.chat.kit.conversation.ext.RedPackgeExt;
import com.mengchat.chat.kit.conversation.ext.ShakeExt;
import com.mengchat.chat.kit.conversation.ext.ShootExt;
import com.mengchat.chat.kit.conversation.ext.VoipExt;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.wildfirechat.model.Conversation;

public class ConversationExtManager {
    private static ConversationExtManager instance;
    private List<ConversationExt> conversationExts;

    private ConversationExtManager() {
        conversationExts = new ArrayList<>();
        init();
    }

    public static synchronized ConversationExtManager getInstance() {
        if (instance == null) {
            instance = new ConversationExtManager();
        }
        return instance;
    }

    private void init() {
        registerExt(ImageExt.class);//照片
        registerExt(RedPackgeExt.class);//红包
        registerExt(LocationExt.class);//位置 定位
        registerExt(FingerGameExt.class);//猜拳
        registerExt(ShakeExt.class);//戳一戳
//        registerExt(VoipExt.class);//视频通话
//        registerExt(ShootExt.class);//拍摄
//        registerExt(FileExt.class);//文件
    }

    public void registerExt(Class<? extends ConversationExt> clazz) {
        Constructor constructor;
        try {
            constructor = clazz.getConstructor();
            ConversationExt ext = (ConversationExt) constructor.newInstance();
            conversationExts.add(ext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterExt(Class<? extends ConversationExt> clazz) {
        // TODO
    }

    public List<ConversationExt> getConversationExts(Conversation conversation) {
        List<ConversationExt> currentExts = new ArrayList<>();
        for (ConversationExt ext : this.conversationExts) {
            if (!ext.filter(conversation)) {
                currentExts.add(ext);
            }
        }
        return currentExts;
    }
}
