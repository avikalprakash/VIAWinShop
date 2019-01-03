package via.winmall.lueorganisation.Modal;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fujitsu on 14/06/2017.
 */

public class SaveUserId {
    private static final String SHARED_PREF_NAME = "saveuserid";
    private static final String TAG_TOKEN = "save";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PHONE_CODE = "phone_code";
    private static final String TAG_MOBILE = "mobile";
    private static final String TAG_USERTYPE = "user_type";
    private static final String TAG_PIC = "pic";
    private static final String TAG_GENGER ="gender";
    private static final String TAG_DOB ="don";

    private static SaveUserId mInstance;
    private static Context mCtx;

    private SaveUserId(Context context) {
        mCtx = context;
    }

    public static synchronized SaveUserId getInstance(Context context)
    {
        if (mInstance == null) {
            mInstance = new SaveUserId(context);
        }
        return mInstance;
    }


    //this method will save the device token to shared preferences
    public boolean saveuserId(String id, String username, String email, String phoneCode,  String mobile, String user_type,
                              String pic, String gender, String dob)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, id);
        editor.putString(TAG_USERNAME, username);
        editor.putString(TAG_EMAIL, email);
        editor.putString(TAG_PHONE_CODE, phoneCode);
        editor.putString(TAG_MOBILE, mobile);
        editor.putString(TAG_USERTYPE, user_type);
        editor.putString(TAG_PIC, pic);
        editor.putString(TAG_GENGER, gender);
        editor.putString(TAG_DOB, dob);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getUserId()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }


    public String getUserName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_USERNAME, null);
    }

    public String getTagUsertype()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_USERTYPE, null);
    }


    public String getTagEmail()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_EMAIL, null);
    }

    public String getTagPhoneCode()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_PHONE_CODE, null);
    }

    public String getTagMobile()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_MOBILE, null);
    }

    public String getTagPic()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_PIC, null);
    }


    public String getTagGenger()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_GENGER, null);
    }

    public String getTagDob()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_DOB, null);
    }




}
