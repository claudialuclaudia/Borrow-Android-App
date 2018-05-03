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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.borrow.android.adapter.RequestItemAdapter;
import cash.borrow.android.adapter.UserSearchItemAdapter;
import cash.borrow.android.model.RequestItem;

public class MainActivity extends AppCompatActivity {

    private Map<String,RequestItem> requestItemMap;
    private List<RequestItem> requestItemList;
    private FirebaseAuth firebaseAuth;

    private RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //not signed in
            finish();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        requestItemList = new ArrayList<>();
        requestItemMap = new HashMap<>();
        populateList();

        rvItems = findViewById(R.id.rvItems);
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        ImageView postButton = (ImageView) findViewById(R.id.action_write);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        PostActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton navSearchButton = (ImageButton) findViewById(R.id.nav_search);
        navSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        SearchActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton navProfileButton = (ImageButton) findViewById(R.id.nav_profile);
        navProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        UserSettingsActivity.class);
                startActivity(myIntent);
            }
        });

    }

    private void populateList() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://140.233.160.180:8080/borrowRequests"; // your URL

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray borrowRequests = response.getJSONArray("borrowRequests");
                            for(int i = 0 ; i < borrowRequests.length() ; i++){
                                JSONObject p = (JSONObject)borrowRequests.get(i);
                                String requestId = p.getString("_id");
                                String userId = p.getString("userId");
                                String userName = p.getString("userName");
                                String userProfileUrl = p.getString("userProfileUrl");
                                int msPast = 10;
                                int amount = Integer.parseInt(p.getString("amount"));
                                int amountRaised = Integer.parseInt(p.getString("amountRaised"));
                                String repaymentDate = p.getString("repaymentDate");
                                String paymentPlan = p.getString("paymentPlan");
                                double interestRate = Double.parseDouble(p.getString("interestRate"));
                                String requestType = p.getString("requestType");
                                String requestReason = p.getString("requestReason");
                                String[] empty = {};
                                RequestItem requestItem = new RequestItem(requestId, userId, userName, userProfileUrl,msPast, amount, amountRaised, repaymentDate, paymentPlan,interestRate,requestType, requestReason, empty, empty, empty, empty, null);
                                requestItemMap.put(requestItem.getRequestId(), requestItem);
                                requestItemList.add(requestItem);
//                                Toast.makeText(getApplicationContext(), requestItemList.toString(), Toast.LENGTH_LONG).show();
                            }

//                            Toast.makeText(getApplicationContext(), requestItemList.toString(), Toast.LENGTH_LONG).show();
                            RequestItemAdapter adapter = new RequestItemAdapter(MainActivity.this, requestItemList);
                            rvItems.setAdapter(adapter);

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