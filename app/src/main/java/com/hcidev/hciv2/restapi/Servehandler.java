package com.hcidev.hciv2.restapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servehandler {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://172.20.10.4/hcirestapi/api/Apicontroller/";


    public static Retrofit getRetrofit() {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
