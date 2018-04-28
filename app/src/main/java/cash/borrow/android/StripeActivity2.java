package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;

public class StripeActivity2 extends AppCompatActivity {

    Button submitButton;
    CardInputWidget mCardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe2);

        submitButton = findViewById(R.id.submit);
        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

//        if (cardToSave == null) {
//            mErrorDialogHandler.showError("Invalid Card Data");
//            Toast.makeText(this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        cardToSave.setName("Customer Name");
//        cardToSave.setAddressZip("12345");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card cardToSave = mCardInputWidget.getCard();
//                Toast.makeText(StripeActivity2.this, "click!", Toast.LENGTH_SHORT).show();
                Stripe stripe = new Stripe(StripeActivity2.this, "pk_test_6pRNASCoBOKtIshFeQd4XMUh");
                stripe.createToken(
                        cardToSave,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                // Send token to your server
                                Toast.makeText(StripeActivity2.this, "Success!", Toast.LENGTH_SHORT).show();
                            }
                            public void onError(Exception error) {
                                // Show localized error message
                                Toast.makeText(StripeActivity2.this, "error!", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(StripeActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }
}
