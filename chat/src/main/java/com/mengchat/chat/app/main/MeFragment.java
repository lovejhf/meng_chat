package com.mengchat.chat.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.allen.library.CircleImageView;
import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.king.zxing.Intents;
import com.mengchat.chat.R;
import com.mengchat.chat.app.personal.UserInfoActivity;
import com.mengchat.chat.kit.WfcScheme;
import com.mengchat.chat.kit.WfcUIKit;
import com.mengchat.chat.kit.channel.ChannelInfoActivity;
import com.mengchat.chat.kit.group.GroupInfoActivity;
import com.mengchat.chat.kit.qrcode.ScanQRCodeActivity;
import com.mengchat.chat.kit.user.UserViewModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfirechat.model.UserInfo;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends Fragment {

    @Bind(R.id.toolbar)
    TitleBar titleBar;
    @Bind(R.id.portraitImageView)
    CircleImageView portraitImageView;
    @Bind(R.id.nameTextView)
    TextView nameTextView;
    @Bind(R.id.accountTextView)
    TextView accountTextView;
    @Bind(R.id.meLinearLayout)
    LinearLayout meLinearLayout;
    @Bind(R.id.wallet)
    SuperTextView wallet;
    @Bind(R.id.qr_sao)
    SuperTextView qrSao;
    @Bind(R.id.new_message)
    SuperTextView newMessage;
    @Bind(R.id.safe)
    SuperTextView safe;
    @Bind(R.id.setting)
    SuperTextView setting;
    @Bind(R.id.help)
    SuperTextView help;
    @Bind(R.id.feed_back)
    SuperTextView feedBack;
    @Bind(R.id.report)
    SuperTextView report;
    private UserViewModel userViewModel;
    private UserInfo userInfo;

    private static final int REQUEST_CODE_SCAN_QR_CODE = 100;
    private Observer<List<UserInfo>> userInfoLiveDataObserver = new Observer<List<UserInfo>>() {
        @Override
        public void onChanged(@Nullable List<UserInfo> userInfos) {
            if (userInfos == null) {
                return;
            }
            for (UserInfo info : userInfos) {
                if (info.uid.equals(userViewModel.getUserId())) {
                    userInfo = info;
                    updateUserInfo(userInfo);
                    break;
                }
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), titleBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_me, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void updateUserInfo(UserInfo userInfo) {
        Glide.with(this).load(userInfo.portrait).apply(new RequestOptions().placeholder(R.mipmap.avatar_def).centerCrop()).into(portraitImageView);
        nameTextView.setText(userInfo.displayName);
        accountTextView.setText("账号: " + userInfo.name);
    }

    private void init() {
        userViewModel = WfcUIKit.getAppScopeViewModel(UserViewModel.class);
        userViewModel.getUserInfoAsync(userViewModel.getUserId(), true)
                .observe(this, info -> {
                    userInfo = info;
                    if (userInfo != null) {
                        updateUserInfo(userInfo);
                    }
                });
        userViewModel.userInfoLiveData().observeForever(userInfoLiveDataObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userViewModel.userInfoLiveData().removeObserver(userInfoLiveDataObserver);
    }

    @OnClick(R.id.meLinearLayout)
    void showMyInfo() {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }

    @OnClick({R.id.wallet, R.id.qr_sao, R.id.new_message, R.id.safe, R.id.setting, R.id.help, R.id.feed_back, R.id.report})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.wallet:
                break;
            case R.id.qr_sao:
                startActivityForResult(new Intent(getActivity(), ScanQRCodeActivity.class), REQUEST_CODE_SCAN_QR_CODE);
                break;
            case R.id.new_message:
                break;
            case R.id.safe:
                break;
            case R.id.setting:
                break;
            case R.id.help:
                break;
            case R.id.feed_back:
                break;
            case R.id.report:
                break;
        }
    }

//    @OnClick(R.id.settintOptionItemView)
//    void setting() {
//        Intent intent = new Intent(getActivity(), SettingActivity.class);
//        startActivity(intent);
//    }

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
}
