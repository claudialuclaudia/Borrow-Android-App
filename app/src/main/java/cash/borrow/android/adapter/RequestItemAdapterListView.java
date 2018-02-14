package cash.borrow.android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cash.borrow.android.R;
import cash.borrow.android.model.RequestItem;

public class RequestItemAdapterListView extends ArrayAdapter<RequestItem> {

    List<RequestItem> mRequestItems;
    LayoutInflater mInflater;

    public RequestItemAdapterListView(@NonNull Context context, @NonNull List<RequestItem> objects) {
        super(context, R.layout.list_item, objects);

        mRequestItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //convertView is null first time around
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.userNameText);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.profile_image);

        RequestItem item = mRequestItems.get(position);

        tvName.setText(item.getUserName()+" wants to borrow $" + item.getAmount() + " for " +
                item.getRequestReason() + " " + item.getSecPast() + " s ago.");
//        imageView.setImageResource(R.drawable.kelly);

        InputStream inputStream = null;
        try {
            String imageFile = item.getImage();
            inputStream = getContext().getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
