package via.winmall.lueorganisation.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.adapter.EWalletListAdapter;
import via.winmall.lueorganisation.adapter.NearMeListAdapter;


public class EWalletFragment extends Fragment {
    TextView pointsText, balanceText, dateText;
    MKLoader loader;
    ListView ew_listview;
    ArrayList<SectorPojo> evalletarraylist = new ArrayList<>();


    public EWalletFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ewallet, container, false);
        pointsText = view.findViewById(R.id.points);
        balanceText = view.findViewById(R.id.balance);
        dateText = view.findViewById(R.id.date);
        loader = view.findViewById(R.id.loader);
        ew_listview = view.findViewById(R.id.ew_listview);
        ew_listview.setSelector( R.color.light_green);
        getWalletDetails();
        return view;
    }

    public void getWalletDetails() {
        loader.setVisibility(View.VISIBLE);
        String user_id = SaveUserId.getInstance(getActivity()).getUserId();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);
        evalletarraylist.clear();


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.USER_WALLET, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {
                loader.setVisibility(View.GONE);
                Log.d("result", jobj.toString());

                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {
                        String wallet = jobj.getString("wallet");
                        JSONObject jsonObject = new JSONObject(wallet);
                        String wallet_balance = jsonObject.getString("wallet_balance");
                        String reward_point = jsonObject.getString("reward_point");
                        pointsText.setText(reward_point);
                        balanceText.setText("RM "+wallet_balance+".00");


                        String last_transaction = jobj.getString("last_transaction");
                        JSONObject jsonObject1 = new JSONObject(last_transaction);

                        String tran_date = jsonObject1.getString("tran_date");
                        dateText.setText(tran_date);


                        String credit_list = jobj.getString("credit_list");
                        JSONArray jsonArray = new JSONArray(credit_list);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            SectorPojo sectorPojo = new SectorPojo();
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            sectorPojo.setId(jobject.getString("id"));
                            sectorPojo.setRm_value(jobject.getString("rm_value"));
                            sectorPojo.setCredit_value(jobject.getString("credit_value"));
                            evalletarraylist.add(sectorPojo);
                        }

                    } else if (error.equals("true")) {
                        String s = jobj.getString("message");

                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (evalletarraylist.size() > 0)
                {
                    EWalletListAdapter eWalletListAdapter = new EWalletListAdapter(getActivity(), evalletarraylist);
                    ew_listview.setAdapter(eWalletListAdapter);
                    eWalletListAdapter.notifyDataSetChanged();
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
