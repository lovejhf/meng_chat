package com.mengchat.chat.kit.contact.newfriend;

import com.mengchat.chat.kit.search.SearchActivity;
import com.mengchat.chat.kit.search.SearchableModule;

import java.util.List;

public class SearchUserActivity extends SearchActivity {

    @Override
    protected void beforeViews() {
    }

    @Override
    protected void initSearchModule(List<SearchableModule> modules) {
        modules.add(new UserSearchModule());
    }
}
