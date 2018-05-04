package cash.borrow.android;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stripe.android.Stripe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StripeAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private Button connectStripeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_account);

        connectStripeButton = findViewById(R.id.stripeConnectButton);
        connectStripeButton.setOnClickListener(this);
    }

    private void connectWithStripe() {
//        To prevent CSRF attacks, add the state parameter, passing along a unique token as the value. We’ll include the state you gave us when we redirect the user back to your site.
        String state = UUID.randomUUID().toString();
        String url = "https://connect.stripe.com/express/oauth/authorize?" +
                "client_id=ca_CnbUTKxV3YcYqL7r0qg0acNjE9NobGyy&state=" + state;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == connectStripeButton){
            connectWithStripe();
        }
    }
}
