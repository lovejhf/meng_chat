package com.mengchat.chat.kit.group.manage;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.contact.UserListAdapter;
import com.mengchat.chat.kit.contact.model.FooterValue;
import com.mengchat.chat.kit.contact.viewholder.footer.FooterViewHolder;


@LayoutRes(resId = R.layout.group_manage_item_add_manager)
public class AddGroupManagerViewHolder extends FooterViewHolder<FooterValue> {

    public AddGroupManagerViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(FooterValue footerValue) {
        // do nothing
    }
}
