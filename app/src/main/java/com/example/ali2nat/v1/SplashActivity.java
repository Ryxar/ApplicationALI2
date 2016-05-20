package com.example.ali2nat.v1;

/**
 * Created by Adrien.D on 19/04/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show activity
        setContentView(R.layout.activity_splash_layout);
        //Launch Timer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Change activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                //Close Activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}