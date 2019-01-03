package via.winmall.lueorganisation.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import via.winmall.lueorganisation.Modal.Help;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.activity.LoginActivity;
import via.winmall.lueorganisation.activity.MainActivity;
import via.winmall.lueorganisation.activity.MerchantProductDetailsActivity;
import via.winmall.lueorganisation.activity.QrScannerHandler;
import via.winmall.lueorganisation.adapter.HalfListAdapter;


public class IntroducerQrFragment extends Fragment {
    MKLoader loader;
    String Data = "";
    String key;
    LinearLayout linear1;
    RelativeLayout relative1;
    ImageView mqrimg;
    TextView mintronametxt, mintrophonetxt;
    Button scanqr_btn;


    public IntroducerQrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_introducer_qr, container, false);
        loader = view.findViewById(R.id.loader);
        linear1 = (LinearLayout) view.findViewById(R.id.linear1);
        relative1 = (RelativeLayout) view.findViewById(R.id.relative1);
        mqrimg = (ImageView) view.findViewById(R.id.imageQR);
        mintronametxt = (TextView) view.findViewById(R.id.introducerName);
        mintrophonetxt = (TextView) view.findViewById(R.id.introducerContact);
        scanqr_btn = view.findViewById(R.id.scanqr_btn);

        scanqr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), QrScannerHandler.class));
            }
        });


        getIntroDetails();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Data = Help.DATA;
            if (Help.DATA.length() > 0) {
                getIntroDetails();
            } else {

            }
        } catch (Exception e) {
        }

    }

    public void getIntroDetails() {
        loader.setVisibility(View.VISIBLE);
        String user_id = SaveUserId.getInstance(getActivity()).getUserId();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.USER_INTRODUCER_DETAILS, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {
                loader.setVisibility(View.GONE);
                Log.d("result", jobj.toString());

                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {
                        String introducer_id = jobj.getString("introducer_id");
                        String introducer_name = jobj.getString("introducer_name");
                        String introducer_phone = jobj.getString("introducer_phone");
                        String qr_code = jobj.getString("qr_code");
                        String qr_image = jobj.getString("qr_image");

                        mintronametxt.setText(jobj.getString("introducer_name"));
                        mintrophonetxt.setText(jobj.getString("introducer_phone"));
                        Picasso.with(getActivity()).load(jobj.getString("qr_image")).into(mqrimg);
                        linear1.setVisibility(View.VISIBLE);
                        relative1.setVisibility(View.INVISIBLE);


                    } else if (error.equals("true")) {
                        String s = jobj.getString("message");

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                                builder.setMessage(s)
                                        .setNegativeButton(getString(R.string.ok), null)
                                        .create()
                                        .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

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
