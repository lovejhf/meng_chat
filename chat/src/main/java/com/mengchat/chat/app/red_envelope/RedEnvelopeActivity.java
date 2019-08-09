package com.mengchat.chat.app.red_envelope;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.aries.ui.view.radius.RadiusTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.mengchat.chat.R;
import com.mengchat.chat.app.BaseBean;
import com.mengchat.chat.app.Config;
import com.mengchat.chat.kit.ChatManagerHolder;
import com.mengchat.chat.kit.WfcBaseActivity;

import butterknife.Bind;
import cn.wildfirechat.RedEnvelopeVo;
import cn.wildfirechat.model.Conversation;

public class RedEnvelopeActivity extends WfcBaseActivity {
    @Bind(R.id.editor_money)
    EditText editorMoney;
    @Bind(R.id.envelope_txt)
    EditText envelopeTxt;
    @Bind(R.id.envelope_money)
    TextView envelopeMoney;
    @Bind(R.id.submit)
    RadiusTextView submit;
    private Conversation type;
    @Bind(R.id.num)
    EditText num;
    @Bind(R.id.package_number)
    LinearLayout packageLayout;

    @Override
    protected int contentLayout() {
        return R.layout.activity_send_envelope;
    }

    @Override
    protected void afterViews() {
        super.afterViews();
        type = getIntent().getParcelableExtra("type");
        if (type.type==Conversation.ConversationType.Group){
            packageLayout.setVisibility(View.VISIBLE);
        }else {
            packageLayout.setVisibility(View.GONE);
        }
        submit.setOnClickListener(v -> {
            String number = num.getText().toString().trim();
            String redNum ="1";
            String sendType="";
            if (TextUtils.isEmpty(number)){
                redNum = "1";
                sendType="1";
            }else {
                redNum = number;
                sendType="2";
            }
            String url = "http://" + Config.APP_SERVER_HOST + ":" + Config.APP_SERVER_PORT + "/redManage/sendRed";
            String title="";
            if (TextUtils.isEmpty(envelopeTxt.getText().toString().trim())){
                title ="恭喜发财,大吉大利";
            }else {
                title =envelopeTxt.getText().toString().trim();
            }
            HttpParams httpParams = new HttpParams();
            httpParams.put("formUser", ChatManagerHolder.gChatManager.getUserId());
            httpParams.put("redMoney", editorMoney.getText().toString().trim());
            httpParams.put("redNumber", redNum);
            httpParams.put("redTitle", title);
            httpParams.put("sendType",sendType);//1单发 2群发
            OkGo.<String> post(url).params(httpParams).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    BaseBean<RedEnvelopeVo> baseBean = JSONObject.parseObject(response.body(),new TypeReference<BaseBean<RedEnvelopeVo>>(){});
                    if (baseBean.getCode()==200){
                        Intent data = new Intent();
                        data.putExtra("redEnvelopeVo",baseBean.getData());
                        setResult(Activity.RESULT_OK, data);
                        finish();
                    }
                }
                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                }
            });


        });

    }
}
