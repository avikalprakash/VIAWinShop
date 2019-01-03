package via.winmall.lueorganisation.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.activity.MerchantProductDetailsActivity;
import via.winmall.lueorganisation.adapter.PopularMenuListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TandCFragment extends Fragment {
    WebView terms_use_text;
    TextView record_status;
    MKLoader loader;
    public TandCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tand_c, container, false);
        terms_use_text = view.findViewById(R.id.terms_use_text);
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
                Urls.MERCHANT_DETAILS_TERMS, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result_terms", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String slideshow=jobj.getString("merchant_detail");
                                JSONObject jsonObject = new JSONObject(slideshow);
                                String termsText = jsonObject.getString("terms_condition");

                                String youtContentStr = String.valueOf(Html
                                        .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#53E550; background-color: #ffffff;\">"
                                                + termsText + "</body>]]>"));

                                terms_use_text.loadData(youtContentStr, "text/html", "UTF-8");

                            }else if (error.equals("true")){
                                    record_status.setVisibility(View.VISIBLE);
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
