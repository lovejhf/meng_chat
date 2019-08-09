package com.mengchat.chat.kit.channel;

import com.mengchat.chat.kit.search.SearchActivity;
import com.mengchat.chat.kit.search.SearchableModule;
import com.mengchat.chat.kit.search.module.ChannelSearchModule;

import java.util.List;

public class SearchChannelActivity extends SearchActivity {
    @Override
    protected void initSearchModule(List<SearchableModule> modules) {
        modules.add(new ChannelSearchModule());
    }
}
