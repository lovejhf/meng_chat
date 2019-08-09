package com.mengchat.chat.kit.conversationlist;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.zhouwei.library.CustomPopWindow;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.king.zxing.Intents;
import com.mengchat.chat.R;
import com.mengchat.chat.app.main.PCLoginActivity;
import com.mengchat.chat.kit.WfcScheme;
import com.mengchat.chat.kit.WfcUIKit;
import com.mengchat.chat.kit.channel.ChannelInfoActivity;
import com.mengchat.chat.kit.contact.newfriend.SearchUserActivity;
import com.mengchat.chat.kit.conversation.CreateConversationActivity;
import com.mengchat.chat.kit.conversationlist.notification.ConnectionStatusNotification;
import com.mengchat.chat.kit.conversationlist.notification.StatusNotificationViewModel;
import com.mengchat.chat.kit.group.GroupInfoActivity;
import com.mengchat.chat.kit.qrcode.QRCodeActivity;
import com.mengchat.chat.kit.qrcode.ScanQRCodeActivity;
import com.mengchat.chat.kit.user.UserInfoActivity;
import com.mengchat.chat.kit.user.UserViewModel;

import java.util.Arrays;
import java.util.List;

import cn.wildfirechat.client.ConnectionStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.ConversationInfo;
import cn.wildfirechat.model.UserInfo;

import static android.app.Activity.RESULT_OK;

public class ConversationListFragment extends Fragment {
    private static final int REQUEST_CODE_SCAN_QR_CODE = 100;

    private RecyclerView recyclerView;
    private TitleBar toolbar;
    private ConversationListAdapter adapter;
    private  DisplayMetrics dm;
    private CustomPopWindow mCustomPopWindow;
    private static final List<Conversation.ConversationType> types = Arrays.asList(Conversation.ConversationType.Single,
            Conversation.ConversationType.Group,
            Conversation.ConversationType.Channel);
    private static final List<Integer> lines = Arrays.asList(0);

    private ConversationListViewModel conversationListViewModel;
    private UserViewModel userViewModel;
    private Observer<ConversationInfo> conversationInfoObserver = new Observer<ConversationInfo>() {
        @Override
        public void onChanged(@Nullable ConversationInfo conversationInfo) {
            // just handle what we care about
            if (types.contains(conversationInfo.conversation.type) && lines.contains(conversationInfo.conversation.line)) {
                adapter.submitConversationInfo(conversationInfo);
                // scroll or not?
                // recyclerView.scrollToPosition(0);
            }
        }
    };

    private Observer<Conversation> conversationRemovedObserver = new Observer<Conversation>() {
        @Override
        public void onChanged(@Nullable Conversation conversation) {
            if (conversation == null) {
                return;
            }
            if (types.contains(conversation.type) && lines.contains(conversation.line)) {
                adapter.removeConversation(conversation);
            }
        }
    };

    // 会话同步
    private Observer<Object> settingUpdateObserver = o -> reloadConversations();

    private Observer<List<UserInfo>> userInfoLiveDataObserver = (userInfos) -> {
        adapter.updateUserInfos(userInfos);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversationlist_frament, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("消息");
        Resources resources = this.getResources();
         dm = resources.getDisplayMetrics();
        ImmersionBar.setTitleBar(getActivity(),toolbar);
        init();
        return view;
    }

