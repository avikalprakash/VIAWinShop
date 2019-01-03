package via.winmall.lueorganisation.Fragment;


import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
public class BecomeMerchantFragment extends Fragment {
    MKLoader loader;
    EditText charge_text, company_name_text, email_text, mobile_text;
    Button submit_btn;

    public BecomeMerchantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_become_merchant, container, false);
        loader = view.findViewById(R.id.loader);
        charge_text = view.findViewById(R.id.charge_text);
        company_name_text = view.findViewById(R.id.company_name_text);
        email_text = view.findViewById(R.id.email_text);
        mobile_text = view.findViewById(R.id.mobile_text);
        submit_btn = view.findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String company_name = company_name_text.getText().toString();
                String email = email_text.getText().toString();
                String mobile = mobile_text.getText().toString();
                String charge = charge_text.getText().toString();
                if (!isValidEmail(email)){
                    email_text.requestFocus();
                    email_text.setError("Invalid Email Id");
                }else if (TextUtils.isEmpty(email)) {
                    email_text.requestFocus();
                    email_text.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(charge)) {
                    charge_text.requestFocus();
                    charge_text.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(company_name)) {
                    company_name_text.requestFocus();
                    company_name_text.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(mobile)) {
                    mobile_text.requestFocus();
                    mobile_text.setError("This Field Is Mandatory");
                }
                else {

                    becomeMerchant();

                }
            }
        });

        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void becomeMerchant() {
        loader.setVisibility(View.VISIBLE);
        String user_id = SaveUserId.getInstance(getActivity()).getUserId();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);

        postParam.put("company_name", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("incharge_name", user_id);

        postParam.put("mobile_no", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("email", user_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.USER_BECOME_MERCHANT, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {
                loader.setVisibility(View.GONE);
                Log.d("result", jobj.toString());

                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {

                        String s = jobj.getString("message");
                        company_name_text.setText("");
                        email_text.setText("");
                        charge_text.setText("");
                        mobile_text.setText("");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                        builder.setMessage(s)
                                .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        getActivity().onBackPressed();
                                    }
                                })
                                .create()
                                .show();


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
