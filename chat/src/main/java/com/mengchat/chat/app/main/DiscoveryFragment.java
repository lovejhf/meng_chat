package com.mengchat.chat.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.mengchat.chat.R;
import com.mengchat.chat.app.PublicWebViewClient;
import com.mengchat.chat.kit.chatroom.ChatRoomListActivity;
import com.mengchat.chat.kit.conversation.ConversationActivity;
import com.mengchat.chat.kit.widget.ProgressWebView;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfirechat.model.Conversation;

public class DiscoveryFragment extends Fragment {
    @Bind(R.id.toolbar)
    TitleBar titleBar;
    @Bind(R.id.webview)
    ProgressWebView mWebView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_discovery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(),titleBar);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mWebView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        mWebView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        mWebView.getSettings().setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebView.getSettings().setAppCacheEnabled(false);//是否使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.setWebViewClient(new PublicWebViewClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.loadUrl("http://baidu.com");
    }

    @OnClick(R.id.chatRoomOptionItemView)
    void chatRoom() {
        Intent intent = new Intent(getActivity(), ChatRoomListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.robotOptionItemView)
    void robot() {
        Intent intent = ConversationActivity.buildConversationIntent(getActivity(), Conversation.ConversationType.Single, "FireRobot", 0);
        startActivity(intent);
    }

    @OnClick(R.id.channelOptionItemView)
    void channel() {

    }
}
