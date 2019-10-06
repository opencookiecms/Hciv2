package com.hcidev.hciv2.controlleradapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcidev.hciv2.R;
import com.hcidev.hciv2.model.Follows;
import com.hcidev.hciv2.model.Subscribefollow;
import com.hcidev.hciv2.model.Users;
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Getallsadapter extends RecyclerView.Adapter<Getallsadapter.ProfileHolder> {

    ArrayList<Users> getalladapter;

    public static final String USERNAME = "USERNAME";
    SharedPreferences pref;

    public Getallsadapter(ArrayList<Users> getalladapter){
        this.getalladapter= getalladapter;
    }

    @NonNull
    @Override
    public Getallsadapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.find_user,viewGroup,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(final Getallsadapter.ProfileHolder profileHolder, int position) {

        final Users users = getalladapter.get(position);
        profileHolder.gettextname.setText(users.getUser_firstname());
        profileHolder.gettextunivers.setText(users.getUser_collage());
        String picpath = users.getUser_pic();



        if(picpath == null) {
            profileHolder.imageView.setImageResource(R.drawable.ic_face_black_24dp);
        }
        else {
            Uri uri = Uri.parse("http://192.168.1.99/hcirestapi/assets/picture/"+picpath);
            Picasso.get().load(uri).into(profileHolder.imageView);
        }

        profileHolder.followbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor edit = getpref.edit();
                pref = v.getContext().getApplicationContext().getSharedPreferences("saveprofile", Context.MODE_PRIVATE);


                String username = pref.getString(USERNAME,"");
                Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);
                Call<Follows> call = apiservice.setFollow(username,users.getUser_username(),"request");
                call.enqueue(new Callback<Follows>() {
                    @Override
                    public void onResponse(Call<Follows> call, Response<Follows> response) {
                        if(response.isSuccessful()){
                            Follows follows = response.body();
                            Toast.makeText(v.getContext(),"Follow success",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(v.getContext(),"Fail error code " + response.code(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Follows> call, Throwable t) {

                    }
                });
            }
        });


    }

    public class ProfileHolder extends RecyclerView.ViewHolder{


        TextView gettextname;
        TextView gettextunivers;
        ImageView imageView;
        Button followbutton;

        public ProfileHolder(View profileview){
            super(profileview);


            gettextname = profileview.findViewById(R.id.textName);
            gettextunivers = profileview.findViewById(R.id.textUniversiti);
            imageView = profileview.findViewById(R.id.imgTypes);
            followbutton = profileview.findViewById(R.id.fButton);


        }
    }


    @Override
    public int getItemCount() {
        return getalladapter.size();
    }
}
