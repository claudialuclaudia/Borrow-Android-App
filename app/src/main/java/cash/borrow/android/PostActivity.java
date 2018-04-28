package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cash.borrow.android.model.UserInfoItem;

public class PostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private FirebaseUser user;

    private ImageView profileImage;
    private EditText requestReason, borrowAmount, repaymentDate, paymentPlan, interestRate, requestType;
    private Button postButton; // button which on clicking, sends the request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_USERS_PATH_UPLOADS);
        storageReference = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        if(user == null){
            //not signed in
            finish();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        profileImage = findViewById(R.id.profile_image);
        requestReason = findViewById(R.id.RequestReason);
        borrowAmount = findViewById(R.id.borrowAmount);
        repaymentDate = findViewById(R.id.repaymentDate);
        paymentPlan = findViewById(R.id.paymentPlan);
        interestRate = findViewById(R.id.interestRate);
        requestType = findViewById(R.id.requestType);
        postButton = findViewById(R.id.postButton);

//        loadUserProfile();
        postRequest();
    }

    //loads user's profile picture
    private void loadUserProfile() {
        Query firebaseSearchQuery = mDatabase.orderByKey().startAt(user.getUid()).endAt(user.getUid());
        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserInfoItem userInfoItem = postSnapshot.getValue(UserInfoItem.class);
                    if (userInfoItem.getProfilePicUrl() != null){
                        Glide.with(PostActivity.this)
                                .load(userInfoItem.getProfilePicUrl())
                                .into(profileImage);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void postRequest() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://140.233.178.240:8080/borrowRequests"; // your URL

        queue.start();
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_LONG).show();
                HashMap<String, String> params = new HashMap<String,String>();
                params.put("userId", user.getUid());
                params.put("userName", user.getEmail());
                params.put("userProfileUrl", user.getPhotoUrl() == null ? "" : user.getPhotoUrl().toString());
                params.put("amount", borrowAmount.getText().toString().trim());
                params.put("amountRaised", "0");
                params.put("repaymentDate", repaymentDate.getText().toString().trim());
                params.put("paymentPlan", paymentPlan.getText().toString().trim());
                params.put("interestRate", interestRate.getText().toString().trim());
                params.put("requestType", requestType.getText().toString().trim());
                params.put("requestReason", requestReason.getText().toString().trim());

                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(com.android.volley.Request.Method.POST,
                        url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"That didn't work!", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });
    }
}
