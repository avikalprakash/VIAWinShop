package via.winmall.lueorganisation.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.activity.LatestInboxDetail;
import via.winmall.lueorganisation.adapter.LatestInboxListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxLatestFragment extends Fragment {
    ListView latestNewsListView;
    MKLoader loader;
    ArrayList<SectorPojo> latestpojoList = new ArrayList<>();
    public InboxLatestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        latestNewsListView = view.findViewById(R.id.latestNewsListView);
        loader = view.findViewById(R.id.loader);
        UpdateInbox();

        latestNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getContext(), LatestInboxDetail.class);
                intent.putExtra("inbox_id", latestpojoList.get(i).getInbox_id());
                intent.putExtra("user_id", latestpojoList.get(i).getUser_id());
                intent.putExtra("title", latestpojoList.get(i).getTitle());
                intent.putExtra("image", latestpojoList.get(i).getImage());
                intent.putExtra("description", latestpojoList.get(i).getDescription());
                intent.putExtra("rest_id", latestpojoList.get(i).getRest_id());
                intent.putExtra("inbox_date", latestpojoList.get(i).getInbox_date());
                intent.putExtra("read_status", latestpojoList.get(i).getRead_status());
                intent.putExtra("from_activity", "inbox");
                startActivity(intent);
            }
        });
        return view;
    }

    public void UpdateInbox() {


        String user_id = SaveUserId.getInstance(getActivity()).getUserId();
        loader.setVisibility(View.VISIBLE);
        latestpojoList.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);



        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.INBOX, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("getting_result", jobj.toString());
                        try {

                            String check = jobj.getString("error");

                            if (check.equals("false"))
                            {
                                String msg = jobj.getString("message");

                                JSONArray jArray = jobj.getJSONArray("inbox");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < jArray.length(); i++) {

                                    //getting the json object of the particular index inside the array
                                    JSONObject jObject = jArray.getJSONObject(i);
                                    SectorPojo latestPojo = new SectorPojo();
                                    latestPojo.setInbox_id(jObject.getString("inbox_id"));
                                    latestPojo.setUser_id(jObject.getString("user_id"));
                                    latestPojo.setTitle(jObject.getString("title"));
                                    latestPojo.setImage(jObject.getString("image"));
                                    latestPojo.setDescription(jObject.getString("description"));
                                    latestPojo.setRest_id(jObject.getString("rest_id"));
                                    latestPojo.setInbox_date(jObject.getString("inbox_date"));
                                    latestPojo.setRead_status(jObject.getString("read_status"));
                                    latestpojoList.add(latestPojo);

                                }

                                LatestInboxListAdapter latestInboxListAdapter = new LatestInboxListAdapter(getActivity(), latestpojoList);
                                latestNewsListView.setAdapter(latestInboxListAdapter);
                                latestInboxListAdapter.notifyDataSetChanged();
                            }

                            else if (check.equals("true"))
                            {
                                String msg = jobj.getString("message");

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
