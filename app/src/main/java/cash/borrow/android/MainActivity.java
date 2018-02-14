package cash.borrow.android;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cash.borrow.android.adapter.RequestItemAdapter;
import cash.borrow.android.model.CommentItem;
import cash.borrow.android.model.RequestItem;
import cash.borrow.android.sample.SampleCommentProvider;
import cash.borrow.android.sample.SampleRequestProvider;

public class MainActivity extends AppCompatActivity {

    List<RequestItem> requestItemList;
//    TextView tvOut;
    Map<String, Double> requestProgress = SampleCommentProvider.requestProgress;
    Map<String, Set> lentMap = SampleCommentProvider.lentMap;
    Map<String, RequestItem> requestItemMap = SampleRequestProvider.requestItemMap;
    Map<String, List<RequestItem>> userMap = SampleRequestProvider.userMap;
    Map<String, Map<String, CommentItem>> requestCommentItemMap = SampleCommentProvider.requestCommentItemMap;
    List<RequestItem> worldFeedList;
    List<RequestItem> friendFeedList;
    List<RequestItem> myConnectsList;
    String userId = "12";

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //not signed in
            finish();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        requestItemList = SampleRequestProvider.requestItemList;
        Collections.sort(requestItemList, new Comparator<RequestItem>() {
            @Override
            public int compare(RequestItem o1, RequestItem o2) {
                    return Double.compare(o1.getSecPast(), o2.getSecPast());
            }
        });

        double progress;
        for (RequestItem item: requestItemList) {
            if (requestProgress.containsKey(item.getRequestId())){
                progress = requestProgress.get(item.getRequestId());
                if (progress >= item.getAmount()) {
                    requestItemList.remove(item);
                }
            }
        }

        worldFeedList = requestItemList;

        RequestItemAdapter adapter = new RequestItemAdapter(this, worldFeedList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(adapter);

        requestItemList = new ArrayList<>();
        for (RequestItem item: worldFeedList) {
            if (Integer.parseInt(item.getUserId()) > 10) {
                requestItemList.add(item);
            }
        }
        friendFeedList = requestItemList;

        myConnectsList = new ArrayList<>();
        //everyone Sarah Lynn has contributed towards
        for (Object reqId: lentMap.get(userId)) {
            myConnectsList = userMap.get(requestItemMap.get(reqId).getUserId());
        }
        //everyone who has contributed towards Sarah Lynn's causes
        for (RequestItem reqItem: userMap.get(userId)){//list of Sarah's requestItems
            for (CommentItem commentItem: requestCommentItemMap.get(reqItem.getRequestId()).values()) {
                //map of commentId : CommentItems
                if (commentItem.isLent()) {
                    for (RequestItem reqI: userMap.get(commentItem.getCommenterId())){
                        //list of RequestItems
                        if (!myConnectsList.contains(reqI)){
                            myConnectsList.add(reqI);
                        }
                    }
                }
            }
        }
        Collections.sort(myConnectsList, new Comparator<RequestItem>() {
            @Override
            public int compare(RequestItem o1, RequestItem o2) {
                return Double.compare(o1.getSecPast(), o2.getSecPast());
            }
        });

//        tvOut = (TextView) findViewById(R.d.out);

        ImageView imageView = (ImageView) findViewById(R.id.action_write);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "I can do nothing because Chase designed nothing--LAZY", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton navSearchButton = (ImageButton) findViewById(R.id.nav_search);
        navSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        ProfileActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton navProfileButton = (ImageButton) findViewById(R.id.nav_profile);
        navProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        ProfileActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    RequestItemAdapter adapter = new RequestItemAdapter(MainActivity.this, worldFeedList);

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
                    recyclerView.setAdapter(adapter);

                } else if (position == 1) {
                    RequestItemAdapter adapter = new RequestItemAdapter(MainActivity.this, friendFeedList);

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
                    recyclerView.setAdapter(adapter);
                } else {
                    RequestItemAdapter adapter = new RequestItemAdapter(MainActivity.this, myConnectsList);

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.signIn) {
////            Toast.makeText(this, "I can do nothing because Chase designed nothing--LAZY", Toast.LENGTH_SHORT).show();
//            Intent myIntent = new Intent(MainActivity.this,
//                    SignInActivity.class);
//            startActivity(myIntent);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
