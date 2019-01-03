package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuyenmonkey.mkloader.MKLoader;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import via.winmall.lueorganisation.Modal.AsycTaskReadResponse;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.adapter.AndroidImageAdapternew;
import via.winmall.lueorganisation.adapter.ItemEntity;
import via.winmall.lueorganisation.adapter.LocalImageSlider;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView tvheadertitle;
    ImageView back;
    float density;
    ViewPager mViewPager;
    CirclePageIndicator indicator;
    private int delay = 5000;
    private int page = 0;
    Handler handler;
    Runnable runnable;
    ImageView gray_img, red_img, blue_img
;    ArrayList<String> ImageList = new ArrayList<>();
    ArrayList<ItemEntity> SliderImage = new ArrayList<ItemEntity>();
    AndroidImageAdapternew adapterView;
    ViewPager viewpager;
    TextView seeAllMenuText;
    LocalImageSlider localImageSlider;
    String product_id="";
    RatingBar ratting;
    MKLoader loader;
    TextView product_name, product_price_text, product_model, product_special_price_text, product_brand,
            product_efficancy_purpose;
    int images[] = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);
        tvheadertitle.setText("Product Details");
        back = (ImageView)findViewById(R.id.ivheaderleft);
        gray_img = findViewById(R.id.gray_img);
        red_img = findViewById(R.id.red_img);
        blue_img = findViewById(R.id.blue_img);
        product_id = getIntent().getStringExtra("product_id");
        product_name = (TextView)findViewById(R.id.product_name);
        product_model = (TextView)findViewById(R.id.model);
        product_price_text = (TextView)findViewById(R.id.price);
        ratting = findViewById(R.id.ratting);
        ratting.setRating(Float.parseFloat("4.5"));
        product_special_price_text = (TextView)findViewById(R.id.special_price);
        product_brand = (TextView)findViewById(R.id.brand);
        product_efficancy_purpose = (TextView)findViewById(R.id.efficancy_purpose);
        loader = findViewById(R.id.loader);


        gray_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gray_img.setImageResource(R.drawable.checked);
                red_img.setImageDrawable(null);
                blue_img.setImageDrawable(null);

            }
        });
        red_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                red_img.setImageResource(R.drawable.checked);
                gray_img.setImageDrawable(null);
                blue_img.setImageDrawable(null);

            }
        });
        blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blue_img.setImageResource(R.drawable.checked);
                red_img.setImageDrawable(null);
                gray_img.setImageDrawable(null);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.imageView);
        indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);
        handler=new Handler();

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        productDetails();

    }

    public void productDetails() {

        loader.setVisibility(View.VISIBLE);
        ImageList.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("product_id", product_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.PRODUCT_DETAILS, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {

                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String product_detail=jobj.getString("product_detail");
                                JSONObject jsonObject = new JSONObject(product_detail);
                                 String product_id = jsonObject.getString("product_id");
                                String product_code = jsonObject.getString("product_code");
                                String product_title = jsonObject.getString("product_title");
                                String brand = jsonObject.getString("brand");
                                String product_price = jsonObject.getString("product_price");
                                String product_sp_price = jsonObject.getString("product_sp_price");
                                String product_desc = jsonObject.getString("product_desc");

                                product_name.setText(product_title);
                                product_model.setText(product_code);
                                product_price_text.setText("RM "+product_price);
                                product_special_price_text.setText("RM "+product_sp_price);
                                product_brand.setText(brand);
                                product_efficancy_purpose.setText(product_desc);

                                String product_image = jsonObject.getString("product_images");
                                JSONArray product_detailArry = new JSONArray(product_image);
                                for (int i = 0; i < product_detailArry.length(); i++) {
                                    ItemEntity itemEntity2 = new ItemEntity();
                                    JSONObject jobject = product_detailArry.getJSONObject(i);
                                    itemEntity2.setSliderImage(jobject.getString("product_image"));
                                    SliderImage.add(itemEntity2);
                                    ImageList.add(jobject.getString("product_image"));

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ImageList.size() > 0)
                        {
                            adapterView = new AndroidImageAdapternew(ProductDetailsActivity.this, ImageList);
                            mViewPager.setAdapter(adapterView);
                            indicator.setViewPager(mViewPager);
                            try {
                                density = getResources().getDisplayMetrics().density;
                                indicator.setRadius(5 * density);
                            }catch (Exception e){}
                            runnable = new Runnable()
                            {
                                public void run()
                                {
                                    if (adapterView.getCount() == page)
                                    {
                                        page = 0;
                                    } else
                                    {
                                        page++;
                                    }
                                    mViewPager.setCurrentItem(page, true);
                                    //      handler1.postDelayed(this, delay);
                                }
                            };//handler1.postDelayed(runnable, delay);
                            Timer swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(runnable);
                                }
                            }, 3000, 3000);
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
        RequestQueue queue = Volley.newRequestQueue(ProductDetailsActivity.this);
        queue.add(jsonObjReq);

    }

}
