package cash.borrow.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cash.borrow.android.model.UserItem;

public class UserItemAdapter extends ArrayAdapter<UserItem> {

    List<UserItem> mUserItems;
    LayoutInflater mInflater;
    private Context mContext;

    public UserItemAdapter(@NonNull Context context, @NonNull List<UserItem> objects) {
        super(context, R.layout.user_item, objects);

        mUserItems = objects;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.user_item, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.userNameText);
        TextView jobText = (TextView) convertView.findViewById(R.id.jobText);
        TextView townAddressText = (TextView) convertView.findViewById(R.id.townAddressText);
        TextView friendAmountText = (TextView) convertView.findViewById(R.id.friendAmountText);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.profile_image);

        UserItem item = mUserItems.get(position);

        userName.setText(item.getUserName());
        jobText.setText(item.getJobTitle());
        townAddressText.setText(item.getTownAddress());
        friendAmountText.setText(item.getFriendsAmount() + " Friends");

        imageView.setImageResource(R.drawable.kelly);

        InputStream inputStream = null;
        try {
            String imageFile = item.getUserImage();
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

//        boolean checked = ((RadioButton) convertView).isChecked();
//        switch(convertView.getId()) {
//            case R.id.borrowed:
//                if (checked) {
//                    Toast.makeText(mContext, "borrowed licked", Toast.LENGTH_SHORT).show();
//                }
//            case R.id.lent:
//                if (checked) {
//                    Toast.makeText(mContext, "lent licked", Toast.LENGTH_SHORT).show();
//                }
//        }
        return convertView;
    }
}
