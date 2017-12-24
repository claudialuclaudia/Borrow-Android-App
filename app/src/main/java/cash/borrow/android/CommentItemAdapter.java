package cash.borrow.android;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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

        comment.setText(item.getCommenter() + ": " + item.getComment() + " " + item.getSecPast()
                + "s ago.");

        return convertView;
    }
}
