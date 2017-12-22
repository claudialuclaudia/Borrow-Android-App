package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cash.borrow.android.model.RequestItem;
import cash.borrow.android.sample.SampleRequestProvider;

public class MainActivity extends AppCompatActivity {

    List<RequestItem> requestItemList = SampleRequestProvider.requestItemList;
//    TextView tvOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Collections.sort(requestItemList, new Comparator<RequestItem>() {
            @Override
            public int compare(RequestItem o1, RequestItem o2) {
                    return Double.compare(o1.getSecPast(), o2.getSecPast());
            }
        });

        RequestItemAdapter adapter = new RequestItemAdapter(this, requestItemList);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);

//        tvOut = (TextView) findViewById(R.d.out);

    }
}
