package com.mengchat.chat.kit.conversationlist.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mengchat.chat.kit.common.AppScopeViewModel;

import java.util.ArrayList;
import java.util.List;

public class StatusNotificationViewModel extends ViewModel implements AppScopeViewModel {
    private MutableLiveData<Object> statusNotificationLiveData;
    private List<StatusNotification> notificationItems;

    public LiveData<Object> statusNotificationLiveData() {
        if (statusNotificationLiveData == null) {
            statusNotificationLiveData = new MutableLiveData<>();
        }
        return statusNotificationLiveData;
    }

    public List<StatusNotification> getNotificationItems() {
        return new ArrayList<>(notificationItems);
    }

    public void showStatusNotification(StatusNotification notification) {
        if (notificationItems == null) {
            notificationItems = new ArrayList<>();
        }
        if (!notificationItems.contains(notification)) {
            notificationItems.add(notification);
        } else {
            notificationItems.set(notificationItems.indexOf(notification), notification);
        }

        if (statusNotificationLiveData != null) {
            statusNotificationLiveData.postValue(new Object());
        }
    }

    public void removeStatusNotification(StatusNotification notification) {
        if (notificationItems == null || notificationItems.isEmpty() || !notificationItems.contains(notification)) {
            return;
        }
        notificationItems.remove(notification);
        if (statusNotificationLiveData != null) {
            statusNotificationLiveData.postValue(new Object());
        }
    }
}
