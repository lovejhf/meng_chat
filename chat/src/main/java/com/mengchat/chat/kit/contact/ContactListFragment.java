package com.mengchat.chat.kit.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.mengchat.chat.app.main.MainActivity;
import com.mengchat.chat.kit.WfcUIKit;
import com.mengchat.chat.kit.channel.ChannelListActivity;
import com.mengchat.chat.kit.contact.model.ContactCountFooterValue;
import com.mengchat.chat.kit.contact.model.FriendRequestValue;
import com.mengchat.chat.kit.contact.model.GroupValue;
import com.mengchat.chat.kit.contact.model.UIUserInfo;
import com.mengchat.chat.kit.contact.newfriend.FriendRequestListActivity;
import com.mengchat.chat.kit.contact.newfriend.SearchUserActivity;
import com.mengchat.chat.kit.contact.viewholder.add_friend.AddFriendRequestViewHolder;
import com.mengchat.chat.kit.contact.viewholder.footer.ContactCountViewHolder;
import com.mengchat.chat.kit.contact.viewholder.header.FriendRequestViewHolder;
import com.mengchat.chat.kit.contact.viewholder.header.GroupViewHolder;
import com.mengchat.chat.kit.group.GroupListActivity;
import com.mengchat.chat.kit.user.UserInfoActivity;
import com.mengchat.chat.kit.user.UserViewModel;
import com.mengchat.chat.kit.widget.QuickIndexBar;

import java.util.List;

import cn.wildfirechat.model.UserInfo;

public class ContactListFragment extends BaseUserListFragment implements QuickIndexBar.OnLetterUpdateListener {
    private UserViewModel userViewModel;

    private Observer<Integer> friendRequestUpdateLiveDataObserver = count -> {
        FriendRequestValue requestValue = new FriendRequestValue(count == null ? 0 : count);
        userListAdapter.updateHeader(0, requestValue);
    };

    private Observer<Object> contactListUpdateLiveDataObserver = o -> {
        loadContacts();
    };

    private void loadContacts() {
        contactViewModel.getContactsAsync(false)
                .observe(this, userInfos -> {
                    userListAdapter.setUsers(userInfoToUIUserInfo(userInfos));
                    userListAdapter.notifyDataSetChanged();

                    for (UserInfo info : userInfos) {
                        if (info.name == null || info.displayName == null) {
                            userViewModel.getUserInfo(info.uid, true);
                        }
                    }
                });
    }

    private Observer<List<UserInfo>> userInfoLiveDataObserver = userInfos -> {
        userListAdapter.updateContacts(userInfoToUIUserInfo(userInfos));
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        contactViewModel.contactListUpdatedLiveData().observeForever(contactListUpdateLiveDataObserver);
        contactViewModel.friendRequestUpdatedLiveData().observeForever(friendRequestUpdateLiveDataObserver);

        userViewModel = WfcUIKit.getAppScopeViewModel(UserViewModel.class);
        userViewModel.userInfoLiveData().observeForever(userInfoLiveDataObserver);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactViewModel.contactListUpdatedLiveData().removeObserver(contactListUpdateLiveDataObserver);
        contactViewModel.friendRequestUpdatedLiveData().removeObserver(friendRequestUpdateLiveDataObserver);
        userViewModel.userInfoLiveData().removeObserver(userInfoLiveDataObserver);
    }

    @Override
    public void initHeaderViewHolders() {
        addHeaderViewHolder(FriendRequestViewHolder.class, new FriendRequestValue(contactViewModel.getUnreadFriendRequestCount()));//好友申请

        addHeaderViewHolder(AddFriendRequestViewHolder.class, new FriendRequestValue(contactViewModel.getUnreadFriendRequestCount()));//好友申请

        addHeaderViewHolder(GroupViewHolder.class, new GroupValue());//我的群聊

//        addHeaderViewHolder(ChannelViewHolder.class, new HeaderValue());//频道
    }

    @Override
    public void initFooterViewHolders() {
        addFooterViewHolder(ContactCountViewHolder.class, new ContactCountFooterValue());
    }

    @Override
    public void onUserClick(UIUserInfo userInfo) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra("userInfo", userInfo.getUserInfo());
        startActivity(intent);
    }

    @Override
    public void onHeaderClick(int index) {
        switch (index) {

            case 0:
                ((MainActivity) getActivity()).hideUnreadFriendRequestBadgeView();
                showFriendRequest();
                break;
            case 1://添加好友
                addFriend();
                break;
            case 2:
                showGroupList();
                break;
            case 3:
                showChannelList();
                break;
        }
    }

    private void addFriend() {
        Intent intent = new Intent(getActivity(), SearchUserActivity.class);
        startActivity(intent);
    }

    private void showFriendRequest() {
        FriendRequestValue value = new FriendRequestValue(0);
        userListAdapter.updateHeader(0, value);

        contactViewModel.clearUnreadFriendRequestStatus();
        Intent intent = new Intent(getActivity(), FriendRequestListActivity.class);
        startActivity(intent);
    }

    private void showGroupList() {
        Intent intent = new Intent(getActivity(), GroupListActivity.class);
        startActivity(intent);
    }

    private void showChannelList() {
        Intent intent = new Intent(getActivity(), ChannelListActivity.class);
        startActivity(intent);
    }
}