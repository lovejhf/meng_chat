package cn.wildfirechat.message;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_RedPackge;

/**
 * Created by heavyrain lee on 2017/12/6.
 */

@ContentTag(type = ContentType_RedPackge, flag = PersistFlag.Persist_And_Count)
public class RedPackgeMessageContent extends MessageContent {
    private String redTitle;
    private String sendLogId;
    private String sendType;
    private String receiveStatus;
    private String redMoney;


    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
    public RedPackgeMessageContent(String title,String sendLogId,String sendType,String receiveStatus,String redMoney) {
        this.redTitle = title;
        this.sendLogId = sendLogId;
        this.sendType = sendType;
        this.receiveStatus=receiveStatus;
        this.redMoney  = redMoney;
    }

    public String getSendLogId() {
        return sendLogId;
    }

    public void setSendLogId(String sendLogId) {
        this.sendLogId = sendLogId;
    }


    public String getRedTitle() {
        return redTitle;
    }

    public void setRedTitle(String redTitle) {
        this.redTitle = redTitle;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("redTitle", redTitle);
            objWrite.put("sendLogId", sendLogId);
            objWrite.put("sendType", sendType);
            objWrite.put("receiveStatus", receiveStatus);
            objWrite.put("redMoney",redMoney);
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
                redTitle = jsonObject.optString("redTitle");
                sendLogId = jsonObject.optString("sendLogId");
                sendType = jsonObject.optString("sendType");//1单发 2 群发
                receiveStatus = jsonObject.optString("receiveStatus");//1.未领取 2.已全部领取
                redMoney = jsonObject.optString("redMoney");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String digest(Message message) {
        return redTitle;
    }


    public RedPackgeMessageContent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.redTitle);
        dest.writeString(this.sendLogId);
        dest.writeString(this.sendType);
        dest.writeString(this.receiveStatus);
    }

    protected RedPackgeMessageContent(Parcel in) {
        this.redTitle = in.readString();
        this.sendLogId = in.readString();
        this.sendType = in.readString();
        this.receiveStatus = in.readString();
    }

    public static final Creator<RedPackgeMessageContent> CREATOR = new Creator<RedPackgeMessageContent>() {
        @Override
        public RedPackgeMessageContent createFromParcel(Parcel source) {
            return new RedPackgeMessageContent(source);
        }

        @Override
        public RedPackgeMessageContent[] newArray(int size) {
            return new RedPackgeMessageContent[size];
        }
    };
}
