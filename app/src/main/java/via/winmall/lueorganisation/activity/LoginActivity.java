
package via.winmall.lueorganisation.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.Modal.UserSessionManager;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout signupLinear;
    Button login, fb_login;
    EditText username_text, password_text;
    MKLoader loader;
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new UserSessionManager(getApplicationContext());
        signupLinear = findViewById(R.id.signupLinear);
        signupLinear.setOnClickListener(this);
        login = findViewById(R.id.login);
        fb_login = findViewById(R.id.fb_login);
        login.setOnClickListener(this);
        fb_login.setOnClickListener(this);
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);
        loader = findViewById(R.id.loader);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupLinear:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
                break;

            case R.id.login:
                String username = username_text.getText().toString();
                String password = password_text.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    username_text.requestFocus();
                    username_text.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(password)) {
                    password_text.requestFocus();
                    password_text.setError("This Field Is Mandatory");
                }
                else {
                    Login();

                }

                break;
            case R.id.fb_login:

                break;
        }
    }


    public void Login() {
        loader.setVisibility(View.VISIBLE);
        String username = username_text.getText().toString();
        String password = password_text.getText().toString();

        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_name", username);
        postParam.put("password", password);
        postParam.put("reg_token", "faddddsddddfsf");
        postParam.put("lang", "en");

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.LOGIN, new JSONObject(postParam),
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
                                String userId = jobj.getString("user_id");
                                String username = jobj.getString("user_name");
                                String email = jobj.getString("email");
                                String phoneCode = jobj.getString("phone_code");
                                String mobile =  jobj.getString("mobile");
                                String user_type = jobj.getString("user_type");
                                String user_pic = jobj.getString("user_pic");
                                String gender = jobj.getString("gender");
                                String dob = jobj.getString("dob");
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                SaveUserId.getInstance(getApplicationContext()).saveuserId(userId, username, email, phoneCode,  mobile, user_type,
                                        user_pic, gender, dob);
                                session.createUserLoginSession(userId);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }

                            else if (check.equals("true"))
                            {
                                String msg = jobj.getString("message");
                                proceedMessage(msg);
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
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(jsonObjReq);

    }

    public void proceedMessage(String response) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
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
                    dialog.dismiss();
            }
        });

        dialog.show();
    }

}
