package com.mengchat.chat.app.red;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.allen.library.CircleImageView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.mengchat.chat.R;
import com.mengchat.chat.app.BaseBean;
import com.mengchat.chat.app.Config;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.wildfirechat.RedEnvelopeVo;

public class RedPackageDetailActivity extends AppCompatActivity {
    @Bind(R.id.bg_header)
    RelativeLayout bgHeader;
    @Bind(R.id.header)
    CircleImageView header;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.red_title)
    TextView redTitle;
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    private String sendLogId;
    private String robUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_package_detail);
        ButterKnife.bind(this);
        sendLogId = getIntent().getStringExtra("sendLogId");
        robUser = getIntent().getStringExtra("robUser");
        ImmersionBar.with(this).keyboardEnable(true).
                statusBarDarkFont(true).
                init();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        String url = "http://" + Config.APP_SERVER_HOST + ":" + Config.APP_SERVER_PORT + "/redManage/redListInfo";
        HttpParams httpParams = new HttpParams();
        httpParams.put("sendLogId", sendLogId);
        httpParams.put("robUser", robUser);
        OkGo.<String> post(url).params(httpParams).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                BaseBean<RedEnvelopeVo> baseBean = JSONObject.parseObject(response.body(),new TypeReference<BaseBean<RedEnvelopeVo>>(){});
                if (baseBean.getCode()==200){
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });


    }

}
