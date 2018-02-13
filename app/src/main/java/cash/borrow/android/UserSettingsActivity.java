package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cash.borrow.android.model.UserInfoItem;

public class UserSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText editTextName, editTextLocation;
    private Button buttonSave, buttonUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        buttonSave.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    private void saveInfo(){
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        UserInfoItem userInfoItem = new UserInfoItem(name, location);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInfoItem);

        Toast.makeText(this, "Info Saved", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSave){
            saveInfo();
        }

        if (view == buttonUpload){
            Intent myIntent = new Intent(UserSettingsActivity.this,
                    UploadActivity.class);
            startActivity(myIntent);
        }
    }
}
