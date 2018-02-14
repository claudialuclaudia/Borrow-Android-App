package cash.borrow.android.model;

/**
 * Created by LL on 2/12/2018.
 */

public class UserInfoItem {
    public String name;
    public String location;

    public UserInfoItem() {

    }

    public UserInfoItem(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
