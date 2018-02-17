package cash.borrow.android.model;

/**
 * Created by LL on 2/12/2018.
 */

public class UserInfoItem {
    public String name;
    public String location;
    public String profilePicUrl;

    public UserInfoItem() {

    }

    public UserInfoItem(String name, String location, String profilePicUrl) {
        this.name = name;
        this.location = location;
        this.profilePicUrl = profilePicUrl;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePicUrl() {return profilePicUrl; }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProfilePicUrl(String profilePicUrl) { this.profilePicUrl = profilePicUrl; }
}
