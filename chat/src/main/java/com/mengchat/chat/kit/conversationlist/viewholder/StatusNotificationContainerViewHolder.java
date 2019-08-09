package com.mengchat.chat.kit.conversationlist.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mengchat.chat.R;
import com.mengchat.chat.kit.annotation.LayoutRes;
import com.mengchat.chat.kit.conversationlist.notification.StatusNotification;
import com.mengchat.chat.kit.conversationlist.notification.StatusNotificationManager;
import com.mengchat.chat.kit.conversationlist.notification.viewholder.StatusNotificationViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatusNotificationContainerViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.notificationContainerLayout)
    LinearLayout containerLayout;

    public StatusNotificationContainerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Fragment fragment, View itemView, List<StatusNotification> statusNotifications) {
        LayoutInflater layoutInflater = LayoutInflater.from(fragment.getContext());

        containerLayout.removeAllViews();
        StatusNotificationViewHolder statusNotificationViewHolder;
        View view;
        for (StatusNotification notification : statusNotifications) {
            try {
                Class<? extends StatusNotificationViewHolder> holderClass = StatusNotificationManager.getInstance().getNotificationViewHolder(notification);
                Constructor constructor = holderClass.getConstructor(Fragment.class);
                statusNotificationViewHolder = (StatusNotificationViewHolder) constructor.newInstance(fragment);
                LayoutRes layoutRes = holderClass.getAnnotation(LayoutRes.class);
                view = layoutInflater.inflate(layoutRes.resId(), (ViewGroup) itemView, false);
                ButterKnife.bind(statusNotificationViewHolder, view);

                statusNotificationViewHolder.onBind(view, notification);
                containerLayout.addView(view);
                // TODO add divider
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
