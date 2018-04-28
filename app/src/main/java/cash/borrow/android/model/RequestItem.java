package cash.borrow.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class RequestItem implements Parcelable {
    private String requestId;
    private String userId;
    private int msPast;
    private int amount;
    private String repaymentDate;
    private String paymentPlan;
    private double interestRate;
    private String requestType;
    private String requestReason;
    private String[] hashTags;
    private String[] atPeople;
    private String[] attachedImages;
    private String[] commentsId;
    private String StripeToken;

    public RequestItem() {
    }

    public RequestItem(String requestId, String userId, int msPast, int amount, String repaymentDate, String paymentPlan, double interestRate, String requestType, String requestReason, String[] hashTags, String[] atPeople, String[] attachedImages, String[] commentsId, String stripeToken) {
        this.requestId = requestId;
        this.userId = userId;
        this.msPast = msPast;
        this.amount = amount;
        this.repaymentDate = repaymentDate;
        this.paymentPlan = paymentPlan;
        this.interestRate = interestRate;
        this.requestType = requestType;
        this.requestReason = requestReason;
        this.hashTags = hashTags;
        this.atPeople = atPeople;
        this.attachedImages = attachedImages;
        this.commentsId = commentsId;
        StripeToken = stripeToken;
    }

    protected RequestItem(Parcel in) {
        requestId = in.readString();
        userId = in.readString();
        msPast = in.readInt();
        amount = in.readInt();
        repaymentDate = in.readString();
        paymentPlan = in.readString();
        interestRate = in.readDouble();
        requestType = in.readString();
        requestReason = in.readString();
        hashTags = in.createStringArray();
        atPeople = in.createStringArray();
        attachedImages = in.createStringArray();
        commentsId = in.createStringArray();
        StripeToken = in.readString();
    }

    public static final Creator<RequestItem> CREATOR = new Creator<RequestItem>() {
        @Override
        public RequestItem createFromParcel(Parcel in) {
            return new RequestItem(in);
        }

        @Override
        public RequestItem[] newArray(int size) {
            return new RequestItem[size];
        }
    };

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

    public int getMsPast() {
        return msPast;
    }

    public void setMsPast(int msPast) {
        this.msPast = msPast;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public String[] getHashTags() {
        return hashTags;
    }

    public void setHashTags(String[] hashTags) {
        this.hashTags = hashTags;
    }

    public String[] getAtPeople() {
        return atPeople;
    }

    public void setAtPeople(String[] atPeople) {
        this.atPeople = atPeople;
    }

    public String[] getAttachedImages() {
        return attachedImages;
    }

    public void setAttachedImages(String[] attachedImages) {
        this.attachedImages = attachedImages;
    }

    public String[] getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(String[] commentsId) {
        this.commentsId = commentsId;
    }

    public String getStripeToken() {
        return StripeToken;
    }

    @Override
    public String toString() {
        return "RequestItem{" +
                "requestId='" + requestId + '\'' +
                ", userId='" + userId + '\'' +
                ", msPast=" + msPast +
                ", amount=" + amount +
                ", repaymentDate='" + repaymentDate + '\'' +
                ", paymentPlan='" + paymentPlan + '\'' +
                ", interestRate=" + interestRate +
                ", requestType='" + requestType + '\'' +
                ", requestReason='" + requestReason + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", atPeople=" + Arrays.toString(atPeople) +
                ", attachedImages=" + Arrays.toString(attachedImages) +
                ", commentsId=" + Arrays.toString(commentsId) +
                ", StripeToken='" + StripeToken + '\'' +
                '}';
    }

    public void setStripeToken(String stripeToken) {
        StripeToken = stripeToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(requestId);
        parcel.writeString(userId);
        parcel.writeInt(msPast);
        parcel.writeInt(amount);
        parcel.writeString(repaymentDate);
        parcel.writeString(paymentPlan);
        parcel.writeDouble(interestRate);
        parcel.writeString(requestType);
        parcel.writeString(requestReason);
        parcel.writeStringArray(hashTags);
        parcel.writeStringArray(atPeople);
        parcel.writeStringArray(attachedImages);
        parcel.writeStringArray(commentsId);
        parcel.writeString(StripeToken);
    }


}
