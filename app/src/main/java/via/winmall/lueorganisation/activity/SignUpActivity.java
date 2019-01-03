package via.winmall.lueorganisation.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout signinLinear;
    private RadioButton radioMale, radioFemale;
    private RadioGroup radioGroup;
    EditText usernameText, emailText, dobText, passText, mobileText;
    Button signupBtn;
    String gender="";
    Calendar myCalendar;
    ProgressDialog pDialog;
    String phone_code="";
    MKLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signinLinear = findViewById(R.id.signinLinear);
        usernameText = findViewById(R.id.username);
        emailText = findViewById(R.id.email);
        dobText = findViewById(R.id.dob);
        passText = findViewById(R.id.password);
        mobileText = findViewById(R.id.mobile);
        signupBtn = findViewById(R.id.sign_up);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        radioMale = (RadioButton)findViewById(R.id.radioMale);
        radioFemale = (RadioButton)findViewById(R.id.radioFemale);
        signupBtn.setOnClickListener(this);
        signinLinear.setOnClickListener(this);
        dobText.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        loader = findViewById(R.id.loader);
        phone_code = "+"+GetCountryZipCode();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioMale:
                        // do operations specific to this selection

                        try {
                            if (radioMale==null || radioMale.getText().equals("")) {
                                Toast.makeText(SignUpActivity.this, "Please select your Gender", Toast.LENGTH_SHORT).show();
                            }
                            else if (radioMale.getText().equals("Male")){
                                gender="Male";
                                radioMale.setTextColor(Color.parseColor("#000000"));
                                radioFemale.setTextColor(Color.parseColor("#c1cdcd"));

                            }

                        }catch (Exception e){}
                        break;
                    case R.id.radioFemale:
                        try {
                            if (radioFemale==null || radioFemale.getText().equals("")) {
                                Toast.makeText(SignUpActivity.this, "Please select your Gender", Toast.LENGTH_SHORT).show();
                            }
                            else if (radioFemale.getText().equals("Female")){
                                gender="Female";
                                radioFemale.setTextColor(Color.parseColor("#000000"));
                                radioMale.setTextColor(Color.parseColor("#c1cdcd"));
                            }

                        }catch (Exception e){}
                        break;



                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signinLinear:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;

            case R.id.sign_up:
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String mobile = mobileText.getText().toString();
                String dob = dobText.getText().toString();
                String password = passText.getText().toString();
                if (!isValidEmail(email)){
                    emailText.requestFocus();
                    emailText.setError("Invalid Email Id");
                }else if (TextUtils.isEmpty(email)) {
                    emailText.requestFocus();
                    emailText.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(password)) {
                    passText.requestFocus();
                    passText.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(username)) {
                    usernameText.requestFocus();
                    usernameText.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(mobile)) {
                    mobileText.requestFocus();
                    mobileText.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(dob)) {
                    dobText.requestFocus();
                    dobText.setError("This Field Is Mandatory");
                }else if (!dob.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")){
                    dobText.requestFocus();
                    dobText.setError("Enter DOB in dd-mm-yyyy format");
                }
                else {

                    SignUp();

                }

                break;

            case R.id.dob:
               /* new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                break;
              }
    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        dobText.setText(sdf.format(myCalendar.getTime()));

    }


    public void SignUp() {
        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String dob = dobText.getText().toString();
        String password = passText.getText().toString();
        String phone_code = "+"+GetCountryZipCode();

        loader.setVisibility(View.VISIBLE);

        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("sign_up_type", "1");
        postParam.put("user_name", username);
        postParam.put("gender", gender);
        postParam.put("email", email);
        postParam.put("phone_code", phone_code);

        postParam.put("mobile", mobile);
        postParam.put("password", password);
        postParam.put("facebook_id", "");
        postParam.put("reg_token", "qazwsxedcrfvtgbyhnuejmikwolp");
        postParam.put("dob", dob);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.SIGN_UP, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        loader.setVisibility(View.GONE);
                        Log.d("result", jobj.toString());

                        try {

                            String check = jobj.getString("error");

                            if (check.equals("false"))
                            {
                                String msg = jobj.getString("message");
                                proceedMessage(msg, "1");
                            }

                            else if (check.equals("true"))
                            {
                                String msg = jobj.getString("message");
                                proceedMessage(msg, "2");
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

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjReq.setTag("tag");
        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        queue.add(jsonObjReq);

    }

    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }

    public void proceedMessage(String response, final String type) {
        final Dialog dialog = new Dialog(SignUpActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SignUpActivity.this);
        View subView = inflater.inflate(R.layout.alert_response, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(subView);
        // dialog.setTitle("Title...");
        Button yes = (Button) dialog.findViewById(R.id.ok);
        TextView responseText = (TextView)dialog.findViewById(R.id.text1);

        responseText.setText(response);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("1")){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else if (type.equals("2")){
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
