package com.example.rtrk.restfulclient.remote;

/***
 * ApiUtils class provides handle to the RetrofitClient object
 */
public class ApiUtils {

    private final static String BASE_URL = "http://192.168.100.99:8084/";
    public static UserService getUserService(){
        return RetrofitClient.getRetrofit(BASE_URL).create(UserService.class);
    }

}
