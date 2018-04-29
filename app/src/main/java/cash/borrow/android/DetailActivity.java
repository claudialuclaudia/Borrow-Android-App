package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.adapter.RequestItemAdapter;
import cash.borrow.android.model.RequestItem;

public class DetailActivity extends AppCompatActivity {
    private List<RequestItem> oneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RequestItem item = getIntent().getExtras().getParcelable(RequestItemAdapter.ITEM_KEY);
        if (item == null) {
            throw new AssertionError("Null data item received!");
        }

        oneList = new ArrayList<>();
        oneList.add(item);

        RequestItemAdapter adapter = new RequestItemAdapter(this, oneList);
    }
}
