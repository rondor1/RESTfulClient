package com.example.rtrk.restfulclient.remote;

import com.example.rtrk.restfulclient.model.RESTfulObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/***
 * Interface for usage of HTTP API
 */
public interface UserService {

    @GET("login/{username}/{password}")
    Call<RESTfulObject> login(@Path("username") String username,
                              @Path("password") String password);

}
