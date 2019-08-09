package com.mengchat.chat.kit.setting;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.mengchat.chat.R;
import com.mengchat.chat.app.Config;
import com.mengchat.chat.kit.WfcBaseActivity;
import com.mengchat.chat.kit.WfcWebViewActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class AboutActivity extends WfcBaseActivity {

    @Bind(R.id.infoTextView)
    TextView infoTextView;

    @Override
    protected int contentLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void afterViews() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String info = packageInfo.packageName + "\n"
                    + packageInfo.versionCode + " " + packageInfo.versionName + "\n"
                    + Config.IM_SERVER_HOST + " " + Config.IM_SERVER_PORT + "\n"
                    + Config.APP_SERVER_HOST + " " + Config.APP_SERVER_PORT + "\n"
                    + Config.ICE_ADDRESS + " " + Config.ICE_USERNAME + " " + Config.ICE_PASSWORD + "\n";

            infoTextView.setText(info);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.introOptionItemView)
    public void intro() {
        WfcWebViewActivity.loadUrl(this, "萌聊IM功能介绍", "https://baidu.com/");
    }

    @OnClick(R.id.agreementOptionItemView)
    public void agreement() {
        WfcWebViewActivity.loadUrl(this, "萌聊IM用户协议", "https://baidu.com/");
    }

    @OnClick(R.id.privacyOptionItemView)
    public void privacy() {
        WfcWebViewActivity.loadUrl(this, "萌聊IM个人信息保护政策", "https://baidu.com/");
    }
}
