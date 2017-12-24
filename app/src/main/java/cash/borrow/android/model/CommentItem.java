package cash.borrow.android.model;

public class CommentItem {
    private String requestId;
    private String commenterId;
    private String commenter;
    private int secPast;
    private boolean lent;
    private double lentAmount;
    private String comment;
    private String commentId;

    public CommentItem() {
    }

    public CommentItem(String requestId, String commenterId, String commenter, int secPast, boolean lent, double lentAmount, String comment, String commentId) {
        this.requestId = requestId;
        this.commenterId = commenterId;
        this.commenter = commenter;
        this.secPast = secPast;
        this.lent = lent;
        this.lentAmount = lentAmount;
        this.comment = comment;
        this.commentId = commentId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public int getSecPast() {
        return secPast;
    }

    public void setSecPast(int secPast) {
        this.secPast = secPast;
    }

    public boolean isLent() {
        return lent;
    }

    public void setLent(boolean lent) {
        this.lent = lent;
    }

    public double getLentAmount() {
        return lentAmount;
    }

    public void setLentAmount(double lentAmount) {
        this.lentAmount = lentAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "requestId='" + requestId + '\'' +
                ", commenterId='" + commenterId + '\'' +
                ", commenter='" + commenter + '\'' +
                ", secPast='" + secPast + '\'' +
                ", lent=" + lent +
                ", lentAmount=" + lentAmount +
                ", comment='" + comment + '\'' +
                ", commentId='" + commentId + '\'' +
                '}';
    }
}
