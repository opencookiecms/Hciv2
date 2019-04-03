package com.hcidev.hciv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hcidev.hciv2.model.Users;
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginactivity extends AppCompatActivity {

    private static int SPLASH_TIME = 3000;
    SharedPreferences preferences;
    public static final String USERNAME = "USERNAME";

    Button login;
    Button register;

    EditText edtusername;
    EditText edtpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = (Button) findViewById(R.id.signInbut);
        register = (Button) findViewById(R.id.registerLoad);

        edtusername = (EditText) findViewById(R.id.usernameLogin);
        edtpassword = (EditText) findViewById(R.id.passLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLogin(username, password);
                    Toast.makeText(getApplicationContext(),"username"+username, Toast.LENGTH_LONG).show();


                    SharedPreferences preferenceses = getSharedPreferences("saveprofile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferenceses.edit();
                    editor.putString(USERNAME, String.valueOf(edtusername.getText()));
                    editor.apply();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loginactivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void doLogin(final String username, final String password){

        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);

        Call<Users> call = apiservice.Login(username, password);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.isSuccessful()){

                    Users users = response.body();



                    //Toast.makeText(getApplicationContext(),"Successful Login:" ,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Loginactivity.this, MainActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),"username or password wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }
}
