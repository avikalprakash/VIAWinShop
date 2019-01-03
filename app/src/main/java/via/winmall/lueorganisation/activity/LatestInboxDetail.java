package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.adapter.LatestInboxListAdapter;

public class LatestInboxDetail extends AppCompatActivity {
    String inbox_id="", user_id="", title="", image="", description="", rest_id="", inbox_date="", read_status="";
    TextView inbox_description, inbox_dateText, titleText,  tvheadertitle, readStatus;
    ImageView imageNews, back, makeFavorite;
    MKLoader loader;
    String from_activity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_inbox_detail);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);


        back = (ImageView)findViewById(R.id.ivheaderleft);
        makeFavorite = (ImageView)findViewById(R.id.makeFavorite);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inbox_description = findViewById(R.id.inbox_description);
        inbox_dateText = findViewById(R.id.inbox_date);
        titleText = findViewById(R.id.title);
        readStatus = findViewById(R.id.readStatus);
        imageNews = findViewById(R.id.imageNews);
        loader = findViewById(R.id.loader);
        inbox_id = getIntent().getStringExtra("inbox_id");
        user_id = getIntent().getStringExtra("user_id");
        title = getIntent().getStringExtra("title");
        image = getIntent().getStringExtra("image");
        description = getIntent().getStringExtra("description");
        rest_id = getIntent().getStringExtra("rest_id");
        inbox_date = getIntent().getStringExtra("inbox_date");
        read_status = getIntent().getStringExtra("read_status");

        from_activity = getIntent().getStringExtra("from_activity");

        UpdateInbox();

        if (!description.equals(null) || !description.equals("")){
            inbox_description.setText(description);
        }
        if (!inbox_date.equals(null) || !inbox_date.equals("")){
            inbox_dateText.setText(inbox_date);
        }
        if (!title.equals(null) || !title.equals("")){
            titleText.setText(title);
        }

        if (!from_activity.equals(null) || !from_activity.equals("")){
            if (from_activity.equals("inbox")){
                tvheadertitle.setText(getString(R.string.inbox_details));
            }else if (from_activity.equals("news")){
                tvheadertitle.setText(getString(R.string.news_details));
            }
        }

        if (!image.equals(null)  || !image.equals("")) {
            Picasso.with(getApplicationContext()).load(image).into(imageNews);
        }


        if (read_status.equals("0")){
            readStatus.setText("New");
            readStatus.setTextColor(Color.GREEN);
        } else if (read_status.equals("1")) {
            readStatus.setText("Read");
            readStatus.setTextColor(Color.GRAY);
        }

    }


    public void UpdateInbox() {


        String    user_id = SaveUserId.getInstance(LatestInboxDetail.this).getUserId();
        String URL="";
        loader.setVisibility(View.VISIBLE);
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);
        if (from_activity.equals("inbox")){
            postParam.put("inbox_id", inbox_id);
             URL= Urls.INBOX_READ;
        }else if (from_activity.equals("news")){
            postParam.put("news_id", inbox_id);
             URL= Urls.LATEST_NEWS_READ;
        }




        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {

                            String check = jobj.getString("error");

                            if (check.equals("false"))
                            {
                                String msg = jobj.getString("message");


                            }

                            else if (check.equals("true"))
                            {
                                String msg = jobj.getString("message");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                VolleyLog.d("tag", "Error: " + error.getMessage());
                //  hideProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjReq.setTag("tag");
        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(LatestInboxDetail.this);
        queue.add(jsonObjReq);

    }
}
