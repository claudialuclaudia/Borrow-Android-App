package cash.borrow.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class RequestItem implements Parcelable {
    private String requestId;
    private String userId;
    private String userName;
    private int secPast;
    private double amount;
    private String requestReason;
    private String image;

    public RequestItem() {
    }

    public RequestItem(String requestId, String userId, String userName, int secPast, double amount, String requestReason, String image) {

        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        if (userId == null) {
            userId = UUID.randomUUID().toString();
        }

        this.requestId = requestId;
        this.userId = userId;
        this.userName = userName;
        this.secPast = secPast;
        this.amount = amount;
        this.requestReason = requestReason;
        this.image = image;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSecPast() {
        return secPast;
    }

    public void setSecPast(int secPast) {
        this.secPast = secPast;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public String toString() {
        return "RequestItem{" +
                "requestId='" + requestId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", secPast=" + secPast +
                ", amount=" + amount +
                ", requestReason='" + requestReason + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.requestId);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeInt(this.secPast);
        dest.writeDouble(this.amount);
        dest.writeString(this.requestReason);
        dest.writeString(this.image);
    }

    protected RequestItem(Parcel in) {
        this.requestId = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.secPast = in.readInt();
        this.amount = in.readDouble();
        this.requestReason = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<RequestItem> CREATOR = new Parcelable.Creator<RequestItem>() {
        @Override
        public RequestItem createFromParcel(Parcel source) {
            return new RequestItem(source);
        }

        @Override
        public RequestItem[] newArray(int size) {
            return new RequestItem[size];
        }
    };
}
