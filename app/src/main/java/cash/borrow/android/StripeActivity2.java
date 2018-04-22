package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;

public class StripeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe2);

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
//            mErrorDialogHandler.showError("Invalid Card Data");
            Toast.makeText(this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
            return;
        }

        cardToSave.setName("Customer Name");
        cardToSave.setAddressZip("12345");

        Stripe stripe = new Stripe(StripeActivity2.this, "pk_test_6pRNASCoBOKtIshFeQd4XMUh");
        stripe.createToken(
                cardToSave,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                    }
                    public void onError(Exception error) {
                        // Show localized error message
                        Toast.makeText(StripeActivity2.this,
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
