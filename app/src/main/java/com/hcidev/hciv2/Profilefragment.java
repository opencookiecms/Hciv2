package com.hcidev.hciv2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcidev.hciv2.controlleradapter.Followsadapter;
import com.hcidev.hciv2.fielpath.Filepath;
import com.hcidev.hciv2.model.Note;
import com.hcidev.hciv2.model.Subscribefollow;
import com.hcidev.hciv2.model.Users;
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_OK;


public class Profilefragment extends Fragment {
    private int PICK_FILE_REQUEST = 1;
    private static final int STORAGE_PERMISSION = 123;

    EditText edtname;
    EditText edtemail;
    EditText edtstatus;
    String mediapath;

    ImageView imageprofile;

    Button buttonsave;
    Button buttonlogout;
    Button buttobrowser;

    TextView usertextviews;
    TextView statusuploadtxt;

    public static final String USERNAME = "USERNAME";
    SharedPreferences pref;

    public Profilefragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview  = inflater.inflate(R.layout.myprofile_fragment, container, false);

        edtname = (EditText) rootview.findViewById(R.id.editName);
        edtemail = (EditText) rootview.findViewById(R.id.editEmail);
        edtstatus = (EditText) rootview.findViewById(R.id.editStatus);

        imageprofile = (ImageView) rootview.findViewById(R.id.imageprofile);

        buttonsave = (Button) rootview.findViewById(R.id.saveprofilebutton);
        buttonlogout = (Button) rootview.findViewById(R.id.logoutbutton);
        buttobrowser = (Button) rootview.findViewById(R.id.browsepic);

        usertextviews = (TextView) rootview.findViewById(R.id.txtusrname);
        statusuploadtxt = (TextView) rootview.findViewById(R.id.picstatus);

        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);

        requestStoragePermission();


        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = getpref.edit();
        pref = getActivity().getApplication().getSharedPreferences("saveprofile", Context.MODE_PRIVATE);


        String username = pref.getString(USERNAME,"");


        buttobrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browserimage();
            }
        });

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediapath == null) {
                    Toast.makeText(getContext(), "Please select file first ", Toast.LENGTH_LONG).show();
                } else {
                    uploadnow();
                }
            }
        });

        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logouttimes();
            }
        });


        Call<List<Users>> call = apiservice.getProfile(username);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                edtname.setText(response.body().get(0).getUser_firstname());
                edtemail.setText(response.body().get(0).getUser_email());
                edtstatus.setText(response.body().get(0).getUser_caption());

                usertextviews.setText(response.body().get(0).getUser_username());
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });


        return rootview;
    }

    public void browserimage(){
        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_FILE_REQUEST);
    }

    public void uploadnow(){
        //        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
//        SharedPreferences.Editor edit = getpref.edit();
//        pref = getActivity().getApplication().getSharedPreferences("saveprofile", Context.MODE_PRIVATE);
//        String username = pref.getString(USERNAME,"");

        File user_pic = new File(mediapath);
        Toast.makeText(getContext(), "test" + user_pic, Toast.LENGTH_LONG).show();
        String user_firtname = edtname.getText().toString();
        String user_email = edtemail.getText().toString();
        String user_caption = edtstatus.getText().toString();

        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = getpref.edit();
        pref = getActivity().getApplication().getSharedPreferences("saveprofile",Context.MODE_PRIVATE);


        String username = pref.getString(USERNAME,"");



        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), user_pic);
        MultipartBody.Part body = MultipartBody.Part.createFormData("user_pic", user_pic.getName(), requestBody);
        RequestBody firstname= RequestBody.create(MediaType.parse("userfirstname"), user_firtname);
        RequestBody email = RequestBody.create(MediaType.parse("useremail"), user_email);
        RequestBody caption = RequestBody.create(MediaType.parse("usercaption"), user_caption);
        Call<Users> call2 = apiservice.updateProfile(body, firstname, email, caption,username );
        call2.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {

                    Users users = response.body();
                    Toast.makeText(getActivity(), "File successful uploaded", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "upload or the files is to big, please find new picture to upload", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri fileuri = data.getData();
            mediapath = Filepath.getRealPath(getContext(), fileuri);

            statusuploadtxt.setText(mediapath);

        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void logouttimes(){
        SharedPreferences preferenceses = getActivity().getSharedPreferences("saveprofile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferenceses.edit();
        editor.putString(USERNAME, "");
        editor.apply();

        Intent intent = new Intent(getContext(), Loginactivity.class);
        startActivity(intent);
    }
}
