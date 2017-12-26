package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.model.UserItem;
import cash.borrow.android.sample.SampleRequestProvider;
import cash.borrow.android.sample.SampleUserProvider;

public class ProfileActivity extends AppCompatActivity {

    List<RequestItem> list;
    UserItem userItem;
    String userId = "12";
    List<UserItem> userItemOne = new ArrayList<UserItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        list = SampleRequestProvider.userMap.get(userId);
        Collections.sort(list, new Comparator<RequestItem>() {
            @Override
            public int compare(RequestItem o1, RequestItem o2) {
                return Double.compare(o1.getSecPast(), o2.getSecPast());
            }
        });
        RequestItemAdapter adapter = new RequestItemAdapter(this, list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        userItem = SampleUserProvider.userItemMap.get(userId);
        userItemOne.add(userItem);
        UserItemAdapter userAdapter = new UserItemAdapter(this, userItemOne);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(userAdapter);

        ImageButton imageButton = (ImageButton) findViewById(R.id.nav_home);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ProfileActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });
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
