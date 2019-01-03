package via.winmall.lueorganisation.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import via.winmall.lueorganisation.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             startActivity(new Intent(getApplicationContext(), MainActivity.class));
             finish();
            }
        }, 3000);
    }
}
