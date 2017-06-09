package com.innoapps.eventmanagement.common.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.login.LoginActivity;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.utils.UserSession;

/**
 * Created by Ankit on 3/6/2017.
 */

public class SplashActivity extends AppCompatActivity {
    UserSession userSession;
    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove notification bar
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                // This method will be executed once the timer is over

              /*  Intent intent = new Intent(SplashActivity.this,ProfileActivity.class);*/
                userSession = new UserSession(SplashActivity.this);
                if (userSession.isUserLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }


}
