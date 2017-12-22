package cash.borrow.android;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cash.borrow.android.model.RequestItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RequestItemAdapter extends RecyclerView.Adapter<RequestItemAdapter.ViewHolder>{

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private List<RequestItem> mItems;
    private Context mContext;

    public RequestItemAdapter(Context context, List<RequestItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public RequestItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //called each time needed a visual representation of item
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestItemAdapter.ViewHolder holder, int position) {
        //called each time run into a new item that needs to be displayed
        //passes ref to view holder, and the position of data item in the collection
        //take that data item and display the value
        final RequestItem item = mItems.get(position);

        try {
            holder.tvName.setText(item.getUserName()+" wants to borrow $" + item.getAmount() + " for " +
                    item.getRequestReason() + " " + item.getSecPast() + " s ago.");
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "You selected " + item.getRequestReason(), Toast.LENGTH_SHORT).show();
//                String itemId = item.getRequestId();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(ITEM_KEY, item);
                mContext.startActivities(new Intent[]{intent});
            }
        });
        
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You've clicked the image of " + item.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //set up bindings to view in xml file

        public TextView tvName;
        public ImageView imageView;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.userNameText);
            imageView = (ImageView) itemView.findViewById(R.id.userImageView);
            mView = itemView;
        }
    }
}
