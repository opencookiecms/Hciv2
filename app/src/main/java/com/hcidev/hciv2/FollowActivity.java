package com.hcidev.hciv2;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hcidev.hciv2.restapi.Apiservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;

public class FollowActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectFollow = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_followers:
                    selectFollow = new Followerfragment();
                    break;
                case R.id.navigation_followings:
                    selectFollow = new Followingfragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentfollower_container,selectFollow)
                    .commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_follow);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
