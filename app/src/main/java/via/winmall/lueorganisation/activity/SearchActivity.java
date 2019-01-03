package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import via.winmall.lueorganisation.R;

public class SearchActivity extends AppCompatActivity {
    TextView tvheadertitle;
    ImageView back;
    GridView grid;
    AutoCompleteTextView itemNameText;
  //  SearchListAdaptor searchListAdaptor;
    ImageView imageBack,msearchimg;
  //  public static ArrayList<SearchItemDetails> customerItemDetailses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);
        tvheadertitle.setText("Search");
        back = (ImageView)findViewById(R.id.ivheaderleft);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        grid=(GridView) findViewById(R.id.grid);

        msearchimg = (ImageView) findViewById(R.id.searchmne);
        msearchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  new SearchCustom().execute();
            }
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
             /*   Intent intent = new Intent(SearchActivity.this,OfferZoneDetails.class);
                intent.putExtra("name",customerItemDetailses.get(position).getName());
                intent.putExtra("price", customerItemDetailses.get(position).getPrice());
                intent.putExtra("description",customerItemDetailses.get(position).getDescription());
                intent.putExtra("image",customerItemDetailses.get(position).getImage());
                intent.putExtra("offerid",customerItemDetailses.get(position).getId());
                startActivity(intent);*/
            }
        });
        itemNameText=(AutoCompleteTextView)findViewById(R.id.itemNameText);

    }

/*    class SearchCustom extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String items_name = itemNameText.getText().toString().trim();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            customerItemDetailses.clear();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/search");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("search_string", items_name);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                  exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                boolean check  = objone.getBoolean("error");
                if(!check) {
                    String message = objone.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    builder.setMessage(message)
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                }else{

                    JSONArray jsonArray = objone.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        SearchItemDetails searchitemDetails = new SearchItemDetails();
                        searchitemDetails.setId(jobject.getString("id"));
                        searchitemDetails.setName(jobject.getString("name"));
                        searchitemDetails.setImage(jobject.getString("image"));
                        searchitemDetails.setDescription(jobject.getString("description"));
                        searchitemDetails.setActual_price(jobject.getString("actual_price"));
                        searchitemDetails.setPrice(jobject.getString("price"));
                        customerItemDetailses.add(searchitemDetails);
                        // promotionDetailses.add(new PromotionDetails((JSONObject)jsonArray.get(i)));
                    }
                   *//* relative.setVisibility(View.VISIBLE);
                    usernameTxt.setText(user_name);
                    mobileTxt.setText(mobile);
                    emailTxt.setText(email);*//*
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            searchListAdaptor = new SearchListAdaptor(SearchActivity.this, customerItemDetailses);
            grid.setAdapter(searchListAdaptor);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    private String readadsResponse(HttpResponse httpResponse) {

        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }*/
}
