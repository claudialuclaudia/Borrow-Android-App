package cash.borrow.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUpInstead;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //already logged in; start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUpInstead = (TextView) findViewById(R.id.textViewSignUpInstead);

        buttonSignIn.setOnClickListener(this);
        textViewSignUpInstead.setOnClickListener(this);
    }

    private void userSignIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing in User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(SignInActivity.this, "Could not sign in. " +
                                    "please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn){
            userSignIn();
        }

        if (view == textViewSignUpInstead){
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }
}
