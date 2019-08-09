package com.mengchat.chat.kit.contact.pick.viewholder;

import android.view.View;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.contact.model.UIUserInfo;
import com.mengchat.chat.kit.contact.pick.CheckableUserListAdapter;
import com.mengchat.chat.kit.contact.viewholder.UserViewHolder;

import butterknife.Bind;

public class CheckableUserViewHolder extends UserViewHolder {
    @Bind(R.id.checkbox)
    CheckBox checkBox;

    public CheckableUserViewHolder(Fragment fragment, CheckableUserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(UIUserInfo userInfo) {
        super.onBind(userInfo);

        checkBox.setVisibility(View.VISIBLE);
        if (!userInfo.isCheckable()) {
            checkBox.setEnabled(false);
            checkBox.setChecked(true);
        } else {
            checkBox.setEnabled(true);
            checkBox.setChecked(userInfo.isChecked());
        }
        checkBox.setEnabled(userInfo.isCheckable());
    }
}
