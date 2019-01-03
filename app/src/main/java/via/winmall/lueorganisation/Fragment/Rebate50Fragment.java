package via.winmall.lueorganisation.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import via.winmall.lueorganisation.activity.MerchantProductDetailsActivity;
import via.winmall.lueorganisation.adapter.HalfListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Rebate50Fragment extends Fragment  {
    ListView list50;
    MKLoader loader;
    ArrayList<SectorPojo> sectorPojosList = new ArrayList<>();

    public Rebate50Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rebate50, container, false);
        list50 = view.findViewById(R.id.list50);
        loader = view.findViewById(R.id.loader);
        SectorListLoad();
        return view;

    }


    public void SectorListLoad() {

        loader.setVisibility(View.VISIBLE);
        sectorPojosList.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.MERCHANT50, new JSONObject(postParam),
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
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (sectorPojosList.size() > 0)
                        {
                            HalfListAdapter halfListAdapter = new HalfListAdapter(getActivity(), sectorPojosList);
                            list50.setAdapter(halfListAdapter);
                            halfListAdapter.notifyDataSetChanged();
                            list50.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    //   Bundle bundle = new Bundle();
                                    //   bundle.putString("store_id", sectorPojosList.get(i).getStore_id());
                                    Intent iDetails = new Intent(getActivity(), MerchantProductDetailsActivity.class);
                                    iDetails.putExtra("store_id", sectorPojosList.get(i).getStore_id());
                                    startActivity(iDetails);
                                }
                            });
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjReq);

    }
}
