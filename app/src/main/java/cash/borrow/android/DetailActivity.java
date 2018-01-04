package cash.borrow.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cash.borrow.android.model.CommentItem;
import cash.borrow.android.model.RequestItem;
import cash.borrow.android.sample.SampleCommentProvider;
import cash.borrow.android.sample.SampleRequestProvider;

public class DetailActivity extends AppCompatActivity {

//    List<RequestItem> requestItemList = SampleRequestProvider.requestItemList;
    List<RequestItem> requestItemList = new ArrayList<>();
//    List<CommentItem> allCommentsList = SampleCommentProvider.commentItemList;
    Map<String, Map<String, CommentItem>> allCommentsMap = SampleCommentProvider.requestCommentItemMap;
    List<CommentItem> commentItemList = new ArrayList<>();
    List<String> commenterNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RequestItem item = getIntent().getExtras().getParcelable(RequestItemAdapter.ITEM_KEY);
        if (item == null) {
            throw new AssertionError("Null data item received!");
        }

        requestItemList.add(item);

        RequestItemAdapter adapter = new RequestItemAdapter(this, requestItemList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detailRvItems);
        recyclerView.setAdapter(adapter);

        if (allCommentsMap.containsKey(item.getRequestId())){
            Map<String, CommentItem> map = allCommentsMap.get(item.getRequestId());
            commentItemList = new ArrayList<>(map.values());
        }

        Collections.sort(commentItemList, new Comparator<CommentItem>() {
            @Override
            public int compare(CommentItem o1, CommentItem o2) {
                return Double.compare(o2.getSecPast(), o1.getSecPast());
            }
        });

        CommentItemAdapter commentAdapter = new CommentItemAdapter(this, commentItemList);
//
        ListView listView = (ListView) findViewById(R.id.commentList);
        listView.setAdapter(commentAdapter);

        ImageView imageView = (ImageView) findViewById(R.id.action_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.nav_home);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DetailActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.nav_profile);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DetailActivity.this,
                        ProfileActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            Toast.makeText(this, "I can do nothing because Chase designed nothing--LAZY", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
