package com.hcidev.hciv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaDataSource;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    EditText regusername;
    EditText regemail;
    EditText regpassword;
    EditText regname;
    Button buttonsignup;

    public static final String USERNAME = "USERNAME";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        regusername = (EditText) findViewById(R.id.editName);
        regemail = (EditText) findViewById(R.id.editEmail);
        regpassword = (EditText)findViewById(R.id.editPassword);
        regname = (EditText) findViewById(R.id.editnames);

        buttonsignup = (Button) findViewById(R.id.buttonReg);



        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);

                apiservice.regUsers(
                        regusername.getText().toString(),
                        regemail.getText().toString(),
                        regpassword.getText().toString(),
                        regname.getText().toString(),"default.png")
                        .enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {
                                if(response.isSuccessful()){
                                    Users users = response.body();
                                    Toast.makeText(getApplicationContext(),"Your accout was susccessful create :" + response.code(),Toast.LENGTH_LONG).show();


                                    SharedPreferences preferenceses = getSharedPreferences("saveprofile", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferenceses.edit();
                                    editor.putString(USERNAME, String.valueOf(regusername.getText()));
                                    editor.apply();

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Fail to sign up please check your network" + response.code(),Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {

                            }
                        });
            }
        });


    }
}
