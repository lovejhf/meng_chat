package cn.wildfirechat;

import android.os.Parcel;
import android.os.Parcelable;

public class RedEnvelopeVo implements Parcelable {
    private String redTitle;
    private String redMoney;
    private String sendLogId;
    private String formUser;
    private String sendType;
    private String receiveStatus;

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getRedTitle() {
        return redTitle;
    }

    public void setRedTitle(String redTitle) {
        this.redTitle = redTitle;
    }

    public String getRedMoney() {
        return redMoney;
    }

    public void setRedMoney(String redMoney) {
        this.redMoney = redMoney;
    }

    public String getSendLogId() {
        return sendLogId;
    }

    public void setSendLogId(String sendLogId) {
        this.sendLogId = sendLogId;
    }

    public String getFormUser() {
        return formUser;
    }

    public void setFormUser(String formUser) {
        this.formUser = formUser;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public RedEnvelopeVo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.redTitle);
        dest.writeString(this.redMoney);
        dest.writeString(this.sendLogId);
        dest.writeString(this.formUser);
        dest.writeString(this.sendType);
        dest.writeString(this.receiveStatus);
    }

    protected RedEnvelopeVo(Parcel in) {
        this.redTitle = in.readString();
        this.redMoney = in.readString();
        this.sendLogId = in.readString();
        this.formUser = in.readString();
        this.sendType = in.readString();
        this.receiveStatus = in.readString();
    }

    public static final Creator<RedEnvelopeVo> CREATOR = new Creator<RedEnvelopeVo>() {
        @Override
        public RedEnvelopeVo createFromParcel(Parcel source) {
            return new RedEnvelopeVo(source);
        }

        @Override
        public RedEnvelopeVo[] newArray(int size) {
            return new RedEnvelopeVo[size];
        }
    };
}
