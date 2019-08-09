package com.mengchat.chat.app.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mengchat.chat.R;
import com.mengchat.chat.kit.ChatManagerHolder;
import cn.wildfirechat.model.UserInfo;

public class RedPackageDialog extends Dialog {
    private Context context;
    onClickRedPackageListener handler;
    public interface onClickRedPackageListener{
        public void onClick();
    }

    public RedPackageDialog(Context context,onClickRedPackageListener handler) {
        super(context, R.style.dialog_select_gender);
        this.context =  context;
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        View v = getLayoutInflater().inflate(R.layout.dialog_red_package, null);
        RelativeLayout imageView = v.findViewById(R.id.open_dialog);
        UserInfo userInfo = ChatManagerHolder.gChatManager.getUserInfo(ChatManagerHolder.gChatManager.getUserId(),false);
        ImageView portrait = v.findViewById(R.id.header_img);
        Glide.with(context).load(userInfo.portrait).into(portrait);
        TextView name = v.findViewById(R.id.name);
        name.setText(userInfo.name);
        imageView.setOnClickListener(v1 -> {
            //打开红包操作
            handler.onClick();
        });
        this.setContentView(v);
        setCanceledOnTouchOutside(true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int windowWidth = dm.widthPixels;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = windowWidth / 6 * 5;
        getWindow().setAttributes(lp);
    }
}
