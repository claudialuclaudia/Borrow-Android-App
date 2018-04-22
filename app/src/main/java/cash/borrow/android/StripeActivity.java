package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;
import com.stripe.android.model.Token;

public class StripeActivity extends AppCompatActivity {

    private EditText customerName;
    private EditText zipcode;
    private Button saveCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe);

//        saveCardButton = findViewById(R.id.save_card_button);
//        customerName = findViewById(R.id.customer_name);
//        zipcode = findViewById(R.id.zip_code);
//        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

//        Card cardToSave = mCardInputWidget.getCard();
//        if (cardToSave == null) {
//            //if the data in the widget is either incomplete or fails client-side validity checks, the Card object will be null.
//            mErrorDialogHandler.showError("Invalid Card Data");
//            Toast.makeText(this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        cardToSave.setName(customerName.getText().toString().trim());
//        cardToSave.setName("name");
//        cardToSave.setAddressLine1("AddressLine1");
//        cardToSave.setAddressLine2("AddressLine2");
//        cardToSave.setAddressCity("AddressCity");
//        cardToSave.setAddressState("AddressState");
//        cardToSave.setAddressCountry("Address Country");
//        cardToSave.setAddressZip(zipcode.getText().toString().trim());

//        saveCardButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Stripe stripe = new Stripe(StripeActivity.this, "pk_test_6pRNASCoBOKtIshFeQd4XMUh");
//                stripe.createToken(
//                        cardToSave,
//                        new TokenCallback() {
//                            public void onSuccess(Token token) {
//                                // Send token to your server
//                                Toast.makeText(StripeActivity.this, "sent to server!", Toast.LENGTH_SHORT).show();
//                            }
//                            public void onError(Exception error) {
//                                // Show localized error message
//                                Toast.makeText(StripeActivity.this,
//                                        error.getLocalizedMessage(),
//                                        Toast.LENGTH_LONG
//                                ).show();
//                            }
//                        }
//                );
//            }
//        });

    }
}
