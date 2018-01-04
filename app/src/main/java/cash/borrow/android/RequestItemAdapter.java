package cash.borrow.android;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.sample.SampleCommentProvider;
import cash.borrow.android.sample.SampleRequestProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static cash.borrow.android.R.id.reqAmount;
import static cash.borrow.android.R.id.requestReason;

public class RequestItemAdapter extends RecyclerView.Adapter<RequestItemAdapter.ViewHolder>{

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    public static final String USER_ID_KEY = "user_id_key";
    private List<RequestItem> mItems;
    private Context mContext;
    Map<String, Double> requestProgress = SampleCommentProvider.requestProgress;
    List<RequestItem> requestItemList = SampleRequestProvider.requestItemList;

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
            String first = item.getUserName() + " wants to borrow ";
            String second;
            if (item.getAmount()%1 != 0) {
                second = "$" + item.getAmount();
            } else {
                second = "$" + (int) item.getAmount();
            }
            String third = " " + item.getSecPast() + "s ago.";

            int secPast = item.getSecPast();
            if (secPast >= 60) {
                String remainderSec = (int)item.getSecPast() % 60 != 0 ? Integer.toString(item.getSecPast()%60) + "s" : "";
                third = " " + secPast/60 + "m" + remainderSec + " ago.";
            }

            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(first+second+third);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#31926f")),first.length(),
                    first.length()+second.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            stringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),first.length(),
                    first.length()+second.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvName.setText(stringBuilder);

            holder.reqReason.setText(item.getRequestReason());

            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);

            holder.progressBar.setMax((int) item.getAmount()!=0 ? (int) item.getAmount(): (int) item.getAmount()+1);

            int progressInt;
            double progress;
            if (!requestProgress.containsKey(item.getRequestId())){
                holder.progressBar.setProgress(0);
            } else {
                progress = requestProgress.get(item.getRequestId());
                progressInt = progress%1!=0 && (int) progress ==0 ? (int) progress+1 : (int) progress;
                if (progress >= item.getAmount()){
                    holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#31926f"), PorterDuff.Mode.SRC_IN);
                }
                holder.progressBar.setProgress(progressInt);
            }

            String reqA;
            if (item.getAmount()%1 != 0) {
                reqA = "$" + item.getAmount();
            } else {
                reqA = "$" + (int) item.getAmount();
            }
            holder.reqAmount.setText(reqA);

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
                mContext.startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "You've clicked the image of " + item.getUserName(), Toast.LENGTH_SHORT).show();
                String userId = item.getUserId();
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra(USER_ID_KEY, userId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //set up bindings to view in xml file

        public TextView tvName;
        public TextView reqReason;
        public TextView reqAmount;
        public ImageView imageView;
        public View mView;
        public ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.userNameText);
            reqReason = (TextView) itemView.findViewById(R.id.requestReason);
            reqAmount = (TextView) itemView.findViewById(R.id.reqAmount);
            imageView = (ImageView) itemView.findViewById(R.id.profile_image);
            mView = itemView;
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