    private void init() {
        toolbar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                showPopBottom();
            }
        });
        adapter = new ConversationListAdapter(this);
        conversationListViewModel = ViewModelProviders
                .of(getActivity(), new ConversationListViewModelFactory(types, lines))
                .get(ConversationListViewModel.class);
        conversationListViewModel.getConversationListAsync(types, lines)
                .observe(this, conversationInfos -> {
                    adapter.setConversationInfos(conversationInfos);
                    adapter.notifyDataSetChanged();
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        toolbar.setTitle("消息");
        DividerItemDecoration itemDecor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recyclerview_horizontal_divider));
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(adapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        conversationListViewModel.conversationInfoLiveData().observeForever(conversationInfoObserver);
        conversationListViewModel.conversationRemovedLiveData().observeForever(conversationRemovedObserver);
        conversationListViewModel.settingUpdateLiveData().observeForever(settingUpdateObserver);

        userViewModel = WfcUIKit.getAppScopeViewModel(UserViewModel.class);
        userViewModel.userInfoLiveData().observeForever(userInfoLiveDataObserver);

        StatusNotificationViewModel statusNotificationViewModel = WfcUIKit.getAppScopeViewModel(StatusNotificationViewModel.class);
        statusNotificationViewModel.statusNotificationLiveData().observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                adapter.updateStatusNotification(statusNotificationViewModel.getNotificationItems());
            }
        });
        conversationListViewModel.connectionStatusLiveData().observe(this, status -> {
            ConnectionStatusNotification connectionStatusNotification = new ConnectionStatusNotification();
            switch (status) {
                case ConnectionStatus.ConnectionStatusConnecting:
                    connectionStatusNotification.setValue("正在连接...");
                    statusNotificationViewModel.showStatusNotification(connectionStatusNotification);
                    break;
                case ConnectionStatus.ConnectionStatusConnected:
                    statusNotificationViewModel.removeStatusNotification(connectionStatusNotification);
                    break;
                case ConnectionStatus.ConnectionStatusUnconnected:
                    connectionStatusNotification.setValue("连接失败");
                    statusNotificationViewModel.showStatusNotification(connectionStatusNotification);
                    break;
                default:
                    break;
            }
        });
    }

    private void reloadConversations() {
        conversationListViewModel.getConversationListAsync(types, lines)
                .observe(this, conversationInfos -> {
                    adapter.setConversationInfos(conversationInfos);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        conversationListViewModel.conversationInfoLiveData().removeObserver(conversationInfoObserver);
        conversationListViewModel.conversationRemovedLiveData().removeObserver(conversationRemovedObserver);
        conversationListViewModel.settingUpdateLiveData().removeObserver(settingUpdateObserver);
        userViewModel.userInfoLiveData().removeObserver(userInfoLiveDataObserver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN_QR_CODE:
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    onScanPcQrCode(result);
                }
                break;
//            case REQUEST_IGNORE_BATTERY_CODE:
//                if (resultCode == RESULT_CANCELED) {
//                    Toast.makeText(this, "允许萌聊IM后台运行，更能保证消息的实时性", Toast.LENGTH_SHORT).show();
//                }
//                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    private void onScanPcQrCode(String qrcode) {
        String prefix = qrcode.substring(0, qrcode.lastIndexOf('/') + 1);
        String value = qrcode.substring(qrcode.lastIndexOf("/") + 1);
//        Uri uri = Uri.parse(value);
//        uri.getAuthority();
//        uri.getQuery()
        switch (prefix) {
            case WfcScheme.QR_CODE_PREFIX_PC_SESSION:
                pcLogin(value);
                break;
            case WfcScheme.QR_CODE_PREFIX_USER:
                showUser(value);
                break;
            case WfcScheme.QR_CODE_PREFIX_GROUP:
                joinGroup(value);
                break;
            case WfcScheme.QR_CODE_PREFIX_CHANNEL:
                subscribeChannel(value);
                break;
            default:
                Toast.makeText(getActivity(), "qrcode: " + qrcode, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void subscribeChannel(String channelId) {
        Intent intent = new Intent(getActivity(), ChannelInfoActivity.class);
        intent.putExtra("channelId", channelId);
        startActivity(intent);
    }

    private void joinGroup(String groupId) {
        Intent intent = new Intent(getActivity(), GroupInfoActivity.class);
        intent.putExtra("groupId", groupId);
        startActivity(intent);
    }
    private void showUser(String uid) {

        UserViewModel userViewModel = WfcUIKit.getAppScopeViewModel(UserViewModel.class);
        UserInfo userInfo = userViewModel.getUserInfo(uid, true);
        if (userInfo == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }

    private void pcLogin(String token) {
        Intent intent = new Intent(getActivity(), PCLoginActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }
    private void showPopBottom(){


        int width = dm.widthPixels;
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_layout1,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
           mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create();
        int y = width /2;
        mCustomPopWindow  .showAsDropDown(toolbar,y ,0);
    }


    /**
     * 添加好友
     */
    private void searchUser() {
        Intent intent = new Intent(getActivity(), SearchUserActivity.class);
        startActivity(intent);
    }

    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.add_friend:
                        searchUser();
                        break;
                    case R.id.chat:
                        createConversation();
                        break;
                    case R.id.sao:
                        startActivityForResult(new Intent(getActivity(), ScanQRCodeActivity.class), REQUEST_CODE_SCAN_QR_CODE);
                        break;
                    case R.id.qr_code:
                        UserQrCode();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.add_friend).setOnClickListener(listener);
        contentView.findViewById(R.id.chat).setOnClickListener(listener);
        contentView.findViewById(R.id.sao).setOnClickListener(listener);
        contentView.findViewById(R.id.qr_code).setOnClickListener(listener);
    }


    private void createConversation() {
        Intent intent = new Intent(getActivity(), CreateConversationActivity.class);
        startActivity(intent);
    }
    private void UserQrCode() {
        UserInfo userInfo = userViewModel.getUserInfo(userViewModel.getUserId(), false);
        String qrCodeValue = WfcScheme.QR_CODE_PREFIX_USER + userInfo.uid;
        startActivity(QRCodeActivity.buildQRCodeIntent(getActivity(), "二维码", userInfo.portrait, qrCodeValue));
    }

}
