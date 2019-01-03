package via.winmall.lueorganisation.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import via.winmall.lueorganisation.Fragment.AboutUsFragment;
import via.winmall.lueorganisation.Fragment.BecomeMerchantFragment;
import via.winmall.lueorganisation.Fragment.ContactUsFragment;
import via.winmall.lueorganisation.Fragment.EWalletFragment;
import via.winmall.lueorganisation.Fragment.EventFragment;
import via.winmall.lueorganisation.Fragment.HomeFragment;
import via.winmall.lueorganisation.Fragment.IntroducerQrFragment;
import via.winmall.lueorganisation.Fragment.LocationFragment;
import via.winmall.lueorganisation.Fragment.LoyaltyProgramFragment;
import via.winmall.lueorganisation.Fragment.MeFragment;
import via.winmall.lueorganisation.Fragment.MyCartFragment;
import via.winmall.lueorganisation.Fragment.MyOrderFragment;
import via.winmall.lueorganisation.Fragment.MyWishlistFragment;
import via.winmall.lueorganisation.Fragment.NearMeFragment;
import via.winmall.lueorganisation.Fragment.PaymentQRFragment;
import via.winmall.lueorganisation.Fragment.Rebate100Fragment;
import via.winmall.lueorganisation.Fragment.Rebate50Fragment;
import via.winmall.lueorganisation.Fragment.SectorFragment;
import via.winmall.lueorganisation.Fragment.TermsOfUseFragment;
import via.winmall.lueorganisation.Fragment.UserGuideFragment;
import via.winmall.lueorganisation.Fragment.VoucherFragment;
import via.winmall.lueorganisation.Modal.AbsRuntimePermission;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.UserSessionManager;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//

