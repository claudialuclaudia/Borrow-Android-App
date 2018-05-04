package cash.borrow.android;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.model.UserInfoItem;

public class UserInitialSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ImageView imageViewUpload;
    private EditText editTextFirstName, editTextLastName, editTextLocation;
    private Button buttonSave, buttonGetStarted;

    private Uri uriProfileImage;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;

    String profileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_initial_settings);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_USERS_PATH_UPLOADS);
        storageReference = FirebaseStorage.getInstance().getReference();

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonGetStarted = findViewById(R.id.buttonGetStarted);
        buttonSave.setOnClickListener(this);
        buttonGetStarted.setOnClickListener(this);

        imageViewUpload = (ImageView) findViewById(R.id.imageViewUpload);
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        loadUserInformation();
    }


    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            Query firebaseSearchQuery = mDatabase.orderByKey().startAt(user.getUid()).endAt(user.getUid());
            firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        UserInfoItem userInfoItem = postSnapshot.getValue(UserInfoItem.class);

                        if (userInfoItem.getFirstName() != null){
                            editTextFirstName.setText(userInfoItem.getFirstName());
                        }
                        if (userInfoItem.getLastName() != null){
                            editTextLastName.setText(userInfoItem.getLastName());
                        }
//                        if (userInfoItem.getFirstName() != null && userInfoItem.getLastName() != null) {
//                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(userInfoItem.getFirstName() + userInfoItem.getLastName()).build();
//                            user.updateProfile(profileUpdates);
//                        }
                        if (userInfoItem.getLocation() != null){
                            editTextLocation.setText(userInfoItem.getLocation());
                        }
                        if (userInfoItem.getProfilePicUrl() != null){
                            Glide.with(UserInitialSettingsActivity.this)
                                    .load(userInfoItem.getProfilePicUrl())
                                    .into(imageViewUpload);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void saveInfo() {
        final FirebaseUser user = mAuth.getCurrentUser();
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String location = editTextLocation.getText().toString().trim();

        if (uriProfileImage != null) {
//            StorageReference profileRef = storageReference.child(user.getUid() + Constants.PROFILE_PICS_PATH_UPLOADS
//                    + System.currentTimeMillis() + "." + getFileExtension(uriProfileImage));
            StorageReference profileRef = storageReference.child(user.getUid() + "/profilePic.jpg");

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(firstName + " " + lastName).build();
            user.updateProfile(profileUpdates);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            profileRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Info Saved", Toast.LENGTH_LONG).show();
                            UserInfoItem userInfoItem = new UserInfoItem(firstName, firstName.toLowerCase(),
                                    lastName, lastName.toLowerCase(), location, taskSnapshot.getDownloadUrl().toString());
                            mDatabase.child(user.getUid()).setValue(userInfoItem);
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
        } else {
            UserInfoItem userInfoItem = new UserInfoItem(firstName, firstName.toLowerCase(),
                    lastName, lastName.toLowerCase(), location, null);
            mDatabase.child(user.getUid()).setValue(userInfoItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserInitialSettingsActivity.this, "Text Info Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

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
//            user.updateProfile(profile);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    //.jpg or .png and all that
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSave) {
            saveInfo();
        }
        if (view == buttonGetStarted) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}