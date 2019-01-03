package via.winmall.lueorganisation.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import via.winmall.lueorganisation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsOfUseFragment extends Fragment {
    WebView para1,para2,para3,para5,para6,para7,para8,para9,para4;
    private ProgressDialog pDialog;
    TextView tvheadertitle;
    ViewPager viewpager;
    ImageView back;
    String p1,p2,p3,p4;

    public TermsOfUseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terms_of_use, container, false);
        para1 = (WebView) view.findViewById(R.id.para1);
        AboutUs();
        return view;

    }

    private void AboutUs(){

        //   final String adminid = SaveUserId.getInstance(LoginActivity.this).getUserId();
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage(getString(R.string.loading));
        pDialog.show();



        String url = null;
        String REGISTER_URL = "http://condoassist2u.com/eatapp/API/term_condition.php";

        REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
        try {
            URL sourceUrl = new URL(REGISTER_URL);
            url = sourceUrl.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("server000",""+response.toString());


                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            JSONObject msgobj = jsonresponse.getJSONObject("message");
                            String kkk = msgobj.getString("term_cond");


                            String youtContentStr = String.valueOf(Html
                                    .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#53E550; background-color: #ffffff;\">"
                                            + kkk + "</body>]]>"));

                            para1.loadData(youtContentStr, "text/html", "UTF-8");


//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                builder.setMessage("Please retry")
//                                        .setNegativeButton("Retry", null)
//                                        .create()
//                                        .show();
//
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

}
