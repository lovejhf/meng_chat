package com.mengchat.chat.kit.search.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mengchat.chat.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.wildfirechat.model.ChannelInfo;

public class ChannelViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.portraitImageView)
    ImageView portraitImageView;
    @Bind(R.id.channelNameTextView)
    TextView channelNameTextView;

    public ChannelViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(ChannelInfo channelInfo) {
        channelNameTextView.setText(channelInfo.name);
        Glide.with(itemView.getContext()).load(channelInfo.portrait).into(portraitImageView);
    }
}
