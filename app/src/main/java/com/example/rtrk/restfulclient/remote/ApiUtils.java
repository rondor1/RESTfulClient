package com.example.rtrk.restfulclient.remote;

/***
 * ApiUtils class makes provides 
 */
public class ApiUtils {

//    private final static String BASE_URL = "http://192.168.1.6/onebrain/";
    private final static String BASE_URL = "http://ip.jsontest.com";
    public static UserService getUserService(){
        return RetrofitClient.getRetrofit(BASE_URL).create(UserService.class);
    }

}
