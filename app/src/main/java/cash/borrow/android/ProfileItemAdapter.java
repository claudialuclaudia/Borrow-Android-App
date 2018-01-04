package cash.borrow.android;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import cash.borrow.android.model.ProfileItem;

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ViewHolder> {
    private List<ProfileItem> mItems;
    private Context mContext;

    public ProfileItemAdapter(Context context, List<ProfileItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ProfileItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //called each time needed a visual representation of item
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.profile_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileItemAdapter.ViewHolder holder, int position) {
        //called each time run into a new item that needs to be displayed
        //passes ref to view holder, and the position of data item in the collection
        //take that data item and display the value
        final ProfileItem item = mItems.get(position);
        holder.description.setText(item.getDescription());
        holder.content.setText(item.getContent());
        if (item.getTextStyle().equals("BOLD")) {
            holder.content.setTypeface(null, Typeface.BOLD);
        }
        holder.content.setTextColor(Color.parseColor(item.getTextColor()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //set up bindings to view in xml file

        public TextView description;
        public TextView content;
        public ViewHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.descriptionView);
            content = (TextView) itemView.findViewById(R.id.contentView);
        }
    }
}
