package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;

public class StripeActivity extends AppCompatActivity {

    private CardInputWidget mCardInputWidget;
    private EditText customerName;
    private EditText zipcode;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe2);

        mCardInputWidget = findViewById(R.id.card_input_widget);
        submitButton = findViewById(R.id.submit);
        customerName = findViewById(R.id.customer_name);
        zipcode = findViewById(R.id.zip_code);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card cardToSave = mCardInputWidget.getCard();
                cardToSave.setName(customerName.getText().toString().trim());
                cardToSave.setAddressZip(zipcode.getText().toString().trim());
                if (cardToSave == null) {
//                    mErrorDialogHandler.showError("Invalid Card Data");
                    Toast.makeText(StripeActivity.this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(StripeActivity.this, "click!", Toast.LENGTH_SHORT).show();
                Stripe stripe = new Stripe(StripeActivity.this, "pk_test_6pRNASCoBOKtIshFeQd4XMUh");
                stripe.createToken(
                        cardToSave,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                // Send token to your server
                                Toast.makeText(StripeActivity.this, token.getCard().toString(), Toast.LENGTH_SHORT).show();
                            }
                            public void onError(Exception error) {
                                // Show localized error message
                                Toast.makeText(StripeActivity.this, "error!", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(StripeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }
}
