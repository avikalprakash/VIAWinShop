package via.winmall.lueorganisation.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import via.winmall.lueorganisation.Modal.HomeServicePojo;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;
import via.winmall.lueorganisation.activity.MerchantList;
import via.winmall.lueorganisation.activity.SubCategoryActivity;
import via.winmall.lueorganisation.adapter.AndroidImageAdapternew;
import via.winmall.lueorganisation.adapter.ExpandableHeightGridView;
import via.winmall.lueorganisation.adapter.GiftListAdapter;
import via.winmall.lueorganisation.adapter.HorizontalListView;
import via.winmall.lueorganisation.adapter.ItemEntity;
import via.winmall.lueorganisation.adapter.LocalImageSlider;
import via.winmall.lueorganisation.adapter.TopCategoryGridViewAdapter;

public class HomeFragment extends Fragment {
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    private BaseAdapter mAdapter = null;
    private HorizontalListView mtodays_deal_listview = null;
    private HorizontalListView special_listview = null;
    private HorizontalListView best_selling_listview = null;
    float density;
    ViewPager mViewPager;
    CirclePageIndicator indicator;
    private int delay = 5000;
    private int page = 0;
    Handler handler;
    Runnable runnable;
    ImageView full_rebate_image, half_rebate_image;
    ArrayList<ItemEntity> SliderImage = new ArrayList<ItemEntity>();
    ArrayList<String> ImageList = new ArrayList<>();
    ArrayList<HomeServicePojo> homeServicePojosList1 = new ArrayList<>();
    ArrayList<HomeServicePojo> homeServicePojosList2 = new ArrayList<>();
    ArrayList<HomeServicePojo> homeServicePojosList3 = new ArrayList<>();
    ArrayList<SectorPojo> category_id_list = new ArrayList<>();
    AndroidImageAdapternew adapterView;
    ExpandableHeightGridView gridView;
    LocalImageSlider localImageSlider;
    MKLoader loader;


