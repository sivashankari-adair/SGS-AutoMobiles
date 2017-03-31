package com.adairtechnology.sgsautomobile.ActivityClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.adairtechnology.sgsautomobile.R;
import com.adairtechnology.sgsautomobile.Utils.Utils;

/**
 * Created by Android-Team1 on 1/4/2017.
 */

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_splash_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("MYPREF", MODE_PRIVATE);
                String restoredTextEmail = prefs.getString("UserName", null);
                String restoredTextPassword = prefs.getString("Password", null);

                if (!Utils.isNetworkAvailable(SplashScreen.this)){
                    Toast.makeText(SplashScreen.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                    if (restoredTextEmail != null && restoredTextPassword != null) {
                        Intent mainIntent = new Intent(SplashScreen.this, HomeScreenActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    } else {
                        Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
            }
                else {
                    Toast.makeText(SplashScreen.this, " Connection Avilable " , Toast.LENGTH_SHORT).show();
                    if (restoredTextEmail != null && restoredTextPassword != null) {
                        Intent mainIntent = new Intent(SplashScreen.this, HomeScreenActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    } else {
                        Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
               }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
