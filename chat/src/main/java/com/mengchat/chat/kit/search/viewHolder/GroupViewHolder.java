package com.mengchat.chat.kit.search.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mengchat.chat.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.wildfirechat.model.GroupSearchResult;

public class GroupViewHolder extends ResultItemViewHolder<GroupSearchResult> {
    @Bind(R.id.portraitImageView)
    ImageView portraitImageView;
    @Bind(R.id.nameTextView)
    TextView nameTextView;
    @Bind(R.id.descTextView)
    TextView descTextView;

    public GroupViewHolder(Fragment fragment, View itemView) {
        super(fragment, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBind(String keyword, GroupSearchResult groupSearchResult) {
        nameTextView.setText(groupSearchResult.groupInfo.name);
        Glide.with(fragment).load(groupSearchResult.groupInfo.portrait).into(portraitImageView);

        String desc = "";
        switch (groupSearchResult.marchedType) {
            case 0:
                desc = "群名称包含: " + keyword;
                break;
            case 1:
                desc = "群成员包含: " + keyword;
                break;
            case 2:
                desc = "群名称和群成员都包含: " + keyword;
                break;
            default:
                break;
        }
        descTextView.setText(desc);
    }
}
