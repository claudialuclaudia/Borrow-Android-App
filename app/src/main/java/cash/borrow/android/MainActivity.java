package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import cash.borrow.android.model.RequestItem;

public class MainActivity extends AppCompatActivity {

    public static Map<String,RequestItem> requestItemMap;
    private FirebaseAuth firebaseAuth;

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

        requestItemMap = new HashMap<>();

        ImageView imageView = (ImageView) findViewById(R.id.action_write);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        PostActivity.class);
                startActivity(myIntent);
            }
        });

        populateList();

        ImageButton navSearchButton = (ImageButton) findViewById(R.id.nav_search);
        navSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        PostActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton navProfileButton = (ImageButton) findViewById(R.id.nav_profile);
        navProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        PostActivity.class);
                startActivity(myIntent);
            }
        });

    }

    private void populateList() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://140.233.178.240:8080/borrowRequests"; // your URL

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray borrowRequests = response.getJSONArray("borrowRequests");
                            Toast.makeText(getApplicationContext(), borrowRequests.toString(), Toast.LENGTH_LONG).show();
//                            StringBuilder sb = new StringBuilder();
                            for(int i = 0 ; i < borrowRequests.length() ; i++){
                                JSONObject p = (JSONObject)borrowRequests.get(i);
                                String requestId = p.getString("_id");
                                String userId = p.getString("userId");
                                int msPast = 10;
                                int amount = p.getInt("amount");
                                String repaymentDate = p.getString("repaymentDate");
                                String paymentPlan = p.getString("paymentPlan");
                                double interestRate = p.getDouble("interestRate");
                                String requestType = p.getString("requestType");
                                String requestReason = p.getString("requestReason");
                                String[] empty = {};
                                RequestItem requestItem = new RequestItem(requestId, userId, msPast, amount, repaymentDate, paymentPlan,interestRate,requestType, requestReason, empty, empty, empty, empty, null);
                                requestItemMap.put(requestItem.getRequestId(), requestItem);
                            }
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