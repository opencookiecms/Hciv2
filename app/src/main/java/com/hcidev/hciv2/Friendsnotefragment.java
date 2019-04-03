package com.hcidev.hciv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcidev.hciv2.FollowActivity;
import com.hcidev.hciv2.R;
import com.hcidev.hciv2.Uploadfragment;
import com.hcidev.hciv2.controlleradapter.Friendsadapter;
import com.hcidev.hciv2.controlleradapter.Profileadapter;
import com.hcidev.hciv2.model.Follows;
import com.hcidev.hciv2.model.Note;
import com.hcidev.hciv2.model.Notecount;
import com.hcidev.hciv2.model.Users;
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Friendsnotefragment extends Fragment {




    RecyclerView recyclerView;
    Friendsadapter friendsadapter;
    TextView usertext;
    TextView followcount;
    TextView followingcount;
    TextView notecount;
    Button uploadfragment;
    TextView followbut;
    ImageView mypic;
    TextView mystatus;
    TextView userpic;
    String userloadpic = "";
    public static final String FUSERNAME = "FUSERNAME";
    SharedPreferences pref;


    public Friendsnotefragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview  = inflater.inflate(R.layout.mynote, container, false);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.mynoterecylerviews);
        usertext = (TextView) rootview.findViewById(R.id.textUsername);
        followcount = (TextView) rootview.findViewById(R.id.followerId);
        followingcount = (TextView) rootview.findViewById(R.id.followingid);
        notecount = (TextView) rootview.findViewById(R.id.mynoteid);
        uploadfragment = (Button) rootview.findViewById(R.id.uploadnew);
        followbut = (TextView) rootview.findViewById(R.id.followid) ;
        mypic = (ImageView) rootview.findViewById(R.id.myimage);
        userpic = (TextView) rootview.findViewById(R.id.pictxt);
        mystatus = (TextView) rootview.findViewById(R.id.textStatus);

        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = getpref.edit();
        pref = getActivity().getApplication().getSharedPreferences("savefriends",Context.MODE_PRIVATE);


        String username = pref.getString(FUSERNAME,"");







       // Picasso.get().load(loadurl).into(mypic);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
       // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(friendsadapter);


        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);

        Call<List<Users>> call = apiservice.getProfile(username);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                usertext.setText(response.body().get(0).getUser_firstname());
                userpic.setText(response.body().get(0).getUser_pic());
                mystatus.setText(response.body().get(0).getUser_caption());

                Uri uri = Uri.parse("http://172.20.10.4/hcirestapi/assets/picture/"+response.body().get(0).getUser_pic());
                Picasso.get().load(uri).into(mypic);



            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });


        Call<ArrayList<Note>> call1 = apiservice.getNote(username);
        call1.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                friendsadapter = new Friendsadapter(response.body());
                recyclerView.setAdapter(friendsadapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {

            }
        });

        Call<List<Follows>> call2 = apiservice.getFollowercount(username);
        call2.enqueue(new Callback<List<Follows>>() {
            @Override
            public void onResponse(Call<List<Follows>> call, Response<List<Follows>> response) {
                followcount.setText(response.body().get(0).getFollower());
                //followingcount.setText(response.body().get(0).getFollowing());
            }

            @Override
            public void onFailure(Call<List<Follows>> call, Throwable t) {

            }
        });

        Call<List<Follows>> call5 = apiservice.getFollowingcount(username);
        call5.enqueue(new Callback<List<Follows>>() {
            @Override
            public void onResponse(Call<List<Follows>> call, Response<List<Follows>> response) {


                if(response.body().get(0).getFollowing()==null){
                    followingcount.setText("0");
                }
                followingcount.setText(response.body().get(0).getFollowing());

            }

            @Override
            public void onFailure(Call<List<Follows>> call, Throwable t) {

            }
        });

        Call<List<Notecount>> call3 = apiservice.getNotecount(username);
        call3.enqueue(new Callback<List<Notecount>>() {
            @Override
            public void onResponse(Call<List<Notecount>> call, Response<List<Notecount>> response) {
                notecount.setText(response.body().get(0).getNotecounting());
            }

            @Override
            public void onFailure(Call<List<Notecount>> call, Throwable t) {

            }
        });


        uploadfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotofragment();
            }
        });

        followbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowActivity.class);
                startActivity(intent);
            }
        });


        return rootview;
    }

    public void gotofragment(){

        Fragment fragment = new Uploadfragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
