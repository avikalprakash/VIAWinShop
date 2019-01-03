package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.adapter.HalfListAdapter;

public class MerchantList extends AppCompatActivity {
    TextView tvheadertitle;
    ImageView back;
    ListView merchantList;

    ArrayList<SectorPojo> sectorPojosList = new ArrayList<>();
    String sector_id;
    TextView record_status;
    String store_id="";
    MKLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_list);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);
        tvheadertitle.setText("Merchant List");
        back = (ImageView)findViewById(R.id.ivheaderleft);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sector_id = getIntent().getStringExtra("sector_id");
        merchantList = findViewById(R.id.merchantList);
        record_status = findViewById(R.id.record_status);
        loader = findViewById(R.id.loader);
        MerchantByStateLoad(sector_id);

        merchantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             //   Bundle bundle = new Bundle();
             //   bundle.putString("store_id", sectorPojosList.get(i).getStore_id());
               Intent iDetails = new Intent(getApplicationContext(), MerchantProductDetailsActivity.class);
               iDetails.putExtra("store_id", sectorPojosList.get(i).getStore_id());
               startActivity(iDetails);
            }
        });
    }

    public void MerchantByStateLoad(String state_id) {

        loader.setVisibility(View.VISIBLE);

        sectorPojosList.clear();
        record_status.setVisibility(View.GONE);
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("sector_id", state_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.MERCHANT_BY_SECTOR, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String slideshow=jobj.getString("store");
                                JSONArray jsonArray = new JSONArray(slideshow);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SectorPojo sectorPojo = new SectorPojo();
                                    JSONObject jobject = jsonArray.getJSONObject(i);
                                    sectorPojo.setStore_id(jobject.getString("store_id"));
                                    sectorPojo.setStore_code(jobject.getString("store_code"));
                                    sectorPojo.setStore_name(jobject.getString("store_name"));
                                    sectorPojo.setBrand(jobject.getString("brand"));
                                    sectorPojo.setSmall_image(jobject.getString("small_image"));
                                    sectorPojosList.add(sectorPojo);
                                }
                            }else if (error.equals("true")){
                                sectorPojosList.clear();
                                record_status.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (sectorPojosList.size() > 0)
                        {
                            HalfListAdapter halfListAdapter = new HalfListAdapter(MerchantList.this, sectorPojosList);
                            merchantList.setAdapter(halfListAdapter);
                            halfListAdapter.notifyDataSetChanged();
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
        RequestQueue queue = Volley.newRequestQueue(MerchantList.this);
        queue.add(jsonObjReq);

    }
}
