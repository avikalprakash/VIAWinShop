package via.winmall.lueorganisation.Fragment;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import via.winmall.lueorganisation.activity.MainActivity;
import via.winmall.lueorganisation.activity.MerchantProductDetailsActivity;
import via.winmall.lueorganisation.adapter.NearMeListAdapter;
import via.winmall.lueorganisation.adapter.SectorListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearMeFragment extends Fragment{

     ListView near_me_list;
     MKLoader loader;
    ArrayList<SectorPojo> sectorPojosList = new ArrayList<>();
    LocationManager locationManager;
    TextView record_status;
    public NearMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_me, container, false);
        near_me_list = view.findViewById(R.id.near_me_list);
        loader = view.findViewById(R.id.loader);
        record_status = view.findViewById(R.id.record_status);
        MainActivity activity = (MainActivity) getActivity();
        String lat = activity.getLat();
        String lang = activity.getLang();

        SectorListLoad(lat, lang);

        return view;
    }

    public void SectorListLoad(String latitude, String longitude) {

        loader.setVisibility(View.VISIBLE);
        sectorPojosList.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("latitude", latitude);
        postParam.put("longitude", longitude);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.MERCHANT_NEARBY, new JSONObject(postParam),
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
                                    sectorPojo.setState_id(jobject.getString("store_id"));
                                    sectorPojo.setStore_code(jobject.getString("store_code"));
                                    sectorPojo.setStore_name(jobject.getString("store_name"));
                                    sectorPojo.setBrand(jobject.getString("brand"));
                                    sectorPojo.setSmall_image(jobject.getString("small_image"));
                                    sectorPojo.setLatitude(jobject.getString("latitude"));
                                    sectorPojo.setLongitude(jobject.getString("longitude"));
                                    sectorPojo.setDistance(jobject.getString("distance"));
                                    sectorPojo.setRating(jobject.getString("rating"));
                                    sectorPojo.setCloseTime(jobject.getString("close_time"));
                                    sectorPojo.setOpenTime(jobject.getString("open_time"));
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
                            NearMeListAdapter nearMeListAdapter = new NearMeListAdapter(getActivity(), sectorPojosList);
                            near_me_list.setAdapter(nearMeListAdapter);
                            nearMeListAdapter.notifyDataSetChanged();
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
