package cash.borrow.android.model;

public class ProfileItem {
    private String userId;
    private String userName;
    private String userImage;
    private int friendsAmount;
    private String houseAddress;
    private String townAddress;
    private String jobTitle;
    private double creditLimit;
    private double imbalance;
    private int numOfReqBorrowed;
    private int numOfReqReturned;
    private int amountOfReqBorrowed;
    private int amountOfReqReturned;

    public ProfileItem() {
    }

    public ProfileItem(String userId, String userName, String userImage, int friendsAmount, String houseAddress, String townAddress, String jobTitle, double creditLimit, double imbalance, int numOfReqBorrowed, int numOfReqReturned, int amountOfReqBorrowed, int amountOfReqReturned) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.friendsAmount = friendsAmount;
        this.houseAddress = houseAddress;
        this.townAddress = townAddress;
        this.jobTitle = jobTitle;
        this.creditLimit = creditLimit;
        this.imbalance = imbalance;
        this.numOfReqBorrowed = numOfReqBorrowed;
        this.numOfReqReturned = numOfReqReturned;
        this.amountOfReqBorrowed = amountOfReqBorrowed;
        this.amountOfReqReturned = amountOfReqReturned;
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

    public int getNumOfReqBorrowed() {
        return numOfReqBorrowed;
    }

    public void setNumOfReqBorrowed(int numOfReqBorrowed) {
        this.numOfReqBorrowed = numOfReqBorrowed;
    }

    public int getNumOfReqReturned() {
        return numOfReqReturned;
    }

    public void setNumOfReqReturned(int numOfReqReturned) {
        this.numOfReqReturned = numOfReqReturned;
    }

    public int getAmountOfReqBorrowed() {
        return amountOfReqBorrowed;
    }

    public void setAmountOfReqBorrowed(int amountOfReqBorrowed) {
        this.amountOfReqBorrowed = amountOfReqBorrowed;
    }

    public int getAmountOfReqReturned() {
        return amountOfReqReturned;
    }

    public void setAmountOfReqReturned(int amountOfReqReturned) {
        this.amountOfReqReturned = amountOfReqReturned;
    }

    @Override
    public String toString() {
        return "ProfileItem{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", friendsAmount=" + friendsAmount +
                ", houseAddress='" + houseAddress + '\'' +
                ", townAddress='" + townAddress + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", creditLimit=" + creditLimit +
                ", imbalance=" + imbalance +
                ", numOfReqBorrowed=" + numOfReqBorrowed +
                ", numOfReqReturned=" + numOfReqReturned +
                ", amountOfReqBorrowed=" + amountOfReqBorrowed +
                ", amountOfReqReturned=" + amountOfReqReturned +
                '}';
    }
}
