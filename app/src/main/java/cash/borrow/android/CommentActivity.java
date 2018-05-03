package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.StorageReference;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cash.borrow.android.adapter.RequestItemAdapter;
import cash.borrow.android.model.RequestItem;
import cash.borrow.android.model.UserInfoItem;

public class CommentActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    private EditText commentContent;
    private Button commentButton;
    private ImageView profileImage;

    RequestItem item;

    private String url = "http://140.233.160.180:8080/lend/"; // your URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_USERS_PATH_UPLOADS);
        user = mAuth.getCurrentUser();
        if(user == null){
            //not signed in
            finish();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        item = getIntent().getExtras().getParcelable(RequestItemAdapter.ITEM_KEY);
        if (item == null) {
            throw new AssertionError("Null data item received!");
        }

        commentContent = findViewById(R.id.commentContent);
        commentButton = findViewById(R.id.commentButton);
        profileImage = findViewById(R.id.profile_image);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComment();
                onBackPressed();
            }
        });

        loadUserProfilePic();

        ImageView imageView = (ImageView) findViewById(R.id.action_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    //loads user's profile picture
    private void loadUserProfilePic() {
        Query firebaseSearchQuery = mDatabase.orderByKey().startAt(user.getUid()).endAt(user.getUid());
        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserInfoItem userInfoItem = postSnapshot.getValue(UserInfoItem.class);
                    if (userInfoItem.getProfilePicUrl() != null){
                        Glide.with(getApplicationContext())
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

    private void saveComment () {
        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        HashMap<String, String> params = new HashMap<String,String>();
        params.put("requestId", item.getRequestId());
        params.put("commenterId", user.getUid());
        params.put("commenterName", user.getDisplayName());
        params.put("commentContent", commentContent.getText().toString().trim());
        params.put("lendAmount", "0");
        params.put("StripeToken", "");

        url = url + item.getRequestId();
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(com.android.volley.Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("message").equals("Data received successfully")) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
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
}
