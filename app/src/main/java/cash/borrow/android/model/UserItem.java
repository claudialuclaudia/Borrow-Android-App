package cash.borrow.android.model;

public class UserItem {
    private String userId;
    private String userName;
    private String userImage;
    private int friendsAmount;
    private String houseAddress;
    private String townAddress;
    private String jobTitle;
    private double creditLimit;
    private double imbalance;

    public UserItem() {
    }

    public UserItem(String userId, String userName, String userImage, int friendsAmount, String houseAddress, String townAddress, String jobTitle, double creditLimit, double imbalance) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.friendsAmount = friendsAmount;
        this.houseAddress = houseAddress;
        this.townAddress = townAddress;
        this.jobTitle = jobTitle;
        this.creditLimit = creditLimit;
        this.imbalance = imbalance;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getFriendsAmount() {
        return friendsAmount;
    }

    public void setFriendsAmount(int friendsAmount) {
        this.friendsAmount = friendsAmount;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getTownAddress() {
        return townAddress;
    }

    public void setTownAddress(String townAddress) {
        this.townAddress = townAddress;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getImbalance() {
        return imbalance;
    }

    public void setImbalance(double imbalance) {
        this.imbalance = imbalance;
    }

    @Override
    public String toString() {
        return "UserItem{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", friendsAmount=" + friendsAmount +
                ", houseAddress='" + houseAddress + '\'' +
                ", townAddress='" + townAddress + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", creditLimit=" + creditLimit +
                ", imbalance=" + imbalance +
                '}';
    }
}
