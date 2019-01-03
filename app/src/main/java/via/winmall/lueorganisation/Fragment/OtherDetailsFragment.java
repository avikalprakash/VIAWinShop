package via.winmall.lueorganisation.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.activity.MerchantProductDetailsActivity;

public class OtherDetailsFragment extends Fragment {
    ImageView image_other_details;
    MKLoader loader;
    TextView record_status;
    public OtherDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_details, container, false);
        image_other_details = view.findViewById(R.id.image_other_details);
        record_status = view.findViewById(R.id.record_status);
        loader = view.findViewById(R.id.loader);
        MerchantProductDetailsActivity activity = (MerchantProductDetailsActivity) getActivity();
        String store_id = activity.getStore_id();
        OtherDetailsLoad(store_id);
        return view;
    }


    public void OtherDetailsLoad(String store_id) {
        loader.setVisibility(View.VISIBLE);
        record_status.setVisibility(View.GONE);
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("store_id", store_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.MERCHANT_DETAILS_OTHER, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result_other", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String slideshow=jobj.getString("merchant_detail");
                                JSONObject jsonObject = new JSONObject(slideshow);
                                String imageOther = jsonObject.getString("big_image");
                                Picasso.with(getActivity()).load(imageOther).into(image_other_details);
                                if (imageOther.equals("")){
                                    record_status.setVisibility(View.VISIBLE);
                                }
                            }else if (error.equals("true")){


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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjReq);

    }
}
