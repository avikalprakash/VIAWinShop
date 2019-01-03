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
import android.widget.Toast;

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
public class LoyaltyProgramFragment extends Fragment {
   TextView pointsText, usernameText, statusText, redemption_countText, outlet_visitText;
   ImageView pic_image;
   MKLoader loader;

    public LoyaltyProgramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loyalty_program, container, false);
        pointsText = view.findViewById(R.id.points);
        usernameText = view.findViewById(R.id.username);
        statusText = view.findViewById(R.id.status);
        redemption_countText = view.findViewById(R.id.redemption_count);
        outlet_visitText = view.findViewById(R.id.outlet_visit);
        pic_image = view.findViewById(R.id.pic_image);
        loader = view.findViewById(R.id.loader);

        getLoyalityDetails();
        return view;
    }

    public void getLoyalityDetails() {
        loader.setVisibility(View.VISIBLE);
        String user_id = SaveUserId.getInstance(getActivity()).getUserId();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.LOYALITY, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {
                loader.setVisibility(View.GONE);
                Log.d("result", jobj.toString());

                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {
                        String details = jobj.getString("detail");
                        JSONObject jsonObject = new JSONObject(details);
                        String user_id = jsonObject.getString("user_id");
                        String user_name = jsonObject.getString("user_name");
                        String email = jsonObject.getString("email");
                        String sign_up_type = jsonObject.getString("sign_up_type");
                        String user_type = jsonObject.getString("user_type");
                        String user_pic = jsonObject.getString("user_pic");
                        String reward_point = jsonObject.getString("reward_point");
                        String redeem_visit = jsonObject.getString("redeem_visit");

                        pointsText.setText(reward_point);
                        usernameText.setText(user_name);
                        statusText.setText(user_type);
                        redemption_countText.setText(redeem_visit+ " redemptions");
                        outlet_visitText.setText(redeem_visit+ " Outlet Visits");
                        Picasso.with(getActivity()).load(user_pic).into(pic_image);



                        String history = jobj.getString("history");
                        JSONObject jsonObject1 = new JSONObject(history);


                    } else if (error.equals("true")) {
                        String s = jobj.getString("message");

                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
