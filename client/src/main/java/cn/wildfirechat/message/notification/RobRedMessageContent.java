package cn.wildfirechat.message.notification;

import android.os.Parcel;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;
import cn.wildfirechat.remote.ChatManager;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_RED_ENVELOPE_STATUS;

@ContentTag(type = ContentType_RED_ENVELOPE_STATUS, flag = PersistFlag.Persist)
public class RobRedMessageContent extends NotificationMessageContent{
    private String redUid;

    private String receiverId;


    private String fromId;

    public RobRedMessageContent(String redUid, String receiverId, String fromId) {
        this.redUid = redUid;
        this.receiverId = receiverId;
        this.fromId = fromId;
    }

    public String getRedUid() {
        return redUid;
    }

    public void setRedUid(String redUid) {
        this.redUid = redUid;
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
            objWrite.put("redUid", redUid);
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
                redUid = jsonObject.optString("redUid");
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
        if (fromSelf) {
            notification="您领取了%s的红包";
            notification = String.format(notification, fromId);
        } else {
            if (TextUtils.equals(ChatManager.Instance().getUserId(),fromId)){
                notification=receiverId+"领取了您的红包";
            }else if (TextUtils.equals(receiverId,ChatManager.Instance().getUserId())){
                notification="您领取了%s的红包";
                notification = String.format(notification, fromId);
            }else {
                notification=receiverId+"领取了"+fromId+"的红包";

            }

        }
        return notification;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.redUid);
        dest.writeString(this.receiverId);
        dest.writeString(this.fromId);
    }

    public RobRedMessageContent() {
    }

    protected RobRedMessageContent(Parcel in) {
        this.redUid = in.readString();
        this.receiverId = in.readString();
        this.fromId = in.readString();
    }

    public static final Creator<RobRedMessageContent> CREATOR = new Creator<RobRedMessageContent>() {
        @Override
        public RobRedMessageContent createFromParcel(Parcel source) {
            return new RobRedMessageContent(source);
        }

        @Override
        public RobRedMessageContent[] newArray(int size) {
            return new RobRedMessageContent[size];
        }
    };
}
