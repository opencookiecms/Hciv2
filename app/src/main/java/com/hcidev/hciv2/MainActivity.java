package com.hcidev.hciv2;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hcidev.hciv2.restapi.Apiservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Apiservice apiservice;
    public static final String USERNAME = "USERNAME";
    SharedPreferences pref;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectfragmetn = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectfragmetn = new Mynotefragment();

                    break;
                case R.id.navigation_friends:
                    selectfragmetn = new Getallfragment();
                    break;
                case R.id.navigation_profile:
                    selectfragmetn = new Profilefragment();
                    break;
                case R.id.navigation_info:
                    selectfragmetn = new Requestfragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,selectfragmetn)
                    .addToBackStack(null)
                    .commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
