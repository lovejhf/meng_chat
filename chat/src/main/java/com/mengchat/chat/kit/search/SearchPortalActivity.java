package com.mengchat.chat.kit.search;

import com.mengchat.chat.kit.search.module.ChannelSearchModule;
import com.mengchat.chat.kit.search.module.ContactSearchModule;
import com.mengchat.chat.kit.search.module.ConversationSearchModule;
import com.mengchat.chat.kit.search.module.GroupSearchViewModule;

import java.util.List;

public class SearchPortalActivity extends SearchActivity {
    @Override
    protected void initSearchModule(List<SearchableModule> modules) {

        SearchableModule module = new ContactSearchModule();
        modules.add(module);

        module = new GroupSearchViewModule();
        modules.add(module);

        module = new ConversationSearchModule();
        modules.add(module);
        modules.add(new ChannelSearchModule());
    }
}
