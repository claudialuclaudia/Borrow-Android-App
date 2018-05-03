package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.adapter.CommentItemAdapter;
import cash.borrow.android.adapter.RequestItemAdapter;
import cash.borrow.android.model.CommentItem;
import cash.borrow.android.model.RequestItem;

public class DetailActivity extends AppCompatActivity {
    private List<RequestItem> oneList;
    private RecyclerView rvItems;
    private ListView listView;
    private List<CommentItem> commentItemList;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        commentItemList = new ArrayList<>();

        rvItems = findViewById(R.id.detailRvItems);
        listView = findViewById(R.id.commentList);
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        RequestItem item = getIntent().getExtras().getParcelable(RequestItemAdapter.ITEM_KEY);
        if (item == null) {
            throw new AssertionError("Null data item received!");
        }

        oneList = new ArrayList<>();
        oneList.add(item);

        RequestItemAdapter adapter = new RequestItemAdapter(this, oneList);
        rvItems.setAdapter(adapter);

        url = "http://140.233.160.180:8080/borrowRequests/" + item.getRequestId(); // your URL
        populateList();

        ImageView imageView = (ImageView) findViewById(R.id.action_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.nav_home);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DetailActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.nav_profile);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DetailActivity.this,
                        UserSettingsActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton imageButton3 = (ImageButton) findViewById(R.id.nav_search);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DetailActivity.this,
                        SearchActivity.class);
                startActivity(myIntent);
            }
        });

    }

    private void populateList() {
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray comments = response.getJSONArray("comments");
                            for(int i = 0 ; i < comments.length() ; i++){
                                JSONObject p = (JSONObject)comments.get(i);
                                String requestId = p.getString("requestId");
                                String  commenterId = p.getString("commenterId");
                                String commenterName = p.getString("commenterName");
                                String commentContent = p.getString("commentContent");
                                int lendAmount = Integer.parseInt(p.getString("lendAmount"));
                                String StripeToken = p.getString("StripeToken");
                                CommentItem commentItem = new CommentItem(requestId, commenterId,
                                        commenterName, commentContent, lendAmount, StripeToken);
                                commentItemList.add(commentItem);
//                                Toast.makeText(getApplicationContext(), requestItemList.toString(), Toast.LENGTH_LONG).show();
                            }

//                            Toast.makeText(getApplicationContext(), requestItemList.toString(), Toast.LENGTH_LONG).show();
                            CommentItemAdapter commentAdapter = new CommentItemAdapter(getApplicationContext(), commentItemList);
                            listView.setAdapter(commentAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_LONG).show();
//                        Log.d("Error.Response", response);
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }
}
