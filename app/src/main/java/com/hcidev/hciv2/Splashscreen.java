package com.hcidev.hciv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME = 3000;
    SharedPreferences pref;
    public static final String USERNAME = "USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);


        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = getpref.edit();
        pref = getApplication().getSharedPreferences("saveprofile",MODE_PRIVATE);

        final String username = pref.getString(USERNAME,"logout");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String logout = "";


                if(pref.getString(USERNAME,"") == logout){
                    Intent intent = new Intent(Splashscreen.this, Loginactivity.class);
                    startActivity(intent);
                    finish();
               }else {
                    Intent intent2 = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
               }


            }
        },SPLASH_TIME);




    }
}
