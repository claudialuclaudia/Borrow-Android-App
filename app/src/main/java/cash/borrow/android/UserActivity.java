package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.model.UserItem;
import cash.borrow.android.sample.SampleCommentProvider;
import cash.borrow.android.sample.SampleRequestProvider;
import cash.borrow.android.sample.SampleUserProvider;

public class UserActivity extends AppCompatActivity {

    List<UserItem> userItemList = SampleUserProvider.userItemList;
    UserItem userItem;
    List<UserItem> userItemOne = new ArrayList<UserItem>();
    List<RequestItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.abs_profile);

        final String userId = getIntent().getExtras().getString(RequestItemAdapter.USER_ID_KEY);

        list = SampleRequestProvider.userMap.get(userId);
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

        ImageView imageView = (ImageView) findViewById(R.id.action_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                RequestItemAdapter adapter = new RequestItemAdapter(UserActivity.this, list);
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
                RequestItemAdapter adapter = new RequestItemAdapter(UserActivity.this, list);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
                recyclerView.setAdapter(adapter);
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.nav_home);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.nav_profile);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserActivity.this,
                        ProfileActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            Toast.makeText(this, "I can do nothing because Chase designed nothing--LAZY", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addFriend(View view) {
        // Do something in response to button click
        Toast.makeText(UserActivity.this, "My name is Chase and I LUUUUUUVVV it when things are CLICKABLE", Toast.LENGTH_SHORT).show();
    }

    public void sendMoney(View view) {
        // Do something in response to button click
        Toast.makeText(UserActivity.this, "My name is Chase and I LUUUUUUVVV it when things are CLICKABLE", Toast.LENGTH_SHORT).show();
    }
}
