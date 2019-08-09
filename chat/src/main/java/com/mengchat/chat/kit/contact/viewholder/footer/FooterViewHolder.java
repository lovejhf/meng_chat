package com.mengchat.chat.kit.contact.viewholder.footer;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mengchat.chat.kit.WfcUIKit;
import com.mengchat.chat.kit.contact.ContactViewModel;
import com.mengchat.chat.kit.contact.UserListAdapter;
import com.mengchat.chat.kit.contact.model.FooterValue;

public abstract class FooterViewHolder<T extends FooterValue> extends RecyclerView.ViewHolder {
    protected Fragment fragment;
    protected UserListAdapter adapter;
    protected ContactViewModel contactViewModel;

    public FooterViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(itemView);
        this.fragment = fragment;
        this.adapter = adapter;
        contactViewModel = WfcUIKit.getAppScopeViewModel(ContactViewModel.class);
    }


    public abstract void onBind(T t);

}
