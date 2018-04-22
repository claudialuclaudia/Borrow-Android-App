package cash.borrow.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Main3Activity extends AppCompatActivity {

    TextView DisplayText; // a text field to display the request response

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        DisplayText = (TextView) findViewById(R.id.DisplayText);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.17:8080/goals"; // your URL

//        DisplayText.setText("try");
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
//                        Log.d("Response", response.toString());
//                        DisplayText.setText(response.toString());
                        try {
                            JSONArray goals = response.getJSONArray("goals");
//                            DisplayText.setText(goals.toString());
//                            JSONObject p = (JSONObject)goals.get(0);
//                            String description = p.getString("description");
//                            DisplayText.setText(description);
                            StringBuilder sb = new StringBuilder();
                            for(int i = 0 ; i < goals.length() ; i++){
                                JSONObject p = (JSONObject)goals.get(i);
                                String id = p.getString("_id");
                                sb.append(i + ": " + id);
//                                sb.append(description);
//                                DisplayText.setText(description);
                            }
                            DisplayText.setText(sb.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        DisplayText.setText(response.length());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DisplayText.setText("That didn't work!");
//                        Log.d("Error.Response", response);
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }
}
