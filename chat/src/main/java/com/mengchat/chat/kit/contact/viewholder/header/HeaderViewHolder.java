package com.mengchat.chat.kit.contact.viewholder.header;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mengchat.chat.kit.contact.UserListAdapter;
import com.mengchat.chat.kit.contact.model.HeaderValue;

public abstract class HeaderViewHolder<T extends HeaderValue> extends RecyclerView.ViewHolder {
    protected Fragment fragment;
    protected UserListAdapter adapter;

    public HeaderViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(itemView);
        this.fragment = fragment;
        this.adapter = adapter;
    }

    public abstract void onBind(T t);

}
