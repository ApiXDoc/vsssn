package com.mradkingshop.vsssn.admin.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mradkingshop.vsssn.R;


public class Splash extends Activity {
    private static final String LOG_TAG = "SplashActivity";

    /**
     * Number of seconds to count down before showing the app open ad. This simulates the time needed
     * to load the app.
     */
    private static final long COUNTER_TIME = 5;

    private long secondsRemaining;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler=new Handler();


        Intent intent = getIntent();

        if (intent.hasExtra("key")) {
            Toast.makeText(getApplicationContext(), getIntent().getExtras().getString("key"), Toast.LENGTH_SHORT).show();

        } else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(Splash.this, login.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);


        }



    }


}


