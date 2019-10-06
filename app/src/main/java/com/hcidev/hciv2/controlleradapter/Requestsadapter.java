package com.hcidev.hciv2.controlleradapter;

import android.net.Uri;
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
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Requestsadapter extends RecyclerView.Adapter<Requestsadapter.ProfileHolder> {

    ArrayList<Subscribefollow> requestadapater;

    public Requestsadapter(ArrayList<Subscribefollow> requestadapater){
        this.requestadapater= requestadapater;
    }

    @NonNull
    @Override
    public Requestsadapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_card,viewGroup,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(final Requestsadapter.ProfileHolder profileHolder, int position) {

        final Subscribefollow subscribeFollow = requestadapater.get(position);
        profileHolder.gettextname.setText(subscribeFollow.getUser_firstname());
        profileHolder.gettextunivers.setText(subscribeFollow.getUser_collage());

        final int ids = subscribeFollow.getHci_fid();
        profileHolder.textid.setText(""+ids);

        String picpath = subscribeFollow.getUser_pic();

        if(picpath == null) {
            profileHolder.imageView.setImageResource(R.drawable.ic_face_black_24dp);
        }
        else {
            Uri uri = Uri.parse("http://192.168.1.99/hcirestapi/assets/picture/"+picpath);
            Picasso.get().load(uri).into(profileHolder.imageView);
        }

        profileHolder.approveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);
                Call<Follows> call = apiservice.setApprove("approve",subscribeFollow.getHci_fid());
                call.enqueue(new Callback<Follows>() {
                    @Override
                    public void onResponse(Call<Follows> call, Response<Follows> response) {
                        if(response.isSuccessful()){
                            Follows follows = response.body();
                            Toast.makeText(v.getContext(),"Approve sucess",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(v.getContext(),"Fail to approve please try agains later" + response.code(),Toast.LENGTH_LONG).show();
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
        TextView textid;
        Button approveb;

        public ProfileHolder(View profileview){
            super(profileview);


            gettextname = profileview.findViewById(R.id.textName);
            gettextunivers = profileview.findViewById(R.id.textUniversiti);
            imageView = profileview.findViewById(R.id.imgTypes);
            approveb = profileview.findViewById(R.id.rButton);
            textid = profileview.findViewById(R.id.textids);


        }
    }


    @Override
    public int getItemCount() {
        return requestadapater.size();
    }
}
