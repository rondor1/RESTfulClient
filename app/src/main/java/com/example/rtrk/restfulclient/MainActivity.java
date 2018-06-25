package com.example.rtrk.restfulclient;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rtrk.restfulclient.remote.ApiUtils;
import com.example.rtrk.restfulclient.remote.UserService;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mImageView;   //<! Top image on login screen
    EditText mUsernameText; //<! Username edit box
    EditText mPasswordText; //<! Password edit box
    Button mLoginButton;    //<! Login button on the bottom of the page
    WifiManager mWifiManager;   //<! Monitors if device is connected
    ResourcesCompat mResourcesCompat;   //<! Image resources
    Intent mRESTfulCommIntent;  //<! Used for transition to restful communication
    JSONObject mJSONObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJSONObject = null;

        mLoginButton = (Button) this.findViewById(R.id.loginButton);
        mImageView = (ImageView) this.findViewById(R.id.imageView);
        mUsernameText = (EditText) this.findViewById(R.id.usernameBox);
        mPasswordText = (EditText) this.findViewById(R.id.passwordBox);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mRESTfulCommIntent = new Intent(this, RESTfulCommunication.class);

        mImageView.setImageDrawable(mResourcesCompat.getDrawable(getResources(),
                R.drawable.splash_screen, null));
        mLoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case  R.id.loginButton :
                /*Check if WiFi is enabled*/
                if(mWifiManager.isWifiEnabled()){
                    /*Check if device is connected to WiFi network*/
                    if(mWifiManager.getConnectionInfo().getNetworkId() != -1){
                        if(validateLoginData(mUsernameText.getText().toString(),
                                mPasswordText.getText().toString()) == true){
                            serverLogin(mUsernameText.getText().toString(),
                                    mPasswordText.getText().toString());
                        }
                    }
                }
                break;
        }
    }

    /***
     * @func validateLoginData checks if users credentials are given.
     * @param username Part of the users credential communicating with RESTful Server
     * @param password Part of the users credential communicating with RESTful Server
     * @return true if credentials given, else false
     */
    private boolean validateLoginData(String username, String password) {
        boolean returnValue = true;
        if(username == null || username.trim().length() == 0){
            returnValue = false;
            Toast.makeText(this, getApplicationContext().getResources()
                    .getText(R.string.usernameMissing), Toast.LENGTH_SHORT).show();
        }
        else if(password == null || password.trim().length() == 0 ){
            returnValue = false;
            Toast.makeText(this, getApplicationContext().getResources().
                    getText(R.string.passwordMissing), Toast.LENGTH_SHORT).show();
        }

        return returnValue;
    }

    /***
     * @func serverLogin Tries to connect to server based on given user credentials
     * @param username Part of the users credential communicating with RESTful Server
     * @param password Part of the users credential communicating with RESTful Server
     */
    private void serverLogin(String username, String password){

        Call<ResponseBody> mCall = ApiUtils.getUserService().login(username, password);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        try{
                            mJSONObject = new JSONObject(response.body().string());
                            if(mJSONObject.getString("ServerResponse").equals("Access Permitted")){
                                startActivity(mRESTfulCommIntent);
                            }
                        }
                        catch (Exception Exception){
                            Toast.makeText(MainActivity.this, getApplicationContext().
                                    getString(R.string.wrongCredentials), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "THROWABLE : "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUsernameText.clearComposingText();
        mPasswordText.clearComposingText();
    }
}