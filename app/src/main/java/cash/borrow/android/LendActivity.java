package cash.borrow.android;

import android.content.Intent;
import android.net.Uri;
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
import java.util.UUID;

import cash.borrow.android.adapter.RequestItemAdapter;
import cash.borrow.android.model.RequestItem;
import cash.borrow.android.model.UserInfoItem;

public class LendActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    private CardInputWidget mCardInputWidget;
    private EditText lendAmount, customerName, zipcode;
    private Button lendButton, connectStripeButton;
    private ImageView profileImage;

    RequestItem item;

    private String url = "http://140.233.160.180:8080/lend/"; // your URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);

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

        lendAmount = findViewById(R.id.lendAmount);
        mCardInputWidget = findViewById(R.id.card_input_widget);
        customerName = findViewById(R.id.customer_name);
        zipcode = findViewById(R.id.zip_code);
        lendButton = findViewById(R.id.lendButton);
        profileImage = findViewById(R.id.profile_image);
        connectStripeButton = findViewById(R.id.stripeConnectButton);

        lendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComment();
            }
        });

        connectStripeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWithStripe();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.action_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadUserProfilePic();

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
        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
//                    mErrorDialogHandler.showError("Invalid Card Data");
            Toast.makeText(getApplicationContext(), "Invalid Card Data", Toast.LENGTH_SHORT).show();
            return;
        }
        cardToSave.setName(customerName.getText().toString().trim());
        cardToSave.setAddressZip(zipcode.getText().toString().trim());
        Stripe stripe = new Stripe(getApplicationContext(), "pk_test_6pRNASCoBOKtIshFeQd4XMUh");

        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        stripe.createToken(
                cardToSave,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        HashMap<String, String> params = new HashMap<String,String>();
                        params.put("requestId", item.getRequestId());
                        params.put("commenterId", user.getUid());
                        params.put("commenterName", user.getDisplayName());
                        params.put("commentContent", null);
                        params.put("lendAmount", lendAmount.getText().toString().trim());
                        params.put("StripeToken", token.toString());

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
                    public void onError(Exception error) {
                        // Show localized error message
                        Toast.makeText(getApplicationContext(), "error!", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(StripeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void connectWithStripe() {
//        To prevent CSRF attacks, add the state parameter, passing along a unique token as the value. We’ll include the state you gave us when we redirect the user back to your site.
        String state = UUID.randomUUID().toString();
        String url = "https://connect.stripe.com/express/oauth/authorize?" +
                "client_id=ca_CnbUTKxV3YcYqL7r0qg0acNjE9NobGyy&state=" + state;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
