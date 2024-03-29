package cn.wildfirechat.message.notification;

import android.content.Context;
import android.os.Parcel;
import android.os.Vibrator;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;
import cn.wildfirechat.remote.ChatManager;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_SHAKE_STATUS;

@ContentTag(type = ContentType_SHAKE_STATUS, flag = PersistFlag.Persist)
public class ShakeMessageContent extends NotificationMessageContent{

    private String receiverId;

    private String fromId;
    public ShakeMessageContent( String receiverId, String fromId) {
        this.receiverId = receiverId;
        this.fromId = fromId;

    }



    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("receiverId", receiverId);
            objWrite.put("fromId", fromId);
            payload.content = objWrite.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return payload;

    }


    @Override
    public void decode(MessagePayload payload) {
        try {
            if (payload.content != null) {
                JSONObject jsonObject = new JSONObject(payload.content);
                receiverId = jsonObject.optString("receiverId");
                fromId = jsonObject.optString("fromId");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String formatNotification(Message message) {


        String notification = "";
        if (TextUtils.equals(ChatManager.Instance().getUserId(),fromId)){
            notification="您戳"+receiverId+"一下";
        }else if (TextUtils.equals(receiverId,ChatManager.Instance().getUserId())){
            notification="%s戳了您一下";
            notification = String.format(notification, fromId);
        }


        return notification;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.receiverId);
        dest.writeString(this.fromId);
    }

    public ShakeMessageContent() {
    }

    protected ShakeMessageContent(Parcel in) {
        this.receiverId = in.readString();
        this.fromId = in.readString();
    }

    public static final Creator<ShakeMessageContent> CREATOR = new Creator<ShakeMessageContent>() {
        @Override
        public ShakeMessageContent createFromParcel(Parcel source) {
            return new ShakeMessageContent(source);
        }

        @Override
        public ShakeMessageContent[] newArray(int size) {
            return new ShakeMessageContent[size];
        }
    };
}
