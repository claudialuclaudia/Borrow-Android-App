package cash.borrow.android.model;

public class ProfileItem {
    private String description;
    private String content;

    public ProfileItem() {
    }

    public ProfileItem(String description, String content) {
        this.description = description;
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProfileItem{" +
                "description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