    int images[] = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    int[] imageId = {
            R.drawable.beer_jar,
            R.drawable.dress,
            R.drawable.coffee,
            R.drawable.doll,
            R.drawable.shoe,
            R.drawable.coat,
            R.drawable.razor,
            R.drawable.camera,
            R.drawable.washing_machine,
    };

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.imageView);
        half_rebate_image = view.findViewById(R.id.half_rebate_image);
        full_rebate_image = view.findViewById(R.id.full_rebate_image);
        loader = view.findViewById(R.id.loader);
        indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);
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


        //load slid show image
        SlideBanner();


        gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        // Get list view
        mtodays_deal_listview = (HorizontalListView)view.findViewById(R.id.todays_deal_listview);
        special_listview = (HorizontalListView)view.findViewById(R.id.special_listview);
        best_selling_listview = (HorizontalListView)view.findViewById(R.id.best_selling_listview);

        gridView.setAdapter(new TopCategoryGridViewAdapter(getActivity(), imageId));
        gridView.setExpanded(true);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                String cat_id = category_id_list.get(position).getSector_id();
                Intent i = new Intent(getContext(), MerchantList.class);
                i.putExtra("sector_id", cat_id);
                startActivity(i);
            }
        });

        getCategoryId();

        full_rebate_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rebate100Fragment rebate100Fragment = new Rebate100Fragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, rebate100Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        half_rebate_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rebate50Fragment rebate50Fragment = new Rebate50Fragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, rebate50Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

    public void getCategoryId() {
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Urls.TOP_CATEGORY, new JSONObject(postParam), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jobj) {

                Log.d("result", jobj.toString());

                try {
                    String error = jobj.getString("error");
                    if (error.equals("false")) {
                        String message = jobj.getString("message");
                        JSONArray jsonArray = new JSONArray(message);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jObject = jsonArray.getJSONObject(i);
                            SectorPojo catId = new SectorPojo();
                            catId.setSerial_no(jObject.getString("serial_no"));
                            catId.setSector_id(jObject.getString("sector_id"));
                            category_id_list.add(catId);
                        }


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





    public void TodaysDeals() {

        loader.setVisibility(View.VISIBLE);
        homeServicePojosList2.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.TODAYS_DEAL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {
                            //getting the whole json object from the response


                            String check = jobj.getString("error");
                            if (check.equals("false")){

                                JSONArray jArray = jobj.getJSONArray("store");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < jArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jObject = jArray.getJSONObject(i);
                                    HomeServicePojo homeServicePojo = new HomeServicePojo();
                                    homeServicePojo.setProduct_id(jObject.getString("product_id"));
                                    homeServicePojo.setProduct_code(jObject.getString("product_code"));
                                    homeServicePojo.setProduct_title(jObject.getString("product_title"));
                                    homeServicePojo.setBrand(jObject.getString("brand"));
                                    homeServicePojo.setProduct_price(jObject.getString("product_price"));
                                    homeServicePojo.setProduct_sp_price(jObject.getString("product_sp_price"));
                                    homeServicePojo.setProduct_desc(jObject.getString("product_desc"));
                                    homeServicePojo.setProduct_image(jObject.getString("product_image"));

                                    homeServicePojosList2.add(homeServicePojo);

                                }
                                GiftListAdapter categoryAdapter = new GiftListAdapter(getActivity(), homeServicePojosList2);
                                mtodays_deal_listview.setAdapter(categoryAdapter);
                                SpecialDeal();
                            }else if (check.equals("true")){

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

    public void SpecialDeal() {


        homeServicePojosList1.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.SPECIAL_DEAL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                       // pDialog.dismiss();
                        Log.d("result", jobj.toString());

                        try {
                            //getting the whole json object from the response


                            String check = jobj.getString("error");
                            if (check.equals("false")){

                                JSONArray jArray = jobj.getJSONArray("store");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < jArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jObject = jArray.getJSONObject(i);
                                    HomeServicePojo homeServicePojo = new HomeServicePojo();
                                    homeServicePojo.setProduct_id(jObject.getString("product_id"));
                                    homeServicePojo.setProduct_code(jObject.getString("product_code"));
                                    homeServicePojo.setProduct_title(jObject.getString("product_title"));
                                    homeServicePojo.setBrand(jObject.getString("brand"));
                                    homeServicePojo.setProduct_price(jObject.getString("product_price"));
                                    homeServicePojo.setProduct_sp_price(jObject.getString("product_sp_price"));
                                    homeServicePojo.setProduct_desc(jObject.getString("product_desc"));
                                    homeServicePojo.setProduct_image(jObject.getString("product_image"));

                                    homeServicePojosList1.add(homeServicePojo);

                                }
                                GiftListAdapter categoryAdapter = new GiftListAdapter(getActivity(), homeServicePojosList1);
                                special_listview.setAdapter(categoryAdapter);
                                BestSelling();
                            }else if (check.equals("true")){

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
              //  pDialog.dismiss();
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


    public void SlideBanner() {

        loader.setVisibility(View.VISIBLE);
        ImageList.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.BANNER_SLIDE, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {

                            String error=jobj.getString("error");
                            if (error.equals("false")) {
                                String slideshow=jobj.getString("message");
                                JSONArray slidShowArry = new JSONArray(slideshow);
                                for (int i = 0; i < slidShowArry.length(); i++) {
                                    ItemEntity itemEntity2 = new ItemEntity();
                                    JSONObject jobject = slidShowArry.getJSONObject(i);
                                    itemEntity2.setSliderImage(jobject.getString("small_image"));
                                    SliderImage.add(itemEntity2);
                                    ImageList.add(jobject.getString("small_image"));

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ImageList.size() > 0)
                        {
                            adapterView = new AndroidImageAdapternew(getActivity(), ImageList);
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

                        TodaysDeals();

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


    public void BestSelling() {

        homeServicePojosList3.clear();
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.BEST_SELLING, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {

                        Log.d("result", jobj.toString());

                        try {

                            String check = jobj.getString("error");
                            if (check.equals("false")){

                                JSONArray jArray = jobj.getJSONArray("store");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < jArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jObject = jArray.getJSONObject(i);
                                    HomeServicePojo homeServicePojo = new HomeServicePojo();
                                    homeServicePojo.setProduct_id(jObject.getString("product_id"));
                                    homeServicePojo.setProduct_code(jObject.getString("product_code"));
                                    homeServicePojo.setProduct_title(jObject.getString("product_title"));
                                    homeServicePojo.setBrand(jObject.getString("brand"));
                                    homeServicePojo.setProduct_price(jObject.getString("product_price"));
                                    homeServicePojo.setProduct_sp_price(jObject.getString("product_sp_price"));
                                    homeServicePojo.setProduct_desc(jObject.getString("product_desc"));
                                    homeServicePojo.setProduct_image(jObject.getString("product_image"));

                                    homeServicePojosList3.add(homeServicePojo);

                                }
                                GiftListAdapter categoryAdapter = new GiftListAdapter(getActivity(), homeServicePojosList3);
                                best_selling_listview.setAdapter(categoryAdapter);

                            }else if (check.equals("true")){

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("tag", "Error: " + error.getMessage());

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
