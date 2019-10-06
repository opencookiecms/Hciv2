package com.hcidev.hciv2.controlleradapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcidev.hciv2.Friendsnotefragment;
import com.hcidev.hciv2.R;
import com.hcidev.hciv2.model.Note;
import com.hcidev.hciv2.model.Subscribefollow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class Followingsadapter extends RecyclerView.Adapter<Followingsadapter.ProfileHolder> {

    ArrayList<Subscribefollow> followsadapter;
    public static final String FUSERNAME = "FUSERNAME";

    public Followingsadapter(ArrayList<Subscribefollow> followsadapter){
        this.followsadapter= followsadapter;
    }

    @NonNull
    @Override
    public Followingsadapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ff_card,viewGroup,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(final Followingsadapter.ProfileHolder profileHolder, int position) {

        final Subscribefollow subscribeFollow = followsadapter.get(position);
        profileHolder.gettextname.setText(subscribeFollow.getUser_firstname());
        profileHolder.gettextunivers.setText(subscribeFollow.getUser_collage());

        String picpath = subscribeFollow.getUser_pic();



        if(picpath == null) {
            profileHolder.imageView.setImageResource(R.drawable.ic_face_black_24dp);
        }
        else {
            Uri uri = Uri.parse("http://192.168.1.99/hcirestapi/assets/picture/"+picpath);
            Picasso.get().load(uri).into(profileHolder.imageView);
        }


        profileHolder.buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("savefriends", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(FUSERNAME, subscribeFollow.getUser_username());
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new Friendsnotefragment();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentfollower_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });



    }

    public class ProfileHolder extends RecyclerView.ViewHolder{


        TextView gettextname;
        TextView gettextunivers;
        ImageView imageView;
        Button buttonview;

        public ProfileHolder(View profileview){
            super(profileview);


            gettextname = profileview.findViewById(R.id.textName);
            gettextunivers = profileview.findViewById(R.id.textUniversiti);
            imageView = profileview.findViewById(R.id.imgTypes);
            buttonview = profileview.findViewById(R.id.dButton);


        }
    }


    @Override
    public int getItemCount() {
        return followsadapter.size();
    }
}
