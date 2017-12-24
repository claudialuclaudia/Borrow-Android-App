package cash.borrow.android;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.model.UserItem;
import cash.borrow.android.sample.SampleRequestProvider;
import cash.borrow.android.sample.SampleUserProvider;

public class UserActivity extends AppCompatActivity {

    List<UserItem> userItemList = SampleUserProvider.userItemList;
    UserItem userItem;
    List<UserItem> userItemOne = new ArrayList<UserItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_profile);


        String userId = getIntent().getExtras().getString(RequestItemAdapter.USER_ID_KEY);
        List<RequestItem> list = SampleRequestProvider.userMap.get(userId);
//        Toast.makeText(this, "got: " + list, Toast.LENGTH_SHORT).show();

        Collections.sort(list, new Comparator<RequestItem>() {
            @Override
            public int compare(RequestItem o1, RequestItem o2) {
                return Double.compare(o1.getSecPast(), o2.getSecPast());
            }
        });

        RequestItemAdapter adapter = new RequestItemAdapter(this, list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(adapter);

        userItem = SampleUserProvider.userItemMap.get(userId);
        userItemOne.add(userItem);
        UserItemAdapter userAdapter = new UserItemAdapter(this, userItemOne);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(userAdapter);

    }
}
