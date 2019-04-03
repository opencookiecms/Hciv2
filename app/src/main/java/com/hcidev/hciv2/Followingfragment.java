package com.hcidev.hciv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcidev.hciv2.controlleradapter.Followingsadapter;
import com.hcidev.hciv2.controlleradapter.Followsadapter;
import com.hcidev.hciv2.model.Subscribefollow;
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Followingfragment extends Fragment {

    RecyclerView recyclerView;
    Followingsadapter followingsadapter;
    Apiservice apiservicel;
    public static final String USERNAME = "USERNAME";
    SharedPreferences pref;


    public Followingfragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview  = inflater.inflate(R.layout.following_content, container, false);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.ffrecylerview);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(followingsadapter);

        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);

        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = getpref.edit();
        pref = getActivity().getApplication().getSharedPreferences("saveprofile", Context.MODE_PRIVATE);


        String username = pref.getString(USERNAME,"");

        Call<ArrayList<Subscribefollow>> call = apiservice.getFollowing(username);
        call.enqueue(new Callback<ArrayList<Subscribefollow>>() {
            @Override
            public void onResponse(Call<ArrayList<Subscribefollow>> call, Response<ArrayList<Subscribefollow>> response) {
                followingsadapter= new Followingsadapter(response.body());
                recyclerView.setAdapter(followingsadapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Subscribefollow>> call, Throwable t) {

            }
        });


        return rootview;
    }
}
