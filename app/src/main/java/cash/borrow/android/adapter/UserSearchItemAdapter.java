package cash.borrow.android.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cash.borrow.android.R;
import cash.borrow.android.model.UserInfoItem;

/**
 * Created by LL on 2/14/2018.
 */

public class UserSearchItemAdapter extends ArrayAdapter<UserInfoItem> {

    private Activity context;
    private List<UserInfoItem> userList;

    public UserSearchItemAdapter (Activity context, List<UserInfoItem> userList){
        super(context, R.layout.user_search_item, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.user_search_item, null, true);

        TextView textViewUserName = (TextView) listViewItem.findViewById(R.id.textViewUserName);

        UserInfoItem userInfoItem = userList.get(position);

        textViewUserName.setText(userInfoItem.getName());

        return listViewItem;
    }
}
