package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
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

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);

        userInfoItemList = new ArrayList<>();
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
