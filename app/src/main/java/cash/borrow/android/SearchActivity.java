package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    EditText editTextSearch;

    DatabaseReference databaseUsers;

    ListView listViewUsers;

    List<UserInfoItem> userInfoItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);

        userInfoItemList = new ArrayList<>();

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

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfoItemList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    UserInfoItem userInfoItem = userSnapshot.getValue(UserInfoItem.class);
                    userInfoItemList.add(userInfoItem);
                }

                UserSearchItemAdapter adapter = new UserSearchItemAdapter(SearchActivity.this, userInfoItemList);
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
