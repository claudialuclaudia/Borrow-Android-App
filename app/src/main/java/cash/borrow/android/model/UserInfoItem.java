package cash.borrow.android.model;

/**
 * Created by LL on 2/12/2018.
 */

public class UserInfoItem {
    public String firstName;
    public String firstNameIgnoreCase;
    public String lastName;
    public String lastNameIgnoreCase;
    public String location;
    public String profilePicUrl;

    public UserInfoItem() {

    }

    public UserInfoItem(String firstName, String firstNameIgnoreCase, String lastName, String lastNameIgnoreCase, String location, String profilePicUrl) {
        this.firstName = firstName;
        this.firstNameIgnoreCase = firstNameIgnoreCase;
        this.lastName = lastName;
        this.lastNameIgnoreCase = lastNameIgnoreCase;
        this.location = location;
        this.profilePicUrl = profilePicUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFirstNameIgnoreCase() {
        return firstNameIgnoreCase;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLastNameIgnoreCase() {
        return lastNameIgnoreCase;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFirstNameIgnoreCase(String firstNameIgnoreCase) {
        this.firstNameIgnoreCase = firstNameIgnoreCase;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLastNameIgnoreCase(String lastNameIgnoreCase) {
        this.lastNameIgnoreCase = lastNameIgnoreCase;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
