package com.mengchat.chat.app.red;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.allen.library.CircleImageView;
import com.gyf.barlibrary.ImmersionBar;
import com.mengchat.chat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RedPackageDetailActivity extends AppCompatActivity {
    @Bind(R.id.bg_header)
    RelativeLayout bgHeader;
    @Bind(R.id.header)
    CircleImageView header;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.red_title)
    TextView redTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_package_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).keyboardEnable(true).
                statusBarDarkFont(true).
                init();
    }

}
