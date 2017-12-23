package cash.borrow.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cash.borrow.android.model.UserItem;

public class UserItemAdapter extends ArrayAdapter<UserItem> {

    List<UserItem> mUserItems;
    LayoutInflater mInflater;

    public UserItemAdapter(@NonNull Context context, @NonNull List<UserItem> objects) {
        super(context, R.layout.user_item, objects);

        mUserItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.user_item, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.userNameText);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.profile_image);

        UserItem item = mUserItems.get(position);

        userName.setText(item.getUserName());
        imageView.setImageResource(R.drawable.kelly);

        return convertView;
    }
}
