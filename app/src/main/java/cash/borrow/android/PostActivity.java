package cash.borrow.android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import cash.borrow.android.model.UserInfoItem;

public class PostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private FirebaseUser user;

    private ImageView profileImage;
    private EditText requestReason, borrowAmount, repaymentDate, paymentPlan, interestRate, requestType;
    private CardInputWidget mCardInputWidget;
    private EditText customerName;
    private EditText zipcode;
    private Button postButton, connectStripeButton; // button which on clicking, sends the request

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String userProfilePic;

    final private String url = "http://140.233.160.180:8080/borrowRequests"; // your URL

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
        borrowAmount = findViewById(R.id.lendAmount);
        repaymentDate = findViewById(R.id.repaymentDate);
        paymentPlan = findViewById(R.id.paymentPlan);
        interestRate = findViewById(R.id.interestRate);
        requestType = findViewById(R.id.requestType);
        mCardInputWidget = findViewById(R.id.card_input_widget);
        customerName = findViewById(R.id.customer_name);
        zipcode = findViewById(R.id.zip_code);
        postButton = findViewById(R.id.postButton);
        connectStripeButton = findViewById(R.id.stripeConnectButton);

        loadUserProfilePic();

        ImageView imageView = (ImageView) findViewById(R.id.action_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRequest();
            }
        });

        connectStripeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWithStripe();
            }
        });

        repaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PostActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                repaymentDate.setText(date);
            }
        };
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
                        Glide.with(PostActivity.this)
                                .load(userInfoItem.getProfilePicUrl())
                                .into(profileImage);
                        userProfilePic = userInfoItem.getProfilePicUrl();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void saveRequest() {
        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
//                    mErrorDialogHandler.showError("Invalid Card Data");
            Toast.makeText(getApplicationContext(), "Invalid Card Data", Toast.LENGTH_SHORT).show();
            return;
        }
        cardToSave.setName(customerName.getText().toString().trim());
        cardToSave.setAddressZip(zipcode.getText().toString().trim());
        Stripe stripe = new Stripe(getApplicationContext(), "pk_test_IVB0XMZKNrMfCxwZPvzUqRpV");

        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        stripe.createToken(
                cardToSave,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        HashMap<String, String> params = new HashMap<String,String>();
                        params.put("userId", user.getUid());
                        params.put("userName", user.getDisplayName());
                        params.put("userEmail", user.getEmail());
                        params.put("userProfileUrl", userProfilePic);
                        params.put("amount", borrowAmount.getText().toString().trim());
                        params.put("amountRaised", "0");
                        params.put("repaymentDate", repaymentDate.getText().toString().trim());
                        params.put("paymentPlan", paymentPlan.getText().toString().trim());
                        params.put("interestRate", interestRate.getText().toString().trim());
                        params.put("requestType", requestType.getText().toString().trim());
                        params.put("requestReason", requestReason.getText().toString().trim());
                        params.put("StripeToken", token.toString());

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
                        Toast.makeText(getApplicationContext(), "Stripe Token Callback error!", Toast.LENGTH_SHORT).show();
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
