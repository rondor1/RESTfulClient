package com.example.rtrk.restfulclient.remote;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/***
 * Interface for usage of HTTP API
 */
public interface UserService{

    @GET("login/{username}/{password}")
    Call<ResponseBody> login(@Path("username") String username,
                             @Path("password") String password);

    @GET("onebrain/{type}/{layout}/{resolution}/{port}/")
    Call<ResponseBody> sendRequest(@Path("type") String type,
                                            @Path("layout") String layout,
                                            @Path("resolution") String resolution,
                                            @Path("port") String port);

    @GET("onebrain/{type}")
    Call<ResponseBody> periodicDiagnosticsRequest(@Path("type") String type);
}
