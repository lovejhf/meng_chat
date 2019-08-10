package com.mengchat.chat.app.personal;

import android.os.Bundle;
import android.view.View;

import com.allen.library.CircleImageView;
import com.allen.library.SuperTextView;
import com.mengchat.chat.R;
import com.mengchat.chat.kit.WfcBaseActivity;
import com.mengchat.chat.kit.WfcScheme;
import com.mengchat.chat.kit.qrcode.QRCodeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfirechat.model.UserInfo;
import cn.wildfirechat.remote.ChatManager;

public class UserInfoActivity extends WfcBaseActivity {
    @Bind(R.id.portraitImageView)
    CircleImageView portraitImageView;
    @Bind(R.id.nickyName)
    SuperTextView nickyName;
    @Bind(R.id.meng_account)
    SuperTextView mengAccount;
    @Bind(R.id.user_qr_code)
    SuperTextView userQrCode;
    @Bind(R.id.sex)
    SuperTextView sex;
    @Bind(R.id.area)
    SuperTextView area;

    @Override
    protected int contentLayout() {
        return R.layout.activity_user_info;
    }


    @OnClick({R.id.nickyName, R.id.meng_account, R.id.user_qr_code, R.id.sex, R.id.area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nickyName:
                break;
            case R.id.meng_account:
                break;
            case R.id.user_qr_code:
                UserQrCode();
                break;
            case R.id.sex:
                break;
            case R.id.area:
                break;
        }
    }

    private void UserQrCode() {
        String uid = ChatManager.Instance().getUserId();
        UserInfo userInfo =ChatManager.Instance().getUserInfo(uid,true);
        String qrCodeValue = WfcScheme.QR_CODE_PREFIX_USER + userInfo.uid;
        startActivity(QRCodeActivity.buildQRCodeIntent(this, "二维码", userInfo.portrait, qrCodeValue));
    }
}
