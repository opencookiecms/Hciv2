package com.hcidev.hciv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class Followerfragment extends Fragment {

    RecyclerView recyclerView;
    Followsadapter followsadapter;
    Apiservice apiservicel;

    public static final String USERNAME = "USERNAME";
    SharedPreferences pref;

    public Followerfragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview  = inflater.inflate(R.layout.follower_content, container, false);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.ffrecylerview);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(followsadapter);

        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = getpref.edit();
        pref = getActivity().getApplication().getSharedPreferences("saveprofile", Context.MODE_PRIVATE);


        String username = pref.getString(USERNAME,"");

        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);



       Call<ArrayList<Subscribefollow>> call = apiservice.getFollowwer(username);
       call.enqueue(new Callback<ArrayList<Subscribefollow>>() {
           @Override
           public void onResponse(Call<ArrayList<Subscribefollow>> call, Response<ArrayList<Subscribefollow>> response) {
               followsadapter = new Followsadapter(response.body());
               recyclerView.setAdapter(followsadapter);
           }

           @Override
           public void onFailure(Call<ArrayList<Subscribefollow>> call, Throwable t) {

           }
       });



        return rootview;
    }
}
