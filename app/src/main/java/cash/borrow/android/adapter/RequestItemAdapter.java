package cash.borrow.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cash.borrow.android.LendActivity;
import cash.borrow.android.R;
import cash.borrow.android.model.RequestItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RequestItemAdapter extends RecyclerView.Adapter<RequestItemAdapter.ViewHolder>{

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    public static final String USER_ID_KEY = "user_id_key";
    private Context context;
    private List<RequestItem> requestItemList;

    public RequestItemAdapter(Context context, List<RequestItem> requestItemList) {
        this.context = context;
        this.requestItemList = requestItemList;
    }

    @Override
    public RequestItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //called each time needed a visual representation of item
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestItemAdapter.ViewHolder holder, int position) {
        //called each time run into a new item that needs to be displayed
        //passes ref to view holder, and the position of data item in the collection
        //take that data item and display the value
        final RequestItem item = requestItemList.get(position);

        try {
            String imageFile = item.getUserProfileUrl();
            InputStream inputStream = context.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.profileImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.userNameText.setText(item.getUserName());
        String temp = "$" + item.getAmount();
        holder.borrowAmont.setText(temp);
        temp = item.getMsPast() + "s ago";
        holder.timeAgo.setText(temp);
        holder.requestReason.setText(item.getRequestReason());
        temp = "Repayment Date: " + item.getRepaymentDate();
        holder.repaymentDate.setText(temp);
        temp = "Interest Rate: " + item.getInterestRate() + "%";
        holder.interestRate.setText(temp);
        holder.progressBar.setMax(item.getAmount());
        holder.progressBar.setProgress(item.getAmountRaised());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You selected " + item.getRequestReason(), Toast.LENGTH_SHORT).show();
//                String itemId = item.getRequestId();
//                Intent intent = new Intent(mContext, DetailActivity.class);
//                intent.putExtra(ITEM_KEY, item);
//                mContext.startActivity(intent);
            }
        });

        holder.lendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "You selected " + item.getRequestReason(), Toast.LENGTH_SHORT).show();
                String itemId = item.getRequestId();
                Intent intent = new Intent(context, LendActivity.class);
                intent.putExtra(ITEM_KEY, item);
                context.startActivity(intent);
            }
        });

        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You've clicked the image of " + item.getUserName(), Toast.LENGTH_SHORT).show();
//                String userId = item.getUserId();
//                Intent intent = new Intent(mContext, UserActivity.class);
//                intent.putExtra(USER_ID_KEY, userId);
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestItemList.size();
    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //set up bindings to view in xml file

        public ImageView profileImage;
        public TextView userNameText;
        public TextView borrowAmont;
        public TextView timeAgo;
        public TextView requestReason;
        public TextView repaymentDate;
        public TextView interestRate;
        public ProgressBar progressBar;
        public ImageView lendButton;
        public View mView;


        public ViewHolder(View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            userNameText = (TextView) itemView.findViewById(R.id.userNameText);
            borrowAmont = (TextView) itemView.findViewById(R.id.lendAmount);
            timeAgo = (TextView) itemView.findViewById(R.id.timeAgo);
            requestReason = (TextView) itemView.findViewById(R.id.requestReason);
            repaymentDate = (TextView) itemView.findViewById(R.id.repaymentDate);
            interestRate = (TextView) itemView.findViewById(R.id.interestRate);
            lendButton = itemView.findViewById(R.id.refresh);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            mView = itemView;
        }
    }
}
