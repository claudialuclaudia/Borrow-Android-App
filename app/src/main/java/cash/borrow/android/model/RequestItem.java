package cash.borrow.android.model;

public class RequestItem {
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
}
