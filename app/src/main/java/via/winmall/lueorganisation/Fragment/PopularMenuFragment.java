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
import via.winmall.lueorganisation.activity.MerchantProductDetailsActivity;
import via.winmall.lueorganisation.activity.ProductDetailsActivity;
import via.winmall.lueorganisation.adapter.NestedListView;
import via.winmall.lueorganisation.adapter.PopularMenuListAdapter;


public class PopularMenuFragment extends Fragment {
    NestedListView popularList;
    MKLoader loader;
    ArrayList<SectorPojo> sectorPojoArrayList
            = new ArrayList<>();
    TextView record_status;
    public PopularMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_menu, container, false);
        popularList = view.findViewById(R.id.popularList);
        MerchantProductDetailsActivity activity = (MerchantProductDetailsActivity) getActivity();
        String store_id = activity.getStore_id();
        record_status = view.findViewById(R.id.record_status);
        loader = view.findViewById(R.id.loader);
        PopularMenuLoad(store_id);
        return view;
    }



    public void PopularMenuLoad(String store_id) {

        loader.setVisibility(View.VISIBLE);
        sectorPojoArrayList.clear();
        record_status.setVisibility(View.GONE);
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("store_id", store_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.MERCHANT_DETAILS_POPULAR_MENU, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String slideshow=jobj.getString("product");
                                JSONArray jsonArray = new JSONArray(slideshow);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SectorPojo sectorPojo = new SectorPojo();
                                    JSONObject jobject = jsonArray.getJSONObject(i);
                                    sectorPojo.setStore_id(jobject.getString("store_id"));
                                    sectorPojo.setProduct_id(jobject.getString("product_id"));
                                    sectorPojo.setProduct_code(jobject.getString("product_code"));
                                    sectorPojo.setProduct_title(jobject.getString("product_title"));
                                    sectorPojo.setBrand(jobject.getString("brand"));
                                    sectorPojo.setProduct_price(jobject.getString("product_price"));
                                    sectorPojo.setProduct_sp_price(jobject.getString("product_sp_price"));
                                    sectorPojo.setProduct_desc(jobject.getString("product_desc"));
                                    sectorPojo.setProduct_image(jobject.getString("product_image"));
                                    sectorPojoArrayList.add(sectorPojo);
                                }
                            }else if (error.equals("true")){
                                sectorPojoArrayList.clear();
                                record_status.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (sectorPojoArrayList.size() > 0)
                        {
                            PopularMenuListAdapter categoryAdapter = new PopularMenuListAdapter(getActivity(), sectorPojoArrayList);
                            popularList.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                            popularList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                                    intent.putExtra("product_id", sectorPojoArrayList.get(i).getProduct_id());
                                    startActivity(intent);
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
