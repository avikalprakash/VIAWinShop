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
import via.winmall.lueorganisation.activity.MerchantList;
import via.winmall.lueorganisation.adapter.SectorListAdapter;


public class SectorFragment extends Fragment {

   ListView sectorList;
   ProgressDialog pDialog;
   MKLoader loader;
    ArrayList<SectorPojo> sectorPojosList = new ArrayList<>();

    public SectorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sector, container, false);
        sectorList = view.findViewById(R.id.sectorList);
        loader = view.findViewById(R.id.loader);
        SectorListLoad();

        sectorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String sector_id = sectorPojosList.get(position).getCat_id();
                Intent iMerchant = new Intent(getContext(), MerchantList.class);
                iMerchant.putExtra("sector_id", sector_id);
                startActivity(iMerchant);

            }
        });
        return view;
    }


    public void SectorListLoad() {

        loader.setVisibility(View.VISIBLE);
        sectorPojosList.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.SECTOR_LIST, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String slideshow=jobj.getString("message");
                                JSONArray jsonArray = new JSONArray(slideshow);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SectorPojo sectorPojo = new SectorPojo();
                                    JSONObject jobject = jsonArray.getJSONObject(i);
                                    sectorPojo.setCat_id(jobject.getString("cat_id"));
                                    sectorPojo.setCat_title(jobject.getString("cat_title"));
                                    sectorPojosList.add(sectorPojo);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (sectorPojosList.size() > 0)
                        {
                            SectorListAdapter sectorListAdapter = new SectorListAdapter(getActivity(), sectorPojosList);
                            sectorList.setAdapter(sectorListAdapter);
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
