package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.sample.SampleRequestProvider;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        String itemId = getIntent().getExtras().getString(RequestItemAdapter.ITEM_ID_KEY);
//        RequestItem item = SampleRequestProvider.requestItemMap.get(itemId);
        RequestItem item = getIntent().getExtras().getParcelable(RequestItemAdapter.ITEM_KEY);
        if (item != null) {
            Toast.makeText(this, "Received item " + item.getRequestId(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }
    }
}
