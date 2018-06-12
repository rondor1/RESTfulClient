package com.example.rtrk.restfulclient.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/***
 * Interface for usage of HTTP API
 */
public interface UserService{

    @GET("login/{username}/{password}")
    Call<ResponseBody> login(@Path("username") String username,
                             @Path("password") String password);

    @GET("onebrain/{type}/{data}")
    Call<ResponseBody> acquireData(@Path("type") String type,
                               @Path("data") String data);
}
