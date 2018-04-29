package cash.borrow.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class GetActivity extends AppCompatActivity {

    TextView DisplayText; // a text field to display the request response

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        DisplayText = (TextView) findViewById(R.id.DisplayText);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://140.233.178.240:8080/goals"; // your URL

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        DisplayText.setText(response.toString());
                        try {
                            JSONArray goals = response.getJSONArray("message");
                            DisplayText.setText(goals.toString());
//                            JSONObject p = (JSONObject)goals.get(0);
//                            String description = p.getString("description");
//                            DisplayText.setText(description);
//                            StringBuilder sb = new StringBuilder();
//                            for(int i = 0 ; i < goals.length() ; i++){
//                                JSONObject p = (JSONObject)goals.get(i);
//                                String id = p.getString("_id");
//                                sb.append(i + ": " + id);
//                                sb.append(description);
//                                DisplayText.setText(description);
//                            }
//                            DisplayText.setText(sb.toString());
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
                        DisplayText.setText("That don't work!");
//                        Log.d("Error.Response", response);
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }
}
