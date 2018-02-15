package cash.borrow.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import cash.borrow.android.model.UserInfoItem;

public class UserSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseUsersReference;

    private ImageView imageViewUpload;
    private EditText editTextName, editTextLocation;
    private Button buttonSave;

    private Uri uriProfileImage;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;

    String profileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        mAuth = FirebaseAuth.getInstance();
        databaseUsersReference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference();

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        imageViewUpload = (ImageView) findViewById(R.id.imageViewUpload);
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

//        loadUserInformation();
        // not working rn...i will figure it out later :(
    }


    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageViewUpload);
            }

            if (user.getDisplayName() != null) {
                editTextName.setText(user.getDisplayName());
            }
        }
    }

    private void saveInfo() {
        String fullName = editTextName.getText().toString();
        String location = editTextLocation.getText().toString().trim();
        UserInfoItem userInfoItem = new UserInfoItem(fullName, location);

        FirebaseUser user = mAuth.getCurrentUser();

        databaseUsersReference.child(user.getUid()).setValue(userInfoItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserSettingsActivity.this, "Text Info Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        if (fullName.isEmpty()) {
//            editTextName.setError("Name required");
//            editTextName.requestFocus();
//            return;
//        }

//        if (user != null && profileImageUrl != null) {
//            UserInfoItem userInfoItem = new UserInfoItem(fullName, location);


//            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(fullName)
//                    .setPhotoUri(Uri.parse(profileImageUrl))
//                    .build();
//
//            user.updateProfile(profile);
        }

    private void uploadImage() {
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference profileRef = storageReference.child(user.getUid() + "/profilepics/"
                + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            profileRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded...");
                        }
                    });
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) { //user have choosen an image
            uriProfileImage = data.getData();
            try {
                Bitmap bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imageViewUpload.setImageBitmap(bitMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSave) {
            saveInfo();
            uploadImage();

        }
    }
}