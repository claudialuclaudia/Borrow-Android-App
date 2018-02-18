package cash.borrow.android.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;


import java.util.List;

import cash.borrow.android.R;
import cash.borrow.android.model.UserInfoItem;

public class UserSearchItemAdapter extends RecyclerView.Adapter<UserSearchItemAdapter.ViewHolder> {

    private Context context;
    private List<UserInfoItem> userList;

    public UserSearchItemAdapter (Activity context, List<UserInfoItem> userList){
//        super(context, R.layout.user_search_item, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.user_search_item, parent, false);
        UserSearchItemAdapter.ViewHolder viewHolder = new UserSearchItemAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfoItem userInfoItem = userList.get(position);

        holder.textViewUserName.setText(userInfoItem.getFirstName() + " " + userInfoItem.getLastName());
        holder.textViewUserLocation.setText(userInfoItem.getLocation());

        Glide.with(context).load(userInfoItem.getProfilePicUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewUserName, textViewUserLocation;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewUserName = (TextView) itemView.findViewById(R.id.textViewUserName);
            textViewUserLocation = (TextView) itemView.findViewById(R.id.textViewUserLocation);
            imageView = (ImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}