public class MainActivity extends AbsRuntimePermission
        implements NavigationView.OnNavigationItemSelectedListener  {
    AlertDialog alertDialog;
    ImageView headerimage, logoUserPic;
    ViewPager viewpager1;
    HomeFragment home;
    DrawerLayout drawer;
    TextView headertext, usernameText,username2;
    View hView;
    BottomNavigationView bn;
    NavigationView navigationView;
    static FrameLayout frame_head;
    String user_id="";
    public static final int REQUEST_PERMISSION_CODE=1;
    String name;
    TextView textView;
    String log="";
    String countString;
    RelativeLayout headerlayout;
    UserSessionManager session;
    SharedPreferences sharedPrefotpaccheckst;
    String username;
    String userID;
    String user_pic;
    ProgressDialog pDialog;
    Context context;
    private static final int REQUEST_PERMISSION = 10;
    MKLoader loader;
    public boolean locationDetected = false;
    ProgressDialog locationProgressDialog;
    LocationManager mlocManager;
    AlertDialog.Builder alert;
    String lat = "";
    String lang = "";
    Timer timer1;
    TimerTask timerTask;
    int count = 0;
    Location myLocation;
    boolean GpsStatus ;
    View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarheader);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }
        context = this;
        userID = SaveUserId.getInstance(MainActivity.this).getUserId();
        bn = (BottomNavigationView) findViewById(R.id.navigation);
        loader = findViewById(R.id.loader);
        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:

                        home = new HomeFragment();
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.container, home);
                        transaction1.commit();
                        break;

                    case R.id.navigation_sector:
                        SectorFragment sectorFragment= new SectorFragment();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.container, sectorFragment);
                        transaction2.addToBackStack(null);
                        transaction2.commit();
                        break;
                    case R.id.navigation_location:
                        LocationFragment locationFragment= new LocationFragment();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.container, locationFragment);
                        transaction3.addToBackStack(null);
                        transaction3.commit();
                        break;
                    case R.id.navigation_inbox:
                        startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                        break;

                    case R.id.navigation_me:
                        MeFragment meFragment= new MeFragment();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.container, meFragment);
                        transaction5.addToBackStack(null);
                        transaction5.commit();

                        break;


                }
                return true;
            }
        });



        checkRunTimePermission();

        requestAppPermissions(new String[]{

                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        //       Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                },

                R.string.ok, REQUEST_PERMISSION);



        headerlayout = (RelativeLayout) findViewById(R.id.headerlayout);
        headerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });

        headerimage=(ImageView)findViewById(R.id.search_header);
        headerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        headertext=(TextView)findViewById(R.id.headertext);
       // searchLayout=(RelativeLayout) findViewById(R.id.search_header);
        headertext.setText("Win Mall");


       // username2=(TextView)findViewById(R.id.username2);


           textView = (TextView)findViewById(R.id.fab2);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.setDrawerListener(toggle);
        //
        toolbar1.setNavigationIcon(R.drawable.ic_dehaze);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().findItem(R.id.home).setVisible(true);
        header = navigationView.getHeaderView(0);


        RelativeLayout headerView = (RelativeLayout) header.findViewById(R.id.relativeMain);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeFragment meFragment= new MeFragment();
                FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                transaction5.replace(R.id.container, meFragment);
                transaction5.addToBackStack(null);
                transaction5.commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        home = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, home);
        transaction.commit();
        frame_head = (FrameLayout)findViewById(R.id.frame_head);


        frame_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyCartFragment myCartFragment = new MyCartFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, myCartFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });





        getGpsLocation();

    }


    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @Override
    protected void onResume() {

        super.onResume();
//                username = SaveUserId.getInstance(MainActivity.this).getUserName();
//                user_pic = SaveUserId.getInstance(MainActivity.this).getTagPic();
        usernameText =(TextView)header.findViewById(R.id.username);
        logoUserPic = (ImageView)header.findViewById(R.id.logoUserPic);
        getProfile();

    }

    public void getProfile() {
        loader.setVisibility(View.VISIBLE);
        user_id = SaveUserId.getInstance(MainActivity.this).getUserId();
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

                        try {
                            usernameText.setText(user_name);
                            Picasso.with(getApplicationContext()).load(user_pic).into(logoUserPic);
                        }catch (Exception e){}

                    } else if (error.equals("true")) {
                        String s = jobj.getString("message");

                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
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
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjReq);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(home.isVisible() && MainActivity.this!= null && !MainActivity.this.isFinishing()){
          //  showDilog();
            AppExit();
        }
        else {
            super.onBackPressed();
        }
    }

    public void proceedLogout() {
        final Dialog dialog = new Dialog(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.alert_logout, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(subView);
        // dialog.setTitle("Title...");
        Button yes = (Button) dialog.findViewById(R.id.yes);
        Button no = (Button) dialog.findViewById(R.id.no);
        //final MyEditText otp=(MyEditText)dialog.findViewById(R.id.otp);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                dialog.dismiss();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    void showDilog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit ?");
        alertDialogBuilder.setCancelable( false );
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                           @Override
                                           public void onShow(DialogInterface arg0) {
                                               alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( getResources().getColor( R.color.colorPrimary ));
                                               alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor( getResources().getColor( R.color.colorPrimary ));
                                           }
                                       }
        );
        alertDialog.show();
    }


    public void AppExit() {
        final Dialog dialog = new Dialog(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.alert_app_exit, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(subView);
        // dialog.setTitle("Title...");
        Button yes = (Button) dialog.findViewById(R.id.yes);
        Button no = (Button) dialog.findViewById(R.id.no);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }




    @Override
    protected void onRestart() {

        super.onRestart();
    }


// Permission giving at runtime

    private void checkRunTimePermission() {

        if(checkPermission()){

            Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

            requestPermission();
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        CAMERA,
                        ACCESS_COARSE_LOCATION,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE

                },  REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case  REQUEST_PERMISSION_CODE:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadContactsPermission ) {

                     //   Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                     //   Toast.makeText(MainActivity.this,"Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*if(sessionManagement.isLoggedIn()) {
           item.setVisible(false);
        }*/


        if (id == R.id.home) {
            // Handle the camera action

            home = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, home);
            transaction.commit();



        }else if(id == R.id.rebate_full){
            Rebate100Fragment rebate100Fragment = new Rebate100Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, rebate100Fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(id == R.id.rebate_half){
            Rebate50Fragment rebate50Fragment = new Rebate50Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, rebate50Fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.near_me){
            NearMeFragment nearMeFragment = new NearMeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, nearMeFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.my_order){
            MyOrderFragment myOrderFragment = new MyOrderFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, myOrderFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.my_cart){
            MyCartFragment myCartFragment = new MyCartFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, myCartFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.my_wishlist){
            MyWishlistFragment myWishlistFragment = new MyWishlistFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, myWishlistFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.introducer_qr){
            IntroducerQrFragment introducerQrFragment = new IntroducerQrFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, introducerQrFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.e_wallet){

            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
          /*  EWalletFragment eWalletFragment = new EWalletFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, eWalletFragment);
            transaction.addToBackStack(null);
            transaction.commit();*/
        }else if(id == R.id.payment_qr){
            PaymentQRFragment paymentQRFragment = new PaymentQRFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, paymentQRFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.loylty_programme){
            LoyaltyProgramFragment loyaltyProgramFragment = new LoyaltyProgramFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, loyaltyProgramFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.e_voucher){
            VoucherFragment voucherFragment = new VoucherFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, voucherFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.news){

            startActivity(new Intent(getApplicationContext(), NewsActivity.class));

        }else if(id == R.id.become_merchant){
            BecomeMerchantFragment becomeMerchantFragment = new BecomeMerchantFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, becomeMerchantFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.user_guide){
            UserGuideFragment userGuideFragment = new UserGuideFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, userGuideFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.about_us){
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, aboutUsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.contact){
            ContactUsFragment contactUsFragment = new ContactUsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, contactUsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.events){
            EventFragment eventFragment = new EventFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, eventFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.terms_use){
            TermsOfUseFragment termsOfUseFragment = new TermsOfUseFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, termsOfUseFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.help){
            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
        }else if(id == R.id.logout){
            proceedLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Logout() {

        loader.setVisibility(View.VISIBLE);
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", userID);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.LOGOUT, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {

                            String check = jobj.getString("error");

                            if (check.equals("false"))
                            {
                                session.logoutUser();
                                finish();

                            }

                            else if (check.equals("true"))
                            {

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
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjReq);

    }


    public static void popBackStack(FragmentManager manager){
        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getGpsLocation() {

      //  locationProgressDialog.show();
        alert = new AlertDialog.Builder(this);
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
           /* alert.setTitle("GPS");
            alert.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS...");*/
            alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
            alert.setView(R.layout.gps_message);
            alert.setPositiveButton(R.string.allow_gps,
                    new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                return;
                            }
                            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                                    (float) 0.01, (android.location.LocationListener) listener);
                            setCriteria();

                            mlocManager.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER, 0, (float)
                                            0.01, (android.location.LocationListener) listener);

                            Intent I = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(I);

                        }
                    });
            alert.show();


        } else {
try {
    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, (android.location.LocationListener) listener);
    mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, (android.location.LocationListener) listener);
}catch (Exception e){}
        }
        count =0;
        startTimer();

    }


    private void startTimer(){
        timer1 = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        if(count >20)
                        {
                          //  if(locationProgressDialog.isShowing()) locationProgressDialog.dismiss();
                            if (myLocation==null){
                                //Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                                timer1.cancel();
                            }
                        }
                    }
                });
            }
        };

        timer1.schedule(timerTask, 1000, 1000);
    }


    private final android.location.LocationListener listener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myLocation=location;


            if (location.getLatitude() > 0.0) {
                DecimalFormat df= new DecimalFormat("#.00000000");

                lat=String.valueOf(df.format(location.getLatitude()));
                lang=String.valueOf(df.format(location.getLongitude()));
            //    locationProgressDialog.setMessage("lat: "+lat+"\n"+"long:"+lang+"\n"+"accuracy:"+location.getAccuracy());
                if (location.getAccuracy()>0 && location.getAccuracy()<100) {
                //    if(locationProgressDialog.isShowing()) locationProgressDialog.dismiss();
                //     Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lang, Toast.LENGTH_LONG).show();
                   /* if (Double.valueOf(lat).equals(",")|| Double.valueOf(lang).equals(",")){
                        lat=".";
                        lang=".";
                    }*/


                    lat=lat.replaceAll(",",".");

                    lang=lang.replaceAll(",",".");


                }
                else
                {


                }
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

    };

    public String setCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = mlocManager.getBestProvider(criteria, true);
        return provider;
    }

    public String getLat() {
        return lat;
    }

    public String getLang() {
        return lang;
    }


}
