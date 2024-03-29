package com.mengchat.chat.app.third.location.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengchat.chat.R;
import com.mengchat.chat.app.third.location.ui.activity.ShowLocationActivity;
import com.mengchat.chat.kit.annotation.EnableContextMenu;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.annotation.MessageContentType;
import com.mengchat.chat.kit.conversation.message.model.UiMessage;
import com.mengchat.chat.kit.conversation.message.viewholder.NormalMessageContentViewHolder;
import com.mengchat.chat.kit.third.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.wildfirechat.message.LocationMessageContent;

@MessageContentType(LocationMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_location_send)
@EnableContextMenu
public class LocationMessageContentViewHolder extends NormalMessageContentViewHolder {

    @Bind(R.id.locationTitleTextView)
    TextView locationTitleTextView;
    @Bind(R.id.locationImageView)
    ImageView locationImageView;

    public LocationMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        LocationMessageContent locationMessage = (LocationMessageContent) message.message.content;
        locationTitleTextView.setText(locationMessage.getTitle());

        if (locationMessage.getThumbnail() != null && locationMessage.getThumbnail().getWidth() > 0) {
            int width = locationMessage.getThumbnail().getWidth();
            int height = locationMessage.getThumbnail().getHeight();
            locationImageView.getLayoutParams().width = UIUtils.dip2Px(width > 200 ? 200 : width);
            locationImageView.getLayoutParams().height = UIUtils.dip2Px(height > 200 ? 200 : height);
            locationImageView.setImageBitmap(locationMessage.getThumbnail());
        } else {
            Glide.with(context).load(R.mipmap.default_location)
                    .apply(new RequestOptions().override(UIUtils.dip2Px(200), UIUtils.dip2Px(200)).centerCrop()).into(locationImageView);
        }
    }

    @OnClick(R.id.locationLinearLayout)
    public void onClick(View view) {
        Intent intent = new Intent(context, ShowLocationActivity.class);
        LocationMessageContent content = (LocationMessageContent) message.message.content;
        intent.putExtra("Lat", content.getLocation().getLatitude());
        intent.putExtra("Long", content.getLocation().getLongitude());
        intent.putExtra("title", content.getTitle());
        context.startActivity(intent);
    }
}
