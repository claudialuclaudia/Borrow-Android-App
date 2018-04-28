package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    Button RequestButton; // button which on clicking, sends the request
    TextView DisplayText; // a text field to display the request response
    TextView DataField; // a text field where the data to be sent is entered

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RequestButton = (Button) findViewById(R.id.RequestButton);
        DataField = (TextView) findViewById(R.id.DataField);
        DisplayText = (TextView) findViewById(R.id.DisplayText);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://140.233.178.240:8080/goals"; // your URL

        queue.start();
        RequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<String,String>();
                params.put("goal", DataField.getText().toString()); // the entered data as the body.

                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(com.android.volley.Request.Method.POST,
                        url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    DisplayText.setText(response.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DisplayText.setText("That didn't work!");
                    }
                });
                queue.add(jsObjRequest);
            }
        });
    }
}
