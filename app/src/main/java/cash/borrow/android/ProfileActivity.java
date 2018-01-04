package cash.borrow.android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cash.borrow.android.model.ProfileItem;
import cash.borrow.android.model.RequestItem;
import cash.borrow.android.model.UserItem;
import cash.borrow.android.sample.SampleCommentProvider;
import cash.borrow.android.sample.SampleRequestProvider;
import cash.borrow.android.sample.SampleSarahProfileProvider;
import cash.borrow.android.sample.SampleUserProvider;

public class ProfileActivity extends AppCompatActivity {

    List<RequestItem> list;
    UserItem userItem;
    String userId = "12";
    List<UserItem> userItemOne = new ArrayList<UserItem>();
    List<ProfileItem> profileItemList = new ArrayList<ProfileItem>();
    private CustomProgressBar seekbar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        seekbar = ((CustomProgressBar) findViewById(R.id.seekBar0));
        seekbar.getThumb().mutate().setAlpha(0);
        initDataToSeekbar();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        list = SampleRequestProvider.userMap.get(userId);
        Collections.sort(list, new Comparator<RequestItem>() {
            @Override
            public int compare(RequestItem o1, RequestItem o2) {
                return Double.compare(o1.getSecPast(), o2.getSecPast());
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        GridLayoutManager mGrid = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mGrid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        RequestItemAdapter adapter = new RequestItemAdapter(this, list);
        recyclerView.setAdapter(adapter);

        userItem = SampleUserProvider.userItemMap.get(userId);
        userItemOne.add(userItem);
        UserItemAdapter userAdapter = new UserItemAdapter(this, userItemOne);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(userAdapter);

        profileItemList = SampleSarahProfileProvider.profileItemList;
//        ProfileItemAdapter profileAdapter = new ProfileItemAdapter(this, profileItemList);
//        ListView profileListView = (ListView) findViewById(R.id.profileRecyclerView);
//        profileListView.setAdapter(profileAdapter);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.profileRecyclerView);
        GridLayoutManager mGrid2 = new GridLayoutManager(this, 1);
        recyclerView2.setLayoutManager(mGrid2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setNestedScrollingEnabled(false);
        ProfileItemAdapter adapter2 = new ProfileItemAdapter(this, profileItemList);
        recyclerView2.setAdapter(adapter2);

        ImageButton imageButton = (ImageButton) findViewById(R.id.nav_home);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ProfileActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

        RadioButton borrowedButton = (RadioButton) findViewById(R.id.borrowed);
        RadioButton lentButton = (RadioButton) findViewById(R.id.lent);

        borrowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(UserActivity.this, "You've clicked borrowed", Toast.LENGTH_SHORT).show();
                list = SampleRequestProvider.userMap.get(userId);
                Collections.sort(list, new Comparator<RequestItem>() {
                    @Override
                    public int compare(RequestItem o1, RequestItem o2) {
                        return Double.compare(o1.getSecPast(), o2.getSecPast());
                    }
                });
                RequestItemAdapter adapter = new RequestItemAdapter(ProfileActivity.this, list);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
                recyclerView.setAdapter(adapter);
            }
        });


        lentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(UserActivity.this, "You've clicked lent", Toast.LENGTH_SHORT).show();
                list = new ArrayList<RequestItem>();
                if (SampleCommentProvider.lentMap.containsKey(userId)) {//if user has lent before
                    for (Object reqId: SampleCommentProvider.lentMap.get(userId)) {//for each request that user has lent towards
                        RequestItem item = SampleRequestProvider.requestItemMap.get(reqId);
                        //get requestItem correlated to the requestId
                        list.add(item);
                    }
                }
                Collections.sort(list, new Comparator<RequestItem>() {
                    @Override
                    public int compare(RequestItem o1, RequestItem o2) {
                        return Double.compare(o1.getSecPast(), o2.getSecPast());
                    }
                });
                RequestItemAdapter adapter = new RequestItemAdapter(ProfileActivity.this, list);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    private void initDataToSeekbar() {
        progressItemList = new ArrayList<ProgressItem>();
        //
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 16;
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.colorAccent;
        progressItemList.add(mProgressItem);
        //
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 33;
        mProgressItem.color = R.color.borrowGreen;
        progressItemList.add(mProgressItem);
        //
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 50;
        mProgressItem.color =  R.color.grey;
        progressItemList.add(mProgressItem);


        seekbar.initData(progressItemList);
        seekbar.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }

    public void addFriend(View view) {
        // Do something in response to button click
        Toast.makeText(ProfileActivity.this, "My name is Chase and I LUUUUUUVVV it when things are CLICKABLE", Toast.LENGTH_SHORT).show();
    }
}
