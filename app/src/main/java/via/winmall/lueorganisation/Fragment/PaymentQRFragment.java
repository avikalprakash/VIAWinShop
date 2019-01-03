package via.winmall.lueorganisation.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentQRFragment extends Fragment {
    MKLoader loader;
    TextView nameTxt, emailTxt, mobileTxt;
    ImageView qrImage;

    public PaymentQRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_qr, container, false);
        loader = view.findViewById(R.id.loader);
        nameTxt = view.findViewById(R.id.name);
        emailTxt = view.findViewById(R.id.email);
        mobileTxt = view.findViewById(R.id.mobile);
        qrImage = view.findViewById(R.id.qr_image);
        getPaymentDetails();
        return view;
    }


    public void getPaymentDetails() {
        loader.setVisibility(View.VISIBLE);
        String user_id = SaveUserId.getInstance(getActivity()).getUserId();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.USER_PAYMENT_QR, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {
                loader.setVisibility(View.GONE);
                Log.d("result", jobj.toString());

                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {
                        String user_id = jobj.getString("user_id");
                        String user_name = jobj.getString("user_name");
                        String email = jobj.getString("email");
                        String phone_code = jobj.getString("phone_code");
                        String mobile = jobj.getString("mobile");
                        String member_id = jobj.getString("member_id");
                        String qr_image = jobj.getString("qr_image");
                        String wallet_address = jobj.getString("wallet_address");

                        mobileTxt.setText(mobile);
                        nameTxt.setText(user_name);
                        emailTxt.setText(email);
                        Picasso.with(getActivity()).load(qr_image).into(qrImage);



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
