package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

//        Collections.sort(commentItemList, new Comparator<CommentItem>() {
//            @Override
//            public int compare(CommentItem o1, CommentItem o2) {
//                return Double.compare(o1.getSecPast(), o2.getSecPast());
//            }
//        });

        CommentItemAdapter commentAdapter = new CommentItemAdapter(this, commentItemList);
//
        ListView listView = (ListView) findViewById(R.id.commentList);
        listView.setAdapter(commentAdapter);
    }
}
