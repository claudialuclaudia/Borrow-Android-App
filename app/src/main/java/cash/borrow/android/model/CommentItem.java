package cash.borrow.android.model;

public class CommentItem {
    private String commentId;
    private String requestId;
    private String commenterId;
    private String commenterName;
    private String commentContent;
    private int lendAmount;
    private String StripeToken;

    public CommentItem() {
    }

    public CommentItem(String commentId, String requestId, String commenterId, String commenterName, String commentContent, int lendAmount, String stripeToken) {
        this.commentId = commentId;
        this.requestId = requestId;
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.commentContent = commentContent;
        this.lendAmount = lendAmount;
        StripeToken = stripeToken;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
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

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getLendAmount() {
        return lendAmount;
    }

    public void setLendAmount(int lendAmount) {
        this.lendAmount = lendAmount;
    }

    public String getStripeToken() {
        return StripeToken;
    }

    public void setStripeToken(String stripeToken) {
        StripeToken = stripeToken;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "commentId='" + commentId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", commenterId='" + commenterId + '\'' +
                ", commenterName='" + commenterName + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", lendAmount=" + lendAmount +
                ", StripeToken='" + StripeToken + '\'' +
                '}';
    }
}
