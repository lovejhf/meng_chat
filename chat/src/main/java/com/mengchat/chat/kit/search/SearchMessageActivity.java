package com.mengchat.chat.kit.search;

import com.mengchat.chat.kit.search.module.ConversationMessageSearchModule;

import java.util.List;

import cn.wildfirechat.model.Conversation;

public class SearchMessageActivity extends SearchActivity {
    private Conversation conversation;

    @Override
    protected void beforeViews() {
        conversation = getIntent().getParcelableExtra("conversation");
    }

    @Override
    protected void initSearchModule(List<SearchableModule> modules) {
        modules.add(new ConversationMessageSearchModule(conversation));
    }
}
