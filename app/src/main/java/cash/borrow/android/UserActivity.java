package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.sample.SampleRequestProvider;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        String userId = getIntent().getExtras().getString(RequestItemAdapter.USER_ID_KEY);
        List<RequestItem> list = SampleRequestProvider.userMap.get(userId);
//        Toast.makeText(this, "got: " + list, Toast.LENGTH_SHORT).show();

        RequestItemAdapter adapter = new RequestItemAdapter(this, list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(adapter);

    }
}
