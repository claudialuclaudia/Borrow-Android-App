package cash.borrow.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cash.borrow.android.R;
import cash.borrow.android.model.CommentItem;

public class CommentItemAdapter extends ArrayAdapter<CommentItem> {

    List<CommentItem> mCommentItems;
    LayoutInflater mInflater;

    public CommentItemAdapter(@NonNull Context context, @NonNull List<CommentItem> objects) {
        super(context, R.layout.comment_item, objects);
        mCommentItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comment_item, parent, false);
        }

        TextView comment = (TextView) convertView.findViewById(R.id.commentText);

        CommentItem item = mCommentItems.get(position);

        String commenter = item.getCommenterName();
        String commentContent;
        if (item.getLendAmount() != 0) { //lender
            commentContent = commenter + " lent $" + item.getLendAmount();
        } else { //commenter
            commentContent = commenter + ": " + item.getCommentContent();
        }
        comment.setText(commentContent);

//        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(first+second+third+fourth);
//        stringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,
//                first.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        stringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),first.length()+second.length(),
//                first.length()+second.length()+third.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#31926f")),first.length()+second.length(),
//                first.length()+second.length()+third.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#a09d9d")),
//                first.length()+second.length()+third.length()+1,
//                first.length()+second.length()+third.length()+fourth.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        stringBuilder.setSpan(new RelativeSizeSpan(0.7f),
//                first.length()+second.length()+third.length()+1,
//                first.length()+second.length()+third.length()+fourth.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        comment.setText(stringBuilder);


//        if (item.isLent()) {
//            comment.setText(item.getCommenter() + " lent $" + LentAmount + " " +
//                    timeAgo + "s ago.");
//        } else {
//            comment.setText(item.getCommenter() + " " + item.getComment() + " " + timeAgo
//                    + " ago.");
//        }

        return convertView;
    }
}
