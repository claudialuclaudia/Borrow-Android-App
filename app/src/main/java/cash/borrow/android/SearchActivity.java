package cash.borrow.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.adapter.UserSearchItemAdapter;
import cash.borrow.android.model.UserInfoItem;

public class SearchActivity extends AppCompatActivity {
    private EditText editTextSearch;
    private ProgressDialog progressDialog;

    private RecyclerView recyclerViewUsers;
    private RecyclerView.Adapter adapter;

    private DatabaseReference mDatabase;

    private List<UserInfoItem> userInfoItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        userInfoItemList = new ArrayList<>();

        progressDialog.setMessage("Loading users...");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_USERS_PATH_UPLOADS);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressDialog.dismiss();

                userInfoItemList.clear();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UserInfoItem userInfoItem = postSnapshot.getValue(UserInfoItem.class);
                    userInfoItemList.add(userInfoItem);
                }
                adapter = new UserSearchItemAdapter(SearchActivity.this, userInfoItemList);
                recyclerViewUsers.setAdapter(adapter);

                editTextSearch = (EditText) findViewById(R.id.editTextSearch);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        ImageButton navSearchButton = (ImageButton) findViewById(R.id.nav_home);
        navSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SearchActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton navProfileButton = (ImageButton) findViewById(R.id.nav_profile);
        navProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SearchActivity.this,
                        ProfileActivity.class);
                startActivity(myIntent);
            }
        });
    }
}