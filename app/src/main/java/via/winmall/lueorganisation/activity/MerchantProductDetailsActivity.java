package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.design.widget.TabLayout;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.adapter.PagerMerchantTabDetails;

public class MerchantProductDetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    TextView tvheadertitle;
    ImageView back;
    String id;
    ImageView mimageView;
    ViewPager viewpager;
    TabLayout tablayout;
    TextView seeAllMenuText;
    String store_id;
    RatingBar ratingBar;
    private GoogleMap mMap;
    double latitude=0;
    double longitude=0;
    SupportMapFragment mapFragment;
    TextView store_status, product_name, product_description, merchantLocationText;
    boolean loc=false;
    private int delay = 3000;
    Handler handler1;
    private GoogleMap googleMap;
    MapView mMapView;
    String giiglemap_adress;
    MKLoader loader;
   // Runnable runnable;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_product_details);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);
        tvheadertitle.setText("Merchant Details");
        seeAllMenuText = (TextView) findViewById(R.id.seeAllMenuText);
        back = (ImageView)findViewById(R.id.ivheaderleft);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ratingBar = (RatingBar)findViewById(R.id.ratting);

        handler1=new Handler();
        store_status = findViewById(R.id.store_status);
        product_name = findViewById(R.id.product_name);
        product_description = findViewById(R.id.product_description);

        viewpager = (ViewPager) findViewById(R.id.pagermerchants);
        tablayout = (TabLayout) findViewById(R.id.tablayoutmerchants);
        merchantLocationText = (TextView)findViewById(R.id.merchantLocationText);
        mimageView = (ImageView) findViewById(R.id.imageView);
        loader = findViewById(R.id.loader);





        TabLayout.Tab firsttab = tablayout.newTab();
        tablayout.addTab(firsttab, 0, true);
        tablayout.addTab(tablayout.newTab(), 1);
        tablayout.addTab(tablayout.newTab(), 2);
       // tablayout.setOnTabSelectedListener(this);
        //Adding onTabSelectedListener to swipe views
        tablayout.setOnTabSelectedListener(this);
        PagerMerchantTabDetails adapter=new PagerMerchantTabDetails(getSupportFragmentManager(),tablayout.getTabCount());
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        store_id = getIntent().getStringExtra("store_id");
        MerchantDetails(store_id);


        seeAllMenuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMenu = new Intent(getApplicationContext(), MerchantDetailAllMenu.class);
                iMenu.putExtra("store_id", store_id);
                startActivity(iMenu);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }





    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    public String getStore_id() {
        return store_id;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }





    public void MerchantDetails(String store_id) {

        loader.setVisibility(View.VISIBLE);
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("store_id", store_id);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.MERCHANT_DETAILS, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String merchant_detail = jobj.getString("merchant_detail");
                                JSONObject jsonObject = new JSONObject(merchant_detail);
                                String store_id = jsonObject.getString("store_id");
                                String store_code = jsonObject.getString("store_code");
                                String store_name = jsonObject.getString("store_name");
                                String address = jsonObject.getString("address");
                                String opentime = jsonObject.getString("open_time");
                                String closetime = jsonObject.getString("close_time");
                                String Latitude = jsonObject.getString("latitude");
                                String Longitude = jsonObject.getString("longitude");
                                String brand = jsonObject.getString("brand");
                                String small_image = jsonObject.getString("small_image");
                                String rating = jsonObject.getString("rating");

                               // store_status.setText(store_name);
                                product_name.setText(store_name);
                                product_description.setText(address);
                                Picasso.with(getApplicationContext()).load(small_image).into(mimageView);
                                ratingBar.setRating(Float.parseFloat(rating));
                                latitude= Double.parseDouble(Latitude);
                                longitude= Double.parseDouble(Longitude);
                                if (Latitude != null || !Latitude.equals("") && Longitude != null || !Longitude.equals("")) {
                                    getAddress(latitude, longitude);
                                    //loc=true;

                                    mMapView.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(GoogleMap mMap) {
                                            googleMap = mMap;

                                            // Add a marker in Sydney, Australia, and move the camera.

                                            LatLng LatLong = new LatLng(latitude, longitude);
                                            Log.d("valueinProductPage","  "+longitude+" "+latitude);

                                            giiglemap_adress = latitude+" "+longitude;
                                            googleMap.addMarker(new MarkerOptions().position(LatLong).title(giiglemap_adress).position(LatLong));

                                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLong));
                                            googleMap.animateCamera( CameraUpdateFactory.zoomTo( 8.0f ) );

                                            googleMap.getMaxZoomLevel();
                                        }
                                    });
                                }


                                try {
                                    //String string1 = "20:11:13";
                                    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(opentime);
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.setTime(time1);

                                    // String string2 = "14:49:00";
                                    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(closetime);
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(time2);
                                    calendar2.add(Calendar.DATE, 1);

                                    //String someRandomTime = "01:00:00";

                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                    String currentDateandTime = sdf.format(new Date());

                                    Log.d("svdavasvasfv",currentDateandTime);

                                    Date d = new SimpleDateFormat("HH:mm:ss").parse(currentDateandTime);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(d);
                                    calendar3.add(Calendar.DATE, 1);

                                    Date x = calendar3.getTime();
                                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                        //checkes whether the current time is between 14:49:00 and 20:11:13.
                                        store_status.setText(getString(R.string.close_now));
                                    }else
                                    {
                                        store_status.setText(getString(R.string.open_now));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else if (error.equals("true")){

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
        RequestQueue queue = Volley.newRequestQueue(MerchantProductDetailsActivity.this);
        queue.add(jsonObjReq);

    }

    public void getAddress(Double latitude, Double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();


                merchantLocationText.setText(state);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
