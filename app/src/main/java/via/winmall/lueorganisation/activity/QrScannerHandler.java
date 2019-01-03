package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import via.winmall.lueorganisation.Modal.Help;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;

public class QrScannerHandler extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mscanner;
    Help handler;
    String Data;
    ProgressDialog dialog;
    Button back;
    MKLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner_handler);
        Log.d("HELLO<><><><>", "qr scanner called");
        mscanner = new ZXingScannerView(this);
        mscanner = new ZXingScannerView(this);
        setContentView(mscanner);
        mscanner.setResultHandler(this);
        loader = (MKLoader) findViewById(R.id.loader);


//                handler.ZINGSCANNERVIEW=1;

        // Start camera on resume
        mscanner.startCamera();
        Help.CAMERA_STATUS = 1;
        back = (Button) findViewById(R.id.back);
       /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("HELLO<><><><>", rawResult.getText()); // Prints scan results
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("HELLO<><><><>", rawResult.getBarcodeFormat().toString());
        Data = rawResult.getText();

        mscanner.stopCameraPreview();
        mscanner.stopCamera();
        Help.DATA = Data;
        String key = "id";
        MakeIntroducer();
        //finish();
    }


    public void onPause() {

        super.onPause();
        if (Help.CAMERA_STATUS == 1) {
            mscanner.stopCamera();
        }
        // Stop camera on pause
        Log.d("Hello<><><><>", "OnPause of scanner is called");
    }

    public void MakeIntroducer() {
        final ProgressDialog progressDialog = new ProgressDialog(QrScannerHandler.this, R.style.MyDialogTheme);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();

        String  user_id = SaveUserId.getInstance(getApplicationContext()).getUserId();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);
        postParam.put("qr_code", Data);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.USER_INTRODUCER_SCAN_QR, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {
                        progressDialog.dismiss();
                        Log.d("result", jobj.toString());

                        try {
                            String error=jobj.getString("error");
                            if (error.equals("false")) {

                                String s = jobj.getString("message");
                                Toast.makeText(QrScannerHandler.this, s, Toast.LENGTH_SHORT).show();
                                finish();

                            }else if (error.equals("true")){
                                String s = jobj.getString("message");
                                Toast.makeText(QrScannerHandler.this, s, Toast.LENGTH_SHORT).show();
                                finish();
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
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjReq);

    }

}