package com.example.rtrk.restfulclient.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * Class handling communication with RESTful Server
 */
public class RetrofitClient {

    private static Retrofit mRetrofit = null;

    /***
     *
     * @param URL Base URL address of the RESTful Server
     * @return Class instance
     */
    public static Retrofit getRetrofit(String URL){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }
}
