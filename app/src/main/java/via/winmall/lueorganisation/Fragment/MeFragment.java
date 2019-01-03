package via.winmall.lueorganisation.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.activity.EditProfile;
import via.winmall.lueorganisation.activity.MainActivity;


public class MeFragment extends Fragment {
    TextView nme_txt, membertype_txt, gender_txt, email_txt, mobile_txt, dob_txt;
    CircleImageView logoImage;
    Button edtpro_btn;
    MKLoader loader;
    String username, member_type, gender, email, mobile, logo, user_id, dob;
    public MeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        nme_txt = (TextView)view.findViewById(R.id.nme_txt);
        membertype_txt = (TextView)view.findViewById(R.id.membertype_txt);
        gender_txt = (TextView)view.findViewById(R.id.gender_txt);
        email_txt = (TextView)view.findViewById(R.id.email_txt);
        mobile_txt = (TextView)view.findViewById(R.id.mobile_txt);
        dob_txt = (TextView)view.findViewById(R.id.dob_txt);
        edtpro_btn = (Button)view.findViewById(R.id.edtpro_btn);
        logoImage = (CircleImageView)view.findViewById(R.id.logo);
        loader = view.findViewById(R.id.loader);
        edtpro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfile.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        user_id = SaveUserId.getInstance(getActivity()).getUserId();
//        username = SaveUserId.getInstance(getActivity()).getUserName();
//        member_type = SaveUserId.getInstance(getActivity()).getTagUsertype();
//        gender = SaveUserId.getInstance(getActivity()).getTagGenger();
//        email = SaveUserId.getInstance(getActivity()).getTagEmail();
//        mobile = SaveUserId.getInstance(getActivity()).getTagMobile();
//        logo = SaveUserId.getInstance(getActivity()).getTagPic();
//        dob = SaveUserId.getInstance(getActivity()).getTagDob();
//        nme_txt.setText(username);
//        membertype_txt.setText(member_type);
//        gender_txt.setText(gender);
//        email_txt.setText(email);
//        mobile_txt.setText(mobile);
//        dob_txt.setText(dob);
//         Picasso.with(getContext()).load(logo).into(logoImage);

        getProfile();
    }

    public void getProfile() {
        //custom loader
        loader.setVisibility(View.VISIBLE);
        //getting user Id
        user_id = SaveUserId.getInstance(getActivity()).getUserId();
        //sending data to server using Map
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.PROFILE_GET, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {

                Log.d("result", jobj.toString());
                loader.setVisibility(View.GONE);
                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {
                        String message = jobj.getString("message");
                        String user_id = jobj.getString("user_id");
                        String user_name = jobj.getString("user_name");
                        String email = jobj.getString("email");
                        String phone_code = jobj.getString("phone_code");
                        String mobile = jobj.getString("mobile");
                        String gender = jobj.getString("gender");
                        String sign_up_type = jobj.getString("sign_up_type");
                        String facebook_id = jobj.getString("facebook_id");
                        String user_type = jobj.getString("user_type");
                        String user_pic = jobj.getString("user_pic");
                        String dob = jobj.getString("dob");

                        // setting values in textView
                        nme_txt.setText(user_name);
                        membertype_txt.setText(user_type);
                        gender_txt.setText(gender);
                        email_txt.setText(email);
                        mobile_txt.setText(mobile);
                        dob_txt.setText(dob);
                        //setting image in imageView using Picasso
                        Picasso.with(getContext()).load(user_pic).into(logoImage);

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
