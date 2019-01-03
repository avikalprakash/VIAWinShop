package via.winmall.lueorganisation.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import via.winmall.lueorganisation.Modal.FileUtil;
import via.winmall.lueorganisation.Modal.SaveUserId;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.Urls.Urls;

public class EditProfile extends AppCompatActivity {

    Button msavebtn;

    EditText mmobiletxt, dob_rdtxt;
    TextView prem ;
    String profilePic;
    Uri imageUri;
    private File actualImage;

    private int PICK_IMAGE_REQUEST = 1;
    private int CAMERA_REQUEST = 2;

    AlertDialog dialog;

    Bitmap newBitmap;

    Bitmap bitmap;
    TextView tvheadertitle, nametxt;
    ImageView back;
    CircleImageView logoImage;
    ProgressDialog pDialog;
    private File actualImagePic;
    Bitmap  compressedImageBitmapPic;
    ImageView editPicture;
    MKLoader loader;
    String username, phone_code, gender, email, mobile, logo, user_id, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        logoImage = (CircleImageView) findViewById(R.id.logomimf);
        nametxt = (TextView)findViewById(R.id.nametxt);
        mmobiletxt = (EditText) findViewById(R.id.mobile_rdtxt);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);
        tvheadertitle.setText(getString(R.string.edit_profile));
        back = (ImageView)findViewById(R.id.ivheaderleft);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dob_rdtxt = findViewById(R.id.dob_rdtxt);
        user_id = SaveUserId.getInstance(this).getUserId();
        username = SaveUserId.getInstance(this).getUserName();
        mobile = SaveUserId.getInstance(this).getTagMobile();
        logo = SaveUserId.getInstance(this).getTagPic();
        dob = SaveUserId.getInstance(this).getTagDob();
        editPicture = findViewById(R.id.editPicture);
        loader = findViewById(R.id.loader);

        if (!username.equals(null) || !username.equals("")) {
            nametxt.setText(username);
        }

        if (!dob.equals(null) || !dob.equals("")) {
            dob_rdtxt.setText(dob);
        }

        if (!mobile.equals(null) || !mobile.equals("")) {
            mmobiletxt.setText(mobile);
        }

        if (!logo.equals(null) || !logo.equals("")) {
            Picasso.with(getApplicationContext()).load(logo).into(logoImage);
        }





        msavebtn = (Button) findViewById(R.id.save_btn);
        msavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = mmobiletxt.getText().toString();
                String dob = dob_rdtxt.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    mmobiletxt.requestFocus();
                    mmobiletxt.setError("This Field Is Mandatory");
                }else if (TextUtils.isEmpty(dob)) {
                    dob_rdtxt.requestFocus();
                    dob_rdtxt.setError("This Field Is Mandatory");
                }else if (!dob.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")){
                    dob_rdtxt.requestFocus();
                    dob_rdtxt.setError("Enter DOB in dd-mm-yyyy format");
                }else {
                    UpdateProfile();
                }
            }
        });


        editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mbuilder = new AlertDialog.Builder(EditProfile.this);
                View mview = getLayoutInflater().inflate(R.layout.edit_image_layout, null);

                LinearLayout cameraBtnLayout = (LinearLayout) mview.findViewById(R.id.cameraBtnLayout);
                cameraBtnLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });

                LinearLayout galleryBtnLayout = (LinearLayout) mview.findViewById(R.id.galleryBtnLayout);
                galleryBtnLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        showFileChooser();

                    }
                });
                mbuilder.setView(mview);
                dialog = mbuilder.create();
                dialog.show();
            }

        });
    }

    private void showFileChooser() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 23) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            } else {

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, ""), PICK_IMAGE_REQUEST);

            }
        } catch (Exception e) {

            //   Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onBackPressed() {

        finish();

        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode >= PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                //   mfimage.setImageBitmap(bitmap);
                actualImage = FileUtil.from(this, filePath);

                try {
                    compressedImageBitmapPic = new Compressor(EditProfile.this).compressToBitmap(actualImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                newBitmap = compressedImageBitmapPic;
               // newBitmap =   rotateImageIfRequired(getApplicationContext(), compressedImageBitmapPic, imageUri);
                Picasso.with(getApplicationContext()).load(filePath).into(logoImage);
                dialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (requestCode == CAMERA_REQUEST) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                //    mfimage.setImageBitmap(bitmap);
                actualImage = FileUtil.from(this, imageUri);

                try {
                    compressedImageBitmapPic = new Compressor(EditProfile.this).compressToBitmap(actualImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                newBitmap = compressedImageBitmapPic;

              //  newBitmap =   rotateImageIfRequired(getApplicationContext(), compressedImageBitmapPic, imageUri);

                Picasso.with(getApplicationContext()).load(actualImage).into(logoImage);
                dialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bmp == null){

        }else {
            bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        }
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void UpdateProfile() {

        String mobile = mmobiletxt.getText().toString();
        user_id = SaveUserId.getInstance(this).getUserId();
        phone_code = SaveUserId.getInstance(this).getTagPhoneCode();
        String gender = SaveUserId.getInstance(this).getTagGenger();
        String dob = dob_rdtxt.getText().toString();
        logo = getStringImage(newBitmap);
        loader.setVisibility(View.VISIBLE);
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("api_key", "U8BnHZ2NpPsPbOlhdNHse");
        postParam.put("user_id", user_id);
        postParam.put("phone_code", phone_code);
        postParam.put("mobile", mobile);
        postParam.put("gender", gender);
        postParam.put("dob", dob);
        postParam.put("user_pic", logo);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Urls.PROFILE_UPDATE, new JSONObject(postParam),
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

                                SaveUserId.getInstance(getApplicationContext()).saveuserId(userId, username, email,
                                        phoneCode,  mobile, user_type, user_pic, gender, dob);
                                Toast.makeText(EditProfile.this, msg, Toast.LENGTH_SHORT).show();
                                finish();
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
        RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        queue.add(jsonObjReq);

    }
}
