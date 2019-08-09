package com.mengchat.chat.app.red;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.aries.ui.view.radius.RadiusLinearLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.mengchat.chat.R;
import com.mengchat.chat.app.BaseBean;
import com.mengchat.chat.app.Config;
import com.mengchat.chat.app.dialog.RedPackageDialog;
import com.mengchat.chat.kit.ChatManagerHolder;
import com.mengchat.chat.kit.annotation.EnableContextMenu;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;
import com.mengchat.chat.kit.conversation.message.viewholder.NormalMessageContentViewHolder;

import butterknife.Bind;
import butterknife.OnClick;
import cn.wildfirechat.message.RedPackgeMessageContent;

@MessageContentType(RedPackgeMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_red_packge)
@EnableContextMenu
public class RedPackgeMessageContentViewHolder extends NormalMessageContentViewHolder {

    @Bind(R.id.locationTitleTextView)
    TextView locationTitleTextView;
    @Bind(R.id.bg_package)
    RadiusLinearLayout bgPackage;
    @Bind(R.id.key)
    ImageView key;
    private Context  context;
    private RedPackageDialog redPackageDialog;

    public RedPackgeMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
        this.context = context;
    }

    @Override
    public void onBind(UiMessage message) {
        RedPackgeMessageContent locationMessage = (RedPackgeMessageContent) message.message.content;

        locationTitleTextView.setText(locationMessage.getRedTitle());
            //获取领取红包状态
        if (!TextUtils.equals(locationMessage.getReceiveStatus(),"2")) {
            bgPackage.getDelegate().setBackgroundColor(Color.parseColor("#FF5C5C"));
        } else {
            bgPackage.getDelegate().setBackgroundColor(Color.parseColor("#FFA4A4"));
        }


    }

    @OnClick(R.id.locationLinearLayout)
    public void onClick(View view) {
        //领取红包 调用接口  改变红包状态  通知消息更改
        RedPackgeMessageContent locationMessage = (RedPackgeMessageContent) message.message.content;

        redPackageDialog = new RedPackageDialog(context, new RedPackageDialog.onClickRedPackageListener() {
            @Override
            public void onClick() {
                //领取红包
                String url = "http://" + Config.APP_SERVER_HOST + ":" + Config.APP_SERVER_PORT + "/redManage/robRed";
                HttpParams httpParams = new HttpParams();
                httpParams.put("robUser", ChatManagerHolder.gChatManager.getUserId());
                httpParams.put("sendLogId", locationMessage.getSendLogId());
                httpParams.put("sendType",locationMessage.getSendType());//1单发 2群发
                OkGo.<String> post(url).params(httpParams).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean<String> baseBean = JSONObject.parseObject(response.body(),new TypeReference<BaseBean<String>>(){});
                        if (baseBean.getCode()==200){
                            redPackageDialog.dismiss();
                            Intent intent = new Intent(context, RedPackageDetailActivity.class);
                            context.startActivity(intent);
                            conversationViewModel.modifyRedPackageStatus(message);
                            // 被领取的红包的Uid

                            //领取人Id //接受人

                            //fromId 发送人ID
                            conversationViewModel.sendRobRedMessage(locationMessage.getSendLogId(),
                                    ChatManagerHolder.gChatManager.getUserId(),
                                    message.message.sender);
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

            }
        });

        String sender = message.message.sender;
        if (!ChatManagerHolder.gChatManager.getUserId().equals(sender)){
            //单独发自己不能领取
                //弹框
            if (!redPackageDialog.isShowing()){
                if (!TextUtils.equals("2",locationMessage.getReceiveStatus())){
                    redPackageDialog.show();
                }
            }

        }else {
            //去查看自己发的红包

            Intent intent = new Intent(context, RedPackageDetailActivity.class);
            context.startActivity(intent);
        }






    }
}
