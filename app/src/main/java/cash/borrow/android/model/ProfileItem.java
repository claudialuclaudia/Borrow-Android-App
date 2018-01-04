package cash.borrow.android.model;

public class ProfileItem {
    private String description;
    private String content;
    private String textStyle;
    private String textColor;

    public ProfileItem() {
    }

    public ProfileItem(String description, String content, String textStyle, String textColor) {
        this.description = description;
        this.content = content;
        this.textStyle = textStyle;
        this.textColor = textColor;
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

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public String toString() {
        return "ProfileItem{" +
                "description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", textStyle='" + textStyle + '\'' +
                ", textColor='" + textColor + '\'' +
                '}';
    }
}
